package com.example.mosh_be.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "user", indexes = {
    @Index(name = "idx_user_phone", columnList = "phone_number")
})
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private LocalDate birthday;

    @Column(name = "phone_number", nullable = false, length = 20)
    private String phoneNumber;

    @Column(length = 20)
    private String sex;

    @Column(nullable = false)
    @Builder.Default
    private Integer reward = 0;

    @Column(name = "image_url", length = 2048)
    private String imageUrl;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public void updateProfile(String nickname, String sex, String imageUrl) {
        if (nickname != null) {
            this.nickname = nickname;
        }
        if (sex != null) {
            this.sex = sex;
        }
        if (imageUrl != null) {
            this.imageUrl = imageUrl;
        }
    }

    public void addReward(int amount) {
        this.reward += amount;
    }

    public void useReward(int amount) {
        if (this.reward < amount) {
            throw new IllegalArgumentException("Insufficient reward balance");
        }
        this.reward -= amount;
    }
}
