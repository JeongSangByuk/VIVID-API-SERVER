package com.vivid.apiserver.domain.individual_video.application.command;

import com.vivid.apiserver.domain.individual_video.dao.repository.IndividualVideoRepository;
import com.vivid.apiserver.domain.individual_video.domain.IndividualVideo;
import com.vivid.apiserver.domain.video.domain.Video;
import com.vivid.apiserver.domain.video_space.domain.VideoSpaceParticipant;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class IndividualVideoCommandService {

    private final IndividualVideoRepository individualVideoRepository;

    public List<IndividualVideo> saveAll(List<Video> videos, VideoSpaceParticipant videoSpaceParticipant) {

        List<IndividualVideo> individualVideos = videos.stream()
                .map(video -> IndividualVideo.newInstance(video, videoSpaceParticipant))
                .collect(Collectors.toList());

        individualVideoRepository.saveAll(individualVideos);

        return individualVideos;
    }


}
