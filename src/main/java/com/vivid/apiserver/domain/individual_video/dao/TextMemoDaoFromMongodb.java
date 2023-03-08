package com.vivid.apiserver.domain.individual_video.dao;

import com.vivid.apiserver.domain.individual_video.dao.repository.TextMemoMongodbRepository;
import com.vivid.apiserver.domain.individual_video.dao.repository.TextMemoMongodbRepository.TextMemoOnMongoDb;
import com.vivid.apiserver.domain.individual_video.domain.TextMemo;
import com.vivid.apiserver.global.error.exception.ErrorCode;
import com.vivid.apiserver.global.error.exception.NotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class TextMemoDaoFromMongodb implements TextMemoDao {

    private final TextMemoMongodbRepository textMemoMongodbRepository;

    private final MongoTemplate mongoTemplate;

    public Optional<TextMemo> findLatestByIndividualId(String individualVideoId) {

        List<TextMemo> textMemos = getTextMemos(individualVideoId);

        return Optional.ofNullable(textMemos.get(0));
    }

    public List<TextMemo> findAllByIndividualId(String individualVideoId) {
        return getTextMemos(individualVideoId);
    }

    public void save(TextMemo textMemo, String individualVideoId) {

        addOrSaveTextMemo(List.of(textMemo), individualVideoId);
    }

    public void saveAll(List<TextMemo> textMemos, String individualVideoId) {

        addOrSaveTextMemo(textMemos, individualVideoId);
    }

    private void addOrSaveTextMemo(List<TextMemo> textMemos, String individualVideoId) {

        Collections.reverse(textMemos);

        if (!textMemoMongodbRepository.existsById(individualVideoId)) {
            textMemoMongodbRepository.save(TextMemoOnMongoDb.of(individualVideoId, textMemos));
            return;
        }

        Query query = new Query(Criteria.where("_id").is(individualVideoId));
        Update update = new Update().push("textMemos").atPosition(0).each(textMemos);
        mongoTemplate.updateFirst(query, update, TextMemoOnMongoDb.class);
    }

    private List<TextMemo> getTextMemos(String individualVideoId) {

        return textMemoMongodbRepository.findById(individualVideoId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.TEXT_MEMO_NOT_EXIST))
                .getTextMemos();
    }


}
