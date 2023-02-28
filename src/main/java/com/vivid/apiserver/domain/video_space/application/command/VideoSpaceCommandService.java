package com.vivid.apiserver.domain.video_space.application.command;

import com.vivid.apiserver.domain.user.domain.User;
import com.vivid.apiserver.domain.video_space.dao.VideoSpaceRepository;
import com.vivid.apiserver.domain.video_space.domain.VideoSpace;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class VideoSpaceCommandService {

    private final VideoSpaceRepository videoSpaceRepository;

    public VideoSpace save(User user) {

        return VideoSpace.newInstance(user);
    }

}
