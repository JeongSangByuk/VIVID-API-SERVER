package com.vivid.apiserver.domain.user.dto;

import com.vivid.apiserver.domain.user.domain.User;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserSignUpResponse {

    private String email;
    private String name;


    @Builder
    public UserSignUpResponse(User user){
        this.email = user.getEmail();
        this.name = user.getName();
    }
}
