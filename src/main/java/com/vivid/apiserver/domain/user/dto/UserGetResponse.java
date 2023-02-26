package com.vivid.apiserver.domain.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserGetResponse {

    private String email;

    private String name;

    private String picture;

    @Builder
    public UserGetResponse(String email, String name, String picture) {
        this.email = email;
        this.name = name;
        this.picture = picture;
    }

}
