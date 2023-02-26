package com.vivid.apiserver.domain.user.dto;

import com.vivid.apiserver.domain.user.domain.Role;
import com.vivid.apiserver.domain.user.domain.User;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class UserLoginRequest{

    @NotBlank
    @Email(message = "이메일을 양식을 지켜주세요.")
    private String email;

    @NotBlank
    private String name;

    private String picture;

    @Builder
    public UserLoginRequest(String email, String name,String picture) {
        this.email = email;
        this.name = name;
        this.picture = picture;
    }

    public User toEntity() {
        return User.builder()
                .email(email)
                .name(name)
                .picture(picture)
                .role(Role.USER)
                .build();
    }

}
