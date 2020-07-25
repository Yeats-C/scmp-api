package com.aiqin.bms.scmp.api.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * @author: nh
 * @since: JDK 1.8
 * @Description:
 */
@Slf4j
@Component
public class RedisTool {
    private static final int RELEASE_SUCCESS = 1;
    private static final int EXPIRE_TIME = 10;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 尝试获取分布式锁
     *
     * @param lockKey   锁
     * @param requestId 请求标识
     * @return 是否获取成功
     */
    public boolean tryGetDistributedLock(String lockKey, String requestId) {
        return tryGetDistributedLock(lockKey, requestId, EXPIRE_TIME);
    }

    /**
     * 尝试获取分布式锁
     *
     * @param lockKey    锁
     * @param requestId  请求标识
     * @param expireTime 超期时间
     * @return 是否获取成功
     */
    public boolean tryGetDistributedLock(String lockKey, String requestId, int expireTime) {
        try {
            Boolean result = stringRedisTemplate.execute((RedisCallback<Boolean>) redisConnection ->
                    redisConnection.set(lockKey.getBytes(StandardCharsets.UTF_8), requestId.getBytes(StandardCharsets.UTF_8), Expiration.seconds(TimeUnit.SECONDS.toSeconds(expireTime)), RedisStringCommands.SetOption.SET_IF_ABSENT));
            return result == null ? false : result;
        } catch (Exception e) {
            log.error("redis lock error.", e);
        }
        return false;
    }

    /**
     * 释放分布式锁
     *
     * @param lockKey   锁
     * @param requestId 请求标识
     * @return 是否释放成功
     */
    public boolean releaseDistributedLock(String lockKey, String requestId) {
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Boolean result = (Boolean) redisTemplate.execute((RedisCallback<Boolean>) connection ->
                connection.eval(script.getBytes(StandardCharsets.UTF_8), ReturnType.BOOLEAN, RELEASE_SUCCESS, lockKey.getBytes(StandardCharsets.UTF_8), requestId.getBytes(StandardCharsets.UTF_8)));
        return result == null ? false : result;
    }
}
