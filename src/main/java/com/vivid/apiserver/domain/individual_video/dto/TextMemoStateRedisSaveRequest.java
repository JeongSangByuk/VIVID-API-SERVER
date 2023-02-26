package com.vivid.apiserver.domain.individual_video.dto;

import com.vivid.apiserver.domain.individual_video.domain.TextMemoState;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class TextMemoStateRedisSaveRequest {

    private String id;

    @NotBlank
    private String stateJson;

    @NotBlank
    private String videoTime;


    @Builder
    public TextMemoStateRedisSaveRequest(String stateJson, String videoTime) {
        this.stateJson = stateJson;
        this.videoTime = videoTime;
    }

    public TextMemoState toEntity(String individualVideoId) {

        return TextMemoState.builder()
                .id(UUID.randomUUID().toString())
                .individualVideoId(UUID.fromString(individualVideoId))
                .stateJson(stateJson)
                .videoTime(LocalTime.parse(videoTime,DateTimeFormatter.ofPattern("HH:mm:ss")))
                .createdAt(LocalDateTime.now())
                .build();

    }
}
