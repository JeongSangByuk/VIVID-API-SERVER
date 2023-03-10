package com.vivid.apiserver.domain.user.dto.response;

import com.vivid.apiserver.domain.user.domain.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserGetResponse {

    private String email;

    private String name;

    private String picture;

    public static UserGetResponse from(User user) {
        return UserGetResponse.builder()
                .email(user.getEmail())
                .name(user.getName())
                .picture(user.getPicture())
                .build();
    }
}
