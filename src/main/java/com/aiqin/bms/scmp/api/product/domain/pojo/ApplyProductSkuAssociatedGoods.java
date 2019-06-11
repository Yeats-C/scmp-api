package com.aiqin.bms.scmp.api.product.domain.pojo;

import com.aiqin.bms.scmp.api.common.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("申请关联商品信息")
@Data
public class ApplyProductSkuAssociatedGoods extends CommonBean {
    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("主商品编码")
    private String mainSkuCode;

    @ApiModelProperty("主商品名称")
    private String mainSkuName;

    @ApiModelProperty("子商品编码")
    private String subSkuCode;

    @ApiModelProperty("子商品名称")
    private String subSkuName;

    @ApiModelProperty("申请编号")
    private String applyCode;

}