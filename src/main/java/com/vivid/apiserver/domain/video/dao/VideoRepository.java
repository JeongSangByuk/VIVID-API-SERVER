package com.vivid.apiserver.domain.video.dao;

import com.vivid.apiserver.domain.video.domain.Video;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoRepository extends JpaRepository<Video, Long> {


}
