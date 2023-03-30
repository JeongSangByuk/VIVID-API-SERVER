package com.vivid.apiserver.domain.individual_video.api;

import com.vivid.apiserver.domain.individual_video.dto.request.TextMemoCacheSaveRequest;
import com.vivid.apiserver.domain.individual_video.dto.response.TextMemoResponse;
import com.vivid.apiserver.domain.individual_video.service.TextMemoService;
import com.vivid.apiserver.global.success.SuccessCode;
import com.vivid.apiserver.global.success.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/individual-videos")
public class TextMemoApi {

    private final TextMemoService textMemoService;

    @Operation(summary = "text memo latest get api", description = "캐시에 있는 text memo 최신 버전을 get합니다. 캐시에 없을시 DB에서 get합니다.")
    @ApiResponse(responseCode = "200", description = "text memo latest을 json 형식으로 반환합니다.")
    @GetMapping("/{individual-video-id}/cache/text-memo-latest")
    public ResponseEntity<SuccessResponse<TextMemoResponse>> getLatestFromCache(
            @PathVariable("individual-video-id") String individualVideoId) {

        return SuccessResponse.success(SuccessCode.OK_SUCCESS, textMemoService.getLatest(individualVideoId));
    }

    @Operation(summary = "text memo list get api", description = "DB에 있는 text memo 리스트를 get합니다.")
    @ApiResponse(responseCode = "200", description = "text memo를 json list 형식으로 반환합니다.")
    @GetMapping("/{individual-video-id}/text-memos")
    public ResponseEntity<SuccessResponse<List<TextMemoResponse>>> getAllFromDb(
            @PathVariable("individual-video-id") String individualVideoId) {

        return SuccessResponse.success(SuccessCode.OK_SUCCESS, textMemoService.getAll(individualVideoId));
    }

    @Operation(summary = "text memo cache save api", description = "text memo를 캐시에 저장합니다.")
    @ApiResponse(responseCode = "200", description = "반환값은 없습니다.")
    @PostMapping("/{individual-video-id}/cache/text-memo")
    public ResponseEntity<SuccessResponse<String>> saveToCache(
            @RequestBody @Valid final TextMemoCacheSaveRequest dto,
            @PathVariable("individual-video-id") String individualVideoId) {

        textMemoService.saveToCache(dto, individualVideoId);

        return SuccessResponse.OK;
    }

    @Operation(summary = "text memo save api", description = "캐시에 있는 text memo 모두를 DB에 저장합니다.")
    @ApiResponse(responseCode = "200", description = "반환값은 없습니다.")
    @PostMapping("/{individual-video-id}/text-memos")
    public ResponseEntity<SuccessResponse<String>> saveAll(
            @PathVariable("individual-video-id") String individualVideoId) {

        textMemoService.saveAll(individualVideoId);

        return SuccessResponse.OK;
    }

}
