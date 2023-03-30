package com.vivid.apiserver.domain.video_space.api;

import com.vivid.apiserver.domain.video_space.dto.response.VideoSpaceParticipantSaveResponse;
import com.vivid.apiserver.domain.video_space.service.VideoSpaceParticipantService;
import com.vivid.apiserver.global.success.SuccessCode;
import com.vivid.apiserver.global.success.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class VideoSpaceParticipantApi {

    private final VideoSpaceParticipantService videoSpaceParticipantService;

    // video space에 account를 추가한다. 즉, VideoSpaceParticipant save
    @Operation(summary = "video space에 user를 추가하는 api", description = "video space에 user를 추가하는 api 입니다.")
    @PostMapping("/api/video-space/{video-space-id}/{user-email}")
    public ResponseEntity<SuccessResponse<VideoSpaceParticipantSaveResponse>> save(
            @PathVariable("video-space-id") Long videoSpaceId,
            @PathVariable("user-email") String userEmail) {

        return SuccessResponse.success(SuccessCode.OK_SUCCESS,
                videoSpaceParticipantService.addVideoSpaceParticipantToVideoSpace(videoSpaceId, userEmail));
    }

    // video space에 account를 추가한다. 즉, VideoSpaceParticipant save
    @Operation(summary = "video space에서 user를 삭제하는 api", description = "video space에 user를 삭제하는 api 입니다.")
    @DeleteMapping("/api/video-space/{video-space-id}/{user-email}")
    public ResponseEntity<SuccessResponse<String>> delete(
            @PathVariable("video-space-id") Long videoSpaceId,
            @PathVariable("user-email") String userEmail) {

        videoSpaceParticipantService.deleteVideoSpaceParticipantFromVideoSpace(videoSpaceId, userEmail);

        return SuccessResponse.OK;
    }


}
