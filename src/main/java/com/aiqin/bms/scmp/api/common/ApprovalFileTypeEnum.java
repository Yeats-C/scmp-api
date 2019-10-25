package com.aiqin.bms.scmp.api.common;

import lombok.Getter;


@Getter
public enum ApprovalFileTypeEnum {
    SKU(Byte.valueOf("1"),"SKU"),
    SUPPLIER(Byte.valueOf("2"),"供应商"),
    GOODS_COMPANY(Byte.valueOf("3"),"SKU供应商"),
    GOODS_WARHOUSE(Byte.valueOf("4"),"仓库"),
    ;
    private Byte type;
    private String desc;
    ApprovalFileTypeEnum (Byte type,String desc) {
        this.type = type;
        this.desc = desc;
    }
}
