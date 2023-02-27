package com.vivid.apiserver.domain.user.api;

import com.vivid.apiserver.domain.user.application.UserLoginService;
import com.vivid.apiserver.domain.user.application.UserService;
import com.vivid.apiserver.domain.user.dto.UserNewTokenDto;
import com.vivid.apiserver.global.success.SuccessCode;
import com.vivid.apiserver.global.success.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserApi {

    private final UserLoginService userLoginService;

    private final UserService userService;

    // access token 만료시 refresh token을 통해 재발급
    @Operation(summary = "user access token issue api", description = "쿠키의 access token get하거나, redis의 refresh token을 활용하여 access token을 재발급합니다.")
    @PostMapping("/api/auth/token")
    public ResponseEntity<SuccessResponse<UserNewTokenDto>> reIssueAccessToken() {

        return SuccessResponse.success(SuccessCode.OK_SUCCESS, userLoginService.reIssueAccessToken());
    }

    @Operation(summary = "user logout api", description = "user가 logout 할 시 호출하는 api입니다. redis의 refresh token을 삭제합니다.")
    @PostMapping("/api/auth/logout")
    public ResponseEntity<SuccessResponse<String>> logout() {

        userLoginService.removeTokensByLogout();

        return SuccessResponse.OK;
    }

    // test user로 로그인합니다. 이때 access token을 발급하며, session에 리프래쉬 토큰이 저장됩니다.
    @Operation(summary = "test user login api", description = "test용 계정으로 login 할 수 있는 api입니다.")
    @GetMapping("/api/auth/token/test")
    public ResponseEntity<SuccessResponse<List<UserNewTokenDto>>> loginByTestUser() {

        return SuccessResponse.success(SuccessCode.OK_SUCCESS, userLoginService.loginByTestUser());

    }

    //    @Operation(summary = "user silent access token issue api", description = "redis의 refresh token을 활용하여 access token을 재발급합니다. access token 만료시 silent refesh 하는 api입니다.")
//    @GetMapping("/auth/token/silent-refresh")
//    public UserNewTokenDto issueNewAccessTokenFromSilentRefresh(){
//
//        UserNewTokenDto newAccessToken = userLoginService.getNewAccessTokenFromSilentRefresh();
//
//        return newAccessToken;
//    }


}
