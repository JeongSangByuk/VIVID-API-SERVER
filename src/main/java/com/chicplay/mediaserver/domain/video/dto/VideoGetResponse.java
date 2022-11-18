package com.chicplay.mediaserver.domain.video.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VideoGetResponse {

    private Long id;

    private String individualVideoId;

    private String title;

    private String thumbnailImagePath;

    private String description;

    private LocalDateTime lastAccessTime;

    private Long progressRate;

    private boolean isUploaded;

    @Builder
    public VideoGetResponse(Long id,String title, String description, String thumbnailImagePath,  boolean isUploaded) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.thumbnailImagePath = thumbnailImagePath;
        this.isUploaded = isUploaded;
    }

    public void changeIndividualVideoState(String individualVideoId, LocalDateTime lastAccessTime, Long progressRate) {
        this.individualVideoId = individualVideoId;
        this.lastAccessTime = lastAccessTime;
        this.progressRate = progressRate;
    }
}
