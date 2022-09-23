package com.chicplay.mediaserver.domain.video_space.dto;

import com.chicplay.mediaserver.domain.video.domain.Video;
import com.chicplay.mediaserver.domain.video.dto.VideoGetResponse;
import com.chicplay.mediaserver.domain.video_space.domain.VideoSpace;
import com.chicplay.mediaserver.domain.video_space.domain.VideoSpaceParticipant;
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
    public VideoSpaceGetResponse(VideoSpaceParticipant videoSpaceParticipant) {

        VideoSpace videoSpace = videoSpaceParticipant.getVideoSpace();
        this.id = videoSpaceParticipant.getId();
        this.name = videoSpace.getName();
        this.description = videoSpace.getDescription();

        List<Video> videoList = videoSpace.getVideos();
        videoList.forEach(video -> {
            videos.add(VideoGetResponse
                    .builder()
                    .id(video.getId())
                    .title(video.getTitle())
                    .description(video.getDescription())
                    .build());
        });
    }
}