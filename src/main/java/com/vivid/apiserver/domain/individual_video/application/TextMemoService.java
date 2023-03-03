package com.vivid.apiserver.domain.individual_video.application;

import com.vivid.apiserver.domain.individual_video.application.command.TextMemoCommandService;
import com.vivid.apiserver.domain.individual_video.application.query.IndividualVideoQueryService;
import com.vivid.apiserver.domain.individual_video.application.query.TextMemoQueryService;
import com.vivid.apiserver.domain.individual_video.domain.IndividualVideo;
import com.vivid.apiserver.domain.individual_video.domain.TextMemo;
import com.vivid.apiserver.domain.individual_video.domain.TextMemoHistory;
import com.vivid.apiserver.domain.individual_video.domain.TextMemoLatest;
import com.vivid.apiserver.domain.individual_video.dto.request.TextMemoCacheSaveRequest;
import com.vivid.apiserver.domain.individual_video.dto.response.TextMemoResponse;
import com.vivid.apiserver.domain.user.application.CurrentUserService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class TextMemoService {

    private final TextMemoCommandService textMemoCommandService;
    private final IndividualVideoQueryService individualVideoQueryService;

    private final TextMemoQueryService textMemoQueryService;

    private final CurrentUserService currentUserService;

    /**
     * text memo 최신 버전 get 메소드
     */
    public TextMemoResponse getLatest(String individualVideoId) {

        // TODO participants fetch join
        IndividualVideo individualVideo = individualVideoQueryService.findById(individualVideoId);
        currentUserService.checkValidUserAccess(individualVideo.getVideoSpaceParticipant().getEmail());

        return textMemoQueryService.getLatestThroughCache(individualVideoId)
                .map(TextMemoResponse::from)
                .orElseGet(TextMemoResponse::createNullObject);
    }

    /**
     * text memo 과거 히스토리 get 메소드
     */
    public List<TextMemoResponse> getHistories(String individualVideoId) {

        IndividualVideo individualVideo = individualVideoQueryService.findById(individualVideoId);
        currentUserService.checkValidUserAccess(individualVideo.getVideoSpaceParticipant().getEmail());

        return textMemoQueryService.getHistories(individualVideoId).stream()
                .map(TextMemoResponse::from)
                .collect(Collectors.toList());
    }

    /**
     * text_memo_latest 저장 메소드
     */
    public void saveToCache(final TextMemoCacheSaveRequest textMemoState, String individualVideoId) {

        TextMemo textMemo = textMemoState.toEntity(individualVideoId);
        textMemoCommandService.saveToCache(textMemo);
    }

    /**
     * cache의 데이터를 전부 db에 저장하는 메소드
     */
    public void saveAll(String individualVideoId) {

        IndividualVideo individualVideo = individualVideoQueryService.findById(individualVideoId);
        currentUserService.checkValidUserAccess(individualVideo.getVideoSpaceParticipant().getEmail());

        saveLatest(individualVideoId);
        saveHistories(individualVideoId);
    }

    private void saveLatest(String individualVideoId) {

        TextMemoLatest textMemoStateLatest = textMemoQueryService.getLatestFromCache(individualVideoId);

        textMemoCommandService.saveLatest(textMemoStateLatest);
        textMemoCommandService.deleteLatestToCache(individualVideoId);
    }

    private void saveHistories(String individualVideoId) {

        List<TextMemoHistory> historyListFromRedis = textMemoQueryService.getHistoriesFromCache(individualVideoId);

        textMemoCommandService.saveHistories(historyListFromRedis);
        textMemoCommandService.deleteHistories(individualVideoId);
    }
}
