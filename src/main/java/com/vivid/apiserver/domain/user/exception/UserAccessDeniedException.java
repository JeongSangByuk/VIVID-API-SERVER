package com.vivid.apiserver.domain.user.exception;

import com.vivid.apiserver.global.error.exception.AccessDeniedException;
import com.vivid.apiserver.global.error.exception.ErrorCode;
import lombok.Getter;

@Getter
public class UserAccessDeniedException extends AccessDeniedException {

    public UserAccessDeniedException() {
        super(ErrorCode.USER_ACCESS_DENIED);
    }
}
