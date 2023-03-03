package com.vivid.apiserver.domain.individual_video.dto.request;

import com.vivid.apiserver.domain.individual_video.domain.TextMemoHistory;
import com.vivid.apiserver.domain.individual_video.domain.TextMemoLatest;
import com.vivid.apiserver.global.util.BaseDateTimeFormatter;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;
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

    public TextMemoLatest toLatestEntity() {

        return TextMemoLatest.builder()
                .id(id)
                .individualVideoId(UUID.fromString(individualVideoId))
                .stateJson(stateJson)
                .videoTime(LocalTime.parse(videoTime, BaseDateTimeFormatter.getLocalTimeFormatter()))
                .createdAt(LocalDateTime.parse(createdAt, BaseDateTimeFormatter.getLocalDateTimeFormatter()))
                .build();
    }

    public TextMemoHistory toHistoryEntity() {

        return TextMemoHistory.builder()
                .id(id)
                .individualVideoId(UUID.fromString(individualVideoId))
                .stateJson(stateJson)
                .videoTime(LocalTime.parse(videoTime, BaseDateTimeFormatter.getLocalTimeFormatter()))
                .createdAt(LocalDateTime.parse(createdAt, BaseDateTimeFormatter.getLocalDateTimeFormatter()))
                .build();
    }
}
