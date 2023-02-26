package com.vivid.apiserver.domain.individual_video.dto;

import com.vivid.apiserver.domain.video.dto.VideoGetResponse;
import com.vivid.apiserver.domain.video_space.dto.VideoSpaceGetResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class DashboardIndividualVideoDto {


    private Long videoSpaceId;

    private String videoSpaceName;

    private String videoSpaceDescription;

    private String individualVideoId;

    private LocalDateTime updatedDate;

    private String videoTitle;

    private String videoDescription;

    private LocalDateTime lastAccessTime;

    @Builder
    public DashboardIndividualVideoDto(VideoSpaceGetResponse videoSpace, VideoGetResponse video) {

        this.videoSpaceId = videoSpace.getId();
        this.videoSpaceName = videoSpace.getName();
        this.videoSpaceDescription = videoSpace.getDescription();

        this.individualVideoId = video.getIndividualVideoId();
        this.updatedDate = video.getLastAccessTime();
        this.videoTitle = video.getTitle();
        this.videoDescription = video.getDescription();
        this.lastAccessTime = video.getLastAccessTime();
    }
}
