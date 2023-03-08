package com.vivid.apiserver.domain.individual_video.application.command;

import com.vivid.apiserver.domain.individual_video.dao.TextMemoCacheDao;
import com.vivid.apiserver.domain.individual_video.dao.TextMemoDao;
import com.vivid.apiserver.domain.individual_video.domain.TextMemo;
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

    public void save(TextMemo TextMemo, String individualVideoId) {
        textMemoDao.save(TextMemo, individualVideoId);
    }

    public void saveAll(List<TextMemo> textMemos, String individualVideoId) {
        textMemoDao.saveAll(textMemos, individualVideoId);
    }

    public void saveToCache(TextMemo textMemo, String individualVideoId) {
        textMemoCacheDao.save(textMemo, individualVideoId);
    }

    public void deleteAllOnCache(String individualVideoId) {
        textMemoCacheDao.deleteAllByIndividualVideoId(individualVideoId);
    }
}
