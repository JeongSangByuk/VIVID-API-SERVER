package com.vivid.apiserver.domain.individual_video.dto.dto;

import com.vivid.apiserver.domain.individual_video.domain.IndividualVideo;
import com.vivid.apiserver.domain.video.domain.Video;
import com.vivid.apiserver.domain.video_space.domain.VideoSpace;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class DashboardIndividualVideoDto {

    private Long videoSpaceId;

    private String videoSpaceName;

    private String videoSpaceDescription;

    private String individualVideoId;

    private LocalDateTime updatedDate;

    private String videoTitle;

    private String videoDescription;

    private Integer progressRate;

    private LocalDateTime lastAccessTime;

    public static DashboardIndividualVideoDto of(IndividualVideo individualVideo) {

        Video video = individualVideo.getVideo();
        VideoSpace videoSpace = individualVideo.getVideoSpaceParticipant().getVideoSpace();

        return DashboardIndividualVideoDto.builder()
                .videoSpaceId(videoSpace.getId())
                .videoSpaceName(videoSpace.getName())
                .videoSpaceDescription(videoSpace.getDescription())
                .individualVideoId(individualVideo.getId().toString())
                .updatedDate(individualVideo.getLastAccessTime())
                .videoTitle(video.getTitle())
                .videoDescription(video.getDescription())
                .progressRate(individualVideo.getProgressRate())
                .lastAccessTime(individualVideo.getLastAccessTime())
                .build();
    }
}
