package com.vivid.apiserver.domain.video_space.application;

import com.vivid.apiserver.domain.user.domain.User;
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

    /**
     * video space의 host인지 validation
     */
    public void checkHostUserAccess(VideoSpace videoSpace, String email) {
        if (!videoSpace.getHostEmail().equals(email)) {
            throw new AccessDeniedException(ErrorCode.VIDEO_SPACE_HOST_ACCESS_REQUIRED);
        }
    }

    /**
     * video space의 host가 삭제하려는지 validation
     */
    public void checkVideoSpaceHostDelete(VideoSpace videoSpace, String email) {
        if (videoSpace.getHostEmail().equals(email)) {
            throw new AccessDeniedException(ErrorCode.VIDEO_SPACE_HOST_DELETE_NOT_ALLOWED);
        }
    }

    /**
     * video space의 user가 participant인지 validation
     */
    public void checkVideoSpaceParticipant(VideoSpace videoSpace, User user) {
        if (!videoSpaceQueryService.isContainedUser(videoSpace, user.getEmail())) {
            throw new AccessDeniedException(ErrorCode.VIDEO_SPACE_ACCESS_DENIED);
        }
    }

    /**
     * video space의 user가 중복된 participant인지 validation
     */
    public void checkDuplicatedParticipant(String email, List<VideoSpaceParticipant> videoSpaceParticipants) {

        videoSpaceParticipants.forEach(videoSpaceParticipant -> {
            if (videoSpaceParticipant.getEmail().equals(email)) {
                throw new InvalidValueException(ErrorCode.VIDEO_SPACE_USER_DUPLICATION);
            }
        });
    }

}
