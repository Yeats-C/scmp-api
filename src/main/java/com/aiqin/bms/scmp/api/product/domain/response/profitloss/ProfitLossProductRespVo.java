package com.aiqin.bms.scmp.api.product.domain.response.profitloss;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@ApiModel("损益商品")
@Data
public class ProfitLossProductRespVo {

    @ApiModelProperty("订单编号")
    private String orderCode;

    @ApiModelProperty("sku编号")
    private String skuCode;

    @ApiModelProperty("sku名称")
    private String skuName;

    @ApiModelProperty("品类")
    private String category;

    @ApiModelProperty("品牌")
    private String brand;

    @ApiModelProperty("颜色")
    private String color;

    @ApiModelProperty("规格")
    private String specification;

    @ApiModelProperty("型号")
    private String model;

    @ApiModelProperty("单位(销售单位)")
    private String unit;

    @ApiModelProperty("类别")
    private String classes;

    @ApiModelProperty("类型")
    private String type;

    @ApiModelProperty("税率")
    private BigDecimal tax;

    @ApiModelProperty("含税成本")
    private BigDecimal taxPrice;

    @ApiModelProperty("数量")
    private Long quantity;

    @ApiModelProperty("含税总成本")
    private Long taxAmount;

    @ApiModelProperty("原因")
    private String reason;

}