package com.aiqin.bms.scmp.api.base;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Description:
 *
 * @author: zth
 * @date: 2019-03-16
 * @time: 11:03
 */
@Getter
@AllArgsConstructor
public enum WarehouseTypeCode {
    //仓库类型编码 0 大效期库 1 销售库 2 退货库 3 销售库 大效期库 null 查询全部
    LARGEPERIOD(0,"大效期库"),
    SALES(1,"销售库"),
    RETURNOFGOODS(2,"退货库"),
    LARGEPERIODANDSALES(3,"销售库 大效期库"),
    ALL(null,"所有库");

    private Integer typeCode;
    private String typeDesc;

}
