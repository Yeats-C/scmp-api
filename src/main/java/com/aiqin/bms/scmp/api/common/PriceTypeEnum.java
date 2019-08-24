package com.aiqin.bms.scmp.api.common;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import org.apache.commons.lang.StringUtils;

import java.util.Objects;

/**
 * @author knight.xie
 * @version 1.0
 * @className PriceTypeEnum
 * @date 2019/5/20 14:54
 */
@ApiModel("价格项目类型")
@Getter
public enum PriceTypeEnum {
    PURCHASE("1","采购","1","采购类"),
    CHANNEL("2","渠道","2,3","销售类,临时类"),
    DISTRIBUTION ("3","分销","2,3","销售类,临时类"),
    SALE("4","零售","2,3","销售类,临时类");

    private String typeCode;
    private String typeName;
    private String attrCodes;
    private String attrNames;

    PriceTypeEnum(String typeCode,String typeName,String attrCodes,String attrNames){
        this.typeCode = typeCode;
        this.typeName = typeName;
        this.attrCodes = attrCodes;
        this.attrNames = attrNames;
    }

    public static PriceTypeEnum getPriceTypeEnumByTypeCode(String typeCode,String attrCode) {
        if (StringUtils.isBlank(typeCode)) {
            return null;
        }
        PriceTypeEnum[] typeEnums = PriceTypeEnum.values();
        for (PriceTypeEnum typeEnum : typeEnums) {
            if (Objects.equals(typeCode,typeEnum.getTypeCode())) {
                if(typeEnum.getAttrCodes().indexOf(attrCode) != -1){
                    return typeEnum;
                }else{
                    return null;
                }
            }
        }
        return null;
    }


}
