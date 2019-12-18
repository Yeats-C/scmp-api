package com.aiqin.bms.scmp.api.product.domain.pojo;

import com.aiqin.bms.scmp.api.common.CommonBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@ApiModel("包装信息申请表")
@Data
public class ApplyProductSkuBoxPacking extends CommonBean {
    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("整箱商品包装 单位")
    private String largeUnit;

    @ApiModelProperty("整箱商品包装 单位编码")
    private String unitCode;

    @ApiModelProperty("包装箱子长度")
    private BigDecimal boxLength;

    @ApiModelProperty("宽度（mm）")
    private BigDecimal boxWidth;

    @ApiModelProperty("箱子高度")
    private BigDecimal boxHeight;

    @ApiModelProperty("箱子体积")
    private BigDecimal boxVolume;

    @ApiModelProperty("毛重")
    private BigDecimal boxGrossWeight;

    @ApiModelProperty("净重")
    private BigDecimal netWeight;

    @ApiModelProperty("商品sku code")
    private String productSkuCode;

    @ApiModelProperty("商品sku 名称")
    private String productSkuName;

    @ApiModelProperty("申请编码")
    private String applyCode;


}