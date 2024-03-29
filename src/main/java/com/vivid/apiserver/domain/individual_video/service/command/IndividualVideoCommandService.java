package com.vivid.apiserver.domain.individual_video.service.command;

import com.vivid.apiserver.domain.individual_video.dao.IndividualVideoDao;
import com.vivid.apiserver.domain.individual_video.dao.repository.IndividualVideoRepository;
import com.vivid.apiserver.domain.individual_video.domain.IndividualVideo;
import com.vivid.apiserver.domain.video.domain.Video;
import com.vivid.apiserver.domain.video_space.domain.VideoSpaceParticipant;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class IndividualVideoCommandService {

    private final IndividualVideoDao individualVideoDao;

    private final IndividualVideoRepository individualVideoRepository;

    public void saveAllByParticipants(List<VideoSpaceParticipant> videoSpaceParticipants, Video video) {

        List<IndividualVideo> individualVideos = videoSpaceParticipants.stream()
                .map(videoSpaceParticipant -> IndividualVideo.of(video, videoSpaceParticipant))
                .collect(Collectors.toList());

        individualVideoRepository.saveAll(individualVideos);
    }

    public void saveAllByVideos(List<Video> videos, VideoSpaceParticipant videoSpaceParticipant) {

        List<IndividualVideo> individualVideos = videos.stream()
                .map(video -> IndividualVideo.of(video, videoSpaceParticipant))
                .collect(Collectors.toList());

        individualVideoRepository.saveAll(individualVideos);
    }

    public void changeLastAccessTime(IndividualVideo individualVideo) {
        individualVideo.changeLastAccessTime();
    }

    public void changeProgressRate(IndividualVideo individualVideo, Integer progressRate) {
        individualVideo.changeProgressRate(progressRate);
    }

    public void delete(IndividualVideo individualVideo) {

        individualVideoRepository.delete(individualVideo);
    }

    public void deleteAll(Video video) {

        individualVideoDao.deleteAll(video);
    }

    public void deleteAll(VideoSpaceParticipant videoSpaceParticipant) {

        individualVideoDao.deleteAll(videoSpaceParticipant);
    }

    public void deleteAll(List<Video> videos, List<VideoSpaceParticipant> videoSpaceParticipants) {
        individualVideoDao.deleteAll(videos, videoSpaceParticipants);


    }
}
