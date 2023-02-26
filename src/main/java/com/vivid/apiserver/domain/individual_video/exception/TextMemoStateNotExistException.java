package com.vivid.apiserver.domain.individual_video.exception;

import com.vivid.apiserver.global.error.exception.EntityNotFoundException;
import com.vivid.apiserver.global.error.exception.ErrorCode;

public class TextMemoStateNotExistException extends EntityNotFoundException {

    public TextMemoStateNotExistException() {
        super(ErrorCode.TEXT_MEMO_STATE_NOT_EXIST);
    }
}
