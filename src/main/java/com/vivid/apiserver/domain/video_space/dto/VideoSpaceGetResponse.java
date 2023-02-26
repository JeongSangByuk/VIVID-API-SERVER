package com.vivid.apiserver.domain.video_space.dto;

import com.vivid.apiserver.domain.video.dto.VideoGetResponse;
import com.vivid.apiserver.domain.video_space.domain.VideoSpace;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VideoSpaceGetResponse {

    private Long id;

    private String name;

    private String description;

    private List<VideoGetResponse> videos = new ArrayList<>();

    @Builder
    public VideoSpaceGetResponse(VideoSpace videoSpace) {

        this.id = videoSpace.getId();
        this.name = videoSpace.getName();
        this.description = videoSpace.getDescription();
    }

    public void addVideo(VideoGetResponse videoGetResponse) {
        this.videos.add(videoGetResponse);
    }
}
