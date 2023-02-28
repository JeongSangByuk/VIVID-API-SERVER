package com.vivid.apiserver.domain.video_space.dao;

import com.vivid.apiserver.domain.user.domain.User;
import com.vivid.apiserver.domain.video_space.domain.VideoSpace;
import com.vivid.apiserver.domain.video_space.domain.VideoSpaceParticipant;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoSpaceParticipantRepository extends JpaRepository<VideoSpaceParticipant, Long> {

    Optional<VideoSpaceParticipant> findByVideoSpaceAndUser(VideoSpace videoSpace, User user);

    void deleteAllByVideoSpace(VideoSpace videoSpace);
}
