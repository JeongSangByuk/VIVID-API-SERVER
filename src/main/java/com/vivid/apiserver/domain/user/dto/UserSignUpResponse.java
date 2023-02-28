package com.vivid.apiserver.domain.user.dto;

import com.vivid.apiserver.domain.user.domain.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class UserSignUpResponse {

    private String email;
    private String name;

    public static UserSignUpResponse from(User user) {
        return UserSignUpResponse.builder()
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }
}
