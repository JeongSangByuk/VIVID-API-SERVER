package com.vivid.apiserver.domain.user.api;

import com.vivid.apiserver.domain.user.application.UserMyPageService;
import com.vivid.apiserver.domain.user.dto.UserMyPageDashboardDataGetResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserMyPageApi {


    private final UserMyPageService userMyPageService;


    @Operation(summary = "my page user dashboard data get api", description = "my page의 user dashboard data를 get하는 api입니다.")
    @GetMapping("/api/my-page/dashboard")
    public UserMyPageDashboardDataGetResponse getUserMyPageDashboardData() {

        UserMyPageDashboardDataGetResponse myPageDashboardData = userMyPageService.getMyPageDashboardData();

        return myPageDashboardData;

    }

    @Operation(summary = "user delete api", description = "user가 회원 탈퇴할 때 사용되는 api입니다.")
    @DeleteMapping("/api/user")
    public void delete(){

        // delete user
        userMyPageService.deleteUser();
    }


}
