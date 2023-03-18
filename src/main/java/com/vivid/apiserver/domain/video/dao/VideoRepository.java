package com.vivid.apiserver.domain.video.dao;

import com.vivid.apiserver.domain.video.domain.Video;
import com.vivid.apiserver.domain.video_space.domain.VideoSpace;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoRepository extends JpaRepository<Video, Long> {

    void deleteAllByVideoSpace(VideoSpace videoSpace);

    List<Video> findAllByVideoSpaceIn(List<VideoSpace> videoSpaces);
}
