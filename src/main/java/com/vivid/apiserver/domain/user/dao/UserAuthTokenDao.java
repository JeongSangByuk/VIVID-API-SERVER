package com.vivid.apiserver.domain.user.dao;

import com.vivid.apiserver.domain.user.exception.RefreshTokenExpiredException;
import com.vivid.apiserver.domain.user.exception.RefreshTokenNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;


@Service
@Slf4j
@Transactional
public class UserAuthTokenDao {

    private final RedisTemplate<String, Object> redisTemplate;

    private final String REFRESH_TOKEN_HASH_KEY = "refresh-token";

    private final String USER_IP_HASH_KEY = "user-ip";

    public UserAuthTokenDao(@Qualifier("userRedisTemplate") RedisTemplate<?, ?> redisTemplate) {
        this.redisTemplate = (RedisTemplate<String, Object>) redisTemplate;
    }

    // save refresh token redis
    public void saveRefreshToken(String email, String userIp, String refreshToken) {

        HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();

        // hash data 생성
        Map<String, Object> userMap = new HashMap<>();
        userMap.put(REFRESH_TOKEN_HASH_KEY, refreshToken);
        userMap.put(USER_IP_HASH_KEY, userIp);

        // save
        hashOperations.putAll(email, userMap);

        // 만료시간 설정
        redisTemplate.expire(email, 14, TimeUnit.DAYS);

//        final ValueOperations<String, Object> stringStringValueOperations = redisTemplate.opsForValue();
//        stringStringValueOperations.set(userIp, refreshToken, 14, TimeUnit.DAYS);

    }

    // get refresh token redis
    public String getRefreshToken(String email, String userIp) {

        HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();

        Optional<Object> refreshToken = Optional.ofNullable(hashOperations.get(email, REFRESH_TOKEN_HASH_KEY));

        // handle not found exception
        refreshToken.orElseThrow(() -> new RefreshTokenNotFoundException());

        String savedUserIp = (String) hashOperations.get(email, USER_IP_HASH_KEY);

        // user ip check, refresh token remove
        if (!userIp.equals(savedUserIp))
            throw new RefreshTokenExpiredException();


        return (String) refreshToken.get();

    }

    // remove refresh token
    public void removeRefreshToken(String email) {

        HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();

        hashOperations.delete(email, REFRESH_TOKEN_HASH_KEY);
        hashOperations.delete(email, USER_IP_HASH_KEY);
    }


}
