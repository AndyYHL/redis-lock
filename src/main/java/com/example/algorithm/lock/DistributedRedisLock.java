package com.example.algorithm.lock;

import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.types.Expiration;

import java.util.Objects;

/**
 * DistributedRedisLock描述
 *
 * @author Administrator
 * @date 2022-10-21
 **/
@Slf4j
public class DistributedRedisLock {
    final RedisTemplate<Object, Object> redisTemplate;

    final ThreadLocal<DistributedRedisHolder> lockKey = new ThreadLocal<>();

    public static final String UNLOCK_LUA;

    static {
        StringBuilder sb = new StringBuilder();
        sb.append("if redis.call(\"get\",KEYS[1]) == ARGV[1] ");
        sb.append("then ");
        sb.append("    return redis.call(\"del\",KEYS[1]) ");
        sb.append("else ");
        sb.append("    return 0 ");
        sb.append("end ");
        UNLOCK_LUA = sb.toString();
    }

    public DistributedRedisLock(RedisTemplate<Object, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 加锁
     *
     * @param key           锁key
     * @param value         存储的值
     * @param expire        过期时间
     * @param retryTimes    重试次数
     * @param retryInterval 重试间隔
     * @return true 加锁成功， false 加锁失败
     */
    public boolean lock(String key, String value, long expire, int retryTimes, long retryInterval) {
        boolean result = setRedisLock(key, value, retryTimes, expire);
        /**
         * 如果获取锁失败，进行重试
         */
        while ((!result) && retryTimes-- > 0) {
            try {
                log.info("lock failed, retrying..." + retryTimes);
                Thread.sleep(retryInterval);
            } catch (InterruptedException e) {
                return false;
            }
            result = setRedisLock(key, value, retryTimes, expire);
        }
        return result;
    }

    /**
     * 释放锁
     *
     * @param key 锁key
     * @return true 释放成功， false 释放失败
     */
    public boolean unLock(String key) {
        /**
         * 释放锁的时候，有可能因为持锁之后方法执行时间大于锁的有效期，此时有可能已经被另外一个线程持有锁，所以不能直接删除
         * 使用lua脚本删除redis中匹配value的key，可以避免由于方法执行时间过长而redis锁自动过期失效的时候误删其他线程的锁
         */
        try {
            RedisCallback<Boolean> callback = (connection) -> {
                String value = lockKey.get().getValue();
                return connection.eval(UNLOCK_LUA.getBytes(), ReturnType.BOOLEAN, 1, key.getBytes(), value.getBytes());
            };
            return Boolean.TRUE.equals(redisTemplate.execute(callback));
        } catch (Exception e) {
            log.error("release lock occured an exception", e);
        } finally {
            lockKey.remove();
        }
        return false;
    }

    /**
     * 设置redis锁
     *
     * @param key        锁key
     * @param value      存储的值
     * @param retryTimes 重试次数
     * @param expire     过期时间
     * @return true 设置成功，false 设置失败
     */
    private boolean setRedisLock(String key, String value, int retryTimes, long expire) {
        try {
            RedisCallback<Boolean> callback = (connection) -> {
                String saveValue = Objects.isNull(value) ? IdUtil.simpleUUID() : value;
                DistributedRedisHolder holder = new DistributedRedisHolder();
                holder.setRetryTimes(retryTimes);
                holder.setKey(key);
                holder.setValue(saveValue);
                //更新的时间周期（毫秒）,公式 = 加锁时间（转成毫秒） / 3
                holder.setModifyPeriod(expire * 1000 / 3);
                // 执行维护线程 必须参数
                holder.setRedisTemplate(redisTemplate);
                holder.setLockKey(lockKey);
                lockKey.set(holder);
                // 将本次 Task 信息加入「延时」队列中
                DistributedRedisLockScheduledExecutor.HOLDER_LIST.add(holder);
                return connection.set(key.getBytes(), saveValue.getBytes(), Expiration.milliseconds(expire), RedisStringCommands.SetOption.SET_IF_ABSENT);
            };
            return Boolean.TRUE.equals(redisTemplate.execute(callback));
        } catch (Exception e) {
            log.error("set redis error", e);
        }
        return false;
    }
}
