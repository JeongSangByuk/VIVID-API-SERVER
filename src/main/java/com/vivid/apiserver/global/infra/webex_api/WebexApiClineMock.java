package com.vivid.apiserver.global.infra.webex_api;

import com.vivid.apiserver.global.infra.webex_api.dto.request.WebexAccessTokenGetRequest;
import com.vivid.apiserver.global.infra.webex_api.dto.response.WebexAccessTokenGetResponse;
import com.vivid.apiserver.global.infra.webex_api.dto.response.WebexRecordingDownloadUrlGetResponse;
import com.vivid.apiserver.global.infra.webex_api.dto.response.WebexRecordingsGetResponse;
import com.vivid.apiserver.global.infra.webex_api.dto.response.WebexRecordingsGetResponse.WebexRecording;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class WebexApiClineMock implements WebexApiClient {

    @Override
    public WebexAccessTokenGetResponse getWebexAccessToken(WebexAccessTokenGetRequest webexAccessTokenGetRequest) {
        return new WebexAccessTokenGetResponse("test webex access token");
    }

    @Override
    public WebexRecordingsGetResponse getWebexRecordings(String accessToken) {

        WebexRecording test01 = WebexRecording.builder()
                .recordingId("test webex 01").topic("test").hostEmail("test01")
                .timeRecorded("01:13:13").build();

        WebexRecording test02 = WebexRecording.builder()
                .recordingId("test webex 01").topic("test2").hostEmail("test02")
                .timeRecorded("01:13:13").build();

        return new WebexRecordingsGetResponse(List.of(test01, test02));
    }

    @Override
    public WebexRecordingDownloadUrlGetResponse getRecordingDownloadUrl(String accessToken, String webexRecordingId) {
        return new WebexRecordingDownloadUrlGetResponse("test url");
    }
}
