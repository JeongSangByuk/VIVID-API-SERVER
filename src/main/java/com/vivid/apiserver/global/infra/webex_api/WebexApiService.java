package com.vivid.apiserver.global.infra.webex_api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class WebexApiService {

    @Value("${webex.api.client-id}")
    private String clientId;

    @Value("${webex.api.client-secret}")
    private String clientSecret;

    @Value("${webex.api.redirect-uri}")
    private String redirectUri;

    @Qualifier("webexRestTemplate")
    private final RestTemplate webexRestTemplate;

    // get webex recording direct download url
    public String getRecordingDownloadUrl(String accessToken, String webexRecordingId) {

        HttpHeaders header = new HttpHeaders();
        header.set("Authorization", "Bearer " + accessToken);

        HttpEntity<String> entity = new HttpEntity<>(header);
        ResponseEntity<String> response = webexRestTemplate.exchange("/recordings/" + webexRecordingId, HttpMethod.GET,
                entity, String.class);

        // parsing 작업
        JSONObject responseJsonObject = new JSONObject(response.getBody());
        JSONObject downloadLinkJsonObject = responseJsonObject.getJSONObject("temporaryDirectDownloadLinks");

        return (String) downloadLinkJsonObject.get("recordingDownloadLink");
    }


}
