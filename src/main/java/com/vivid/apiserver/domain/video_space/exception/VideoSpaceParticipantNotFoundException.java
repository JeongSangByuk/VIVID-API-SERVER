package com.vivid.apiserver.domain.video_space.exception;

import com.vivid.apiserver.global.error.exception.EntityNotFoundException;
import com.vivid.apiserver.global.error.exception.ErrorCode;

public class VideoSpaceParticipantNotFoundException extends EntityNotFoundException {

    public VideoSpaceParticipantNotFoundException() {
        super(ErrorCode.VIDEO_SPACE_PARTICIPANT_NOT_FOUND);
    }
}
