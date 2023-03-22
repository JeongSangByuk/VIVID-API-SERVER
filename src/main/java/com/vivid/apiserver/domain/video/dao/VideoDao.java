package com.vivid.apiserver.domain.video.dao;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.vivid.apiserver.domain.video.domain.QVideo;
import com.vivid.apiserver.domain.video.domain.Video;
import com.vivid.apiserver.domain.video_space.domain.QVideoSpace;
import com.vivid.apiserver.domain.video_space.domain.VideoSpace;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class VideoDao {

    private final JPAQueryFactory query;

    public Optional<Video> findWithVideoSpaceById(Long videoId) {

        QVideo qVideo = QVideo.video;
        QVideoSpace qVideoSpace = QVideoSpace.videoSpace;

        Video video = query.selectFrom(qVideo)
                .leftJoin(qVideo.videoSpace, qVideoSpace).fetchJoin()
                .where(qVideo.id.eq(videoId))
                .distinct().fetchOne();

        return Optional.ofNullable(video);
    }

    public void deleteAllByVideoSpace(VideoSpace videoSpace) {

        QVideo qVideo = QVideo.video;

        query.update(qVideo)
                .where(qVideo.videoSpace.eq(videoSpace))
                .set(qVideo.deleted, Boolean.TRUE)
                .execute();
    }

}
