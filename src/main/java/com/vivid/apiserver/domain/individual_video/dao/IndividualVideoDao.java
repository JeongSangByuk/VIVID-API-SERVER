package com.vivid.apiserver.domain.individual_video.dao;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.vivid.apiserver.domain.individual_video.domain.IndividualVideo;
import com.vivid.apiserver.domain.individual_video.domain.QIndividualVideo;
import com.vivid.apiserver.domain.video.domain.QVideo;
import com.vivid.apiserver.domain.video.domain.Video;
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
public class IndividualVideoDao {

    private final JPAQueryFactory query;

    public List<IndividualVideo> findAllWithVideoByVideoSpaceParticipants(
            List<VideoSpaceParticipant> videoSpaceParticipants) {

        QIndividualVideo qIndividualVideo = QIndividualVideo.individualVideo;
        QVideo qVideo = QVideo.video;

        return query.selectFrom(qIndividualVideo)
                .leftJoin(qIndividualVideo.video, qVideo).fetchJoin()
                .where(qIndividualVideo.videoSpaceParticipant.in(videoSpaceParticipants))
                .distinct().fetch();
    }

    public Optional<IndividualVideo> findWithVideoAndVideoSpaceParticipantById(UUID individualVideoId) {

        QIndividualVideo qIndividualVideo = QIndividualVideo.individualVideo;
        QVideoSpaceParticipant qVideoSpaceParticipant = QVideoSpaceParticipant.videoSpaceParticipant;
        QVideo qVideo = QVideo.video;

        IndividualVideo individualVideo = query.selectFrom(qIndividualVideo)
                .leftJoin(qIndividualVideo.videoSpaceParticipant, qVideoSpaceParticipant).fetchJoin()
                .leftJoin(qIndividualVideo.video, qVideo).fetchJoin()
                .where(qIndividualVideo.id.eq(individualVideoId))
                .distinct().fetchOne();

        return Optional.ofNullable(individualVideo);
    }

    public void deleteAll(Video video) {

        QIndividualVideo qIndividualVideo = QIndividualVideo.individualVideo;

        query.update(qIndividualVideo)
                .where(qIndividualVideo.video.eq(video))
                .set(qIndividualVideo.deleted, Boolean.TRUE)
                .execute();
    }

    public void deleteAll(VideoSpaceParticipant videoSpaceParticipant) {

        QIndividualVideo qIndividualVideo = QIndividualVideo.individualVideo;

        query.update(qIndividualVideo)
                .where(qIndividualVideo.videoSpaceParticipant.eq(videoSpaceParticipant))
                .set(qIndividualVideo.deleted, Boolean.TRUE)
                .execute();
    }

    public void deleteAll(List<Video> videos,
            List<VideoSpaceParticipant> videoSpaceParticipants) {

        QIndividualVideo qIndividualVideo = QIndividualVideo.individualVideo;

        query.update(qIndividualVideo)
                .where(qIndividualVideo.videoSpaceParticipant.in(videoSpaceParticipants)
                        .or(qIndividualVideo.video.in(videos)))
                .set(qIndividualVideo.deleted, Boolean.TRUE)
                .execute();
    }


}
