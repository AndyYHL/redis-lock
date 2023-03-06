package com.example.algorithm.lock;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * ReidsLockAutoConfiguration描述
 *
 * @author Administrator
 * @date 2022-10-21
 **/
@Slf4j
@Configuration
public class ReidsLockAutoConfiguration {
    @Bean
    public DistributedRedisLock distributedRedisLock(RedisTemplate<Object, Object> redisTemplate){
        log.info("init Distributed Redis Lock");
        return new DistributedRedisLock(redisTemplate);
    }

    @Bean
    public DistributedRedisLockAspect distributedRedisLockAspect(DistributedRedisLock distributedRedisLock){
        log.info("init Distributed Redis Lock Aspect");
        return new DistributedRedisLockAspect(distributedRedisLock);
    }
}
