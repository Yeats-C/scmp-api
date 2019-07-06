package com.aiqin.bms.scmp.api.product.service.changeprice.enumerate;

import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-07-04
 * @time: 20:46
 */
@Getter
public enum ChangePriceType {
    purchase(1,"采购变价"),
    sale(2,"销售变价"),
    temp(3,"临时变价");
    private Integer type;
    private String desc;

    ChangePriceType(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }
    public  static Map<Integer,ChangePriceType> getAllType(){
        return Arrays.stream(values()).collect(Collectors.toMap(ChangePriceType::getType, Function.identity(), (k1, k2) -> k2));
    }
}
