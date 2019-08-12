package com.aiqin.bms.scmp.api.supplier.domain;

import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 描述:
 *
 * @Author: Kt.w
 * @Date: 2019/1/2
 * @Version 1.0
 * @since 1.0
 */
@Getter
public enum LogisticsCenterEnum {

    CHINA("100","全国",null,null),
    PROVINCE("99","全省",null,null),
    BEI_JING("110000", "北京","99","北京市"),
    BEI_JING_TOTAL("99", "北京市",null,null),
    TIAN_JIN("120000", "天津","99","天津市"),
    TIAN_JIN_TOTAL("99", "天津市",null,null),
    SHANG_HAI("310000", "上海","99","上海市"),
    SHANG_HAI_TOTAL("99", "上海市",null,null),
    CHONG_QING("500000", "重庆","99","重庆市"),
    CHONG_QING_TOTAL("99", "重庆市",null,null);

    private String status;
    private String name;
    private String nextStatus;
    private String nextName;
    LogisticsCenterEnum(String status, String name,String nextStatus,String nextName) {
        this.status = status;
        this.name = name;
        this.nextStatus = nextStatus;
        this.nextName = nextName;
    }
    public static Map<String,LogisticsCenterEnum> getAllStatus(){
       return Arrays.stream(values()).collect(Collectors.toMap(LogisticsCenterEnum::getStatus, Function.identity(),(k1,k2)->k2));
    }
}
