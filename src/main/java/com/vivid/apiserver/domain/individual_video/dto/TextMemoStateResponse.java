package com.vivid.apiserver.domain.individual_video.dto;


import com.vivid.apiserver.domain.individual_video.domain.TextMemoState;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
public class TextMemoStateResponse {

    private String stateJson;

    private LocalTime videoTime;

    private LocalDateTime createdAt;


    @Builder
    public TextMemoStateResponse(TextMemoState textMemoState) {
        this.stateJson = textMemoState.getStateJson();
        this.videoTime = textMemoState.getVideoTime();
        this.createdAt = textMemoState.getCreatedAt();
    }

    @Builder
    public TextMemoStateResponse() {
        this.stateJson = "";
        this.videoTime = null;
        this.createdAt = null;
    }


}
