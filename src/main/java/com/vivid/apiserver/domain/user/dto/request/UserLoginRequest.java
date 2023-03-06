package com.vivid.apiserver.domain.user.dto.request;

import com.vivid.apiserver.domain.user.domain.Role;
import com.vivid.apiserver.domain.user.domain.User;
import java.util.Map;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserLoginRequest {

    @NotBlank
    @Email(message = "이메일을 양식을 지켜주세요.")
    private String email;

    @NotBlank
    private String name;

    private String picture;

    public static UserLoginRequest of(Map<String, Object> attributes) {
        return UserLoginRequest.builder()
                .email((String) attributes.get("email"))
                .name((String) attributes.get("name"))
                .picture((String) attributes.get("picture"))
                .build();
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
