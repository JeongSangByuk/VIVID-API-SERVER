package com.vivid.apiserver.domain.video_space.dao;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.vivid.apiserver.domain.video.domain.QVideo;
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

    // videoId를 통해 video return
    public VideoSpace findById(final Long id) {

        // fetch join + queryDLS를 통한 get, 2중 join
        Optional<VideoSpace> videoSpace = Optional.ofNullable(query.select(QVideoSpace.videoSpace)
                .from(QVideoSpace.videoSpace)
                .leftJoin(QVideoSpace.videoSpace.videos, QVideo.video)
                .fetchJoin()
                .where(QVideoSpace.videoSpace.id.eq(id))
                .leftJoin(QVideoSpace.videoSpace.videoSpaceParticipants, QVideoSpaceParticipant.videoSpaceParticipant)
                .fetchJoin()
                .where(QVideoSpace.videoSpace.id.eq(id))
                .distinct().fetchOne());

        // not found exception
        videoSpace.orElseThrow(() -> new VideoSpaceNotFoundException());

        return videoSpace.get();
    }


}
