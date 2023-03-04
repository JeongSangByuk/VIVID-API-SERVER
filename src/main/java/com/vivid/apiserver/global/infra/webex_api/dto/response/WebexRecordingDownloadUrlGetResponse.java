package com.vivid.apiserver.global.infra.webex_api.dto.response;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class WebexRecordingDownloadUrlGetResponse {

    @JsonProperty(value = "recordingDownloadLink")
    private String downloadUrl;

}
