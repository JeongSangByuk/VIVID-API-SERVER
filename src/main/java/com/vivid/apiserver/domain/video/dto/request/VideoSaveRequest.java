package com.vivid.apiserver.domain.video.dto.request;

import com.vivid.apiserver.domain.video.domain.Video;
import com.vivid.apiserver.domain.video_space.domain.VideoSpace;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema
public class VideoSaveRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    public Video toEntity(VideoSpace videoSpace, String email) {
        return Video.builder()
                .videoSpace(videoSpace)
                .title(this.title)
                .description(this.description)
                .uploaderId(email)
                .build();
    }

}
