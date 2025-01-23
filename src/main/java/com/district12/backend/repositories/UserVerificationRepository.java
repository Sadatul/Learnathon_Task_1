package com.district12.backend.repositories;

import com.district12.backend.dtos.UnverifiedUser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Repository
public class UserVerificationRepository {
    private final RedisTemplate<String, String> userRedisTemplate;
    private final ObjectMapper objectMapper;

    @Value("${verification.email.redis.prefix}")
    private  String redisKeyPrefix;

    @Value("${verification.email.timeout}")
    private  Long optExpiration;

    public UserVerificationRepository(RedisTemplate<String, String> userRedisTemplate,
                                      ObjectMapper objectMapper) {
        this.userRedisTemplate = userRedisTemplate;
        this.objectMapper = objectMapper;
    }

    public void putUserVerificationInfo(String email, UnverifiedUser unverifiedUser) throws JsonProcessingException {
        userRedisTemplate.opsForValue().set(redisKeyPrefix + email,
                objectMapper.writeValueAsString(unverifiedUser),
                optExpiration, TimeUnit.SECONDS);
    }

    public Optional<UnverifiedUser> getUserVerificationInfoByEmail(String email) throws JsonProcessingException {
        String data = userRedisTemplate.opsForValue().get(redisKeyPrefix + email);
        if (data == null) {
            return Optional.empty();
        }
        return Optional.of(objectMapper.readValue(data, UnverifiedUser.class));
    }

    public void deleteUserVerInfoByEmail(String email) {
        userRedisTemplate.delete(redisKeyPrefix + email);
    }
}
