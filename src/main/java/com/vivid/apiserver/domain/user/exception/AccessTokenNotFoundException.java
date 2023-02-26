package com.vivid.apiserver.domain.user.exception;

import com.vivid.apiserver.global.error.exception.AccessDeniedException;
import com.vivid.apiserver.global.error.exception.ErrorCode;

public class AccessTokenNotFoundException extends AccessDeniedException {

    public AccessTokenNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
