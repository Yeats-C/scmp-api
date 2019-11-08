package com.aiqin.bms.scmp.api.base;

import java.lang.annotation.*;

/**
 * @author wanglei
 * @date 2019/11/7
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface PropertyMsg {
    String value();
    String replace() default "";

}
