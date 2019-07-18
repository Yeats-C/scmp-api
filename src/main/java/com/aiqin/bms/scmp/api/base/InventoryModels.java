package com.aiqin.bms.scmp.api.base;

import lombok.Getter;

import java.util.Objects;

/**
 * @author knight.xie
 * @version 1.0
 * @className QualityAssuranceManagements
 * @date 2019/7/18 17:27
 * @description TODO
 */
@Getter
public enum InventoryModels {
    YES(Byte.valueOf("0"),"有库存销售"),
    NO(Byte.valueOf("1"),"无库存销售"),
    ;
    private Byte type;
    private String name;

    InventoryModels(Byte type, String name) {
        this.type = type;
        this.name = name;
    }

    public static InventoryModels getByType(Byte type) {
        for (InventoryModels value : InventoryModels.values()) {
            if(Objects.equals(value.getType(), type)) {
                return value;
            }
        }
        return null;
    }
}
