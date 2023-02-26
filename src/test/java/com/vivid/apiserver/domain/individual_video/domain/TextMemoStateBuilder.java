package com.vivid.apiserver.domain.individual_video.domain;

import com.vivid.apiserver.domain.individual_video.dto.TextMemoStateDynamoSaveRequest;
import com.vivid.apiserver.domain.individual_video.dto.TextMemoStateRedisSaveRequest;
import com.vivid.apiserver.global.util.BaseDateTimeFormatter;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TextMemoStateBuilder {

    public static String STATE_JSON = "{drawings : ~~~}";

    public static String VIDEO_TIME = "01:01:01";


    public static TextMemoStateRedisSaveRequest redisSaveRequestBuilder(String individualVideoId) {

        TextMemoStateRedisSaveRequest textMemoStateRedisSaveRequest = TextMemoStateRedisSaveRequest.builder()
                .stateJson(STATE_JSON)
                .videoTime(VIDEO_TIME)
                .build();

        return textMemoStateRedisSaveRequest;
    }

    public static TextMemoStateDynamoSaveRequest dynamoSaveRequestBuilder(String individualVideoId) {

        TextMemoStateDynamoSaveRequest textMemoStateDynamoSaveRequest = TextMemoStateDynamoSaveRequest.builder()
                .id(UUID.randomUUID().toString())
                .individualVideoId(individualVideoId)
                .videoTime(VIDEO_TIME)
                .stateJson(STATE_JSON )
                .createdAt(BaseDateTimeFormatter.getLocalDateTimeFormatter().format(LocalDateTime.now()))
                .build();

        return textMemoStateDynamoSaveRequest;
    }

    public static Map<String,String> individualVideoIdMapBuilder(String individualVideoId){
        Map<String, String> request = new HashMap<>();
        request.put("individualVideoId", individualVideoId);
        return request;
    }

    public static String getRandomIndividualVideoId(){
        return UUID.randomUUID().toString();
    }


}
