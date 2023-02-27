package com.vivid.apiserver.domain.individual_video.exception;

import com.vivid.apiserver.global.error.exception.NotFoundException;
import com.vivid.apiserver.global.error.exception.ErrorCode;

public class TextMemoStateNotExistException extends NotFoundException {

    public TextMemoStateNotExistException() {
        super(ErrorCode.TEXT_MEMO_STATE_NOT_EXIST);
    }
}
