package com.vivid.apiserver.global.infra.webex_api;

import com.vivid.apiserver.global.infra.webex_api.dto.response.WebexRecordingsGetResponse.WebexRecording;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class WebexRecordingGetResponse {

    private String recordingId;

    private String topic;

    private String hostEmail;

    private String timeRecorded;

    public static WebexRecordingGetResponse from(WebexRecording webexRecoding) {
        return WebexRecordingGetResponse.builder()
                .recordingId(webexRecoding.getRecordingId())
                .topic(webexRecoding.getTopic())
                .hostEmail(webexRecoding.getHostEmail())
                .timeRecorded(webexRecoding.getTimeRecorded())
                .build();
    }
}
