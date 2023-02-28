package com.vivid.apiserver.domain.individual_video.dao.repository;

import com.vivid.apiserver.domain.individual_video.domain.IndividualVideo;
import com.vivid.apiserver.domain.video_space.domain.VideoSpaceParticipant;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IndividualVideoRepository extends JpaRepository<IndividualVideo, UUID> {

    List<IndividualVideo> findAllByVideoSpaceParticipantId(Long videoSpaceParticipantId);

    void deleteAllByVideoSpaceParticipant(VideoSpaceParticipant participant);
}
