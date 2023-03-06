package com.vivid.apiserver.domain.individual_video.api;

import com.vivid.apiserver.domain.individual_video.application.IndividualVideoService;
import com.vivid.apiserver.domain.individual_video.dto.response.IndividualVideoDetailsGetResponse;
import com.vivid.apiserver.domain.individual_video.dto.response.SnapshotImageUploadResponse;
import com.vivid.apiserver.global.infra.storage.AwsS3Service;
import com.vivid.apiserver.global.success.SuccessCode;
import com.vivid.apiserver.global.success.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
    public ResponseEntity<SuccessResponse<SnapshotImageUploadResponse>> uploadSnapshotImage(
            @RequestPart("image")
            @Parameter(description = "multipartFile image file")
            MultipartFile multipartFile,

            @RequestParam("video-time") Long videoTime,
            @PathVariable("individual-video-id") String individualVideoId) {

        return SuccessResponse.success(SuccessCode.OK_SUCCESS,
                individualVideoService.uploadSnapshotImage(multipartFile, individualVideoId, videoTime));
    }

    @Operation(summary = "individual video get api", description = "individual video uuid를 통해 individual video detail info, file url, visual index file path를 get하는 api 입니다.")
    @GetMapping("/api/individual-videos/{individual-video-id}")
    public ResponseEntity<SuccessResponse<IndividualVideoDetailsGetResponse>> getDetails(
            @PathVariable("individual-video-id") String individualVideoId) {

        return SuccessResponse.success(SuccessCode.OK_SUCCESS,
                individualVideoService.getDetailsById(individualVideoId));
    }

    @Operation(summary = "individual video last accessed update api", description = "individual video의 최종 접근 시각을 최신화하는 api입니다.")
    @PutMapping("/api/individual-videos/{individual-video-id}/accessed")
    public ResponseEntity<SuccessResponse<String>> updateLastAccessTime(
            @PathVariable("individual-video-id") String individualVideoId) {

        individualVideoService.updateLastAccessTime(individualVideoId);

        return SuccessResponse.OK;

    }

    @Operation(summary = "individual video progress rate api", description = "individual video의 학습률/진행도를 업데이트하는 api입니다.")
    @PutMapping("/api/individual-videos/{individual-video-id}/progress-rate/{percent}")
    public ResponseEntity<SuccessResponse<String>> updateProgressRate(
            @PathVariable("individual-video-id") String individualVideoId,
            @PathVariable("percent") Integer progressRate) {

        individualVideoService.updateProgressRate(individualVideoId, progressRate);

        return SuccessResponse.OK;
    }

    @Operation(summary = "individual video delete api", description = "individual video를 삭제하는 api입니다.")
    @DeleteMapping("/api/individual-videos/{individual-video-id}")
    public ResponseEntity<SuccessResponse<String>> delete(@PathVariable("individual-video-id") String individualVideoId)
            throws IOException {

        // delete by id
        individualVideoService.deleteById(individualVideoId);

        return SuccessResponse.OK;
    }
}
