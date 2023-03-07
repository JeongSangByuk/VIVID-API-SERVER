package com.vivid.apiserver.global.infra.webex_api.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class WebexRecordingsGetResponse {

    @JsonProperty(value = "items")
    private List<WebexRecording> recordings = new ArrayList<>();

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class WebexRecording {

        private String recordingId;

        private String topic;

        private String hostEmail;

        private String timeRecorded;
    }

}
