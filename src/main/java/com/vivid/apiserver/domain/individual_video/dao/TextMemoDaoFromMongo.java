package com.vivid.apiserver.domain.individual_video.dao;

import com.vivid.apiserver.domain.individual_video.dao.repository.TextMemoMongoRepository;
import com.vivid.apiserver.domain.individual_video.dao.repository.TextMemoMongoRepository.TextMemoOnMongoDb;
import com.vivid.apiserver.domain.individual_video.domain.TextMemo;
import com.vivid.apiserver.global.error.exception.ErrorCode;
import com.vivid.apiserver.global.error.exception.NotFoundException;
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
public class TextMemoDaoFromMongo implements TextMemoDao {

    private final TextMemoMongoRepository textMemoMongoRepository;

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
        Optional<TextMemoOnMongoDb> textMemoOnMongoDbOptional = textMemoMongoRepository.findById(individualVideoId);

        if (textMemoOnMongoDbOptional.isEmpty()) {
            textMemoMongoRepository.save(TextMemoOnMongoDb.of(individualVideoId, textMemos));
        } else {

            Query query = new Query(Criteria.where("_id").is(individualVideoId));
            Update update = new Update().push("textMemos").each(textMemos);
            mongoTemplate.updateFirst(query, update, TextMemoOnMongoDb.class);

//            TextMemoOnMongoDb textMemoOnMongoDb = textMemoOnMongoDbOptional.get();
//            textMemoOnMongoDb.getTextMemos().addAll(textMemos);
//            textMemoMongoRepository.save(textMemoOnMongoDb);
        }
    }

    private List<TextMemo> getTextMemos(String individualVideoId) {
        return textMemoMongoRepository.findById(individualVideoId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.TEXT_MEMO_NOT_EXIST))
                .getTextMemos();
    }


}
