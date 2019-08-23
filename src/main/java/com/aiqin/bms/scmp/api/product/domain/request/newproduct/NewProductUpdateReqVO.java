package com.aiqin.bms.scmp.api.product.domain.request.newproduct;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@ApiModel("更新商品信息")
public class NewProductUpdateReqVO {
    @ApiModelProperty("条形码")
    @NotEmpty(message = "助记码不能为空")
    private String barCode;

    @ApiModelProperty("商品编码")
    @NotEmpty(message = "商品编码不能为空")
    private String productCode;

    @ApiModelProperty("商品名称")
    @NotEmpty(message = "商品名称不能为空")
    private String productName;

    @ApiModelProperty("采购组编号")
    private String purchasingGroupCode;

    @ApiModelProperty("采购组名称")
    private String purchasingGroupName;

}
