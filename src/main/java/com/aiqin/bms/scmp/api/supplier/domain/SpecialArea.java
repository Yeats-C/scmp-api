package com.aiqin.bms.scmp.api.supplier.domain;

import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-07-16
 * @time: 16:54
 */
@Getter
public enum SpecialArea {
    北京("北京",true,false),
    上海("上海",true,false),
    重庆("重庆",true,false),
    天津("天津",true,false),
    香港("香港",true,false),
    澳门("澳门",true,false),
    台湾("台湾",false,false);

    private String AreaName;
    private Boolean hasCity;
    private Boolean hasDistrict;

    SpecialArea(String areaName, Boolean hasCity, Boolean hasDistrict) {
        AreaName = areaName;
        this.hasCity = hasCity;
        this.hasDistrict = hasDistrict;
    }
    public static Map<String,SpecialArea> getAll(){
        return Arrays.stream(values()).collect(Collectors.toMap(SpecialArea::getAreaName, Function.identity()));
    }
}
