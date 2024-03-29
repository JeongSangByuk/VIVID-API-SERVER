package com.vivid.apiserver.domain.video.service.query;

import com.vivid.apiserver.domain.video.dao.VideoDao;
import com.vivid.apiserver.domain.video.dao.VideoRepository;
import com.vivid.apiserver.domain.video.domain.Video;
import com.vivid.apiserver.domain.video_space.domain.VideoSpace;
import com.vivid.apiserver.global.error.exception.ErrorCode;
import com.vivid.apiserver.global.error.exception.NotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class VideoQueryService {

    private final VideoRepository videoRepository;

    private final VideoDao videoDao;

    public Video findById(Long videoId) {
        return videoRepository.findById(videoId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.VIDEO_NOT_FOUND));
    }

    public Video findWithVideoSpaceById(Long videoId) {
        return videoDao.findWithVideoSpaceById(videoId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.VIDEO_NOT_FOUND));
    }

    public List<Video> findAllByVideoSpaces(List<VideoSpace> videoSpaces) {
        return videoRepository.findAllByVideoSpaceIn(videoSpaces);
    }

}
