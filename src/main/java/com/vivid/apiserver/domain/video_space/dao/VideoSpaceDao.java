package com.vivid.apiserver.domain.video_space.dao;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.vivid.apiserver.domain.video_space.domain.QVideoSpace;
import com.vivid.apiserver.domain.video_space.domain.QVideoSpaceParticipant;
import com.vivid.apiserver.domain.video_space.domain.VideoSpace;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class VideoSpaceDao {

    private final JPAQueryFactory query;

    public Optional<VideoSpace> findWithVideoSpaceParticipantsById(Long videoSpaceId) {

        QVideoSpace qVideoSpace = QVideoSpace.videoSpace;
        QVideoSpaceParticipant qVideoSpaceParticipant = QVideoSpaceParticipant.videoSpaceParticipant;

        VideoSpace videoSpace = query.selectFrom(qVideoSpace)
                .leftJoin(qVideoSpace.videoSpaceParticipants, qVideoSpaceParticipant).fetchJoin()
                .where(qVideoSpace.id.eq(videoSpaceId))
                .distinct().fetchOne();

        return Optional.ofNullable(videoSpace);
    }
}
