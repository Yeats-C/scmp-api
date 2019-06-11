package com.aiqin.bms.scmp.api.supplier.domain.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("关联商品")
@Data
public class ProductSkuAssociatedGoodsDraft {

    @ApiModelProperty("子商品编码")
    private String subSkuCode;

    @ApiModelProperty("子商品名称")
    private String subSkuName;

}