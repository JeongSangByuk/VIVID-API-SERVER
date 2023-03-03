package com.vivid.apiserver.domain.video.application.command;

import com.vivid.apiserver.domain.video.dao.VideoRepository;
import com.vivid.apiserver.domain.video.domain.Video;
import com.vivid.apiserver.domain.video_space.domain.VideoSpace;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class VideoCommandService {

    private final VideoRepository videoRepository;

    public void save(Video video) {
        videoRepository.save(video);
    }

    public void deleteAllByVideoSpace(VideoSpace videoSpace) {
        videoRepository.deleteAllByVideoSpace(videoSpace);
    }


}
