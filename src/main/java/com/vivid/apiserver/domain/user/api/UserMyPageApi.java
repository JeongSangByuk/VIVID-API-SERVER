package com.vivid.apiserver.domain.user.api;

import com.vivid.apiserver.domain.user.application.UserMyPageService;
import com.vivid.apiserver.domain.user.dto.response.UserMyPageDashboardDataGetResponse;
import com.vivid.apiserver.global.success.SuccessCode;
import com.vivid.apiserver.global.success.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserMyPageApi {

    private final UserMyPageService userMyPageService;

    @Operation(summary = "my page user dashboard data get api", description = "my page의 user dashboard data를 get하는 api입니다.")
    @GetMapping("/api/my-page/dashboard")
    public ResponseEntity<SuccessResponse<UserMyPageDashboardDataGetResponse>> getUserMyPageDashboardData() {

        return SuccessResponse.success(SuccessCode.OK_SUCCESS, userMyPageService.getMyPageDashboardData());

    }
}
