package com.aiqin.bms.scmp.api.base;

import io.swagger.annotations.ApiModel;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author knight.xie
 * @version 1.0
 * @className SkuStatusEnum
 * @date 2019/7/6 19:22
 * @description TODO
 */
@ApiModel("SKU状态(商品配置)枚举")
@Getter
public enum SkuStatusEnum {
    IN_USE(Byte.valueOf("0"),"在用","有一个仓在用，那么商品在用"),
    STOP_PURCHASE(Byte.valueOf("1"),"停止进货","所有仓没有在用，有一个仓停止进货，那么商品停止进货"),
    STOP_DISTRIBUTION(Byte.valueOf("2"),"停止配送","所有仓没有在用和停止进货，有一个仓停止配送，那么商品停止配送"),
    STOP_SALES(Byte.valueOf("3"),"停止销售","所有仓没有在用和停止进货和停止配送，那么商品停止销售"),
    ;
    private Byte status;
    private String name;
    private String desc;
    SkuStatusEnum(Byte status, String name, String desc){
        this.status = status;
        this.name = name;
        this.desc = desc;
    }
    public static SkuStatusEnum getByStatus(Byte status){
        for (SkuStatusEnum value : SkuStatusEnum.values()) {
            if (Objects.equals(value.getStatus(),status)) {
                return value;
            }
        }
        return null;
    }
    public static Map<String,SkuStatusEnum> getAllStatus(){
       return Arrays.stream(values()).collect(Collectors.toMap(SkuStatusEnum::getName, Function.identity()));
    }
}
