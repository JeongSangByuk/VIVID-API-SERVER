package com.vivid.apiserver.domain.individual_video.dto;

import com.vivid.apiserver.domain.individual_video.domain.TextMemoStateHistory;
import com.vivid.apiserver.domain.individual_video.domain.TextMemoStateLatest;
import com.vivid.apiserver.global.util.BaseDateTimeFormatter;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class TextMemoStateDynamoSaveRequest {

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
    public TextMemoStateDynamoSaveRequest(String id, String individualVideoId, String stateJson, String videoTime, String createdAt) {
        this.id = id;
        this.individualVideoId = individualVideoId;
        this.stateJson = stateJson;
        this.videoTime = videoTime;
        this.createdAt = createdAt;
    }

    public TextMemoStateLatest toLatestEntity() {

        return TextMemoStateLatest.builder()
                .id(id)
                .individualVideoId(UUID.fromString(individualVideoId))
                .stateJson(stateJson)
                .videoTime(LocalTime.parse(videoTime, BaseDateTimeFormatter.getLocalTimeFormatter()))
                .createdAt(LocalDateTime.parse(createdAt, BaseDateTimeFormatter.getLocalDateTimeFormatter()))
                .build();
    }

    public TextMemoStateHistory toHistoryEntity() {

        return TextMemoStateHistory.builder()
                .id(id)
                .individualVideoId(UUID.fromString(individualVideoId))
                .stateJson(stateJson)
                .videoTime(LocalTime.parse(videoTime,BaseDateTimeFormatter.getLocalTimeFormatter()))
                .createdAt(LocalDateTime.parse(createdAt,BaseDateTimeFormatter.getLocalDateTimeFormatter()))
                .build();
    }
}
