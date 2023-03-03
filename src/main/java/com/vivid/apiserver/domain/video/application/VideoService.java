package com.vivid.apiserver.domain.video.application;

import com.vivid.apiserver.domain.individual_video.application.command.IndividualVideoCommandService;
import com.vivid.apiserver.domain.individual_video.dto.response.IndividualVideoDetailsGetResponse;
import com.vivid.apiserver.domain.user.application.CurrentUserService;
import com.vivid.apiserver.domain.user.domain.User;
import com.vivid.apiserver.domain.video.application.command.VideoCommandService;
import com.vivid.apiserver.domain.video.domain.Video;
import com.vivid.apiserver.domain.video.dto.request.VideoSaveRequest;
import com.vivid.apiserver.domain.video.dto.response.VideoSaveResponse;
import com.vivid.apiserver.domain.video.exception.VideoNotFoundException;
import com.vivid.apiserver.domain.video_space.application.VideoSpaceValidateService;
import com.vivid.apiserver.domain.video_space.application.query.VideoSpaceQueryService;
import com.vivid.apiserver.domain.video_space.domain.VideoSpace;
import com.vivid.apiserver.global.infra.storage.AwsS3Service;
import java.io.IOException;
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


    private final VideoSpaceQueryService videoSpaceQueryService;

    private final VideoCommandService videoCommandService;
    private final IndividualVideoCommandService individualVideoCommandService;

    private final VideoSpaceValidateService videoSpaceValidateService;

    private final CurrentUserService currentUserService;
    private final AwsS3Service awsS3Service;

    // multipart 파일을 통한 비디오 직접 업로드 메소드
    public VideoSaveResponse uploadByDirectUpload(MultipartFile multipartFile, Long videoSpaceId,
            VideoSaveRequest videoSaveRequest) {

        User currentUser = currentUserService.getCurrentUser();
        VideoSpace videoSpace = videoSpaceQueryService.findById(videoSpaceId);

        videoSpaceValidateService.checkHostUserAccess(videoSpace, currentUser.getEmail());

        Video video = videoSaveRequest.toEntity(videoSpace, currentUser.getEmail());

        awsS3Service.uploadVideoToS3ByMultipartFile(multipartFile, video.getId());
        videoCommandService.save(video);
        individualVideoCommandService.saveAllByParticipants(videoSpace.getVideoSpaceParticipants(), video);

        return new VideoSaveResponse(video.getId());
    }

    // download url을 통한 비디오 업로드 메소드
    public VideoSaveResponse uploadByDownloadUrl(String recordingDownloadUrl, Long videoSpaceId,
 드          VideoSaveRequest videoSaveRequest) throws IOException {

        User currentUser = currentUserService.getCurrentUser();

        // 해당 video의 video space find
        VideoSpace videoSpace = videoSpaceQueryService.findById(videoSpaceId);

        videoSpaceValidateService.checkHostUserAccess(videoSpace, currentUser.getEmail());

        // 객체 저장
        Video savedVideo = videoRepository.save(videoSaveRequest.toEntity(videoSpace, email));

        // aws upload by download url
        VideoSaveResponse videoSaveResponse = awsS3Service.uploadVideoToS3ByDownloadUrl(recordingDownloadUrl,
                savedVideo.getId());

        individualVideoCommandService.saveAllByParticipants(videoSpace.getVideoSpaceParticipants(), savedVideo);

        return videoSaveResponse;
    }

    // delete
    public void delete(Long videoId) {

        // 해당 video의 find
        Video video = videoRepository.findById(videoId).orElseThrow(VideoNotFoundException::new);

        // 삭제 연관 관계 편의 메소드
        video.delete();

        // delete by id
        videoRepository.deleteById(videoId);
    }

    // upload 된 후 video의 uploaded 상태 변경
    public void changeUploadState(Long videoId, boolean isUploaded) throws IOException {

        // 해당 video의 find
//        Video video = videoDao.findById(videoId);
        Video video = videoRepository.findById(videoId).orElseThrow(VideoNotFoundException::new);

        // uploaded true
        video.changeIsUploaded(isUploaded);

        // get thumbnail imgae
        List<String> visualIndexImages = awsS3Service.getVisualIndexImages(videoId);

        // video thumbnail change
        video.changeThumbnailImagePath(visualIndexImages.get(0));

    }

    public IndividualVideoDetailsGetResponse getFilePath(Long videoId) throws IOException {

        // video file path get
        String videoFilePath = awsS3Service.getVideoFilePath(videoId);

        // visual index file path list get
        List<String> visualIndexImageFilePathList = awsS3Service.getVisualIndexImages(videoId);

        IndividualVideoDetailsGetResponse individualVideoDetailsGetResponse = IndividualVideoDetailsGetResponse.builder()
                .videoFilePath(videoFilePath)
                .visualIndexImageFilePathList(visualIndexImageFilePathList)
                .build();

        return individualVideoDetailsGetResponse;
    }


}
