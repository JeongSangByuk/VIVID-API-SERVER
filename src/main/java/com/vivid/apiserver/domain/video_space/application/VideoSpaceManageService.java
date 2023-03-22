package com.vivid.apiserver.domain.video_space.application;

import com.vivid.apiserver.domain.individual_video.application.command.IndividualVideoCommandService;
import com.vivid.apiserver.domain.individual_video.domain.IndividualVideo;
import com.vivid.apiserver.domain.user.domain.User;
import com.vivid.apiserver.domain.video.application.command.VideoCommandService;
import com.vivid.apiserver.domain.video_space.application.command.VideoSpaceCommandService;
import com.vivid.apiserver.domain.video_space.application.command.VideoSpaceParticipantCommandService;
import com.vivid.apiserver.domain.video_space.domain.VideoSpace;
import com.vivid.apiserver.domain.video_space.domain.VideoSpaceParticipant;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class VideoSpaceManageService {

    private final VideoCommandService videoCommandService;
    private final VideoSpaceCommandService videoSpaceCommandService;
    private final IndividualVideoCommandService individualVideoCommandService;
    private final VideoSpaceParticipantCommandService videoSpaceParticipantCommandService;

    /**
     * video space 초기 상태로 생성하는 메소드
     */
    public void createInitialVideoSpace(User currentUser, VideoSpace videoSpace) {
        videoSpaceCommandService.save(videoSpace);
        videoSpaceParticipantCommandService.save(videoSpace, currentUser);
    }

    /**
     * video space를 삭제하면서 연관된 모든 entity 삭제하는 메소드
     */
    public void deleteVideoSpace(VideoSpace videoSpace) {

        List<IndividualVideo> individualVideos = findAllIndividualVideosByVideoSpace(videoSpace);

//        individualVideoCommandService.deleteAll(individualVideos);
        videoSpaceParticipantCommandService.deleteAllByVideoSpace(videoSpace);
        videoCommandService.deleteAllByVideoSpace(videoSpace);
        videoSpaceCommandService.delete(videoSpace);
    }

    /**
     * video space와 연관된 모든 individual videos를 get하는 메소드
     */
    public List<IndividualVideo> findAllIndividualVideosByVideoSpace(VideoSpace videoSpace) {
        return videoSpace.getVideoSpaceParticipants().stream()
                .map(VideoSpaceParticipant::getIndividualVideos)
                .flatMap(List::stream)
                .distinct()
                .collect(Collectors.toList());
    }

}
