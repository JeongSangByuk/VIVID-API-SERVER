package com.vivid.apiserver.domain.video_space.exception;

import com.vivid.apiserver.global.error.exception.NotFoundException;
import com.vivid.apiserver.global.error.exception.ErrorCode;

public class VideoSpaceParticipantNotFoundException extends NotFoundException {

    public VideoSpaceParticipantNotFoundException() {
        super(ErrorCode.VIDEO_SPACE_PARTICIPANT_NOT_FOUND);
    }
}
