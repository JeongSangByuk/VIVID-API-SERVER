package com.vivid.apiserver.domain.user.service;

import com.vivid.apiserver.domain.user.domain.User;
import com.vivid.apiserver.domain.user.service.query.UserQueryService;
import com.vivid.apiserver.global.auth.util.TokenUtil;
import com.vivid.apiserver.global.error.exception.AccessDeniedException;
import com.vivid.apiserver.global.error.exception.ErrorCode;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class CurrentUserService {

    private final UserQueryService userQueryService;

    /**
     * 현재 로그인 돼 있는 유저 엔티티 get 메소드
     */
    public User getCurrentUser() {

        String email = TokenUtil.getCurrentUserEmail();
        return userQueryService.findByEmail(email);
    }

    /*
     * 현재 로그인 돼 있는 유저 엔티티와 접근된 email을 비교하여 권한 체크 메소드
     */
    public void checkValidUserAccess(String accessedEmail) {

        User user = getCurrentUser();

        if (!accessedEmail.equals(user.getEmail())) {
            throw new AccessDeniedException(ErrorCode.USER_ACCESS_DENIED);
        }
    }

    public void checkValidUserAccess(String currentUserEmail, String accessedEmail) {

        if (!accessedEmail.equals(currentUserEmail)) {
            throw new AccessDeniedException(ErrorCode.USER_ACCESS_DENIED);
        }
    }

    /**
     * 현재 로그인 돼 있는 유저의 webex access token get 메소드
     */
    public String getWebexAccessToken() {

        User user = getCurrentUser();

        return Optional.ofNullable(user.getInstitution().getWebexAccessToken())
                .orElseThrow(() -> new AccessDeniedException(ErrorCode.WEBEX_ACCESS_TOKEN_NOT_FOUND_IN_HEADER));
    }
}
