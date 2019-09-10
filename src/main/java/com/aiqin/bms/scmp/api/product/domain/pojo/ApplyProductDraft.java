package com.aiqin.bms.scmp.api.product.domain.pojo;

import com.aiqin.bms.scmp.api.common.CommonBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("申请商品草稿")
@Data
public class ApplyProductDraft extends CommonBean {
    @ApiModelProperty("")
    private Long id;

    @ApiModelProperty("条形码")
    private String barCode;

    @ApiModelProperty("商品编码")
    private String productCode;

    @ApiModelProperty("商品名称")
    private String productName;

    @ApiModelProperty("公司编码")
    private String companyCode;

    @ApiModelProperty("公司名称")
    private String companyName;

    @ApiModelProperty(value = "申请类型")
    private Byte applyType;

    @ApiModelProperty(value = "申请类型名称")
    private String applyTypeName;

}