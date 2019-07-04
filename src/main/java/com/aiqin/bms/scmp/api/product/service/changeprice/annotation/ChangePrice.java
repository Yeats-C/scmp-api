package com.aiqin.bms.scmp.api.product.service.changeprice.annotation;

import com.aiqin.bms.scmp.api.product.service.changeprice.enumerate.ChangePriceType;

import java.lang.annotation.*;

/**
 * Description:
 * @author: NullPointException
 * @date: 2019-06-12
 * @time: 16:48
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited//元注解，不加反射获取不到该注解
public @interface ChangePrice {
    ChangePriceType value();
}
