package com.aiqin.bms.scmp.api.product.domain.request.newproduct;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@ApiModel("新增商品信息")
@Data
public class NewProductSaveReqVO {
    @ApiModelProperty("条形码")
    private String barCode;

    @ApiModelProperty("商品名称")
    @NotEmpty(message = "商品名称不能为空")
    private String productName;

    @ApiModelProperty("采购组编号")
    private String purchasingGroupCode;

    @ApiModelProperty("采购组名称")
    private String purchasingGroupName;



}
