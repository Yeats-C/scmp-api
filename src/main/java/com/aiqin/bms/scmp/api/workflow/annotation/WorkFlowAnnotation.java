package com.aiqin.bms.scmp.api.workflow.annotation;

import java.lang.annotation.*;

/**
 * Description:
 * 强制！！！！
 * 在需要审批的模块service类上加上该注解 并且实现WorkFlowHelper接口用于审批流的回调
 * @author: NullPointException
 * @date: 2019-06-12
 * @time: 16:48
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited//元注解，不加反射获取不到该注解
public @interface WorkFlowAnnotation {
    WorkFlow value();
}
