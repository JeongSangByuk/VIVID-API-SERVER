package com.vivid.apiserver.domain.video_space.application.query;

import com.vivid.apiserver.domain.video_space.dao.VideoSpaceRepository;
import com.vivid.apiserver.domain.video_space.domain.VideoSpace;
import com.vivid.apiserver.domain.video_space.domain.VideoSpaceParticipant;
import com.vivid.apiserver.global.error.exception.ErrorCode;
import com.vivid.apiserver.global.error.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class VideoSpaceQueryService {

    private final VideoSpaceRepository videoSpaceRepository;

    public VideoSpace findById(Long id) {

        return videoSpaceRepository.findById(id)
                .orElseThrow(() -> {
                    throw new NotFoundException(ErrorCode.VIDEO_SPACE_NOT_FOUND);
                });
    }

    public boolean containsUser(VideoSpace videoSpace, String email) {

        // 로그인 user가 video space particiapnt인지 판단
        for (VideoSpaceParticipant videoSpaceParticipant : videoSpace.getVideoSpaceParticipants()) {
            if (videoSpaceParticipant.getUser().getEmail().equals(email)) {
                return true;
            }
        }

        return false;
    }
}
