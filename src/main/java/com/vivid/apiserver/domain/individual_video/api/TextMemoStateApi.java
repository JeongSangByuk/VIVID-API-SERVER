package com.vivid.apiserver.domain.individual_video.api;

import com.vivid.apiserver.domain.individual_video.application.TextMemoService;
import com.vivid.apiserver.domain.individual_video.dto.request.TextMemoCacheSaveRequest;
import com.vivid.apiserver.domain.individual_video.dto.response.TextMemoResponse;
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
public class TextMemoStateApi {

    private final TextMemoService textMemoService;

    // redis cache에 state save 메소드
    // save할 때는 latest, history의 구분 없이 input을 받고, dao 파트에서 나눠서 저장한다.
    @Operation(summary = "text state 레디스 save api", description = "text state를 레디스 캐시에 저장합니다.")
    @ApiResponse(responseCode = "200", description = "반환값은 없습니다.")
    @PostMapping("/{individual-video-id}/cache/text-memo-state")
    public ResponseEntity<SuccessResponse<String>> saveToCache(
            @RequestBody @Valid final TextMemoCacheSaveRequest dto,
            @PathVariable("individual-video-id") String individualVideoId) {

        textMemoService.saveToCache(dto, individualVideoId);

        return SuccessResponse.OK;
    }

    // dynamodb에 latest,history 모두 save
    @Operation(summary = "text state 다이나모DB save api", description = "레디스 캐시에 있는 text state 모두를 다이나모DB에 저장합니다.")
    @ApiResponse(responseCode = "200", description = "반환값은 없습니다.")
    @PostMapping("/{individual-video-id}/text-memo-states")
    public ResponseEntity<SuccessResponse<String>> saveListToDynamoDb(
            @PathVariable("individual-video-id") String individualVideoId) {

        textMemoService.saveAll(individualVideoId);

        return SuccessResponse.OK;
    }

    // redis 캐시로 부터 text memo state latest get
    // redis에 데이터가 없다면(만료됐다면) dynamoDB에서 get한다.
    @Operation(summary = "text state latest get api", description = "레디스 캐시에 있는 text state latest를 get합니다. 캐시에 없을시 다이나모DB에서 get합니다.")
    @ApiResponse(responseCode = "200", description = "text state latest을 json 형식으로 반환합니다.")
    @GetMapping("/{individual-video-id}/cache/text-memo-state-latest")
    public ResponseEntity<SuccessResponse<TextMemoResponse>> getFromCache(
            @PathVariable("individual-video-id") String individualVideoId) {

        return SuccessResponse.success(SuccessCode.OK_SUCCESS,
                textMemoService.getLatest(individualVideoId));
    }

    // redis 캐시로 부터 text memo state latest get
    // redis에 데이터가 없다면(만료됐다면) dynamoDB에서 get한다.
    @Operation(summary = "text state history list get api", description = "다이나모DB 에 있는 text state history list를 get합니다.")
    @ApiResponse(responseCode = "200", description = "text state history를 json list 형식으로 반환합니다.")
    @GetMapping("/{individual-video-id}/text-memo-state-history")
    public ResponseEntity<SuccessResponse<List<TextMemoResponse>>> getHistoryListFromDynamoDb(
            @PathVariable("individual-video-id") String individualVideoId) {

        return SuccessResponse.success(SuccessCode.OK_SUCCESS, textMemoService.getHistories(
                individualVideoId));
    }

    // dynamoDB에 text state latest문 저장 메소드
//    @Operation(summary = "text state latest 다이나모DB save api", description = "레디스 캐시에 있는 text state latest를 다이나모DB에 저장합니다.")
//    @ApiResponse(responseCode = "200", description = "반환값은 없습니다.")
//    @PostMapping("/{individual-video-id}/text-memo-state-latest")
//    public void saveLatestToDynamoDb(@PathVariable("individual-video-id") String individualVideoId) {
//
//        textMemoStateService.saveLatestToDynamoDb(individualVideoId);
//    }
//
//    // dynamoDB에 text state history문 저장 메소드.
//    @Operation(summary = "text state history 다이나모DB save api", description = "레디스 캐시에 있는 text state history 전부를 다이나모DB에 저장합니다.")
//    @ApiResponse(responseCode = "200", description = "반환값은 없습니다.")
//    @PostMapping("/{individual-video-id}/text-memo-state-history")
//    public void saveHistoryToDynamoDb(@PathVariable("individual-video-id") String individualVideoId) {
//
//        textMemoStateService.saveHistoryToDynamoDb(individualVideoId);
//    }

    /*
    old version
     */

    // redis 캐시에 텍스트 메모 스테이트 리스트를 저장하는 메소드
//    @PostMapping("/cache/text-memo-states")
//    public void saveTextMemoStatesToCache(@RequestBody @Valid final List<TextMemoStateRedisSaveRequest> textMemoStates) {
//
//        individualVideoService.saveTextMemoStatesToRedis(textMemoStates);
//    }

}
