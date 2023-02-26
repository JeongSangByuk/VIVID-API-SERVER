package com.vivid.apiserver.global.infra.webex_api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.*;

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

    private final ObjectMapper mapper;

    // 최초 로그인 시, access token get
    public String getAccessToken(String code) {

        // http body 설정
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("grant_type", "authorization_code");
        parameters.add("client_id", clientId);
        parameters.add("client_secret", clientSecret);
        parameters.add("code", code);
        parameters.add("redirect_uri", redirectUri);

        // http header 설정.
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity formEntity = new HttpEntity<>(parameters, headers);

        // webex api 호출
        ResponseEntity<Map> response = webexRestTemplate.postForEntity("/access_token", formEntity, Map.class);

        // access token get
        String accessToken = response.getBody().get("access_token").toString();

        return accessToken;
    }

    // webex recordings get api 호출
    public List<WebexRecordingGetResponse> getWebexRecordings(String accessToken) throws JsonProcessingException {

        HttpHeaders header = new HttpHeaders();
        header.set("Authorization", "Bearer "+ accessToken);

        HttpEntity<String> entity = new HttpEntity<>(header);
        ResponseEntity<String> response = webexRestTemplate.exchange("/recordingReport/accessSummary", HttpMethod.GET, entity, String.class);


        // parsing 작업
        JSONObject responseJsonObject = new JSONObject(response.getBody());
        JSONArray recordingArray = (JSONArray) responseJsonObject.get("items");

        List<WebexRecordingGetResponse> webexRecordingGetResponses = new ArrayList<>();

        for(int i=0; i<recordingArray.length(); i++){
            JSONObject jsonObj = (JSONObject)recordingArray.get(i);

            WebexRecordingGetResponse webexRecordingGetResponse = WebexRecordingGetResponse.builder()
                    .recordingId((String) jsonObj.get("recordingId"))
                    .topic((String) jsonObj.get("topic"))
                    .hostEmail((String) jsonObj.get("hostEmail"))
                    .timeRecorded((String) jsonObj.get("timeRecorded")).build();

            webexRecordingGetResponses.add(webexRecordingGetResponse);
        }

        return webexRecordingGetResponses;

    }

    // get webex recording direct download url
    public String getRecordingDownloadUrl(String accessToken, String webexRecordingId) {

        HttpHeaders header = new HttpHeaders();
        header.set("Authorization", "Bearer "+ accessToken);

        HttpEntity<String> entity = new HttpEntity<>(header);
        ResponseEntity<String> response = webexRestTemplate.exchange("/recordings/" + webexRecordingId, HttpMethod.GET, entity, String.class);

        // parsing 작업
        JSONObject responseJsonObject = new JSONObject(response.getBody());
        JSONObject downloadLinkJsonObject = responseJsonObject.getJSONObject("temporaryDirectDownloadLinks");

        return (String) downloadLinkJsonObject.get("recordingDownloadLink");
    }





}
