package com.district12.backend.configs;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ConditionalOnProperty(value = "spring.cache.type", havingValue = "redis")
@Import({RedisAutoConfiguration.class})
public class RedisConfiguration {
}
