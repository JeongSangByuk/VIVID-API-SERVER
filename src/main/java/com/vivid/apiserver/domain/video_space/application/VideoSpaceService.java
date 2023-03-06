package com.vivid.apiserver.domain.video_space.application;

import com.vivid.apiserver.domain.user.application.CurrentUserService;
import com.vivid.apiserver.domain.user.domain.User;
import com.vivid.apiserver.domain.user.dto.response.UserGetResponse;
import com.vivid.apiserver.domain.video.dto.response.HostedVideoGetResponse;
import com.vivid.apiserver.domain.video_space.application.query.VideoSpaceParticipantQueryService;
import com.vivid.apiserver.domain.video_space.application.query.VideoSpaceQueryService;
import com.vivid.apiserver.domain.video_space.domain.VideoSpace;
import com.vivid.apiserver.domain.video_space.domain.VideoSpaceParticipant;
import com.vivid.apiserver.domain.video_space.dto.request.VideoSpaceSaveRequest;
import com.vivid.apiserver.domain.video_space.dto.response.HostedVideoSpaceGetResponse;
import com.vivid.apiserver.domain.video_space.dto.response.VideoSpaceGetResponse;
import com.vivid.apiserver.domain.video_space.dto.response.VideoSpaceSaveResponse;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class VideoSpaceService {

    private final VideoSpaceQueryService videoSpaceQueryService;
    private final VideoSpaceParticipantQueryService videoSpaceParticipantQueryService;

    private final CurrentUserService currentUserService;
    private final VideoSpaceManageService videoSpaceManageService;
    private final VideoSpaceValidateService videoSpaceValidateService;

    /**
     * 특정 video space 정보 get 메소드
     */
    public VideoSpaceGetResponse getOne(Long videoSpaceId) {

        User currentUser = currentUserService.getCurrentUser();
        VideoSpace videoSpace = videoSpaceQueryService.findById(videoSpaceId);

        videoSpaceValidateService.checkVideoSpaceParticipant(videoSpace, currentUser);

        VideoSpaceParticipant videoSpaceParticipant =
                videoSpaceParticipantQueryService.findByUserAndVideoSpace(currentUser, videoSpace);

        return VideoSpaceGetResponse.of(videoSpace, videoSpaceParticipant.getIndividualVideos());
    }

    /**
     * 유저가 속한 video space 전부 get 메소드
     */
    public List<VideoSpaceGetResponse> getList() {

        // TODO fetch join
        User currentUser = currentUserService.getCurrentUser();
        List<VideoSpaceParticipant> videoSpaceParticipants = currentUser.getVideoSpaceParticipants();

        return videoSpaceParticipants.stream()
                .map(videoSpaceParticipant -> getOne(videoSpaceParticipant.getVideoSpace().getId()))
                .collect(Collectors.toList());
    }

    /**
     * 특정 video video space의 host 정보 get 메소드
     */
    public HostedVideoSpaceGetResponse getHostedOne(Long videoSpaceId) {

        User currentUser = currentUserService.getCurrentUser();
        VideoSpace videoSpace = videoSpaceQueryService.findById(videoSpaceId);

        videoSpaceValidateService.checkHostUserAccess(videoSpace, currentUser.getEmail());

        List<HostedVideoGetResponse> hostedVideoGetResponses = videoSpace.getVideos().stream()
                .map(HostedVideoGetResponse::from)
                .collect(Collectors.toList());

        List<UserGetResponse> userGetResponses = videoSpace.getVideoSpaceParticipants().stream()
                .map(videoSpaceParticipant -> UserGetResponse.from(videoSpaceParticipant.getUser()))
                .collect(Collectors.toList());

        return HostedVideoSpaceGetResponse.builder()
                .videoSpace(videoSpace)
                .videos(hostedVideoGetResponses)
                .users(userGetResponses).build();
    }

    /**
     * 자신이 생생한(host인) video space list를 get합니다.
     */
    public List<HostedVideoSpaceGetResponse> getHostedList() {

        User currentUser = currentUserService.getCurrentUser();
        List<VideoSpace> videoSpaces = videoSpaceQueryService.findListByHostedEmail(currentUser.getEmail());

        return videoSpaces.stream()
                .map(videoSpace -> getHostedOne(videoSpace.getId()))
                .collect(Collectors.toList());
    }

    /**
     * video space 생성 메소드
     */
    public VideoSpaceSaveResponse save(VideoSpaceSaveRequest videoSpaceSaveRequest) {

        User currentUser = currentUserService.getCurrentUser();
        VideoSpace videoSpace = videoSpaceSaveRequest.toEntity(currentUser.getEmail());

        videoSpaceManageService.createInitialVideoSpace(currentUser, videoSpace);

        return VideoSpaceSaveResponse.from(videoSpace);
    }

    /**
     * videos space 삭제 메소드
     */
    public void delete(Long videoSpaceId) {

        User currentUser = currentUserService.getCurrentUser();
        VideoSpace videoSpace = videoSpaceQueryService.findById(videoSpaceId);

        videoSpaceValidateService.checkHostUserAccess(videoSpace, currentUser.getEmail());

        videoSpaceManageService.deleteVideoSpace(videoSpace);
    }
}
