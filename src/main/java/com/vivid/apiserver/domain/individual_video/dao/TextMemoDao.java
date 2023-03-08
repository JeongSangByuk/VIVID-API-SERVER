package com.vivid.apiserver.domain.individual_video.dao;

import com.vivid.apiserver.domain.individual_video.domain.TextMemo;
import java.util.List;
import java.util.Optional;

public interface TextMemoDao {

    Optional<TextMemo> findLatestByIndividualId(String individualVideoId);

    List<TextMemo> findAllByIndividualId(String individualVideoId);

    void save(TextMemo textMemo, String individualVideoId);

    void saveAll(List<TextMemo> textMemos, String individualVideoId);

}
