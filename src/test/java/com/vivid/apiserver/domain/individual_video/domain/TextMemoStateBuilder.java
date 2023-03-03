package com.vivid.apiserver.domain.individual_video.domain;

import com.vivid.apiserver.domain.individual_video.dto.request.TextMemoCacheSaveRequest;
import com.vivid.apiserver.domain.individual_video.dto.request.TextMemoSaveRequest;
import com.vivid.apiserver.global.util.BaseDateTimeFormatter;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TextMemoStateBuilder {

    public static String STATE_JSON = "{drawings : ~~~}";

    public static String VIDEO_TIME = "01:01:01";


    public static TextMemoCacheSaveRequest redisSaveRequestBuilder(String individualVideoId) {

        TextMemoCacheSaveRequest textMemoCacheSaveRequest = TextMemoCacheSaveRequest.builder()
                .stateJson(STATE_JSON)
                .videoTime(VIDEO_TIME)
                .build();

        return textMemoCacheSaveRequest;
    }

    public static TextMemoSaveRequest dynamoSaveRequestBuilder(String individualVideoId) {

        TextMemoSaveRequest textMemoSaveRequest = TextMemoSaveRequest.builder()
                .id(UUID.randomUUID().toString())
                .individualVideoId(individualVideoId)
                .videoTime(VIDEO_TIME)
                .stateJson(STATE_JSON)
                .createdAt(BaseDateTimeFormatter.getLocalDateTimeFormatter().format(LocalDateTime.now()))
                .build();

        return textMemoSaveRequest;
    }

    public static Map<String, String> individualVideoIdMapBuilder(String individualVideoId) {
        Map<String, String> request = new HashMap<>();
        request.put("individualVideoId", individualVideoId);
        return request;
    }

    public static String getRandomIndividualVideoId() {
        return UUID.randomUUID().toString();
    }


}
