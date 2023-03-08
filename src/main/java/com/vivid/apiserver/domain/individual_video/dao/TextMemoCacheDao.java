package com.vivid.apiserver.domain.individual_video.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.vivid.apiserver.domain.individual_video.domain.TextMemo;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TextMemoCacheDao {

    private final String TEXT_MEMO_KEY = "text_memo";

    private final RedisTemplate<String, TextMemo> redisTemplate;
    private final ZSetOperations<String, TextMemo> zSetOperations;
    private final ObjectMapper objectMapper;

    public TextMemoCacheDao(@Qualifier("noteRedisTemplate") RedisTemplate<String, TextMemo> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.zSetOperations = redisTemplate.opsForZSet();
        this.objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }

    public void save(TextMemo textMemo, String individualVideoId) {
        String key = getDefaultStateKey(individualVideoId);
        zSetOperations.add(key, textMemo, Timestamp.valueOf(textMemo.getCreatedAt()).getTime());
    }

    public Optional<TextMemo> findLatestByIndividualVideoId(String individualVideoId) {

        String key = getDefaultStateKey(individualVideoId);
        Set<TextMemo> textMemo = zSetOperations.range(key, -1, -1);

        if (textMemo == null || textMemo.isEmpty()) {
            return Optional.empty();
        }

        return Optional.ofNullable(textMemo.iterator().next());
    }

    public List<TextMemo> findAllByIndividualVideoId(String individualVideoId) {

        String key = getDefaultStateKey(individualVideoId);
        Set<TextMemo> textMemos = redisTemplate.opsForZSet().range(key, 0, -1);

        if (textMemos == null) {
            return Collections.emptyList();
        }

        return textMemos.stream()
                .map(textMemo -> objectMapper.convertValue(textMemo, TextMemo.class))
                .collect(Collectors.toList());
    }

    public void deleteAllByIndividualVideoId(String individualVideoId) {

        String key = getDefaultStateKey(individualVideoId);
        redisTemplate.delete(key);
    }

    public String getDefaultStateKey(String individualVideoId) {
        return TEXT_MEMO_KEY + "_" + individualVideoId;
    }
}
