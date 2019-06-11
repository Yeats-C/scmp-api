package com.aiqin.bms.scmp.api.product.domain.pojo;

import com.aiqin.bms.scmp.api.common.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("供应商产能信息")
@Data
public class ProductSkuSupplyUnitCapacity extends CommonBean {
    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("供应商编码")
    private String supplyUnitCode;

    @ApiModelProperty("供应商名称")
    private String supplyUnitName;

    @ApiModelProperty("sku编码")
    private String productSkuCode;

    @ApiModelProperty("sku名称")
    private String productSkuName;

    @ApiModelProperty("生产量")
    private Long outPut;

    @ApiModelProperty("需要天数")
    private Long needDays;

    @ApiModelProperty("申请编码")
    private String applyCode;

}