package com.vivid.apiserver.domain.video_space.application;

import com.vivid.apiserver.domain.video_space.application.query.VideoSpaceQueryService;
import com.vivid.apiserver.domain.video_space.domain.VideoSpace;
import com.vivid.apiserver.domain.video_space.domain.VideoSpaceParticipant;
import com.vivid.apiserver.global.error.exception.AccessDeniedException;
import com.vivid.apiserver.global.error.exception.ErrorCode;
import com.vivid.apiserver.global.error.exception.InvalidValueException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class VideoSpaceValidationService {

    private final VideoSpaceQueryService videoSpaceQueryService;

    public void checkHostUserAccess(VideoSpace videoSpace, String email) {

        if (!videoSpace.getHostEmail().equals(email)) {
            throw new AccessDeniedException(ErrorCode.VIDEO_SPACE_HOST_ACCESS_REQUIRED);
        }
    }

    public void checkDuplicatedParticipant(String targetEmail, List<VideoSpaceParticipant> videoSpaceParticipants) {

        videoSpaceParticipants.forEach(videoSpaceParticipant -> {
            if (videoSpaceParticipant.getEmail().equals(targetEmail)) {
                throw new InvalidValueException(ErrorCode.VIDEO_SPACE_USER_DUPLICATION);
            }
        });
    }

    public void checkVideoSpaceHostDelete(VideoSpace videoSpace, String email) {
        if (email.equals(videoSpace.getHostEmail())) {
            throw new AccessDeniedException(ErrorCode.VIDEO_SPACE_HOST_DELETE_NOT_ALLOWED);
        }
    }


}
