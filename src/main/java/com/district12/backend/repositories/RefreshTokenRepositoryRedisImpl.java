package com.district12.backend.repositories;

import com.district12.backend.dtos.RefreshToken;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
@ConditionalOnProperty(value = "spring.cache.type", havingValue = "redis")
public class RefreshTokenRepositoryRedisImpl implements RefreshTokenBaseRepository{

    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    @Value("${auth.jwt.refresh-token.timeout}")
    private int refreshTokenTimeout;

    @Value("${auth.jwt.refresh-token.prefix}")
    private  String redisKeyPrefix;

    private static final String TOKEN_PREFIX = "token:";
    private static final String USER_ID_PREFIX = "userId:";

    @Override
    public void saveToken(RefreshToken refreshToken) {
        try {
            String serializeObj = objectMapper.writeValueAsString(refreshToken);
            deleteTokenForUserId(refreshToken.getUser().getId());

            redisTemplate.opsForValue().set(redisKeyPrefix + TOKEN_PREFIX + refreshToken.getToken(),
                    serializeObj, refreshTokenTimeout, TimeUnit.SECONDS);
            redisTemplate.opsForValue().set(redisKeyPrefix + USER_ID_PREFIX + refreshToken.getUser().getId(),
                    refreshToken.getToken(), refreshTokenTimeout, TimeUnit.SECONDS);
        } catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error saving refresh token");
        }
    }

    @Override
    public Optional<RefreshToken> findByToken(String hashedToken) {
        try {
            String data = redisTemplate.opsForValue().get(redisKeyPrefix + TOKEN_PREFIX + hashedToken);
            if (data == null) {
                return Optional.empty();
            }
            return Optional.of(objectMapper.readValue(data, RefreshToken.class));
        }
        catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving refresh token");
        }

    }

    @Override
    public void deleteTokenForUserId(Long userId) {
        String key = redisKeyPrefix + USER_ID_PREFIX + userId;
        if(Boolean.TRUE.equals(redisTemplate.hasKey(key))) {
            String token = redisTemplate.opsForValue().get(key);
            redisTemplate.delete(redisKeyPrefix + TOKEN_PREFIX + token);
            redisTemplate.delete(key);
        }
    }
}
