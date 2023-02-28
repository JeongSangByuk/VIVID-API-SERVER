package com.vivid.apiserver.domain.auth.dao;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@Transactional
public class RefreshTokenDao {

    private final RedisTemplate<String, Object> redisTemplate;

    private final String REFRESH_TOKEN_HASH_KEY = "refresh-token";

    public RefreshTokenDao(@Qualifier("userRedisTemplate") RedisTemplate<?, ?> redisTemplate) {
        this.redisTemplate = (RedisTemplate<String, Object>) redisTemplate;
    }

    // save refresh token redis
    public void saveRefreshToken(String email, String refreshToken) {

        HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();

        Map<String, Object> userMap = new HashMap<>();
        userMap.put(REFRESH_TOKEN_HASH_KEY, refreshToken);

        hashOperations.putAll(email, userMap);
        redisTemplate.expire(email, 14, TimeUnit.DAYS);

    }

    // get refresh token redis
    public Optional<Object> getRefreshToken(String email) {

        HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();

        return Optional.ofNullable(hashOperations.get(email, REFRESH_TOKEN_HASH_KEY));
    }

    // remove refresh token
    public void removeRefreshToken(String email) {

        HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();

        hashOperations.delete(email, REFRESH_TOKEN_HASH_KEY);
    }


}
