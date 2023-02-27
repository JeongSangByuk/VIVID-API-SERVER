package com.vivid.apiserver.domain.individual_video.dto.response;


import com.vivid.apiserver.domain.individual_video.domain.TextMemoState;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.Builder;
import lombok.Getter;

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
