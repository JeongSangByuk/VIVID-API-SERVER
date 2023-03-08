package com.vivid.apiserver.domain.individual_video.dto.request;

import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TextMemoSaveRequest {

    @NotBlank
    private String id;

    @NotBlank
    protected String individualVideoId;

    @NotBlank
    private String stateJson;

    @NotBlank
    private String videoTime;

    @NotBlank
    private String createdAt;


    @Builder
    public TextMemoSaveRequest(String id, String individualVideoId, String stateJson, String videoTime,
            String createdAt) {
        this.id = id;
        this.individualVideoId = individualVideoId;
        this.stateJson = stateJson;
        this.videoTime = videoTime;
        this.createdAt = createdAt;
    }
}
