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

    /**
     * text memo의 최신 버전의 키값은 '_latest'와 조합되어 생성된다.
     * text memo의 기록 버전의 키값은 '_history'와 조합되어 생성된다.
     */

    /**
     * text memo의 latest 버전을 업데이트하고, history 버전을 add한다.
     */
//    public TextMemo save(TextMemo textMemo) {
//
//        redisTemplate.executePipelined((RedisCallback<Object>) connection -> {
//
//            String individualVideoId = textMemo.getIndividualVideoId().toString();
//            String defaultStateKey = getDefaultStateKey(individualVideoId);
//            String textMemoLatestKey = getTextMemoLatestKey(individualVideoId);
//            String textMemoHistoryKey = getTextMemoHistoryKey(textMemo, individualVideoId);
//
//            Map<String, Object> map = objectMapper.convertValue(textMemo, Map.class);
//
//            // text_memo_state set 형식에 history의 key 추가.
//            connection.sAdd(keySerializer.serialize(defaultStateKey), valueSerializer.serialize(textMemoHistoryKey));
//
//            for (String key : map.keySet()) {
//
//                // text_memo_latest 추가
//                connection.hashCommands().hSet(keySerializer.serialize(textMemoLatestKey),
//                        valueSerializer.serialize(key), valueSerializer.serialize(map.get(key)));
//
//                // text_memo_history 추가
//                connection.hashCommands().hSet(keySerializer.serialize(textMemoHistoryKey),
//                        valueSerializer.serialize(key), valueSerializer.serialize(map.get(key)));
//            }
//
//            return null;
//        });
//
//        return textMemo;
//    }

    /**
     * text memo latest get 메소드.
     */
//    public Optional<TextMemo> getLatest(String individualVideoId) {
//
//        String textMemoLatestKey = getTextMemoLatestKey(getDefaultStateKey(individualVideoId));
//        Map<Object, Object> map = redisTemplate.opsForHash().entries(textMemoLatestKey);
//
//        if (map.isEmpty()) {
//            return Optional.empty();
//        }
//
//        return Optional.ofNullable(objectMapper.convertValue(map, TextMemo.class));
//    }

    // key_history를 통해 레디스에서 find and 객체 list return
//    public List<TextMemo> getTextMemoHistories(String individualVideoId) {
//
//        List<TextMemo> list = new ArrayList<>();
//
//        // redis의 set 형식에서 멤버들을 꺼내온다.
//        Set<Object> stateHistoryMembers = redisTemplate.opsForSet().members(getDefaultStateKey(individualVideoId));
//
//        redisTemplate.execute((RedisCallback<List<String>>) connection -> {
//
//            List<TextMemo> textMemoHistories = stateHistoryMembers.stream()
//                    .map(key -> objectMapper.convertValue(redisTemplate.opsForHash().entries(key.toString()),
//                            TextMemo.class))
//                    .collect(Collectors.toList());
//
//            list.addAll(textMemoHistories);
//
//            return null;
//        });
//
//        return list;
//    }
//    public void deleteHistoryFromCache(String individualVideoId) {
//
//        String key = getDefaultStateKey(individualVideoId);
//
//        Set<Object> members = redisTemplate.opsForSet().members(key);
//
//        redisTemplate.executePipelined((RedisCallback<Object>) connection -> {
//
//            connection.del(keySerializer.serialize(key));
//
//            members.forEach(state -> {
//                connection.del(keySerializer.serialize(state));
//            });
//
//            return null;
//        });
//    }
//
//    public void deleteLatest(String individualVideoId) {
//        redisTemplate.delete(getDefaultStateKey(individualVideoId) + "_latest");
//    }

}
