package com.vivid.apiserver.domain.individual_video.exception;

import com.vivid.apiserver.global.error.exception.ErrorCode;
import com.vivid.apiserver.global.error.exception.NotFoundException;

public class TextMemoStateNotExistException extends NotFoundException {

    public TextMemoStateNotExistException() {
        super(ErrorCode.TEXT_MEMO_NOT_EXIST);
    }
}
