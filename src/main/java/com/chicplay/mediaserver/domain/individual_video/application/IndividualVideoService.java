package com.chicplay.mediaserver.domain.individual_video.application;

import com.chicplay.mediaserver.domain.user.application.UserService;
import com.chicplay.mediaserver.domain.individual_video.dao.IndividualVideoDao;
import com.chicplay.mediaserver.domain.individual_video.dao.repository.IndividualVideoRepository;
import com.chicplay.mediaserver.domain.individual_video.domain.IndividualVideo;
import com.chicplay.mediaserver.domain.individual_video.dto.IndividualVideoDetailsGetResponse;
import com.chicplay.mediaserver.domain.individual_video.dto.IndividualVideoGetResponse;
import com.chicplay.mediaserver.domain.individual_video.dto.SnapshotImageUploadResponse;
import com.chicplay.mediaserver.domain.individual_video.exception.IndividualVideoNotFoundException;
import com.chicplay.mediaserver.domain.video.domain.Video;
import com.chicplay.mediaserver.domain.video_space.application.VideoSpaceParticipantFindService;
import com.chicplay.mediaserver.domain.video_space.application.VideoSpaceParticipantService;
import com.chicplay.mediaserver.domain.video_space.application.VideoSpaceService;
import com.chicplay.mediaserver.domain.video_space.domain.VideoSpace;
import com.chicplay.mediaserver.domain.video_space.domain.VideoSpaceParticipant;
import com.chicplay.mediaserver.global.infra.storage.AwsS3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class IndividualVideoService {

    private final UserService userService;

    private final IndividualVideoRepository individualVideoRepository;

    private final IndividualVideoDao individualVideoDao;

    private final VideoSpaceParticipantFindService videoSpaceParticipantFindService;

    private final AwsS3Service awsS3Service;

    // find by id
    public IndividualVideo findById(String individualVideoId) {

        IndividualVideo individualVideo = individualVideoRepository.findById(UUID.fromString(individualVideoId))
                .orElseThrow(IndividualVideoNotFoundException::new);

        return individualVideo;
    }

    public IndividualVideoDetailsGetResponse getDetailsById(String individualVideoId) throws IOException {

        IndividualVideo individualVideo = findById(individualVideoId);

        // login user 권한 체크
        userService.checkValidUserAccess(individualVideo.getVideoSpaceParticipant().getUser().getEmail());

        // last access time update
        individualVideo.changeLastAccessTime();

        // last access video update
        userService.changeLastAccessIndividualVideoId(UUID.fromString(individualVideoId));

        // video file path get
        String videoFilePath = awsS3Service.getVideoFilePath(individualVideo.getVideo().getId());

        // visualIndexImageList생성
        List<String> visualIndexImages = awsS3Service.getVisualIndexImages(individualVideo.getVideo().getId());

        // response dto 생성
        IndividualVideoDetailsGetResponse individualVideoDetailsGetResponse = IndividualVideoDetailsGetResponse.builder()
                .individualVideo(individualVideo)
                .videoFilePath(videoFilePath)
                .visualIndexImageFilePathList(visualIndexImages)
                .build();

        return individualVideoDetailsGetResponse;
    }

    // video save 된 후, video space의 account 전부에게 individual video 생성
    public void createAfterVideoSaved(Video video, VideoSpace videoSpace) {

        List<IndividualVideo> individualVideos = new ArrayList<>();

        // 각각의 space 참가자의 individual video를 생성하기 위함
        videoSpace.getVideoSpaceParticipants().forEach(videoSpaceParticipant -> {

            // list에 individulaVideo 객체를 각각 생성해서 add
            individualVideos.add(IndividualVideo.builder()
                    .video(video)
                    .videoSpaceParticipant(videoSpaceParticipant)
                    .build());
        });

        // jpa bulk insert, uuid 방식이어서 jpa bulk insert 가능
        individualVideoRepository.saveAll(individualVideos);

    }

    public void deleteById(String individualVideoId) {

        IndividualVideo individualVideo = findById(individualVideoId);

        // login user 권한 체크
        userService.checkValidUserAccess(individualVideo.getVideoSpaceParticipant().getUser().getEmail());

        // 연관 관계 끊기
        individualVideo.delete();

        // delete by id
        individualVideoRepository.deleteById(UUID.fromString(individualVideoId));

    }


    // image upload service
    public SnapshotImageUploadResponse uploadSnapshotImage(MultipartFile file, String individualVideoId, Long videoTime) {

        IndividualVideo individualVideo = findById(individualVideoId);

        // login user 권한 체크
        userService.checkValidUserAccess(individualVideo.getVideoSpaceParticipant().getUser().getEmail());

        // image upload, upload된 image file path get
        String snapshotImageFilePath = awsS3Service.uploadSnapshotImagesToS3(file, individualVideoId, videoTime);

        // responses dto로 변환
        SnapshotImageUploadResponse response = SnapshotImageUploadResponse.builder()
                .filePath(snapshotImageFilePath)
                .time(videoTime).build();

        return response;
    }

    // 해당 individual video의 마지막 접근 시간을 update하는 메소드
    public void updateLastAccessTime(String individualVideoId) {

        // individual video get
        IndividualVideo individualVideo = findById(individualVideoId);

        // login user 권한 체크
        userService.checkValidUserAccess(individualVideo.getVideoSpaceParticipant().getUser().getEmail());

        // last access time update
        individualVideo.changeLastAccessTime();
    }

    // 해당 individual video의 progress rate를 update하는 메소드
    public void updateProgressRate(String individualVideoId, Long progressRate) {

        // individual video get
        IndividualVideo individualVideo = findById(individualVideoId);

        // login user 권한 체크
        userService.checkValidUserAccess(individualVideo.getVideoSpaceParticipant().getUser().getEmail());

        // progress rate update
        individualVideo.changeProgressRate(progressRate);
    }

    public void checkValidUserAccessId(String individualVideoId) {

        // individual video get
        IndividualVideo individualVideo = findById(individualVideoId);

        // user 권한 체크
        userService.checkValidUserAccess(individualVideo.getVideoSpaceParticipant().getUser().getEmail());
    }

    // 참가해있는 space의 individual video get
    public List<IndividualVideoGetResponse> findAllByVideoParticipantId(Long videoSpaceParticipantId) {

        // 로그인 id와 videoSpaceParticipantId의 user id가 같은지 판단.
        VideoSpaceParticipant videoSpaceParticipant = videoSpaceParticipantFindService.findById(videoSpaceParticipantId);
        userService.checkValidUserAccess(videoSpaceParticipant.getUser().getEmail());

        // find all individual videos
        List<IndividualVideo> individualVideos = individualVideoRepository.findAllByVideoSpaceParticipantId(videoSpaceParticipantId);

        List<IndividualVideoGetResponse> individualVideoGetResponses = new ArrayList<>();

        if (individualVideos == null || individualVideos.isEmpty())
            return individualVideoGetResponses;

        individualVideos.forEach(individualVideo -> {
            individualVideoGetResponses.add(IndividualVideoGetResponse.builder()
                    .individualVideo(individualVideo)
                    .build());
        });

        return individualVideoGetResponses;
    }

}
