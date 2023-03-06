package com.example.algorithm.lock;

import lombok.Data;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * DistributedRedisHolder描述
 *
 * @author Administrator
 * @date 2022-10-24
 **/
@Data
public class DistributedRedisHolder {
    /**
     * 业务唯一 key
     */
    private String key;
    /**
     * 业务存储的值
     */
    private String value;
    /**
     * 重试次数
     */
    private int retryTimes;
    /**
     * 上次更新时间（ms）
     */
    private long lastModifyTime;
    /**
     * 当前尝试次数
     */
    private int currentCount;
    /**
     * 更新的时间周期（毫秒）,公式 = 加锁时间（转成毫秒） / 3
     */
    private Long modifyPeriod;
    /**
     * redis操作
     */
    private RedisTemplate<Object, Object> redisTemplate;
    /**
     * 执行线程
     */
    private ThreadLocal<DistributedRedisHolder> lockKey;
}
