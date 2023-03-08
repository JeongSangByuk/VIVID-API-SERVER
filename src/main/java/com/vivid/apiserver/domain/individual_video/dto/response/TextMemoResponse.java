package com.vivid.apiserver.domain.individual_video.dto.response;


import com.vivid.apiserver.domain.individual_video.domain.TextMemo;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class TextMemoResponse {

    private String stateJson;

    private Long videoTime;

    private LocalDateTime createdAt;

    public static TextMemoResponse from(TextMemo textMemo) {
        return TextMemoResponse.builder()
                .stateJson(textMemo.getStateJson())
                .videoTime(textMemo.getVideoTime())
                .createdAt(textMemo.getCreatedAt())
                .build();
    }

    public static TextMemoResponse createNullObject() {
        return TextMemoResponse.builder()
                .stateJson(null)
                .videoTime(null)
                .createdAt(null)
                .build();
    }


}
