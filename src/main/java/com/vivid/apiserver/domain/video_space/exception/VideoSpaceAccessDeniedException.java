package com.vivid.apiserver.domain.video_space.exception;

import com.vivid.apiserver.global.error.exception.AccessDeniedException;
import com.vivid.apiserver.global.error.exception.ErrorCode;

public class VideoSpaceAccessDeniedException extends AccessDeniedException {

    public VideoSpaceAccessDeniedException() {
        super(ErrorCode.VIDEO_SPACE_ACCESS_DENIED);
    }
}
