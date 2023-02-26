package com.vivid.apiserver.domain.individual_video.dto;

import com.vivid.apiserver.domain.individual_video.domain.IndividualVideo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class IndividualVideoGetResponse {


    private UUID individualVideoId;

    private LocalDateTime updatedDate;

    private String videoTitle;

    private String videoDescription;

    private LocalDateTime videoCreatedDate;

    @Builder
    public IndividualVideoGetResponse(IndividualVideo individualVideo) {
        this.individualVideoId = individualVideo.getId();
        this.updatedDate = individualVideo.getUpdatedDate();
        this.videoTitle = individualVideo.getVideo().getTitle();
        this.videoDescription = individualVideo.getVideo().getDescription();
        this.videoCreatedDate = individualVideo.getVideo().getUpdatedDate();
    }
}
