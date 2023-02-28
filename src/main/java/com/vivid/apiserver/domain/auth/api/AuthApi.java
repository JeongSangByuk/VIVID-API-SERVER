package com.vivid.apiserver.domain.auth.api;

import com.vivid.apiserver.domain.auth.application.AuthService;
import com.vivid.apiserver.domain.auth.dto.response.AccessTokenResponse;
import com.vivid.apiserver.global.success.SuccessCode;
import com.vivid.apiserver.global.success.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthApi {

    private final AuthService authService;

    // access token 만료시 refresh token을 통해 재발급
    @Operation(summary = "user access token issue api", description = "쿠키의 access token get하거나, redis의 refresh token을 활용하여 access token을 재발급합니다.")
    @PostMapping("/api/auth/token")
    public ResponseEntity<SuccessResponse<AccessTokenResponse>> reIssueAccessToken(HttpServletRequest request,
            HttpServletResponse response) {

        return SuccessResponse.success(SuccessCode.OK_SUCCESS, authService.getAccessToken(request, response));
    }

    @Operation(summary = "user logout api", description = "user가 logout 할 시 호출하는 api입니다. redis의 refresh token을 삭제합니다.")
    @PostMapping("/api/auth/logout")
    public ResponseEntity<SuccessResponse<String>> logout(HttpServletRequest request,
            HttpServletResponse response) {

        authService.removeTokensByLogout(request, response);

        return SuccessResponse.OK;
    }
}
