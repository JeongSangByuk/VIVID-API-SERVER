package com.vivid.apiserver.domain.video_space.application;

import com.vivid.apiserver.domain.user.application.CurrentUserService;
import com.vivid.apiserver.domain.user.domain.User;
import com.vivid.apiserver.domain.user.dto.UserGetResponse;
import com.vivid.apiserver.domain.video.domain.Video;
import com.vivid.apiserver.domain.video.dto.request.VideoGetResponse;
import com.vivid.apiserver.domain.video.dto.response.HostedVideoGetResponse;
import com.vivid.apiserver.domain.video_space.application.query.VideoSpaceParticipantQueryService;
import com.vivid.apiserver.domain.video_space.application.query.VideoSpaceQueryService;
import com.vivid.apiserver.domain.video_space.dao.VideoSpaceDao;
import com.vivid.apiserver.domain.video_space.dao.VideoSpaceRepository;
import com.vivid.apiserver.domain.video_space.domain.VideoSpace;
import com.vivid.apiserver.domain.video_space.domain.VideoSpaceParticipant;
import com.vivid.apiserver.domain.video_space.dto.request.VideoSpaceSaveRequest;
import com.vivid.apiserver.domain.video_space.dto.response.HostedVideoSpaceGetResponse;
import com.vivid.apiserver.domain.video_space.dto.response.VideoSpaceGetResponse;
import com.vivid.apiserver.domain.video_space.dto.response.VideoSpaceSaveResponse;
import com.vivid.apiserver.domain.video_space.exception.VideoSpaceHostedAccessRequiredException;
import com.vivid.apiserver.global.error.exception.AccessDeniedException;
import com.vivid.apiserver.global.error.exception.ErrorCode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class VideoSpaceService {

    private final VideoSpaceRepository videoSpaceRepository;

    private final VideoSpaceQueryService videoSpaceQueryService;

    private final VideoSpaceParticipantQueryService videoSpaceParticipantQueryService;

    private final VideoSpaceDao videoSpaceDao;

    private final CurrentUserService currentUserService;

    public VideoSpaceGetResponse getOne(Long videoSpaceId) {

        User currentUser = currentUserService.getCurrentMember();

        // video space get
        VideoSpace videoSpace = videoSpaceQueryService.findById(videoSpaceId);

        // 로그인 user가 video space particiapnt인지 판단
        if (!videoSpaceQueryService.containsUser(videoSpace, currentUser.getEmail())) {
            throw new AccessDeniedException(ErrorCode.VIDEO_SPACE_ACCESS_DENIED);
        }

        // find video participant by user and video space
        VideoSpaceParticipant videoSpaceParticipant = videoSpaceParticipantQueryService.findByUserAndVideoSpace(
                currentUser,
                videoSpace);

        // videospace get response 생성.
        VideoSpaceGetResponse videoSpaceGetResponse = createVideoSpaceGetResponse(videoSpaceParticipant);

        return videoSpaceGetResponse;

    }

    // 로그인한 account의 video space, video get list get 메소드
    public List<VideoSpaceGetResponse> getList() {

        User currentUser = currentUserService.getCurrentMember();

        List<VideoSpaceGetResponse> videoSpaceGetResponseList = new ArrayList<>();

        // user가 참여해 있는 video space get
        user.getVideoSpaceParticipants().forEach(videoSpaceParticipant -> {

            VideoSpaceGetResponse videoSpaceGetResponse = createVideoSpaceGetResponse(videoSpaceParticipant);

            videoSpaceGetResponseList.add(videoSpaceGetResponse);
        });

        return videoSpaceGetResponseList;
    }

    // 자신이 생성한(host인) video space 하나를 get합니다.
    public HostedVideoSpaceGetResponse getHostedOne(Long videoSpaceId) {

        User currentUser = currentUserService.getCurrentMember();

        // video space get
        VideoSpace videoSpace = videoSpaceQueryService.findById(videoSpaceId);

        // host 권한 체크
        if (!videoSpace.getHostEmail().equals(email)) {
            throw new VideoSpaceHostedAccessRequiredException();
        }

        // response dto 생성
        HostedVideoSpaceGetResponse hostedVideoSpaceGetResponse = HostedVideoSpaceGetResponse.builder()
                .videoSpace(videoSpace).build();

        // create video response dto
        videoSpace.getVideos().forEach(video -> {
            hostedVideoSpaceGetResponse.addVideoGetResponse(HostedVideoGetResponse.builder()
                    .id(video.getId())
                    .title(video.getTitle())
                    .description(video.getDescription())
                    .isUploaded(video.isUploaded())
                    .thumbnailImagePath(video.getThumbnailImagePath()).build());

        });

        // create user response dto
        videoSpace.getVideoSpaceParticipants().forEach(videoSpaceParticipant -> {
            User user = videoSpaceParticipant.getUser();
            hostedVideoSpaceGetResponse.addUserGetResponse(UserGetResponse.builder()
                    .email(user.getEmail())
                    .name(user.getName())
                    .picture(user.getPicture())
                    .build());
        });

        return hostedVideoSpaceGetResponse;
    }

    // 자신이 생성한(host인) video space list를 get합니다.
    public List<HostedVideoSpaceGetResponse> getHostedList() {

        User currentUser = currentUserService.getCurrentMember();

        // find by host email
        List<VideoSpace> videoSpaces = videoSpaceRepository.findAllByHostEmail(email);

        List<HostedVideoSpaceGetResponse> hostedVideoSpaceGetResponseList = new ArrayList<>();

        // size 0일 경우 exception
        if (videoSpaces.size() == 0 || videoSpaces == null) {
            return hostedVideoSpaceGetResponseList;
        }

        // video space마다 response dto 생성
        videoSpaces.forEach(videoSpace -> {

            HostedVideoSpaceGetResponse hostedVideoSpaceGetResponse = HostedVideoSpaceGetResponse.builder()
                    .videoSpace(videoSpace).build();

            // create video response dto
            videoSpace.getVideos().forEach(video -> {
                hostedVideoSpaceGetResponse.addVideoGetResponse(HostedVideoGetResponse.builder()
                        .id(video.getId())
                        .title(video.getTitle())
                        .description(video.getDescription())
                        .isUploaded(video.isUploaded())
                        .thumbnailImagePath(video.getThumbnailImagePath()).build());
            });

            // create user response dto
            videoSpace.getVideoSpaceParticipants().forEach(videoSpaceParticipant -> {
                User user = videoSpaceParticipant.getUser();
                hostedVideoSpaceGetResponse.addUserGetResponse(UserGetResponse.builder()
                        .email(user.getEmail())
                        .name(user.getName())
                        .picture(user.getPicture())
                        .build());
            });

            hostedVideoSpaceGetResponseList.add(hostedVideoSpaceGetResponse);
        });

        return hostedVideoSpaceGetResponseList;
    }


    // video space save, 생성시 생성자에 대해서 participant 자동 생성
    public VideoSpaceSaveResponse save(VideoSpaceSaveRequest videoSpaceSaveRequest) {

        User currentUser = currentUserService.getCurrentMember();

        // video space 생성
        VideoSpace savedVideoSpace = videoSpaceRepository.save(videoSpaceSaveRequest.toEntity(user.getEmail()));

        // 생성자가 포함된 video space participant create, 연관 관계 매핑에 의해 생성된다.
        VideoSpaceParticipant videoSpaceParticipant = VideoSpaceParticipant.builder().videoSpace(savedVideoSpace)
                .user(user).build();
        savedVideoSpace.getVideoSpaceParticipants().add(videoSpaceParticipant);

        return VideoSpaceSaveResponse.builder().videoSpace(savedVideoSpace).build();
    }

    public void delete(Long videoSpaceId) {

        User user = userService.getByAccessToken();

        VideoSpace videoSpace = videoSpaceQueryService.findById(videoSpaceId);

        // 자신이 host인 경우만 삭제 가능, throw new
        if (!videoSpace.getHostEmail().equals(user.getEmail())) {
            throw new VideoSpaceHostedAccessRequiredException();
        }

        // 연관 관계 끊기.
        videoSpace.delete();

        // space delete
        videoSpaceRepository.deleteById(videoSpaceId);

    }


    // video space get response 생성 메소드, indivudal video data 포함.
    private VideoSpaceGetResponse createVideoSpaceGetResponse(VideoSpaceParticipant videoSpaceParticipant) {

        VideoSpace videoSpace = videoSpaceParticipant.getVideoSpace();

        List<Video> videoList = videoSpace.getVideos();

        // response dto 생성, 해당 dto에 video 리스트가 포함된다.
        VideoSpaceGetResponse videoSpaceGetResponse = VideoSpaceGetResponse.builder()
                .videoSpace(videoSpace)
                .build();

        HashMap<Long, VideoGetResponse> videoSpaceGetResponseHashMap = new HashMap<>();

        // 각각 video에 대해 individual video id랑 매칭하기 위해 hash map으로 생성
        videoList.forEach(video -> {

            // video 마다 dto 생성, 해당 dto는 individual_video_id도 포함한다.
            VideoGetResponse videoGetResponse = VideoGetResponse.builder()
                    .id(video.getId())
                    .title(video.getTitle())
                    .description(video.getDescription())
                    .thumbnailImagePath(video.getThumbnailImagePath())
                    .isUploaded(video.isUploaded())
                    .build();

            videoSpaceGetResponseHashMap.put(video.getId(), videoGetResponse);
        });

        // individual video 정보와 매핑.
        videoSpaceParticipant.getIndividualVideos().forEach(individualVideo -> {

            // video key 없으면 return, 잘못된 데이터.
            if (!videoSpaceGetResponseHashMap.containsKey(individualVideo.getVideo().getId())) {
                return;
            }

            VideoGetResponse videoGetResponse = videoSpaceGetResponseHashMap.get(individualVideo.getVideo().getId());

            // individual video data add
            videoGetResponse.changeIndividualVideoState(individualVideo.getId().toString(),
                    individualVideo.getLastAccessTime(), individualVideo.getProgressRate());

            videoSpaceGetResponse.addVideo(videoGetResponse);
        });

        return videoSpaceGetResponse;
    }
}
