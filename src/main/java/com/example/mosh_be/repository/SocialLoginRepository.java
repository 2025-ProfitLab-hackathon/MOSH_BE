package com.example.mosh_be.repository;

import com.example.mosh_be.domain.entity.SocialLogin;
import com.example.mosh_be.domain.enums.SocialProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SocialLoginRepository extends JpaRepository<SocialLogin, Long> {
    Optional<SocialLogin> findByProviderAndProviderUid(SocialProvider provider, String providerUid);
    Optional<SocialLogin> findByUserIdAndProvider(Long userId, SocialProvider provider);
}
