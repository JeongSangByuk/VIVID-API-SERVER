package com.vivid.apiserver.domain.video_space.dao;

import com.vivid.apiserver.domain.video_space.domain.VideoSpaceParticipant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoSpaceParticipantRepository extends JpaRepository<VideoSpaceParticipant, Long> {

}
