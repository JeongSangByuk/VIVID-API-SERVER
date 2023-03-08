package com.vivid.apiserver.global.config;

import com.vivid.apiserver.domain.individual_video.domain.TextMemo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableRedisRepositories
public class RedisNoteConfig {

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis-note.port}")
    private int noteRedisPort;

    @Value("${spring.redis.password}")
    private String redisPassword;

    @Primary
    @Bean(name = "noteRedisConnectionFactory")
    public RedisConnectionFactory noteRedisConnectionFactory() {

        final RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName(redisHost);
        configuration.setPort(noteRedisPort);
        configuration.setPassword(redisPassword);

        return new LettuceConnectionFactory(configuration);
    }

    @Primary
    @Bean(name = "noteRedisTemplate")
    public RedisTemplate<String, TextMemo> noteRedisTemplate(
            @Qualifier(value = "noteRedisConnectionFactory") RedisConnectionFactory redisConnectionFactory) {

        RedisTemplate<String, TextMemo> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(TextMemo.class));
        return redisTemplate;
    }

//    @Primary
//    @Bean(name = "noteRedisCacheManager")
//    public RedisCacheManager noteRedisCacheManager() {
//
//        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration
//                .defaultCacheConfig()
//                .serializeKeysWith(
//                        RedisSerializationContext.SerializationPair
//                                .fromSerializer(new StringRedisSerializer()))
//                .serializeValuesWith(
//                        RedisSerializationContext.SerializationPair
//                                .fromSerializer(new StringRedisSerializer())
//                )
//                .entryTtl(Duration.ofMillis(10L));
//
//        return RedisCacheManager.RedisCacheManagerBuilder
//                .fromConnectionFactory(noteRedisConnectionFactory())
//                .cacheDefaults(redisCacheConfiguration)
//                .build();
//    }

//    @Bean
//    public ObjectMapper objectMapper() {
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
//        mapper.registerModules(new JavaTimeModule(), new Jdk8Module());
//        return mapper;
//    }


}
