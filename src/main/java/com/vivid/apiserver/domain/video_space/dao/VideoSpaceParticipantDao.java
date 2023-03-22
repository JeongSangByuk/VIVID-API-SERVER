package com.vivid.apiserver.domain.video_space.dao;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.vivid.apiserver.domain.user.domain.QUser;
import com.vivid.apiserver.domain.video_space.domain.QVideoSpace;
import com.vivid.apiserver.domain.video_space.domain.QVideoSpaceParticipant;
import com.vivid.apiserver.domain.video_space.domain.VideoSpace;
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

        QVideoSpaceParticipant qVideoSpaceParticipant = QVideoSpaceParticipant.videoSpaceParticipant;
        QVideoSpace qVideoSpace = QVideoSpace.videoSpace;

        return query.selectFrom(qVideoSpaceParticipant)
                .leftJoin(qVideoSpaceParticipant.videoSpace, qVideoSpace).fetchJoin()
                .where(qVideoSpaceParticipant.user.id.eq(userId))
                .distinct().fetch();
    }

    public List<VideoSpaceParticipant> findAllWithUserByVideoSpaces(List<VideoSpace> videoSpaces) {

        QVideoSpaceParticipant qVideoSpaceParticipant = QVideoSpaceParticipant.videoSpaceParticipant;
        QUser qUser = QUser.user;

        return query.selectFrom(qVideoSpaceParticipant)
                .leftJoin(qVideoSpaceParticipant.user, qUser).fetchJoin()
                .where(qVideoSpaceParticipant.videoSpace.in(videoSpaces))
                .distinct().fetch();
    }

    public void deleteAll(List<VideoSpaceParticipant> videoSpaceParticipants) {

        QVideoSpaceParticipant qVideoSpaceParticipant = QVideoSpaceParticipant.videoSpaceParticipant;

        query.update(qVideoSpaceParticipant)
                .where(qVideoSpaceParticipant.in(videoSpaceParticipants))
                .set(qVideoSpaceParticipant.deleted, Boolean.TRUE)
                .execute();
    }

    public void deleteAllByVideoSpace(VideoSpace videoSpace) {

        QVideoSpaceParticipant qVideoSpaceParticipant = QVideoSpaceParticipant.videoSpaceParticipant;

        query.update(qVideoSpaceParticipant)
                .where(qVideoSpaceParticipant.videoSpace.eq(videoSpace))
                .set(qVideoSpaceParticipant.deleted, Boolean.TRUE)
                .execute();
    }
}
