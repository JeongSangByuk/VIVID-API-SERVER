package com.vivid.apiserver.domain.individual_video.application.command;

import com.vivid.apiserver.domain.individual_video.dao.TextMemoCacheDao;
import com.vivid.apiserver.domain.individual_video.dao.TextMemoDao;
import com.vivid.apiserver.domain.individual_video.domain.TextMemo;
import com.vivid.apiserver.domain.individual_video.domain.TextMemoHistory;
import com.vivid.apiserver.domain.individual_video.domain.TextMemoLatest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class TextMemoCommandService {

    private final TextMemoDao textMemoDao;
    private final TextMemoCacheDao textMemoCacheDao;

    public void saveLatest(TextMemoLatest textMemoLatest) {
        textMemoDao.saveLatest(textMemoLatest);
    }

    public void saveHistories(List<TextMemoHistory> textMemoStateHistories) {
        textMemoDao.saveHistories(textMemoStateHistories);
    }

    public void saveToCache(TextMemo textMemo) {
        textMemoCacheDao.save(textMemo);
    }

    public void deleteHistories(String individualVideoId) {
        textMemoCacheDao.deleteHistoryFromCache(individualVideoId);
    }

    public void deleteLatestToCache(String individualVideoId) {
        textMemoCacheDao.deleteLatest(individualVideoId);
    }
}