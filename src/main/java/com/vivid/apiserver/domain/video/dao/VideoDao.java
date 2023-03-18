package com.vivid.apiserver.domain.video.dao;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.vivid.apiserver.domain.user.domain.QUser;
import com.vivid.apiserver.domain.video_space.domain.QVideoSpaceParticipant;
import com.vivid.apiserver.domain.video_space.domain.VideoSpace;
import com.vivid.apiserver.domain.video_space.domain.VideoSpaceParticipant;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class VideoDao {

    private final JPAQueryFactory query;

    public List<VideoSpaceParticipant> findAllWithUserByVideoSpaces(List<VideoSpace> videoSpaces) {

        QVideoSpaceParticipant qVideoSpaceParticipant = QVideoSpaceParticipant.videoSpaceParticipant;
        QUser qUser = QUser.user;

        return query.selectFrom(qVideoSpaceParticipant)
                .leftJoin(qVideoSpaceParticipant.user, qUser).fetchJoin()
                .where(qVideoSpaceParticipant.videoSpace.in(videoSpaces))
                .distinct().fetch();
    }


}
