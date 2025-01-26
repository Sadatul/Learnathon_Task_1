package com.district12.backend.configs;

import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("prod")
@Import({RedisAutoConfiguration.class})
public class RedisConfiguration {
}
