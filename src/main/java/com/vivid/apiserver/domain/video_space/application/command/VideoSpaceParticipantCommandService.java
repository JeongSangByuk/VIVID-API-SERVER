package com.vivid.apiserver.domain.video_space.application.command;

import com.vivid.apiserver.domain.user.domain.User;
import com.vivid.apiserver.domain.video_space.dao.VideoSpaceParticipantRepository;
import com.vivid.apiserver.domain.video_space.domain.VideoSpace;
import com.vivid.apiserver.domain.video_space.domain.VideoSpaceParticipant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class VideoSpaceParticipantCommandService {

    private final VideoSpaceParticipantRepository videoSpaceParticipantRepository;

    public VideoSpaceParticipant save(VideoSpace videoSpace, User user) {

        VideoSpaceParticipant videoSpaceParticipant = VideoSpaceParticipant.newInstance(videoSpace, user);

        videoSpaceParticipantRepository.save(videoSpaceParticipant);

        return videoSpaceParticipant;
    }


}
