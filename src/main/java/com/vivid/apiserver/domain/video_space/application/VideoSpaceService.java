package com.vivid.apiserver.domain.video_space.application;

import com.vivid.apiserver.domain.user.application.CurrentUserService;
import com.vivid.apiserver.domain.user.domain.User;
import com.vivid.apiserver.domain.user.dto.UserGetResponse;
import com.vivid.apiserver.domain.video.dto.response.HostedVideoGetResponse;
import com.vivid.apiserver.domain.video_space.application.query.VideoSpaceParticipantQueryService;
import com.vivid.apiserver.domain.video_space.application.query.VideoSpaceQueryService;
import com.vivid.apiserver.domain.video_space.domain.VideoSpace;
import com.vivid.apiserver.domain.video_space.domain.VideoSpaceParticipant;
import com.vivid.apiserver.domain.video_space.dto.request.VideoSpaceSaveRequest;
import com.vivid.apiserver.domain.video_space.dto.response.HostedVideoSpaceGetResponse;
import com.vivid.apiserver.domain.video_space.dto.response.VideoSpaceGetResponse;
import com.vivid.apiserver.domain.video_space.dto.response.VideoSpaceSaveResponse;
import com.vivid.apiserver.domain.video_space.exception.VideoSpaceHostedAccessRequiredException;
import java.util.ArrayList;
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
public class VideoSpaceService {

    private final VideoSpaceQueryService videoSpaceQueryService;
    private final VideoSpaceParticipantQueryService videoSpaceParticipantQueryService;

    private final CurrentUserService currentUserService;
    private final VideoSpaceValidationService videoSpaceValidationService;


    /**
     * 특정 video space 정보 get 메소드
     */
    public VideoSpaceGetResponse getOne(Long videoSpaceId) {

        User currentUser = currentUserService.getCurrentMember();
        VideoSpace videoSpace = videoSpaceQueryService.findById(videoSpaceId);

        videoSpaceValidationService.checkVideoSpaceParticipant(videoSpace, currentUser);

        VideoSpaceParticipant videoSpaceParticipant =
                videoSpaceParticipantQueryService.findByUserAndVideoSpace(currentUser, videoSpace);

        return VideoSpaceGetResponse.of(videoSpace, videoSpaceParticipant.getIndividualVideos());
    }

    /**
     * 유저가 속한 video space 전부 get 메소드
     */
    public List<VideoSpaceGetResponse> getList() {

        // TODO fetch join
        User currentUser = currentUserService.getCurrentMember();
        List<VideoSpaceParticipant> videoSpaceParticipants = currentUser.getVideoSpaceParticipants();

        return videoSpaceParticipants.stream()
                .map(videoSpaceParticipant -> getOne(videoSpaceParticipant.getVideoSpace().getId()))
                .collect(Collectors.toList());
    }

    // 자신이 생성한(host인) video space 하나를 get합니다.
    public HostedVideoSpaceGetResponse getHostedOne(Long videoSpaceId) {

        User currentUser = currentUserService.getCurrentMember();
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
}
