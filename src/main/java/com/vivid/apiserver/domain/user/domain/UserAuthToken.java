package com.vivid.apiserver.domain.user.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;


@Getter
@RedisHash("user_auth_token")
public class UserAuthToken {

    @Id
    private String email;
    private String accessToken;
    private String refreshToken;

    @Builder
    public UserAuthToken(String email, String accessToken, String refreshToken) {
        this.email = email;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
