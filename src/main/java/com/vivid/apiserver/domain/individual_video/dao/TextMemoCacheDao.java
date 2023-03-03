package com.vivid.apiserver.domain.individual_video.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vivid.apiserver.domain.individual_video.domain.TextMemo;
import com.vivid.apiserver.domain.individual_video.domain.TextMemoHistory;
import com.vivid.apiserver.domain.individual_video.domain.TextMemoLatest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TextMemoCacheDao {

    private final String TEXT_MEMO_STATE_KEY = "text_memo_state";

    private final RedisTemplate<String, Object> redisTemplate;

    private final ObjectMapper objectMapper;

    private final RedisSerializer keySerializer;

    private final RedisSerializer valueSerializer;

    public TextMemoCacheDao(@Qualifier("noteRedisTemplate") RedisTemplate<?, ?> redisTemplate,
            ObjectMapper objectMapper) {

        this.redisTemplate = (RedisTemplate<String, Object>) redisTemplate;
        this.objectMapper = objectMapper;

        this.keySerializer = this.redisTemplate.getStringSerializer();
        this.valueSerializer = this.redisTemplate.getValueSerializer();
    }

    /**
     * text memo의 최신 버전의 키값은 '_latest'와 조합되어 생성된다.
     * text memo의 기록 버전의 키값은 '_history'와 조합되어 생성된다.
     */

    /**
     * text memo의 latest 버전을 업데이트하고, history 버전을 add한다.
     */
    public TextMemo save(TextMemo textMemo) {

        redisTemplate.executePipelined((RedisCallback<Object>) connection -> {

            String individualVideoId = textMemo.getIndividualVideoId().toString();
            String defaultStateKey = getDefaultStateKey(individualVideoId);
            String textMemoLatestKey = getTextMemoLatestKey(individualVideoId);
            String textMemoHistoryKey = getTextMemoHistoryKey(textMemo, individualVideoId);

            Map<String, Object> map = objectMapper.convertValue(textMemo, Map.class);

            // text_memo_state set 형식에 history의 key 추가.
            connection.sAdd(keySerializer.serialize(defaultStateKey), valueSerializer.serialize(textMemoHistoryKey));

            for (String key : map.keySet()) {

                // text_memo_latest 추가
                connection.hashCommands().hSet(keySerializer.serialize(textMemoLatestKey),
                        valueSerializer.serialize(key), valueSerializer.serialize(map.get(key)));

                // text_memo_history 추가
                connection.hashCommands().hSet(keySerializer.serialize(textMemoHistoryKey),
                        valueSerializer.serialize(key), valueSerializer.serialize(map.get(key)));
            }

            return null;
        });

        return textMemo;
    }

    /**
     * text memo latest get 메소드.
     */
    public Optional<TextMemoLatest> getLatest(String individualVideoId) {

        String textMemoLatestKey = getTextMemoLatestKey(getDefaultStateKey(individualVideoId));
        Map<Object, Object> map = redisTemplate.opsForHash().entries(textMemoLatestKey);

        if (map.isEmpty()) {
            return Optional.empty();
        }

        return Optional.ofNullable(objectMapper.convertValue(map, TextMemoLatest.class));
    }

    // key_history를 통해 레디스에서 find and 객체 list return
    public List<TextMemoHistory> getTextMemoHistories(String individualVideoId) {

        List<TextMemoHistory> list = new ArrayList<>();

        // redis의 set 형식에서 멤버들을 꺼내온다.
        Set<Object> stateHistoryMembers = redisTemplate.opsForSet().members(getDefaultStateKey(individualVideoId));

        redisTemplate.execute((RedisCallback<List<String>>) connection -> {

            List<TextMemoHistory> textMemoHistories = stateHistoryMembers.stream()
                    .map(key -> objectMapper.convertValue(redisTemplate.opsForHash().entries(key.toString()),
                            TextMemoHistory.class))
                    .collect(Collectors.toList());

            list.addAll(textMemoHistories);

            return null;
        });

        return list;
    }

    public void deleteHistoryFromRedis(String individualVideoId) {

        String key = getDefaultStateKey(individualVideoId);

        Set<Object> members = redisTemplate.opsForSet().members(key);

        redisTemplate.executePipelined((RedisCallback<Object>) connection -> {

            // history set delete
            connection.del(keySerializer.serialize(key));

            // 모든 history 삭제
            members.forEach(state -> {
                connection.del(keySerializer.serialize(state));
            });

            return null;
        });
    }

    public void deleteLatest(String individualVideoId) {
        redisTemplate.delete(getDefaultStateKey(individualVideoId) + "_latest");
    }

    public String getDefaultStateKey(String individualVideoId) {
        return TEXT_MEMO_STATE_KEY + "_" + individualVideoId;
    }

    private String getTextMemoHistoryKey(TextMemo textMemo, String individualVideoId) {
        return getDefaultStateKey(individualVideoId) + "_history:" + textMemo.getId();
    }

    private String getTextMemoLatestKey(String individualVideoId) {
        return getDefaultStateKey(individualVideoId) + "_latest";
    }

}
