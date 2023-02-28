package com.vivid.apiserver.domain.video_space.application;

import com.vivid.apiserver.domain.user.domain.User;
import com.vivid.apiserver.domain.video_space.application.command.VideoSpaceCommandService;
import com.vivid.apiserver.domain.video_space.application.command.VideoSpaceParticipantCommandService;
import com.vivid.apiserver.domain.video_space.domain.VideoSpace;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class VideoSpaceCreateService {

    private final VideoSpaceCommandService videoSpaceCommandService;
    private final VideoSpaceParticipantCommandService videoSpaceParticipantCommandService;

    public void createInitialVideoSpace(User currentUser, VideoSpace videoSpace) {
        videoSpaceCommandService.save(videoSpace);
        videoSpaceParticipantCommandService.save(videoSpace, currentUser);
    }

}
