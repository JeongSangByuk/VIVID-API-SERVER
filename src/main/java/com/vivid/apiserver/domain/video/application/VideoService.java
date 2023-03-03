package com.vivid.apiserver.domain.video.application;

import com.vivid.apiserver.domain.individual_video.application.command.IndividualVideoCommandService;
import com.vivid.apiserver.domain.user.application.CurrentUserService;
import com.vivid.apiserver.domain.user.domain.User;
import com.vivid.apiserver.domain.video.application.command.VideoCommandService;
import com.vivid.apiserver.domain.video.application.query.VideoQueryService;
import com.vivid.apiserver.domain.video.domain.Video;
import com.vivid.apiserver.domain.video.dto.request.VideoSaveRequest;
import com.vivid.apiserver.domain.video.dto.response.VideoSaveResponse;
import com.vivid.apiserver.domain.video_space.application.VideoSpaceValidateService;
import com.vivid.apiserver.domain.video_space.application.query.VideoSpaceQueryService;
import com.vivid.apiserver.domain.video_space.domain.VideoSpace;
import com.vivid.apiserver.global.infra.storage.AwsS3Service;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class VideoService {


    private final VideoQueryService videoQueryService;
    private final VideoSpaceQueryService videoSpaceQueryService;

    private final VideoCommandService videoCommandService;
    private final IndividualVideoCommandService individualVideoCommandService;

    private final VideoSpaceValidateService videoSpaceValidateService;

    private final CurrentUserService currentUserService;
    private final AwsS3Service awsS3Service;

    /**
     * multipart 파일을 통한 비디오 직접 업로드 메소드
     */
    public VideoSaveResponse uploadByDirectUpload(MultipartFile multipartFile, Long videoSpaceId,
            VideoSaveRequest videoSaveRequest) {

        User currentUser = currentUserService.getCurrentUser();
        VideoSpace videoSpace = videoSpaceQueryService.findById(videoSpaceId);

        videoSpaceValidateService.checkHostUserAccess(videoSpace, currentUser.getEmail());

        Video video = videoSaveRequest.toEntity(videoSpace, currentUser.getEmail());

        videoCommandService.save(video);
        individualVideoCommandService.saveAllByParticipants(videoSpace.getVideoSpaceParticipants(), video);
        awsS3Service.uploadVideoToS3ByMultipartFile(multipartFile, video.getId());

        return new VideoSaveResponse(video.getId());
    }

    /**
     * download url을 통한 비디오 업로드 메소드
     */
    public VideoSaveResponse uploadByDownloadUrl(String url, Long videoSpaceId, VideoSaveRequest videoSaveRequest) {

        User currentUser = currentUserService.getCurrentUser();
        VideoSpace videoSpace = videoSpaceQueryService.findById(videoSpaceId);

        videoSpaceValidateService.checkHostUserAccess(videoSpace, currentUser.getEmail());

        Video video = videoSaveRequest.toEntity(videoSpace, currentUser.getEmail());

        individualVideoCommandService.saveAllByParticipants(videoSpace.getVideoSpaceParticipants(), video);
        awsS3Service.uploadVideoToS3ByDownloadUrl(url, video.getId());

        return new VideoSaveResponse(video.getId());
    }

    /**
     * video 삭제 메소드
     */
    public void delete(Long videoId) {

        Video video = videoQueryService.findById(videoId);

        videoCommandService.delete(video);
        individualVideoCommandService.deleteAll(video.getIndividualVideos());
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
