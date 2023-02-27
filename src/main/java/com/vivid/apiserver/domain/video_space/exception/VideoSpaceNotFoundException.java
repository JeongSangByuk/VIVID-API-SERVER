package com.vivid.apiserver.domain.video_space.exception;

import com.vivid.apiserver.global.error.exception.NotFoundException;
import com.vivid.apiserver.global.error.exception.ErrorCode;

public class VideoSpaceNotFoundException extends NotFoundException {

    public VideoSpaceNotFoundException() {
        super(ErrorCode.VIDEO_SPACE_NOT_FOUND);
    }
}
