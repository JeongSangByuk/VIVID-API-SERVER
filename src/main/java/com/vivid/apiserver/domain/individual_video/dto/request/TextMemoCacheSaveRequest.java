package com.vivid.apiserver.domain.individual_video.dto.request;

import com.vivid.apiserver.domain.individual_video.domain.TextMemo;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TextMemoCacheSaveRequest {

    private String id;

    @NotBlank
    private String stateJson;

    @NotNull
    private Long videoTime;


    @Builder
    public TextMemoCacheSaveRequest(String stateJson, Long videoTime) {
        this.stateJson = stateJson;
        this.videoTime = videoTime;
    }

    public TextMemo toEntity() {

        return TextMemo.builder()
                .id(UUID.randomUUID().toString())
                .stateJson(stateJson)
                .videoTime(videoTime)
                .createdAt(LocalDateTime.now())
                .build();

    }
}
