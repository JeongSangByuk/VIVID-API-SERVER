package com.vivid.apiserver.domain.video.application;

import com.vivid.apiserver.domain.user.application.UserService;
import com.vivid.apiserver.domain.user.domain.Institution;
import com.vivid.apiserver.domain.user.domain.User;
import com.vivid.apiserver.domain.video.dto.VideoSaveRequest;
import com.vivid.apiserver.domain.video.dto.VideoSaveResponse;
import com.vivid.apiserver.global.infra.webex_api.WebexApiService;
import com.vivid.apiserver.global.infra.webex_api.WebexRecordingGetResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class WebexVideoService {

    private final UserService userService;

    private final VideoService videoService;

    private final WebexApiService webexApiService;


    // get recordings
    public List<WebexRecordingGetResponse> getWebexRecordings() throws JsonProcessingException {

        // access token get
        String webexAccessToken = userService.getWebexAccessToken();

        // get recordings by webex api
        List<WebexRecordingGetResponse> webexRecordings = webexApiService.getWebexRecordings(webexAccessToken);

        return webexRecordings;
    }

    // webex api를 이용해서 access token을 얻어내고, save합니다.
    public void saveWebexAccessTokenFromWebexApi(String code) {

        // user get
        User user = userService.getByAccessToken();

        // access token get
        String accessToken = webexApiService.getAccessToken(code);

        // access token save
        Institution institution = user.getInstitution();
        institution.changeWebexAccessToken(accessToken);

        user.changeInstitution(institution);
    }

    // webex recording을 upload합니다.
    public VideoSaveResponse uploadRecording(String webexRecordingId, Long videoSpaceId, VideoSaveRequest videoSaveRequest) throws IOException {

        // access token get
        String webexAccessToken = userService.getWebexAccessToken();

        // webex recording download url get
        String recordingDownloadUrl = webexApiService.getRecordingDownloadUrl(webexAccessToken, webexRecordingId);

        // s3로 업로드
        VideoSaveResponse videoSaveResponse = videoService.uploadByDownloadUrl(recordingDownloadUrl, videoSpaceId, videoSaveRequest);

        return videoSaveResponse;
    }

    public void loginWebex(String id) {


    }
}
