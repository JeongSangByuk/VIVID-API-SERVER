package com.vivid.apiserver.domain.video_space.dao;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.vivid.apiserver.domain.video_space.domain.QVideoSpace;
import com.vivid.apiserver.domain.video_space.domain.QVideoSpaceParticipant;
import com.vivid.apiserver.domain.video_space.domain.VideoSpaceParticipant;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class VideoSpaceParticipantDao {

    private final JPAQueryFactory query;

    public List<VideoSpaceParticipant> findWithVideoSpaceByUserId(UUID userId) {

        QVideoSpaceParticipant videoSpaceParticipant = QVideoSpaceParticipant.videoSpaceParticipant;
        QVideoSpace videoSpace = QVideoSpace.videoSpace;

        return query.selectFrom(videoSpaceParticipant)
                .leftJoin(videoSpaceParticipant.videoSpace, videoSpace).fetchJoin()
                .where(videoSpaceParticipant.user.id.eq(userId))
                .distinct().fetch();
    }
}
