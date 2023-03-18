package com.vivid.apiserver.domain.individual_video.dao.repository;

import com.vivid.apiserver.domain.individual_video.domain.IndividualVideo;
import com.vivid.apiserver.domain.video_space.domain.VideoSpaceParticipant;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IndividualVideoRepository extends JpaRepository<IndividualVideo, UUID> {

    void deleteAllByVideoSpaceParticipant(VideoSpaceParticipant participant);
}
