package com.vivid.apiserver.domain.user.dto;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserNewTokenResponse {

    @NotBlank
    private String accessToken;
}
