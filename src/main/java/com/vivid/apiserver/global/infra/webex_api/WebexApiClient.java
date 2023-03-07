package com.vivid.apiserver.global.infra.webex_api;

import com.vivid.apiserver.global.infra.webex_api.dto.request.WebexAccessTokenGetRequest;
import com.vivid.apiserver.global.infra.webex_api.dto.response.WebexAccessTokenGetResponse;
import com.vivid.apiserver.global.infra.webex_api.dto.response.WebexRecordingDownloadUrlGetResponse;
import com.vivid.apiserver.global.infra.webex_api.dto.response.WebexRecordingsGetResponse;
import feign.Headers;
import feign.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


//@FeignClient(name = "WebexApiClient", url = "https://webexapis.com/v1")
public interface WebexApiClient {

    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @PostMapping(value = "/access_token")
    WebexAccessTokenGetResponse getWebexAccessToken(@RequestBody WebexAccessTokenGetRequest webexAccessTokenGetRequest);

    @Headers("Authorization: Bearer {accessToken}")
    @GetMapping(value = "/recordingReport/accessSummary")
    WebexRecordingsGetResponse getWebexRecordings(@Param("accessToken") String accessToken);

    @Headers("Authorization: Bearer {accessToken}")
    @GetMapping(value = "/recordings/{webexRecordingId}")
    WebexRecordingDownloadUrlGetResponse getRecordingDownloadUrl(@Param("accessToken") String accessToken,
            @PathVariable("webexRecordingId") String webexRecordingId);

}
