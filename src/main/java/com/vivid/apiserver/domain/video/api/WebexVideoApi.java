package com.vivid.apiserver.domain.video.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vivid.apiserver.domain.video.application.WebexVideoService;
import com.vivid.apiserver.domain.video.dto.request.VideoSaveRequest;
import com.vivid.apiserver.domain.video.dto.response.VideoSaveResponse;
import com.vivid.apiserver.global.infra.webex_api.dto.response.WebexRecordingGetResponse;
import com.vivid.apiserver.global.success.SuccessCode;
import com.vivid.apiserver.global.success.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class WebexVideoApi {


    @Value("${webex.api.login-uri}")
    private String webexLoginUrl;

    private final WebexVideoService webexVideoService;

    @PostMapping("/api/webex/recordings/{video-space-id}/{recording-id}")
    @Operation(summary = "webex video 업로드 메소드", description = "webex video를 s3에 업로드하는 api 입니다.")
    public ResponseEntity<SuccessResponse<VideoSaveResponse>> uploadVideosFromWebex(
            @PathVariable("video-space-id") Long videoSpaceId,
            @PathVariable("recording-id") String recordingId,
            @RequestBody VideoSaveRequest videoSaveRequest) {

        return SuccessResponse.success(SuccessCode.OK_SUCCESS,
                webexVideoService.uploadRecording(recordingId, videoSpaceId, videoSaveRequest));

    }

    @PostMapping("/api/webex/token/{code}")
    @Operation(summary = "webex access token save", description = "webex access token을 get한 후, user entity에 save하는 api입니다.")
    public ResponseEntity<SuccessResponse<String>> saveWebexAccessToken(@PathVariable("code") String code) {

        webexVideoService.saveWebexAccessTokenFromWebexApi(code);

        return SuccessResponse.OK;
    }

    @GetMapping("/api/webex/recordings")
    @Operation(summary = "webex recordings get api", description = "webex 녹화본 리스트를 get하는 api입니다.")
    public ResponseEntity<SuccessResponse<List<WebexRecordingGetResponse>>> getWebexRecordings()
            throws JsonProcessingException {

        return SuccessResponse.success(SuccessCode.OK_SUCCESS, webexVideoService.getWebexRecordings());
    }

    @GetMapping("/api/login/webex")
    @Operation(summary = "webex login redirection", description = "해당 url로 이동시, webex login창으로 redirection 됩니다.")
    public ResponseEntity<SuccessResponse<String>> redirectWebexLoginUrl(HttpServletResponse httpServletResponse)
            throws IOException {
        httpServletResponse.sendRedirect(webexLoginUrl);
        return SuccessResponse.OK;

    }
}