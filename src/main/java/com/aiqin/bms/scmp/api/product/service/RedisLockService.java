package com.aiqin.bms.scmp.api.product.service;

public interface RedisLockService {

    public boolean lock(String key, String value);

    public void unlock(String key, String value);
}
