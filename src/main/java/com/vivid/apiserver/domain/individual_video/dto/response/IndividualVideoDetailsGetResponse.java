package com.vivid.apiserver.domain.individual_video.dto.response;

import com.vivid.apiserver.domain.individual_video.domain.IndividualVideo;
import com.vivid.apiserver.domain.video.domain.Video;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class IndividualVideoDetailsGetResponse {


    private String title;

    private String description;

    private String videoFilePath;

    private List<String> visualIndexImageFilePathList;

    private LocalDateTime createdDate;


    @Builder
    public IndividualVideoDetailsGetResponse(IndividualVideo individualVideo, String videoFilePath,
            List<String> visualIndexImageFilePathList) {
        Video video = individualVideo.getVideo();
        this.title = video.getTitle();
        this.description = video.getDescription();
        this.createdDate = video.getCreatedDate();
        this.videoFilePath = videoFilePath;
        this.visualIndexImageFilePathList = visualIndexImageFilePathList;
    }
}
