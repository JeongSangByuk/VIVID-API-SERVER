package com.vivid.apiserver.domain.video.dto.response;

import com.vivid.apiserver.domain.individual_video.domain.IndividualVideo;
import com.vivid.apiserver.domain.video.domain.Video;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class VideoGetResponse {

    private Long id;

    private String individualVideoId;

    private String title;

    private String thumbnailImagePath;

    private String description;

    private LocalDateTime lastAccessTime;

    private Long progressRate;

    private boolean isUploaded;

    public static VideoGetResponse of(Video video, IndividualVideo individualVideo) {
        return VideoGetResponse.builder()
                .id(video.getId())
                .title(video.getTitle())
                .description(video.getDescription())
                .thumbnailImagePath(video.getThumbnailImagePath())
                .isUploaded(video.isUploaded())
                .individualVideoId(individualVideo.getId().toString())
                .progressRate(individualVideo.getProgressRate())
                .lastAccessTime(individualVideo.getLastAccessTime())
                .build();
    }
}
