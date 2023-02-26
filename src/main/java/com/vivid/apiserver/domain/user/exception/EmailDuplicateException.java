package com.vivid.apiserver.domain.user.exception;

import com.vivid.apiserver.global.error.exception.ErrorCode;
import com.vivid.apiserver.global.error.exception.InvalidValueException;
import lombok.Getter;

@Getter
public class EmailDuplicateException extends InvalidValueException {

    public EmailDuplicateException() {
        super(ErrorCode.EMAIL_DUPLICATION);
    }
}
