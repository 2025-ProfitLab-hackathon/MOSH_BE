package com.example.mosh_be.util;

import com.example.mosh_be.domain.entity.User;
import com.example.mosh_be.dto.user.UserResponse;

public class UserMapper {

    public static UserResponse toResponse(User user) {
        return UserResponse.builder()
                .userId(user.getUserId())
                .nickname(user.getNickname())
                .birthday(user.getBirthday())
                .phoneNumber(user.getPhoneNumber())
                .sex(user.getSex())
                .reward(user.getReward())
                .imageUrl(user.getImageUrl())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
