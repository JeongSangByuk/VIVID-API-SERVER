package com.vivid.apiserver.domain.video.application;

import com.vivid.apiserver.domain.individual_video.application.command.IndividualVideoCommandService;
import com.vivid.apiserver.domain.user.application.CurrentUserService;
import com.vivid.apiserver.domain.user.domain.User;
import com.vivid.apiserver.domain.video.application.command.VideoCommandService;
import com.vivid.apiserver.domain.video.application.query.VideoQueryService;
import com.vivid.apiserver.domain.video.domain.Video;
import com.vivid.apiserver.domain.video_space.application.VideoSpaceValidateService;
import com.vivid.apiserver.global.infra.storage.AwsS3Service;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class VideoService {

    private final VideoQueryService videoQueryService;

    private final VideoCommandService videoCommandService;
    private final IndividualVideoCommandService individualVideoCommandService;

    private final CurrentUserService currentUserService;
    private final VideoSpaceValidateService videoSpaceValidateService;
    private final AwsS3Service awsS3Service;

    /**
     * 비디오 삭제 메소드
     */
    public void delete(Long videoId) {

        User currentUser = currentUserService.getCurrentUser();
        Video video = videoQueryService.findWithVideoSpaceAndIndividualVideosById(videoId);

        videoSpaceValidateService.checkHostUserAccess(video.getVideoSpace(), currentUser.getEmail());

        individualVideoCommandService.deleteAll(video.getIndividualVideos());
        videoCommandService.delete(video);

    }

    /**
     * TODO consume 로직으로 변경
     * upload 된 후 video의 uploaded 상태 변경하는 메소드
     */
    public void changeUploadState(Long videoId) {

        Video video = videoQueryService.findById(videoId);
        List<String> visualIndexImages = awsS3Service.getVisualIndexImages(videoId);

        videoCommandService.changeWhenUploaded(video, visualIndexImages.get(0));
    }
}
