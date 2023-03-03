package com.vivid.apiserver.domain.video.application.query;

import com.vivid.apiserver.domain.video.dao.VideoRepository;
import com.vivid.apiserver.domain.video.domain.Video;
import com.vivid.apiserver.global.error.exception.ErrorCode;
import com.vivid.apiserver.global.error.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class VideoQueryService {

    private final VideoRepository videoRepository;

    public Video findById(Long videoId) {
        return videoRepository.findById(videoId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.VIDEO_NOT_FOUND));
    }


}
