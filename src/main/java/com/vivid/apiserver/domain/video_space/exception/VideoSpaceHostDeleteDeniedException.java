package com.vivid.apiserver.domain.video_space.exception;

import com.vivid.apiserver.global.error.exception.AccessDeniedException;
import com.vivid.apiserver.global.error.exception.ErrorCode;

public class VideoSpaceHostDeleteDeniedException extends AccessDeniedException {

    public VideoSpaceHostDeleteDeniedException() {
        super(ErrorCode.VIDEO_SPACE_HOST_DELETE_NOT_ALLOWED);
    }
}
