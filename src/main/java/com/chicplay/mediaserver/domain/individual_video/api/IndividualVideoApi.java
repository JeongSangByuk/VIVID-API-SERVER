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

    @PostMapping(value = "/api/individual-videos/{individual-video-id}/snapshot",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "image snapshot save api", description = "이미지 스냅샷을 저장하는 메소드입니다.")
    @ApiResponse(responseCode = "200", description = "이미지 업로드 완료 후, 각각 이미지의 url을 json 형식으로 반환합니다.")
    public SnapshotImageUploadResponse uploadSnapshotImage(
            @RequestPart("image")
            @Parameter(description = "multipartFile image file")
            MultipartFile multipartFile,

            @RequestParam("video-time") Long videoTime,
            @PathVariable("individual-video-id") String individualVideoId
            ) {

        return individualVideoService.uploadSnapshotImage(multipartFile, individualVideoId,videoTime);
    }

    @Operation(summary = "individual video get api", description = "individual video uuid를 통해 individual video detail info, file url, visual index file path를 get하는 api 입니다.")
    @GetMapping("/api/individual-videos/{individual-video-id}")
    public IndividualVideoDetailsGetResponse getDetails(@PathVariable("individual-video-id") String individualVideoId) throws IOException {

        IndividualVideoDetailsGetResponse individualVideoDetailsGetResponse = individualVideoService.getDetailsById(individualVideoId);

        return individualVideoDetailsGetResponse;
    }

    @Operation(summary = "individual video last accessed update api", description = "individual video의 최종 접근 시각을 최신화하는 api입니다.")
    @PutMapping("/api/individual-videos/{individual-video-id}/accessed")
    public void updateLastAccessTime(@PathVariable("individual-video-id") String individualVideoId) throws IOException {

        // 최종 접근 시간 변경
        individualVideoService.updateLastAccessTime(individualVideoId);
    }

    @Operation(summary = "individual video progress rate api", description = "individual video의 학습률/진행도를 업데이트하는 api입니다.")
    @PutMapping("/api/individual-videos/{individual-video-id}/progress-rate/{percent}")
    public void updateProgressRate(@PathVariable("individual-video-id") String individualVideoId,
                                   @PathVariable("percent") Long progressRate) throws IOException {

        // progress rate 수정
        individualVideoService.updateProgressRate(individualVideoId, progressRate);
    }

    @Operation(summary = "individual video delete api", description = "individual video를 삭제하는 api입니다.")
    @DeleteMapping("/api/individual-videos/{individual-video-id}")
    public void delete(@PathVariable("individual-video-id") String individualVideoId) throws IOException {

        // delete by id
        individualVideoService.deleteById(individualVideoId);

    }


    // Deprecated

    @Operation(summary = "[Deprecated]individual videos list get api", description = "[Deprecated]video space participant id를 이용하여 individual video id list를 get 하는 api입니다")
    @GetMapping("/api/individual-videos")
    public List<IndividualVideoGetResponse> getList(@RequestBody @Valid IndividualVideosGetRequest individualVideosGetRequest) {

        List<IndividualVideoGetResponse> individualVideoGetResponse = individualVideoService.findAllByVideoParticipantId(individualVideosGetRequest.getVideoSpaceParticipantId());

        return individualVideoGetResponse;
    }


}
