package com.chicplay.mediaserver.domain.individual_video.api;

import com.chicplay.mediaserver.domain.individual_video.application.IndividualVideoService;
import com.chicplay.mediaserver.domain.individual_video.dto.IndividualVideoGetResponse;
import com.chicplay.mediaserver.domain.individual_video.dto.IndividualVideosGetRequest;
import com.chicplay.mediaserver.domain.individual_video.dto.SnapShotImageUploadRequest;
import com.chicplay.mediaserver.domain.video.application.VideoService;
import com.chicplay.mediaserver.domain.individual_video.dto.IndividualVideoDetailsGetResponse;
import com.chicplay.mediaserver.global.infra.storage.AwsS3Service;
import com.chicplay.mediaserver.domain.individual_video.dto.SnapshotImageUploadResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Encoding;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor
public class IndividualVideoApi {

    private final AwsS3Service awsS3Service;

    private final IndividualVideoService individualVideoService;

    private final VideoService videoService;

    @PostMapping(value = "/api/videos/{individual-video-id}/snapshot",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "image snapshot save api", description = "이미지 스냅샷을 저장하는 메소드입니다.")
    @ApiResponse(responseCode = "200", description = "이미지 업로드 완료 후, 각각 이미지의 url을 json 형식으로 반환합니다.")
    public SnapshotImageUploadResponse uploadSnapshotImage(
            @RequestPart("video")
            @Parameter(description = "multipartFile image file")
            MultipartFile multipartFile,

            @RequestPart("snapshotInfo")
            @Parameter(description = "SnapShotImageUploadRequest 객체 json input")
            @Valid final SnapShotImageUploadRequest request,

            @PathVariable("individual-video-id") String individualVideoId
            ) {

        return individualVideoService.uploadSnapshotImage(multipartFile, individualVideoId, request.getVideoTime());
    }

    @Operation(summary = "individual videos list get api", description = "video space participant id를 이용하여 individual video id list를 get 하는 api입니다")
    @GetMapping("/api/videos")
    public List<IndividualVideoGetResponse> getList(@RequestBody @Valid IndividualVideosGetRequest individualVideosGetRequest) {

        List<IndividualVideoGetResponse> individualVideoGetResponse = individualVideoService.getByVideoSpaceParticipantId(individualVideosGetRequest.getVideoSpaceParticipantId());

        return individualVideoGetResponse;
    }

    @Operation(summary = "individual video get api", description = "individual video uuid를 통해 individual video detail info, file url, visual index file path를 get하는 api 입니다.")
    @GetMapping("/api/videos/{individual-video-id}")
    public IndividualVideoDetailsGetResponse getDetails(@PathVariable("individual-video-id") String individualVideoId) throws IOException {

        IndividualVideoDetailsGetResponse individualVideoDetailsGetResponse = individualVideoService.getById(individualVideoId);

        return individualVideoDetailsGetResponse;
    }

}
