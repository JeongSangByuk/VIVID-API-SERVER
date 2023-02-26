package com.vivid.apiserver.global.infra.webex_api;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WebexRecordingGetResponse {

    private String recordingId;

    private String topic;

    private String hostEmail;

    private String timeRecorded;

    @Builder
    public WebexRecordingGetResponse(String recordingId, String topic, String hostEmail, String timeRecorded) {
        this.recordingId = recordingId;
        this.topic = topic;
        this.hostEmail = hostEmail;
        this.timeRecorded = timeRecorded;
    }
}
