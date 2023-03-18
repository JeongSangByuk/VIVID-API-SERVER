package com.vivid.apiserver.domain.video_space.application;

import com.vivid.apiserver.domain.user.application.CurrentUserService;
import com.vivid.apiserver.domain.user.domain.User;
import com.vivid.apiserver.domain.user.dto.response.UserGetResponse;
import com.vivid.apiserver.domain.video.application.query.VideoQueryService;
import com.vivid.apiserver.domain.video.domain.Video;
import com.vivid.apiserver.domain.video.dto.response.HostedVideoGetResponse;
import com.vivid.apiserver.domain.video_space.application.query.VideoSpaceParticipantQueryService;
import com.vivid.apiserver.domain.video_space.application.query.VideoSpaceQueryService;
import com.vivid.apiserver.domain.video_space.domain.VideoSpace;
import com.vivid.apiserver.domain.video_space.domain.VideoSpaceParticipant;
import com.vivid.apiserver.domain.video_space.dto.response.HostedVideoSpaceGetResponse;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class VideoSpaceHostService {

    private final VideoQueryService videoQueryService;
    private final VideoSpaceParticipantQueryService videoSpaceParticipantQueryService;
    private final VideoSpaceQueryService videoSpaceQueryService;

    private final CurrentUserService currentUserService;
    private final VideoSpaceValidateService videoSpaceValidateService;


    /**
     * 특정 video video space의 host 정보 get 메소드
     */
    public HostedVideoSpaceGetResponse getHostedOne(Long videoSpaceId) {

        User currentUser = currentUserService.getCurrentUser();
        VideoSpace videoSpace = videoSpaceQueryService.findById(videoSpaceId);

        videoSpaceValidateService.checkHostUserAccess(videoSpace, currentUser.getEmail());

        List<VideoSpaceParticipant> videoSpaceParticipants = videoSpaceParticipantQueryService.findAllWithUserByVideoSpaces(
                List.of(videoSpace));

        return createHostedVideoSpaceGetResponse(videoSpace, videoSpace.getVideos(), videoSpaceParticipants);
    }

    /**
     * 자신이 생생한(host인) video space list를 get합니다.
     */
    public List<HostedVideoSpaceGetResponse> getHostedList() {

        User currentUser = currentUserService.getCurrentUser();
        List<VideoSpace> videoSpaces = videoSpaceQueryService.findAllByHostedEmail(currentUser.getEmail());

        Map<Long, List<Video>> videoMap = videoQueryService.findAllByVideoSpaces(videoSpaces).stream()
                .collect(Collectors.groupingBy(video -> video.getVideoSpace().getId()));

        Map<Long, List<VideoSpaceParticipant>> videoSpaceParticipantMap = videoSpaceParticipantQueryService
                .findAllWithUserByVideoSpaces(videoSpaces).stream()
                .collect(Collectors.groupingBy(videoSpaceParticipant -> videoSpaceParticipant.getVideoSpace().getId()));

        return videoSpaces.stream()
                .map(videoSpace -> createHostedVideoSpaceGetResponseFromMap(videoMap, videoSpaceParticipantMap,
                        videoSpace))
                .collect(Collectors.toList());
    }

    private HostedVideoSpaceGetResponse createHostedVideoSpaceGetResponse(VideoSpace videoSpace, List<Video> videos,
            List<VideoSpaceParticipant> videoSpaceParticipants) {

        List<HostedVideoGetResponse> hostedVideoGetResponses = videos.stream()
                .map(HostedVideoGetResponse::from)
                .collect(Collectors.toList());

        List<UserGetResponse> userGetResponses = videoSpaceParticipants.stream()
                .map(videoSpaceParticipant -> UserGetResponse.from(videoSpaceParticipant.getUser()))
                .collect(Collectors.toList());

        return HostedVideoSpaceGetResponse.builder()
                .videoSpace(videoSpace)
                .videos(hostedVideoGetResponses)
                .users(userGetResponses).build();
    }

    private HostedVideoSpaceGetResponse createHostedVideoSpaceGetResponseFromMap(
            Map<Long, List<Video>> videoMap,
            Map<Long, List<VideoSpaceParticipant>> videoSpaceParticipantMap, VideoSpace videoSpace) {

        List<Video> videos = videoMap.getOrDefault(videoSpace.getId(), Collections.emptyList());

        List<VideoSpaceParticipant> videoSpaceParticipants = videoSpaceParticipantMap.getOrDefault(
                videoSpace.getId(), Collections.emptyList());

        return createHostedVideoSpaceGetResponse(videoSpace, videos, videoSpaceParticipants);
    }

}
