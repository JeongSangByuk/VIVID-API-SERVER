package com.vivid.apiserver.domain.individual_video.service.query;

import com.vivid.apiserver.domain.individual_video.dao.TextMemoCacheDao;
import com.vivid.apiserver.domain.individual_video.dao.TextMemoDao;
import com.vivid.apiserver.domain.individual_video.domain.TextMemo;
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
    public Optional<TextMemo> getLatestThroughCache(String individualVideoId) {

        return textMemoCacheDao.findLatestByIndividualVideoId(individualVideoId)
                .or(() -> textMemoDao.findLatestByIndividualId(individualVideoId));
    }

    public List<TextMemo> getHistories(String individualVideoId) {

        List<TextMemo> textMemoHistories = textMemoDao.findAllByIndividualId(individualVideoId);

        if (textMemoHistories.isEmpty()) {
            throw new NotFoundException(ErrorCode.TEXT_MEMO_NOT_EXIST);
        }

        return textMemoHistories;
    }

    public TextMemo getLatestFromCache(String individualVideoId) {

        return textMemoCacheDao.findLatestByIndividualVideoId(individualVideoId)
                .orElseThrow(() -> {
                    throw new NotFoundException(ErrorCode.TEXT_MEMO_NOT_EXIST);
                });
    }

    public List<TextMemo> getHistoriesFromCache(String individualVideoId) {

        List<TextMemo> textMemos = textMemoCacheDao.findAllByIndividualVideoId(individualVideoId);

        if (textMemos.isEmpty()) {
            throw new NotFoundException(ErrorCode.TEXT_MEMO_NOT_EXIST);
        }

        return textMemos;
    }


}
