package com.vivid.apiserver.domain.video_space.api;

import com.vivid.apiserver.domain.video_space.application.VideoSpaceParticipantService;
import com.vivid.apiserver.domain.video_space.dto.VideoSpaceParticipantSaveResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class VideoSpaceParticipantApi {

    private final VideoSpaceParticipantService videoSpaceParticipantService;

    // video space에 account를 추가한다. 즉, VideoSpaceParticipant save
    @Operation(summary = "video space에 user를 추가하는 api", description = "video space에 user를 추가하는 api 입니다.")
    @PostMapping("/api/video-space/{video-space-id}/{user-email}")
    public VideoSpaceParticipantSaveResponse save(
            @PathVariable("video-space-id") Long videoSpaceId,
            @PathVariable("user-email") String userEmail) {

        VideoSpaceParticipantSaveResponse videoSpaceParticipantSaveResponse = videoSpaceParticipantService.save(videoSpaceId, userEmail);

        return videoSpaceParticipantSaveResponse;
    }

    // video space에 account를 추가한다. 즉, VideoSpaceParticipant save
    @Operation(summary = "video space에서 user를 삭제하는 api", description = "video space에 user를 삭제하는 api 입니다.")
    @DeleteMapping("/api/video-space/{video-space-id}/{user-email}")
    public void delete(
            @PathVariable("video-space-id") Long videoSpaceId,
            @PathVariable("user-email") String userEmail) {

        videoSpaceParticipantService.deleteVideoSpaceParticipant(videoSpaceId,userEmail);


    }



}
