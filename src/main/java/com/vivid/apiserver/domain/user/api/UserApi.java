package com.vivid.apiserver.domain.user.api;

import com.vivid.apiserver.domain.user.application.UserService;
import com.vivid.apiserver.global.success.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserApi {

    private final UserService userService;

    @Operation(summary = "user delete api", description = "user가 회원 탈퇴할 때 사용되는 api입니다.")
    @DeleteMapping("/api/user")
    public ResponseEntity<SuccessResponse<String>> delete(HttpServletRequest request, HttpServletResponse response) {

        userService.delete(request, response);

        return SuccessResponse.OK;
    }


}
