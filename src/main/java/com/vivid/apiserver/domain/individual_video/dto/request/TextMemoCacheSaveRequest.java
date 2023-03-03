package com.vivid.apiserver.domain.individual_video.dto.request;

import com.vivid.apiserver.domain.individual_video.domain.TextMemo;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TextMemoCacheSaveRequest {

    private String id;

    @NotBlank
    private String stateJson;

    @NotBlank
    private String videoTime;


    @Builder
    public TextMemoCacheSaveRequest(String stateJson, String videoTime) {
        this.stateJson = stateJson;
        this.videoTime = videoTime;
    }

    public TextMemo toEntity(String individualVideoId) {

        return TextMemo.builder()
                .id(UUID.randomUUID().toString())
                .individualVideoId(UUID.fromString(individualVideoId))
                .stateJson(stateJson)
                .videoTime(LocalTime.parse(videoTime, DateTimeFormatter.ofPattern("HH:mm:ss")))
                .createdAt(LocalDateTime.now())
                .build();

    }
}
