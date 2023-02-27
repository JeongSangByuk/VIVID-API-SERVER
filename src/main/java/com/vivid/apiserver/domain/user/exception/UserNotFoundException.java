package com.vivid.apiserver.domain.user.exception;

import com.vivid.apiserver.global.error.exception.NotFoundException;
import com.vivid.apiserver.global.error.exception.ErrorCode;

public class UserNotFoundException extends NotFoundException {

    public UserNotFoundException() {
        super(ErrorCode.USER_NOT_FOUND);
    }
}
