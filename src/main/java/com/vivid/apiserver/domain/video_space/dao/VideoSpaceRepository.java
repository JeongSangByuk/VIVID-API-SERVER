package com.vivid.apiserver.domain.video_space.dao;

import com.vivid.apiserver.domain.video_space.domain.VideoSpace;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoSpaceRepository extends JpaRepository<VideoSpace, Long> {

    List<VideoSpace> findAllByHostEmail(String hostEmail);
}
