package com.vivid.apiserver.domain.video.application;

import com.vivid.apiserver.domain.user.application.CurrentUserService;
import com.vivid.apiserver.domain.user.application.command.UserCommandService;
import com.vivid.apiserver.domain.user.domain.User;
import com.vivid.apiserver.domain.video.dto.request.VideoSaveRequest;
import com.vivid.apiserver.domain.video.dto.response.VideoSaveResponse;
import com.vivid.apiserver.global.infra.webex_api.WebexApiClient;
import com.vivid.apiserver.global.infra.webex_api.WebexRecordingGetResponse;
import com.vivid.apiserver.global.infra.webex_api.dto.request.WebexAccessTokenGetRequest;
import com.vivid.apiserver.global.infra.webex_api.dto.response.WebexRecordingsGetResponse;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class WebexVideoService {

    @Value("${webex.api.client-id}")
    private String clientId;

    @Value("${webex.api.client-secret}")
    private String clientSecret;

    @Value("${webex.api.redirect-uri}")
    private String redirectUri;

    private final UserCommandService userCommandService;

    private final CurrentUserService currentUserService;
    private final VideoUploadService videoUploadService;

    private final WebexApiClient webexApiClient;

    /**
     * webex 녹화본 리스트를 get하는 메소드
     */
    public List<WebexRecordingGetResponse> getWebexRecordings() {

        String accessToken = currentUserService.getWebexAccessToken();

        WebexRecordingsGetResponse webexRecordings = webexApiClient.getWebexRecordings(accessToken);

        return webexRecordings.getRecordings().stream()
                .map(WebexRecordingGetResponse::from)
                .collect(Collectors.toList());
    }

    /**
     * webex access token 정보를 저장하는 메소드
     */
    public void saveWebexAccessTokenFromWebexApi(String code) {

        User user = currentUserService.getCurrentUser();
        WebexAccessTokenGetRequest webexAccessTokenGetRequest = createWebexAccessTokenGetRequest(code);

        String accessToken = webexApiClient.getWebexAccessToken(webexAccessTokenGetRequest).getAccessToken();

        userCommandService.changeWebexAccessToken(user, accessToken);
    }

    /**
     * 특정 webex 녹화본을 다운로드하는 메소드
     */
    public VideoSaveResponse uploadRecording(String webexRecordingId, Long videoSpaceId,
            VideoSaveRequest videoSaveRequest) {

        String accessToken = currentUserService.getWebexAccessToken();

        String downloadUrl = webexApiClient.getRecordingDownloadUrl(accessToken, webexRecordingId).getDownloadUrl();

        return videoUploadService.uploadByDownloadUrl(downloadUrl, videoSpaceId, videoSaveRequest);
    }

    private WebexAccessTokenGetRequest createWebexAccessTokenGetRequest(String code) {
        return WebexAccessTokenGetRequest.builder()
                .grantType("authorization_code")
                .clientId(clientId)
                .clientSecret(clientSecret)
                .redirectUri(redirectUri)
                .code(code)
                .build();
    }
}
