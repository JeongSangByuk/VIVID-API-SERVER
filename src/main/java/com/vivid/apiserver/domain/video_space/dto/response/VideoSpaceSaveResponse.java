package com.vivid.apiserver.domain.video_space.dto.response;

import com.vivid.apiserver.domain.video_space.domain.VideoSpace;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VideoSpaceSaveResponse {

    private Long id;

    private String name;

    private String description;

    @Builder
    public VideoSpaceSaveResponse(VideoSpace videoSpace) {
        this.id = videoSpace.getId();
        this.name = videoSpace.getName();
        this.description = videoSpace.getDescription();
    }
}
