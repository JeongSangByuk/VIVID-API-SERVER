package com.chicplay.mediaserver.domain.video.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HostedVideoGetResponse {

    private Long id;

    private String title;

    private String description;

    private String thumbnailImagePath;

    private boolean isUploaded;

    @Builder
    public HostedVideoGetResponse(Long id, String title, String description, String thumbnailImagePath, boolean isUploaded) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.thumbnailImagePath = thumbnailImagePath;
        this.isUploaded = isUploaded;
    }
}
