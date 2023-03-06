package com.vivid.apiserver.domain.individual_video.application.query;

import com.vivid.apiserver.domain.individual_video.dao.TextMemoCacheDao;
import com.vivid.apiserver.domain.individual_video.dao.TextMemoDao;
import com.vivid.apiserver.domain.individual_video.domain.TextMemoHistory;
import com.vivid.apiserver.domain.individual_video.domain.TextMemoLatest;
import com.vivid.apiserver.global.error.exception.ErrorCode;
import com.vivid.apiserver.global.error.exception.NotFoundException;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class TextMemoQueryService {

    private final TextMemoDao textMemoDao;
    private final TextMemoCacheDao textMemoCacheDao;

    /**
     * text memo latest get 메소드. 캐시를 거쳤다가, 캐시에 존재하지 않을 경우 db에 접근하여 get합니다.
     */
    public Optional<TextMemoLatest> getLatestThroughCache(String individualVideoId) {

        return textMemoCacheDao.getLatest(individualVideoId)
                .or(() -> textMemoDao.getLatest(individualVideoId));
    }

    public List<TextMemoHistory> getHistories(String individualVideoId) {

        List<TextMemoHistory> textMemoHistories = textMemoDao.getHistories(individualVideoId);

        if (textMemoHistories.isEmpty()) {
            throw new NotFoundException(ErrorCode.TEXT_MEMO_NOT_EXIST);
        }

        return textMemoHistories;
    }

    public TextMemoLatest getLatestFromCache(String individualVideoId) {

        return textMemoCacheDao.getLatest(individualVideoId)
                .orElseThrow(() -> {
                    throw new NotFoundException(ErrorCode.TEXT_MEMO_NOT_EXIST);
                });
    }

    public List<TextMemoHistory> getHistoriesFromCache(String individualVideoId) {

        List<TextMemoHistory> textMemoHistories = textMemoCacheDao.getTextMemoHistories(individualVideoId);

        if (textMemoHistories.isEmpty()) {
            throw new NotFoundException(ErrorCode.TEXT_MEMO_NOT_EXIST);
        }

        return textMemoHistories;
    }


}
