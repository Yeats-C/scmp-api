package com.aiqin.bms.scmp.api.product.domain.request.newproduct;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@ApiModel("商品草稿提交")
@Data
public class DraftSaveListReqVO {
    @NotEmpty(message = "条形码不能为空")
    @ApiModelProperty("条形码")
    private String barCode;
    @NotEmpty(message = "商品编码不能为空")
    @ApiModelProperty("商品编码")
    private String productCode;
     @NotEmpty(message = "商品名称不能为空")
    @ApiModelProperty("商品名称")
    private String productName;

    @ApiModelProperty("公司编码")
    private String companyCode;

    @ApiModelProperty("公司名称")
    private String companyName;

}
