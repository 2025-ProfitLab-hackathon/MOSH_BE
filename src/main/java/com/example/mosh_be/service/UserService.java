package com.example.mosh_be.service;

import com.example.mosh_be.domain.entity.User;
import com.example.mosh_be.dto.user.UserResponse;
import com.example.mosh_be.dto.user.UserUpdateRequest;
import com.example.mosh_be.exception.ResourceNotFoundException;
import com.example.mosh_be.repository.UserRepository;
import com.example.mosh_be.util.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponse getMyProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return UserMapper.toResponse(user);
    }

    @Transactional
    public UserResponse updateMyProfile(Long userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        user.updateProfile(request.getNickname(), request.getSex(), request.getImageUrl());
        return UserMapper.toResponse(user);
    }
}
