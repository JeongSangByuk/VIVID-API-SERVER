package com.vivid.apiserver.domain.video_space.exception;

import com.vivid.apiserver.global.error.exception.EntityNotFoundException;
import com.vivid.apiserver.global.error.exception.ErrorCode;

public class VideoSpaceNotFoundException extends EntityNotFoundException {

    public VideoSpaceNotFoundException() {
        super(ErrorCode.VIDEO_SPACE_NOT_FOUND);
    }
}
