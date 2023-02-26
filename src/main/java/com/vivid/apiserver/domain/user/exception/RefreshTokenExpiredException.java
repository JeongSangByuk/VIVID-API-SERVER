package com.vivid.apiserver.domain.user.exception;

import com.vivid.apiserver.global.error.exception.AccessDeniedException;
import com.vivid.apiserver.global.error.exception.ErrorCode;

public class RefreshTokenExpiredException extends AccessDeniedException {

    public RefreshTokenExpiredException() {
        super(ErrorCode.REFRESH_TOKEN_NOT_FOUND);
    }
}
