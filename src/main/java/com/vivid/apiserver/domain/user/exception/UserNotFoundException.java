package com.vivid.apiserver.domain.user.exception;

import com.vivid.apiserver.global.error.exception.EntityNotFoundException;
import com.vivid.apiserver.global.error.exception.ErrorCode;

public class UserNotFoundException extends EntityNotFoundException {

    public UserNotFoundException() {
        super(ErrorCode.USER_NOT_FOUND);
    }
}
