package com.example.algorithm.lock;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.context.annotation.Configuration;

import java.util.Iterator;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * ScheduledExecutorService描述
 *
 * @author Administrator
 * @date 2022-10-24
 **/
@Slf4j
@Configuration
public class DistributedRedisLockScheduledExecutor {
    /**
     * 扫描的任务队列
     */
    protected static final Queue<DistributedRedisHolder> HOLDER_LIST = new ConcurrentLinkedQueue<>();
    /**
     * 线程池，每个 JVM 使用一个线程去维护 keyAliveTime，定时执行 runnable
     */
    static final ScheduledExecutorService SCHEDULER =
            new ScheduledThreadPoolExecutor(1,
                    new BasicThreadFactory.Builder().namingPattern("redisLock-schedule-pool").daemon(true).build());

    static {
        // 两秒执行一次「续时」操作
        SCHEDULER.scheduleAtFixedRate(() -> {
            // 这里记得加 try-catch，否者报错后定时任务将不会再执行=-=
            Iterator<DistributedRedisHolder> iterator = HOLDER_LIST.iterator();
            while (iterator.hasNext()) {
                DistributedRedisHolder holder = iterator.next();
                // 判空
                if (holder == null) {
                    iterator.remove();
                    continue;
                }
                log.info("存储的key:{}", holder.getKey());
                String stringValue = (String) holder.getRedisTemplate().opsForValue().get(holder.getKey());
                log.info("存储的key:{}存储的值:{}", holder.getKey(), stringValue);
                // 判断 key 是否还有效，无效的话进行移除
                if (Objects.isNull(stringValue)) {
                    iterator.remove();
                    continue;
                }
                // 超时重试次数，超过时给线程设定中断
                if (holder.getCurrentCount() > holder.getRetryTimes()) {
                    holder.getLockKey().remove();
                    iterator.remove();
                    continue;
                }
                // 判断是否进入最后三分之一时间
                long curTime = System.currentTimeMillis();
                boolean shouldExtend = (holder.getLastModifyTime() + holder.getModifyPeriod()) <= curTime;
                if (shouldExtend) {
                    holder.setLastModifyTime(curTime);
                    holder.getRedisTemplate().expire(holder.getKey(), holder.getLastModifyTime(), TimeUnit.SECONDS);
                    log.info("businessKey : [" + holder.getKey() + "], try count : " + holder.getCurrentCount());
                    holder.setCurrentCount(holder.getCurrentCount() + 1);
                }
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    public DistributedRedisLockScheduledExecutor() {
        // TODO document why this constructor is empty
    }
}
