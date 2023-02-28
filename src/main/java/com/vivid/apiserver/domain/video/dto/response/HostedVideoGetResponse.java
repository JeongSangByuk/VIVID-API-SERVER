package com.vivid.apiserver.domain.video.dto.response;

import com.vivid.apiserver.domain.video.domain.Video;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HostedVideoGetResponse {

    private Long id;

    private String title;

    private String description;

    private String thumbnailImagePath;

    private boolean isUploaded;

    public static HostedVideoGetResponse from(Video video) {
        return HostedVideoGetResponse.builder()
                .id(video.getId())
                .title(video.getTitle())
                .description(video.getDescription())
                .isUploaded(video.isUploaded())
                .thumbnailImagePath(video.getThumbnailImagePath())
                .build();
    }
}
