package com.vivid.apiserver.domain.user.exception;

import com.vivid.apiserver.global.error.exception.AccessDeniedException;
import com.vivid.apiserver.global.error.exception.ErrorCode;

public class AccessTokenInvalidException extends AccessDeniedException {

    public AccessTokenInvalidException() {
        super(ErrorCode.ACCESS_TOKEN_INVALID);
    }
}
