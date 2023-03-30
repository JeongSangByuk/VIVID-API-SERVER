package com.vivid.apiserver.domain.video_space.api;

import com.vivid.apiserver.domain.video_space.dto.response.HostedVideoSpaceGetResponse;
import com.vivid.apiserver.domain.video_space.service.VideoSpaceHostService;
import com.vivid.apiserver.global.success.SuccessCode;
import com.vivid.apiserver.global.success.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class VideoSpaceHostApi {

    private final VideoSpaceHostService videoSpaceHostService;

    @Operation(summary = "hosted video space one get by id api", description = "video space id를 통해 hosted video space를 get api 입니다.")
    @GetMapping("/api/video-space/hosted/{video-space-id}")
    public ResponseEntity<SuccessResponse<HostedVideoSpaceGetResponse>> getHostedOneById(
            @PathVariable("video-space-id") Long videoSpaceId) {

        return SuccessResponse.success(SuccessCode.OK_SUCCESS, videoSpaceHostService.getHostedOne(videoSpaceId));
    }

    @Operation(summary = "video space hosted list get api", description = "로그인한 user가 host인 video space list를 get api 입니다.")
    @GetMapping("/api/video-space/hosted")
    public ResponseEntity<SuccessResponse<List<HostedVideoSpaceGetResponse>>> getHostedList() {

        return SuccessResponse.success(SuccessCode.OK_SUCCESS, videoSpaceHostService.getHostedList());
    }
}
