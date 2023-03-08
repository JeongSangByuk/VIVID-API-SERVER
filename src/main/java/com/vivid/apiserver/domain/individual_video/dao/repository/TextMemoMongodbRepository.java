package com.vivid.apiserver.domain.individual_video.dao.repository;

import com.vivid.apiserver.domain.individual_video.dao.repository.TextMemoMongodbRepository.TextMemoOnMongoDb;
import com.vivid.apiserver.domain.individual_video.domain.TextMemo;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TextMemoMongodbRepository extends MongoRepository<TextMemoOnMongoDb, String> {

    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Document(collection = "text_memo")
    class TextMemoOnMongoDb {

        @Id
        private String individualVideoId;

        private List<TextMemo> textMemos;

        public static TextMemoOnMongoDb of(String individualVideoId, List<TextMemo> textMemos) {
            return TextMemoOnMongoDb.builder()
                    .individualVideoId(individualVideoId)
                    .textMemos(textMemos)
                    .build();
        }
    }
}
