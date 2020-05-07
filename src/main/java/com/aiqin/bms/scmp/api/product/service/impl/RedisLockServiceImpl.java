package com.aiqin.bms.scmp.api.product.service.impl;

import com.aiqin.bms.scmp.api.product.service.RedisLockService;
import com.alibaba.druid.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisLockServiceImpl implements RedisLockService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisLockServiceImpl.class);

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 加锁
     * @param key 商品id
     * @param value 当前时间 + 超时时间
     * @return
     */
    public boolean lock(String key, String value) {
        // setIfAbsent()也就是redis的setnx,当key不存在时设置value
        if(redisTemplate.opsForValue().setIfAbsent(key, value)) {
            //加锁成功
            LOGGER.info("redis 加锁成功：" + key);
            return true;
        }
        //当锁已存在，可以获取该锁的value，来判断是否过期
        LOGGER.info("redis 锁已存在，来判断是否过期：" + key);
        String currentValue = redisTemplate.opsForValue().get(key);
        //如果锁过期
        if (!StringUtils.isEmpty(currentValue)
                && Long.parseLong(currentValue) < System.currentTimeMillis()) {
            //获取上一个锁的时间
            String oldValue = redisTemplate.opsForValue().getAndSet(key, value);
            //如果多个线程同时进入这里，则可以通过判断oldValue与currentValue是否相等来限制多个线程加锁
            if (!StringUtils.isEmpty(oldValue) && oldValue.equals(currentValue)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 解锁
     * @param key
     * @param value
     */
    public void unlock(String key, String value) {
        try {
            String currentValue = redisTemplate.opsForValue().get(key);
            if (!StringUtils.isEmpty(currentValue) && currentValue.equals(value)) {
                redisTemplate.opsForValue().getOperations().delete(key);
            }
        }catch (Exception e) {
            LOGGER.error("redis分布式锁：解锁异常, {}", e);
        }
    }
}
