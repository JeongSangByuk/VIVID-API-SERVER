package com.vivid.apiserver.domain.user.application;

import com.vivid.apiserver.domain.individual_video.dto.dto.DashboardIndividualVideoDto;
import com.vivid.apiserver.domain.user.domain.User;
import com.vivid.apiserver.domain.user.dto.response.UserMyPageDashboardDataGetResponse;
import com.vivid.apiserver.domain.video_space.application.VideoSpaceManageService;
import com.vivid.apiserver.domain.video_space.domain.VideoSpace;
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

    private final VideoSpaceManageService videoSpaceManageService;
    private final CurrentUserService currentUserService;

    public UserMyPageDashboardDataGetResponse getMyPageDashboardData() {

        User user = currentUserService.getCurrentUser();

        // TODO video-part 부터 video space, individual video까지 전부... N+1 발생함
        List<VideoSpace> videoSpaces = user.getVideoSpaceParticipants().stream()
                .map(VideoSpaceParticipant::getVideoSpace)
                .collect(Collectors.toList());

        List<DashboardIndividualVideoDto> dashboardIndividualVideos = getDashboardIndividualVideoDto(videoSpaces);

        Integer completedIndividualVideoCount = countCompletedIndividualVideos(dashboardIndividualVideos);

        DashboardIndividualVideoDto lastAccessIndividualVideo = getLastAccessIndividualVideo(user,
                dashboardIndividualVideos);

        return UserMyPageDashboardDataGetResponse.builder()
                .user(user)
                .lastStudiedIndividualVideo(lastAccessIndividualVideo)
                .dashboardIndividualVideos(dashboardIndividualVideos)
                .videoSpaceCount(videoSpaces.size())
                .totalIndividualVideoCount(dashboardIndividualVideos.size())
                .completedIndividualVideoCount(completedIndividualVideoCount)
                .build();
    }

    private List<DashboardIndividualVideoDto> getDashboardIndividualVideoDto(List<VideoSpace> videoSpaces) {
        return videoSpaces.stream()
                .map(videoSpaceManageService::findAllIndividualVideosByVideoSpace)
                .flatMap(List::stream)
                .distinct()
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