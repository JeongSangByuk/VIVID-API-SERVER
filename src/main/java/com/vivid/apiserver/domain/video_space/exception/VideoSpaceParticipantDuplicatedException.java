package com.vivid.apiserver.domain.video_space.exception;

import com.vivid.apiserver.global.error.exception.ErrorCode;
import com.vivid.apiserver.global.error.exception.InvalidValueException;

public class VideoSpaceParticipantDuplicatedException extends InvalidValueException {

    public VideoSpaceParticipantDuplicatedException() {
        super(ErrorCode.VIDEO_SPACE_USER_DUPLICATION);
    }
}
