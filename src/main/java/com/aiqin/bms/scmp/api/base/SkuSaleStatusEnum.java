package com.aiqin.bms.scmp.api.base;

import io.swagger.annotations.ApiModel;
import lombok.Getter;

import java.util.Objects;

/**
 * @author knight.xie
 * @version 1.0
 * @className SkuSaleStatusEnum
 * @date 2019/7/6 19:29
 * @description TODO
 */
@ApiModel("SKU销售状态枚举")
@Getter
public enum SkuSaleStatusEnum {

    NOT_IN_STOCK(Byte.valueOf("0"),"未进货","商品创建后，没有一个仓进过货"),
    NEW_SKU(Byte.valueOf("0"),"新品","首次进货的90天内"),
    NORMAL(Byte.valueOf("0"),"正常","首次进货的90天后"),
    DIE_OUT(Byte.valueOf("0"),"淘汰品","所有仓都不“在用”（状态）了"),
    ;
    private Byte status;
    private String name;
    private String desc;
    SkuSaleStatusEnum(Byte status, String name, String desc){
        this.status = status;
        this.name = name;
        this.desc = desc;
    }
    public static SkuSaleStatusEnum getByStatus(Byte status){
        for (SkuSaleStatusEnum value : SkuSaleStatusEnum.values()) {
            if (Objects.equals(value.getStatus(),status)) {
                return value;
            }
        }
        return null;
    }
}
