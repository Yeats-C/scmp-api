package com.aiqin.bms.scmp.api.product.domain.pojo;

import com.aiqin.bms.scmp.api.common.CommonBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("申请SKU库存信息")
@Data
public class ApplyProductSkuStockInfo extends CommonBean {
    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("商品编码")
    private String productCode;

    @ApiModelProperty("规格")
    private String spec;

    @ApiModelProperty("商品名称")
    private String productName;

    @ApiModelProperty("单位编码")
    private String unitCode;

    @ApiModelProperty("单位名称")
    private String unitName;

    @ApiModelProperty("条形码")
    private String barCode;

    @ApiModelProperty("基商品含量")
    private Integer baseProductContent;

    @ApiModelProperty("SKU编码")
    private String productSkuCode;

    @ApiModelProperty("SKU名称")
    private String productSkuName;

    @ApiModelProperty("申请编码")
    private String applyCode;

    @ApiModelProperty("拆零系数")
    private Long zeroRemovalCoefficient;
}