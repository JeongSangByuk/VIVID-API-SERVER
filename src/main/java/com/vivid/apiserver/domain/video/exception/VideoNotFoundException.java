package com.vivid.apiserver.domain.video.exception;

import com.vivid.apiserver.global.error.exception.NotFoundException;
import com.vivid.apiserver.global.error.exception.ErrorCode;

public class VideoNotFoundException extends NotFoundException {

    public VideoNotFoundException() {
        super(ErrorCode.VIDEO_NOT_FOUND);
    }

    public VideoNotFoundException(String id) {
        super(id.toString() + " is not found");
    }
}
