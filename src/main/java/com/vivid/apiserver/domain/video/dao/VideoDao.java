package com.vivid.apiserver.domain.video.dao;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.vivid.apiserver.domain.individual_video.domain.QIndividualVideo;
import com.vivid.apiserver.domain.video.domain.QVideo;
import com.vivid.apiserver.domain.video.domain.Video;
import com.vivid.apiserver.domain.video_space.domain.QVideoSpace;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class VideoDao {

    private final JPAQueryFactory query;

    public Optional<Video> findWithVideoSpaceAndIndividualVideosById(Long videoId) {

        QVideo qVideo = QVideo.video;
        QVideoSpace qVideoSpace = QVideoSpace.videoSpace;
        QIndividualVideo qIndividualVideo = QIndividualVideo.individualVideo;

        Video video = query.selectFrom(qVideo)
                .leftJoin(qVideo.videoSpace, qVideoSpace).fetchJoin()
                .leftJoin(qVideo.individualVideos, qIndividualVideo).fetchJoin()
                .where(qVideo.id.eq(videoId))
                .distinct().fetchOne();

        return Optional.ofNullable(video);
    }

}
