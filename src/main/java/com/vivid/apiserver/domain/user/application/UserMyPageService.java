package com.vivid.apiserver.domain.user.application;

import com.vivid.apiserver.domain.individual_video.dto.dto.DashboardIndividualVideoDto;
import com.vivid.apiserver.domain.user.domain.User;
import com.vivid.apiserver.domain.user.dto.UserMyPageDashboardDataGetResponse;
import com.vivid.apiserver.domain.video.dto.response.VideoGetResponse;
import com.vivid.apiserver.domain.video_space.application.VideoSpaceService;
import com.vivid.apiserver.domain.video_space.dto.response.VideoSpaceGetResponse;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class UserMyPageService {


    private final VideoSpaceService videoSpaceService;

    private final CurrentUserService currentUserService;

    // dashboard의 user data를 get합니다.
    public UserMyPageDashboardDataGetResponse getMyPageDashboardData() {

        User user = currentUserService.getCurrentUser();

        // user video space get, inner video data get
        List<VideoSpaceGetResponse> videoSpaces = videoSpaceService.getList();

        DashboardIndividualVideoDto lastStudiedIndividualVideo = null;
        Long videoSpaceCount = 0L;
        Long totalIndividualVideoCount = 0L;
        Long completedIndividualVideoCount = 0L;

        List<DashboardIndividualVideoDto> dashboardIndividualVideos = new ArrayList<>();

        // 각각의 individual video를 create
        for (VideoSpaceGetResponse videoSpace : videoSpaces) {

            // video space cnt ++
            videoSpaceCount += 1;

            for (VideoGetResponse video : videoSpace.getVideos()) {

                // totla video cnt ++
                totalIndividualVideoCount += 1;

                // completed video ++
                if (video.getProgressRate() == 100L) {
                    completedIndividualVideoCount += 1;
                }

                DashboardIndividualVideoDto individualVideo = DashboardIndividualVideoDto.builder()
                        .video(video)
                        .videoSpace(videoSpace)
                        .build();

                // lasted studied individual video
                if (user.getLastAccessIndividualVideoId() != null
                        && user.getLastAccessIndividualVideoId().toString()
                        .equals(individualVideo.getIndividualVideoId())) {
                    lastStudiedIndividualVideo = individualVideo;
                }

                // 각각의 individual video dto로 add
                dashboardIndividualVideos.add(individualVideo);
            }
        }

        // 접근 시간 순으로 정렬
        dashboardIndividualVideos.sort((o1, o2) -> o2.getLastAccessTime().compareTo(o1.getLastAccessTime()));

        // response dto 생성
        UserMyPageDashboardDataGetResponse userMyPageDashboardDataGetResponse = UserMyPageDashboardDataGetResponse.builder()
                .user(user)
                .lastStudiedIndividualVideo(lastStudiedIndividualVideo)
                .videoSpaceCount(videoSpaceCount)
                .dashboardIndividualVideos(dashboardIndividualVideos)
                .totalIndividualVideoCount(totalIndividualVideoCount)
                .completedIndividualVideoCount(completedIndividualVideoCount)
                .build();

        return userMyPageDashboardDataGetResponse;
    }
}
