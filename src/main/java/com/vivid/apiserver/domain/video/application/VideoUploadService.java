package com.vivid.apiserver.domain.video.application;

import com.vivid.apiserver.domain.individual_video.application.command.IndividualVideoCommandService;
import com.vivid.apiserver.domain.user.application.CurrentUserService;
import com.vivid.apiserver.domain.user.domain.User;
import com.vivid.apiserver.domain.video.application.command.VideoCommandService;
import com.vivid.apiserver.domain.video.domain.Video;
import com.vivid.apiserver.domain.video.dto.request.VideoSaveRequest;
import com.vivid.apiserver.domain.video.dto.response.VideoSaveResponse;
import com.vivid.apiserver.domain.video_space.application.VideoSpaceValidateService;
import com.vivid.apiserver.domain.video_space.application.query.VideoSpaceQueryService;
import com.vivid.apiserver.domain.video_space.domain.VideoSpace;
import com.vivid.apiserver.global.infra.storage.AwsS3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
@RequiredArgsConstructor
public class VideoUploadService {

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

        Video video = uploadVideo(videoSpaceId, videoSaveRequest);

        awsS3Service.uploadVideoToS3ByMultipartFile(multipartFile, video.getId());

        return new VideoSaveResponse(video.getId());
    }

    /**
     * download url을 통한 비디오 업로드 메소드
     */
    public VideoSaveResponse uploadByDownloadUrl(String url, Long videoSpaceId, VideoSaveRequest videoSaveRequest) {

        Video video = uploadVideo(videoSpaceId, videoSaveRequest);

        awsS3Service.uploadVideoToS3ByDownloadUrl(url, video.getId());

        return new VideoSaveResponse(video.getId());
    }


    /**
     * 비디오 업로드하는 메소드
     */
    private Video uploadVideo(Long videoSpaceId, VideoSaveRequest videoSaveRequest) {
        User currentUser = currentUserService.getCurrentUser();
        VideoSpace videoSpace = videoSpaceQueryService.findById(videoSpaceId);

        videoSpaceValidateService.checkHostUserAccess(videoSpace, currentUser.getEmail());

        Video video = videoSaveRequest.toEntity(videoSpace, currentUser.getEmail());

        videoCommandService.save(video);
        individualVideoCommandService.saveAllByParticipants(videoSpace.getVideoSpaceParticipants(), video);

        return video;
    }
}
