package com.vivid.apiserver.domain.video.dao;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.vivid.apiserver.domain.individual_video.domain.QIndividualVideo;
import com.vivid.apiserver.domain.video.domain.QVideo;
import com.vivid.apiserver.domain.video.domain.Video;
import com.vivid.apiserver.global.error.exception.ErrorCode;
import com.vivid.apiserver.global.error.exception.NotFoundException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class VideoDao {

    private final VideoRepository videoRepository;

    private final JPAQueryFactory query;

    // videoId를 통해 video return
    public Video findById(final Long id) {

        // fetch join + queryDLS를 통한 get
        Optional<Video> video = Optional.ofNullable(query.select(QVideo.video)
                .from(QVideo.video)
                .leftJoin(QVideo.video.individualVideos, QIndividualVideo.individualVideo)
                .fetchJoin()
                .where(QVideo.video.id.eq(id))
                .distinct().fetchOne());

        // not found exception
        video.orElseThrow(() -> new NotFoundException(ErrorCode.VIDEO_NOT_FOUND));

        return video.get();
    }


}
