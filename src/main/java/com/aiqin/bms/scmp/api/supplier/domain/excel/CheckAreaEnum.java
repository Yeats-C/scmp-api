package com.aiqin.bms.scmp.api.supplier.domain.excel;

import lombok.Getter;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-07-16
 * @time: 21:03
 */
@Getter
public enum CheckAreaEnum {
    普通省市县(1,"未找到对应省信息","未找到对应市信息","未找到对应县信息"),
    发货省市县(1,"未找到对应发货省信息","未找到对应发货市信息","未找到对应发货县信息"),
    退货省市县(1,"未找到对应退货省信息","未找到对应退货市信息","未找到对应退货县信息");
    private Integer type;
    private String province;
    private String city;
    private String dis;

    CheckAreaEnum(Integer type, String province, String city, String dis) {
        this.type = type;
        this.province = province;
        this.city = city;
        this.dis = dis;
    }
}
