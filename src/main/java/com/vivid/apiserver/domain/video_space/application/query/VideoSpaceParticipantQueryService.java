package com.vivid.apiserver.domain.video_space.application.query;

import com.vivid.apiserver.domain.user.domain.User;
import com.vivid.apiserver.domain.video_space.dao.VideoSpaceParticipantDao;
import com.vivid.apiserver.domain.video_space.dao.VideoSpaceParticipantRepository;
import com.vivid.apiserver.domain.video_space.domain.VideoSpace;
import com.vivid.apiserver.domain.video_space.domain.VideoSpaceParticipant;
import com.vivid.apiserver.global.error.exception.ErrorCode;
import com.vivid.apiserver.global.error.exception.NotFoundException;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class VideoSpaceParticipantQueryService {

    private final VideoSpaceParticipantDao videoSpaceParticipantDao;
    private final VideoSpaceParticipantRepository videoSpaceParticipantRepository;

    public VideoSpaceParticipant findById(Long videoSpaceParticipantId) {

        return videoSpaceParticipantRepository.findById(videoSpaceParticipantId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.VIDEO_SPACE_PARTICIPANT_NOT_FOUND));
    }

    public VideoSpaceParticipant findByUserAndVideoSpace(User user, VideoSpace videoSpace) {

        return videoSpaceParticipantRepository.findByVideoSpaceAndUser(videoSpace, user)
                .orElseThrow(() -> new NotFoundException(ErrorCode.VIDEO_SPACE_PARTICIPANT_NOT_FOUND));
    }

    public List<VideoSpaceParticipant> findAllWithVideoSpaceByUserId(UUID userId) {
        return videoSpaceParticipantDao.findAllWithVideoSpaceByUserId(userId);
    }

    public VideoSpaceParticipant findWithVideoSpaceByUserIdAndVideoSpaceId(UUID userId, Long videoSpaceId) {
        return videoSpaceParticipantDao.findWithVideoSpaceByUserIdAndVideoSpaceId(userId, videoSpaceId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.VIDEO_SPACE_PARTICIPANT_NOT_FOUND));
    }

}
