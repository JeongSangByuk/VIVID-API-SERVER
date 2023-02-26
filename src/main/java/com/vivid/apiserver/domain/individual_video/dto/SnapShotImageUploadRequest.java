package com.vivid.apiserver.domain.individual_video.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@Schema
public class SnapShotImageUploadRequest {

    @NotBlank
    private String videoTime;

    @Builder
    public SnapShotImageUploadRequest(String videoTime) {
        this.videoTime = videoTime;
    }


}
