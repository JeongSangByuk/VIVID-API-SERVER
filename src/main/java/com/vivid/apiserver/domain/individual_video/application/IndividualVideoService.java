package com.vivid.apiserver.domain.individual_video.application;

import com.vivid.apiserver.domain.individual_video.application.command.IndividualVideoCommandService;
import com.vivid.apiserver.domain.individual_video.application.query.IndividualVideoQueryService;
import com.vivid.apiserver.domain.individual_video.domain.IndividualVideo;
import com.vivid.apiserver.domain.individual_video.dto.response.IndividualVideoDetailsGetResponse;
import com.vivid.apiserver.domain.individual_video.dto.response.SnapshotImageUploadResponse;
import com.vivid.apiserver.domain.user.application.CurrentUserService;
import com.vivid.apiserver.domain.user.application.command.UserCommandService;
import com.vivid.apiserver.domain.user.domain.User;
import com.vivid.apiserver.global.infra.storage.AwsS3Service;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class IndividualVideoService {

    private final IndividualVideoQueryService individualVideoQueryService;

    private final UserCommandService userCommandService;
    private final IndividualVideoCommandService individualVideoCommandService;

    private final CurrentUserService currentUserService;
    private final AwsS3Service awsS3Service;

    /**
     * individual vidoe의 상세 정보 get 메소드
     */
    public IndividualVideoDetailsGetResponse getDetailsById(String individualVideoId) {

        User user = currentUserService.getCurrentUser();

        IndividualVideo individualVideo = individualVideoQueryService.findWithVideoAndVideoSpaceParticipantById(
                UUID.fromString(individualVideoId));

        currentUserService.checkValidUserAccess(user.getEmail(), individualVideo.getVideoSpaceParticipant().getEmail());

        updateIndividualVideoLastAccess(individualVideoId, user, individualVideo);

        return createIndividualVideoDetailsGetResponse(individualVideo);
    }

    /**
     * snapshot 사진을 업로드하는 메소드
     */
    public SnapshotImageUploadResponse uploadSnapshotImage(MultipartFile file, String individualVideoId,
            Long videoTime) {

        IndividualVideo individualVideo = individualVideoQueryService.findById(individualVideoId);

        currentUserService.checkValidUserAccess(individualVideo.getVideoSpaceParticipant().getEmail());

        String snapshotImageFilePath = awsS3Service.uploadSnapshotImagesToS3(file, individualVideoId, videoTime);

        return SnapshotImageUploadResponse.of(snapshotImageFilePath, videoTime);
    }

    /**
     * individual video의 마지막 접근 시간을 업데이트하는 메소드
     */
    public void updateLastAccessTime(String individualVideoId) {

        IndividualVideo individualVideo = individualVideoQueryService.findById(individualVideoId);

        currentUserService.checkValidUserAccess(individualVideo.getVideoSpaceParticipant().getEmail());

        individualVideoCommandService.changeLastAccessTime(individualVideo);
    }

    /**
     * individual video의 진행도를 업데이트하는 메소드
     */
    public void updateProgressRate(String individualVideoId, Integer progressRate) {

        IndividualVideo individualVideo = individualVideoQueryService.findById(individualVideoId);

        currentUserService.checkValidUserAccess(individualVideo.getVideoSpaceParticipant().getEmail());

        individualVideoCommandService.changeProgressRate(individualVideo, progressRate);
    }

    /**
     * individual video를 삭제하는 메소드
     */
    public void deleteById(String individualVideoId) {

        IndividualVideo individualVideo = individualVideoQueryService.findById(individualVideoId);

        currentUserService.checkValidUserAccess(individualVideo.getVideoSpaceParticipant().getEmail());

        individualVideoCommandService.delete(individualVideo);
    }

    private IndividualVideoDetailsGetResponse createIndividualVideoDetailsGetResponse(IndividualVideo individualVideo) {

        Long videoId = individualVideo.getVideo().getId();
        String videoFilePath = awsS3Service.getVideoFilePath(videoId);
        List<String> visualIndexImages = awsS3Service.getVisualIndexImages(videoId);

        return IndividualVideoDetailsGetResponse.builder()
                .individualVideo(individualVideo)
                .videoFilePath(videoFilePath)
                .visualIndexImageFilePathList(visualIndexImages)
                .build();
    }

    private void updateIndividualVideoLastAccess(String individualVideoId, User user, IndividualVideo individualVideo) {
        userCommandService.changeLastAccessIndividualVideoId(user, UUID.fromString(individualVideoId));
        individualVideo.changeLastAccessTime();
    }

}
