package com.aiqin.bms.scmp.api.base;

import java.lang.annotation.*;

/**
 * @date 2019/11/7
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface PropertyMsg {
    /**
     * 需要修改的字段名字
     *
     * @return
     */
    String value();

    /**
     * 字符串替换 格式"1_是,0_否"
     *
     * @return
     */
    String replace() default "";

    /**
     * 是否是在删除时需要要展示的字段
     *
     * @return
     */
    boolean delete() default false;

}
