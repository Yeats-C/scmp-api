package com.aiqin.bms.scmp.api.common;

import com.google.common.base.Objects;
import io.swagger.annotations.ApiModel;
import lombok.Getter;

/**
 * @author knight.xie
 * @version 1.0
 * @date 2019/5/9 18:50
 */
@ApiModel("审批类型枚举")
@Getter
public enum ApprovalTypeEnum {
    PRODUCT_SKU(1,"商品"),
    PRODUCT_CONFIG(2,"商品配置"),
    SALES_AREA(3,"销售区域");
    private Integer type;
    private String name;
    ApprovalTypeEnum(Integer type,String name) {
        this.type = type;
        this.name = name;
    }

    /**
     * @param type
     * @return
     */
    public static ApprovalTypeEnum getByType(Integer type)
    {
        for (ApprovalTypeEnum typeEnum : ApprovalTypeEnum.values())
        {
            if (Objects.equal(typeEnum.getType(),type))
            {
                return typeEnum;
            }
        }
        return null;
    }
}
