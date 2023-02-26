package com.vivid.apiserver.domain.video_space.application;

import com.vivid.apiserver.domain.video_space.dao.VideoSpaceRepository;
import com.vivid.apiserver.domain.video_space.domain.VideoSpace;
import com.vivid.apiserver.domain.video_space.domain.VideoSpaceParticipant;
import com.vivid.apiserver.domain.video_space.exception.VideoSpaceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class VideoSpaceFindService {

    private final VideoSpaceRepository videoSpaceRepository;

    public VideoSpace findById(Long id) {

        // id를 통해 videoSpace get
        VideoSpace videoSpace = videoSpaceRepository.findById(id)
                .orElseThrow(VideoSpaceNotFoundException::new);

        return videoSpace;
    }

    public boolean containsUser(VideoSpace videoSpace, String email) {

        // 로그인 user가 video space particiapnt인지 판단
        for (VideoSpaceParticipant videoSpaceParticipant : videoSpace.getVideoSpaceParticipants()) {
            if(videoSpaceParticipant.getUser().getEmail().equals(email))
                return true;
        }

        return false;
    }
}
