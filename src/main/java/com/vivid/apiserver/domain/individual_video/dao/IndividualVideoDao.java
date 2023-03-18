package com.vivid.apiserver.domain.individual_video.dao;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.vivid.apiserver.domain.individual_video.domain.IndividualVideo;
import com.vivid.apiserver.domain.individual_video.domain.QIndividualVideo;
import com.vivid.apiserver.domain.video.domain.QVideo;
import com.vivid.apiserver.domain.video_space.domain.VideoSpaceParticipant;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class IndividualVideoDao {

    private final JPAQueryFactory query;

    public List<IndividualVideo> findAllWithVideoByVideoSpaceParticipant(
            List<VideoSpaceParticipant> videoSpaceParticipants) {

        QIndividualVideo qIndividualVideo = QIndividualVideo.individualVideo;
        QVideo qVideo = QVideo.video;

        return query.selectFrom(qIndividualVideo)
                .leftJoin(qIndividualVideo.video, qVideo).fetchJoin()
                .where(qIndividualVideo.videoSpaceParticipant.in(videoSpaceParticipants))
                .distinct().fetch();
    }


}
