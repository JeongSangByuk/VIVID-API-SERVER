package com.chicplay.mediaserver.domain.video.api;

import com.chicplay.mediaserver.domain.individual_video.dto.IndividualVideosGetRequest;
import com.chicplay.mediaserver.domain.video.application.WebexVideoService;
import com.chicplay.mediaserver.domain.video.dto.VideoSaveRequest;
import com.chicplay.mediaserver.domain.video.dto.VideoSaveResponse;
import com.chicplay.mediaserver.global.infra.storage.AwsS3Service;
import com.chicplay.mediaserver.global.infra.webex_api.WebexRecordingGetResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class WebexVideoApi {


    @Value("${webex.api.login-uri}")
    private String webexLoginUrl;

    private final WebexVideoService webexVideoService;

    @PostMapping("/api/webex/recordings/{video-space-id}/{recording-id}")
    @Operation(summary = "webex video 업로드 메소드", description = "webex video를 s3에 업로드하는 api 입니다.")
    public VideoSaveResponse uploadVideosFromWebex(
            @PathVariable("video-space-id") Long videoSpaceId,
            @PathVariable("recording-id") String recordingId,
            @RequestBody VideoSaveRequest videoSaveRequest) throws IOException {

        VideoSaveResponse videoSaveResponse = webexVideoService.uploadRecording(recordingId, videoSpaceId, videoSaveRequest);

        return videoSaveResponse;
    }

    @PostMapping("/api/webex/token/{code}")
    @Operation(summary = "webex access token save", description = "webex access token을 get한 후, user entity에 save하는 api입니다.")
    public void saveWebexAccessToken(@PathVariable("code") String code) {

        webexVideoService.saveWebexAccessTokenFromWebexApi(code);
    }

    @GetMapping("/api/webex/recordings")
    @Operation(summary = "webex recordings get api", description = "webex 녹화본 리스트를 get하는 api입니다.")
    public List<WebexRecordingGetResponse> getWebexRecordings() throws JsonProcessingException {

        List<WebexRecordingGetResponse> webexRecordings = webexVideoService.getWebexRecordings();

        return webexRecordings;
    }

    @GetMapping("/api/login/webex")
    @Operation(summary = "webex login redirection", description = "해당 url로 이동시, webex login창으로 redirection 됩니다.")
    public void redirectWebexLoginUrl(HttpServletResponse httpServletResponse) throws IOException {
        httpServletResponse.sendRedirect(webexLoginUrl);
    }
}