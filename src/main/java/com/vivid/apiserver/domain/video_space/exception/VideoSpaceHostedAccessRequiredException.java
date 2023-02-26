package com.vivid.apiserver.domain.video_space.exception;

import com.vivid.apiserver.global.error.exception.AccessDeniedException;
import com.vivid.apiserver.global.error.exception.ErrorCode;

public class VideoSpaceHostedAccessRequiredException extends AccessDeniedException {

    public VideoSpaceHostedAccessRequiredException() {
        super(ErrorCode.VIDEO_SPACE_HOST_ACCESS_REQUIRED);
    }
}
