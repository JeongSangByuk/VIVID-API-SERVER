package com.vivid.apiserver.domain.video_space.application.query;

import com.vivid.apiserver.domain.video_space.dao.VideoSpaceDao;
import com.vivid.apiserver.domain.video_space.dao.VideoSpaceRepository;
import com.vivid.apiserver.domain.video_space.domain.VideoSpace;
import com.vivid.apiserver.domain.video_space.domain.VideoSpaceParticipant;
import com.vivid.apiserver.global.error.exception.ErrorCode;
import com.vivid.apiserver.global.error.exception.NotFoundException;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class VideoSpaceQueryService {

    private final VideoSpaceDao videoSpaceDao;
    private final VideoSpaceRepository videoSpaceRepository;

    public VideoSpace findById(Long id) {

        return videoSpaceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.VIDEO_SPACE_NOT_FOUND));
    }

    public VideoSpace findWithVideoSpaceParticipantsById(Long id) {

        return videoSpaceDao.findWithVideoSpaceParticipantsById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.VIDEO_SPACE_NOT_FOUND));
    }

    public List<VideoSpace> findAllByHostedEmail(String email) {

        return videoSpaceRepository.findAllByHostEmail(email);
    }

    /**
     * video space에 포함된 유저인지 판단하는 메소드
     */
    public boolean isContainedUser(VideoSpace videoSpace, String email) {

        Optional<String> participant = videoSpace.getVideoSpaceParticipants().stream()
                .map(VideoSpaceParticipant::getEmail)
                .filter(email::equals)
                .findFirst();

        return participant.isPresent();
    }
}
