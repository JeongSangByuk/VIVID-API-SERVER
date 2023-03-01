package com.vivid.apiserver.domain.video_space.dto.response;

import com.vivid.apiserver.domain.video_space.domain.VideoSpace;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VideoSpaceSaveResponse {

    private Long id;

    private String name;

    private String description;

    public static VideoSpaceSaveResponse from(VideoSpace videoSpace) {

        return VideoSpaceSaveResponse.builder()
                .id(videoSpace.getId())
                .name(videoSpace.getName())
                .description(videoSpace.getDescription())
                .build();
    }
}
