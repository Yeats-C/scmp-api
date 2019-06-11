package com.aiqin.bms.scmp.api.config;

import com.aiqin.ground.util.exception.GroundRuntimeException;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

import java.lang.reflect.Method;

@Slf4j
public class AsyncExceptionHandler implements AsyncUncaughtExceptionHandler {
    @Override
    public void handleUncaughtException(Throwable throwable, Method method, Object... objects) {
        log.info("Async method: {} has uncaught exception,params:{}", method.getName(), JSON.toJSONString(objects));
        if (throwable instanceof GroundRuntimeException) {
            GroundRuntimeException asyncException = (GroundRuntimeException) throwable;
            log.info("asyncException:{}", asyncException.getMessage());
        }
    }
}