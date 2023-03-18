package com.vivid.apiserver.domain.video_space.dao;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.vivid.apiserver.domain.video_space.domain.QVideoSpace;
import com.vivid.apiserver.domain.video_space.domain.QVideoSpaceParticipant;
import com.vivid.apiserver.domain.video_space.domain.VideoSpaceParticipant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class VideoSpaceParticipantDao {

    private final JPAQueryFactory query;

    public Optional<VideoSpaceParticipant> findWithVideoSpaceByUserIdAndVideoSpaceId(UUID userId, Long videoSpaceId) {

        QVideoSpaceParticipant qVideoSpaceParticipant = QVideoSpaceParticipant.videoSpaceParticipant;
        QVideoSpace qVideoSpace = QVideoSpace.videoSpace;

        VideoSpaceParticipant videoSpaceParticipant = query.selectFrom(qVideoSpaceParticipant)
                .leftJoin(qVideoSpaceParticipant.videoSpace, qVideoSpace).fetchJoin()
                .where(qVideoSpaceParticipant.user.id.eq(userId)
                        .and(qVideoSpaceParticipant.videoSpace.id.eq(videoSpaceId)))
                .distinct().fetchOne();

        return Optional.ofNullable(videoSpaceParticipant);
    }


    public List<VideoSpaceParticipant> findAllWithVideoSpaceByUserId(UUID userId) {

        QVideoSpaceParticipant videoSpaceParticipant = QVideoSpaceParticipant.videoSpaceParticipant;
        QVideoSpace videoSpace = QVideoSpace.videoSpace;

        return query.selectFrom(videoSpaceParticipant)
                .leftJoin(videoSpaceParticipant.videoSpace, videoSpace).fetchJoin()
                .where(videoSpaceParticipant.user.id.eq(userId))
                .distinct().fetch();
    }
}
