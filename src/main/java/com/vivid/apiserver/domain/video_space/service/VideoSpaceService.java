package com.vivid.apiserver.domain.video_space.service;

import com.vivid.apiserver.domain.individual_video.domain.IndividualVideo;
import com.vivid.apiserver.domain.individual_video.service.query.IndividualVideoQueryService;
import com.vivid.apiserver.domain.user.domain.User;
import com.vivid.apiserver.domain.user.service.CurrentUserService;
import com.vivid.apiserver.domain.video.domain.Video;
import com.vivid.apiserver.domain.video.service.query.VideoQueryService;
import com.vivid.apiserver.domain.video_space.domain.VideoSpace;
import com.vivid.apiserver.domain.video_space.domain.VideoSpaceParticipant;
import com.vivid.apiserver.domain.video_space.dto.request.VideoSpaceSaveRequest;
import com.vivid.apiserver.domain.video_space.dto.response.VideoSpaceGetResponse;
import com.vivid.apiserver.domain.video_space.dto.response.VideoSpaceSaveResponse;
import com.vivid.apiserver.domain.video_space.service.query.VideoSpaceParticipantQueryService;
import com.vivid.apiserver.domain.video_space.service.query.VideoSpaceQueryService;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class VideoSpaceService {

    private final VideoQueryService videoQueryService;
    private final VideoSpaceQueryService videoSpaceQueryService;
    private final VideoSpaceParticipantQueryService videoSpaceParticipantQueryService;
    private final IndividualVideoQueryService individualVideoQueryService;

    private final CurrentUserService currentUserService;
    private final VideoSpaceManageService videoSpaceManageService;
    private final VideoSpaceValidateService videoSpaceValidateService;

    /**
     * 특정 video space 정보 get 메소드
     */
    public VideoSpaceGetResponse getOne(Long videoSpaceId) {

        User currentUser = currentUserService.getCurrentUser();

        VideoSpaceParticipant videoSpaceParticipant = videoSpaceParticipantQueryService.findWithVideoSpaceByUserIdAndVideoSpaceId(
                currentUser.getId(), videoSpaceId);

        List<IndividualVideo> individualVideos = individualVideoQueryService.findAllWithVideoByVideoSpaceParticipant(
                List.of(videoSpaceParticipant));

        return VideoSpaceGetResponse.of(videoSpaceParticipant, individualVideos);
    }

    /**
     * 유저가 속한 video space 전부 get 메소드
     */
    public List<VideoSpaceGetResponse> getList() {

        User user = currentUserService.getCurrentUser();

        List<VideoSpaceParticipant> videoSpaceParticipants = videoSpaceParticipantQueryService.findAllWithVideoSpaceByUserId(
                user.getId());

        List<IndividualVideo> individualVideos = individualVideoQueryService.findAllWithVideoByVideoSpaceParticipant(
                videoSpaceParticipants);

        Map<Long, List<IndividualVideo>> individualVideosMap = individualVideos.stream()
                .collect(Collectors.groupingBy(this::getVideoSpaceIdFromIndividualVideo));

        return videoSpaceParticipants.stream()
                .map(videoSpaceParticipant -> VideoSpaceGetResponse.of(videoSpaceParticipant, individualVideosMap))
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

        VideoSpace videoSpace = videoSpaceQueryService.findWithVideoSpaceParticipantsById(videoSpaceId);
        List<VideoSpaceParticipant> videoSpaceParticipants = videoSpace.getVideoSpaceParticipants();
        List<Video> videos = videoQueryService.findAllByVideoSpaces(List.of(videoSpace));

        videoSpaceValidateService.checkHostUserAccess(videoSpace, currentUser.getEmail());

        videoSpaceManageService.deleteVideoSpace(videoSpace, videoSpaceParticipants, videos);
    }

    private Long getVideoSpaceIdFromIndividualVideo(IndividualVideo individualVideo) {
        return individualVideo.getVideoSpaceParticipant().getVideoSpace().getId();
    }
}
