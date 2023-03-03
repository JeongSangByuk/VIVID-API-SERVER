package com.vivid.apiserver.domain.user.application;

import com.vivid.apiserver.domain.user.application.query.UserQueryService;
import com.vivid.apiserver.domain.user.domain.User;
import com.vivid.apiserver.domain.user.exception.AccessTokenNotFoundException;
import com.vivid.apiserver.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserQueryService userQueryService;

    private final CurrentUserService currentUserService;

    public String getWebexAccessToken() {

        User user = currentUserService.getCurrentUser();

        // access token get
        String webexAccessToken = user.getInstitution().getWebexAccessToken();

        // access token not found exception
        if (!StringUtils.hasText(webexAccessToken)) {
            throw new AccessTokenNotFoundException(ErrorCode.ACCESS_TOKEN_NOT_FOUND_IN_HEADER);
        }

        return webexAccessToken;
    }
}
