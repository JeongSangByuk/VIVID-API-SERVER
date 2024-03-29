package com.vivid.apiserver.domain.video_space.api;

import com.vivid.apiserver.domain.video_space.dto.request.VideoSpaceSaveRequest;
import com.vivid.apiserver.domain.video_space.dto.response.VideoSpaceGetResponse;
import com.vivid.apiserver.domain.video_space.dto.response.VideoSpaceSaveResponse;
import com.vivid.apiserver.domain.video_space.service.VideoSpaceService;
import com.vivid.apiserver.global.success.SuccessCode;
import com.vivid.apiserver.global.success.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class VideoSpaceApi {

    private final VideoSpaceService videoSpaceService;

    @Operation(summary = "video space list get api", description = "로그인한 user의 video space list를 get api 입니다.")
    @GetMapping("/api/video-space")
    public ResponseEntity<SuccessResponse<List<VideoSpaceGetResponse>>> getAllByUser() {

        return SuccessResponse.success(SuccessCode.OK_SUCCESS, videoSpaceService.getList());
    }

    @Operation(summary = "video space get one by id api", description = "video space id를 통해 video space를 get api 입니다.")
    @GetMapping("/api/video-space/{video-space-id}")
    public ResponseEntity<SuccessResponse<VideoSpaceGetResponse>> getById(
            @PathVariable("video-space-id") Long videoSpaceId) {

        return SuccessResponse.success(SuccessCode.OK_SUCCESS, videoSpaceService.getOne(videoSpaceId));

    }

    @Operation(summary = "video space create api", description = "video space를 생성하는 api 입니다. 최초 생성시, 생성자만 참가해 있습니다.")
    @PostMapping("/api/video-space")
    public ResponseEntity<SuccessResponse<VideoSpaceSaveResponse>> save(
            @RequestBody @Valid final VideoSpaceSaveRequest videoSpaceSaveRequest) {

        return SuccessResponse.success(SuccessCode.OK_SUCCESS, videoSpaceService.save(videoSpaceSaveRequest));
    }

    @Operation(summary = "video space delete api", description = "video space를 삭제하는 api 입니다.")
    @DeleteMapping("/api/video-space/{video-space-id}")
    public ResponseEntity<SuccessResponse<String>> deleteById(@PathVariable("video-space-id") Long videoSpaceId) {

        videoSpaceService.delete(videoSpaceId);

        return SuccessResponse.OK;
    }


}
