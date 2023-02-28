package com.vivid.apiserver.domain.video_space.application.query;

import com.vivid.apiserver.domain.user.domain.User;
import com.vivid.apiserver.domain.video_space.dao.VideoSpaceParticipantRepository;
import com.vivid.apiserver.domain.video_space.domain.VideoSpace;
import com.vivid.apiserver.domain.video_space.domain.VideoSpaceParticipant;
import com.vivid.apiserver.domain.video_space.exception.VideoSpaceParticipantNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class VideoSpaceParticipantQueryService {

    private final VideoSpaceParticipantRepository videoSpaceParticipantRepository;

    public VideoSpaceParticipant findById(Long videoSpaceParticipantId) {

        // find video space by id
        VideoSpaceParticipant videoSpaceParticipant = videoSpaceParticipantRepository.findById(videoSpaceParticipantId)
                .orElseThrow(VideoSpaceParticipantNotFoundException::new);

        return videoSpaceParticipant;
    }

    public VideoSpaceParticipant findByUserAndVideoSpace(User user, VideoSpace videoSpace) {

        VideoSpaceParticipant videoSpaceParticipant = videoSpaceParticipantRepository.findByVideoSpaceAndUser(
                        videoSpace, user)
                .orElseThrow(VideoSpaceParticipantNotFoundException::new);

        return videoSpaceParticipant;
    }

}
