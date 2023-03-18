package com.vivid.apiserver.domain.user.application;

import com.vivid.apiserver.domain.individual_video.application.query.IndividualVideoQueryService;
import com.vivid.apiserver.domain.individual_video.domain.IndividualVideo;
import com.vivid.apiserver.domain.individual_video.dto.dto.DashboardIndividualVideoDto;
import com.vivid.apiserver.domain.user.domain.User;
import com.vivid.apiserver.domain.user.dto.response.UserMyPageDashboardDataGetResponse;
import com.vivid.apiserver.domain.video_space.application.query.VideoSpaceParticipantQueryService;
import com.vivid.apiserver.domain.video_space.domain.VideoSpaceParticipant;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class UserMyPageService {

    private final IndividualVideoQueryService individualVideoQueryService;
    private final VideoSpaceParticipantQueryService videoSpaceParticipantQueryService;

    private final CurrentUserService currentUserService;

    /**
     * my page dashboard 렌더링에 필요한 데이터를 get하는 메소드
     */
    public UserMyPageDashboardDataGetResponse getMyPageDashboardData() {

        User user = currentUserService.getCurrentUser();

        List<VideoSpaceParticipant> videoSpaceParticipants = videoSpaceParticipantQueryService.findAllWithVideoSpaceByUserId(
                user.getId());

        List<DashboardIndividualVideoDto> dashboardIndividualVideos = getDashboardIndividualVideoDto(
                videoSpaceParticipants);

        return UserMyPageDashboardDataGetResponse.builder()
                .user(user)
                .dashboardIndividualVideos(dashboardIndividualVideos)
                .lastStudiedIndividualVideo(getLastAccessIndividualVideo(user, dashboardIndividualVideos))
                .videoSpaceCount(countVideoSpaceSize(videoSpaceParticipants))
                .completedIndividualVideoCount(countCompletedIndividualVideos(dashboardIndividualVideos))
                .build();
    }

    private Integer countVideoSpaceSize(List<VideoSpaceParticipant> videoSpaceParticipants) {

        long count = videoSpaceParticipants.stream()
                .map(VideoSpaceParticipant::getVideoSpace)
                .distinct()
                .count();

        return Math.toIntExact(count);
    }

    private List<DashboardIndividualVideoDto> getDashboardIndividualVideoDto(
            List<VideoSpaceParticipant> videoSpaceParticipants) {

        List<IndividualVideo> individualVideos = individualVideoQueryService.findAllWithVideoByVideoSpaceParticipant(
                videoSpaceParticipants);

        return individualVideos.stream()
                .map(DashboardIndividualVideoDto::of)
                .sorted((video1, video2) -> video2.getLastAccessTime().compareTo(video1.getLastAccessTime()))
                .collect(Collectors.toList());
    }

    private Integer countCompletedIndividualVideos(List<DashboardIndividualVideoDto> dashboardIndividualVideos) {
        return Math.toIntExact(dashboardIndividualVideos.stream()
                .filter(dashboardIndividualVideoDto -> dashboardIndividualVideoDto.getProgressRate().equals(200))
                .count());
    }

    private DashboardIndividualVideoDto getLastAccessIndividualVideo(User user,
            List<DashboardIndividualVideoDto> dashboardIndividualVideos) {

        if (user.getLastAccessIndividualVideoId() == null) {
            return null;
        }

        String lastAccessIndividualId = user.getLastAccessIndividualVideoId().toString();

        return dashboardIndividualVideos.stream()
                .filter(individualVideo -> lastAccessIndividualId.equals(individualVideo.getIndividualVideoId()))
                .findFirst()
                .get();
    }
}