package com.example.mosh_be.service;

import com.example.mosh_be.domain.entity.SocialLogin;
import com.example.mosh_be.domain.entity.User;
import com.example.mosh_be.domain.enums.SocialProvider;
import com.example.mosh_be.dto.auth.*;
import com.example.mosh_be.dto.user.UserResponse;
import com.example.mosh_be.exception.BadRequestException;
import com.example.mosh_be.exception.ResourceNotFoundException;
import com.example.mosh_be.repository.SocialLoginRepository;
import com.example.mosh_be.repository.UserRepository;
import com.example.mosh_be.security.JwtUtil;
import com.example.mosh_be.util.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final SocialLoginRepository socialLoginRepository;
    private final RedisTemplate<String, Object> redisTemplate;
    private final JwtUtil jwtUtil;

    private static final int VERIFICATION_CODE_EXPIRY = 180; // 3 minutes

    @Transactional
    public AuthTokenResponse socialLogin(SocialProvider provider, SocialLoginRequest request) {
        // Check if social login exists
        SocialLogin socialLogin = socialLoginRepository
                .findByProviderAndProviderUid(provider, request.getProviderUid())
                .orElse(null);

        User user;
        HttpStatus status = HttpStatus.OK;

        if (socialLogin == null) {
            // Create new user
            user = User.builder()
                    .nickname(request.getNickname())
                    .phoneNumber(request.getPhoneNumber())
                    .birthday(request.getBirthday())
                    .sex(request.getSex())
                    .imageUrl(request.getImageUrl())
                    .reward(0)
                    .build();
            user = userRepository.save(user);

            // Create social login
            socialLogin = SocialLogin.builder()
                    .userId(user.getUserId())
                    .provider(provider)
                    .providerUid(request.getProviderUid())
                    .build();
            socialLoginRepository.save(socialLogin);

            status = HttpStatus.CREATED;
        } else {
            user = userRepository.findById(socialLogin.getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        }

        String token = jwtUtil.generateToken(user.getUserId());
        UserResponse userResponse = UserMapper.toResponse(user);

        return AuthTokenResponse.builder()
                .tokenType("Bearer")
                .accessToken(token)
                .expiresInSeconds((int) jwtUtil.getExpirationInSeconds())
                .user(userResponse)
                .build();
    }

    public PhoneVerificationSendResponse sendVerificationCode(PhoneVerificationSendRequest request) {
        String verificationId = "pv_" + UUID.randomUUID().toString().replace("-", "").substring(0, 16);
        String code = String.format("%06d", new Random().nextInt(1000000));

        // Store in Redis
        redisTemplate.opsForValue().set(
                "phone:verification:" + verificationId,
                code,
                VERIFICATION_CODE_EXPIRY,
                TimeUnit.SECONDS
        );

        // Also store phone number for later use
        redisTemplate.opsForValue().set(
                "phone:verification:" + verificationId + ":phone",
                request.getPhoneNumber(),
                VERIFICATION_CODE_EXPIRY,
                TimeUnit.SECONDS
        );

        return PhoneVerificationSendResponse.builder()
                .verificationId(verificationId)
                .expiresInSeconds(VERIFICATION_CODE_EXPIRY)
                .build();
    }

    public PhoneVerificationConfirmResponse confirmVerificationCode(PhoneVerificationConfirmRequest request) {
        String storedCode = (String) redisTemplate.opsForValue().get("phone:verification:" + request.getVerificationId());

        if (storedCode == null) {
            throw new ResourceNotFoundException("Verification ID not found or expired");
        }

        if (!storedCode.equals(request.getCode())) {
            throw new BadRequestException("Invalid verification code");
        }

        // Generate phone verification token
        String phoneVerificationToken = "pvt_" + UUID.randomUUID().toString().replace("-", "").substring(0, 16);

        // Get phone number
        String phoneNumber = (String) redisTemplate.opsForValue().get("phone:verification:" + request.getVerificationId() + ":phone");

        // Store phone verification token
        redisTemplate.opsForValue().set(
                "phone:token:" + phoneVerificationToken,
                phoneNumber,
                600, // 10 minutes
                TimeUnit.SECONDS
        );

        // Delete verification code
        redisTemplate.delete("phone:verification:" + request.getVerificationId());
        redisTemplate.delete("phone:verification:" + request.getVerificationId() + ":phone");

        return PhoneVerificationConfirmResponse.builder()
                .phoneVerificationToken(phoneVerificationToken)
                .build();
    }

    @Transactional
    public AuthTokenResponse phoneLogin(PhoneLoginRequest request) {
        String phoneNumber = (String) redisTemplate.opsForValue().get("phone:token:" + request.getPhoneVerificationToken());

        if (phoneNumber == null) {
            throw new BadRequestException("Invalid or expired phone verification token");
        }

        User user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseGet(() -> {
                    User newUser = User.builder()
                            .nickname(request.getNickname())
                            .phoneNumber(phoneNumber)
                            .birthday(request.getBirthday())
                            .sex(request.getSex())
                            .imageUrl(request.getImageUrl())
                            .reward(0)
                            .build();
                    return userRepository.save(newUser);
                });

        // Delete token
        redisTemplate.delete("phone:token:" + request.getPhoneVerificationToken());

        String token = jwtUtil.generateToken(user.getUserId());
        UserResponse userResponse = UserMapper.toResponse(user);

        return AuthTokenResponse.builder()
                .tokenType("Bearer")
                .accessToken(token)
                .expiresInSeconds((int) jwtUtil.getExpirationInSeconds())
                .user(userResponse)
                .build();
    }
}
