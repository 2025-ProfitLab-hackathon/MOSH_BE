package com.example.mosh_be.domain.entity;

import com.example.mosh_be.domain.enums.SocialProvider;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "social_login",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_social_user_provider", columnNames = {"user_id", "provider"}),
        @UniqueConstraint(name = "uk_social_provider_uid", columnNames = {"provider", "provider_uid"})
    },
    indexes = {
        @Index(name = "idx_social_user", columnList = "user_id")
    }
)
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SocialLogin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "social_login_id")
    private Long socialLoginId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private SocialProvider provider;

    @Column(name = "provider_uid", nullable = false, length = 191)
    private String providerUid;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
