package com.example.mosh_be.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateRequest {
    private String nickname;
    private String sex;
    private String imageUrl;
}
