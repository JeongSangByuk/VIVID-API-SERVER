package com.vivid.apiserver.domain.video.api;

import com.vivid.apiserver.domain.video.application.VideoService;
import com.vivid.apiserver.domain.video.application.VideoUploadService;
import com.vivid.apiserver.domain.video.dto.request.VideoSaveRequest;
import com.vivid.apiserver.domain.video.dto.response.VideoSaveResponse;
import com.vivid.apiserver.global.success.SuccessCode;
import com.vivid.apiserver.global.success.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping()
public class VideoApi {

    private final VideoService videoService;

    private final VideoUploadService videoUploadService;

    /**
     * 1. 직접 업로드
     * 2. VOD(유튜브) 링크를 통한 업로드
     * 3. 녹강(Webex, Zoom) 공유 링크를 통한 업로드
     */

    /***
     * raw-video-storage에 video를 업로드하는 api
     * aws media convert를 이용한
     * 인코딩이 완료되면 lambda 함수를 통해서 자동으로 save api 호출
     */
    @Operation(summary = "video 직접 업로드 api", description = "video를 직접 업로드하는 api")
    @PostMapping(value = "/api/videos/{video-space-id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SuccessResponse<VideoSaveResponse>> upload(
            @RequestPart("video")
            @Parameter(description = "multipartFile video file")
            MultipartFile multipartFile,

            @RequestPart(value = "title")
            @Parameter(description = "title") final String title,

            @RequestPart(value = "description")
            @Parameter(description = "description") final String description,

            @PathVariable("video-space-id") Long videoSpaceId
    ) {

        VideoSaveResponse videoSaveResponse = videoUploadService.uploadByDirectUpload(multipartFile, videoSpaceId,
                new VideoSaveRequest(title, description));

        return SuccessResponse.success(SuccessCode.OK_SUCCESS, videoSaveResponse);
    }

    @Operation(summary = "video 삭제 api", description = "video를 직접 업로드하는 api")
    @DeleteMapping(value = "/api/videos/{video-id}")
    public ResponseEntity<SuccessResponse<String>> delete(@PathVariable("video-id") Long videoId) {

        videoService.delete(videoId);

        return SuccessResponse.OK;
    }

    @Operation(summary = "video의 업로드 상태를 변환 api", description = "video의 업로드 상태를 true로 바꾸는 api 입니다. 해당 api는 aws 람다에서 호출됩니다.")
    @PutMapping(value = "/api/videos/{video-id}/uploaded")
    public ResponseEntity<SuccessResponse<String>> changeUploadStateAfterUploaded(
            @PathVariable("video-id") Long videoId) {

        videoService.changeUploadState(videoId);

        return SuccessResponse.OK;

    }

}




