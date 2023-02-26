package com.vivid.apiserver.domain.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class UserNewTokenDto {

    @NotBlank
    private String accessToken;

    @Builder
    public UserNewTokenDto(String accessToken) {
        this.accessToken = accessToken;
    }
}
