package com.aiqin.bms.scmp.api.product.domain.request.order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@ApiModel("订单商品信息")
@Data
public class SupplyOrderProductItem {
    @ApiModelProperty("商品主键")
    private Long id;

    @ApiModelProperty("订单主表编码")
    private String orderCode;

    @ApiModelProperty("sku编号")
    private String skuCode;

    @ApiModelProperty("sku名称")
    private String skuName;

    @ApiModelProperty("商品名称")
    private String productName;

    @ApiModelProperty("商品编码")
    private String productCode;

    @ApiModelProperty("商品行号")
    private Long productLineNum;

    @ApiModelProperty("颜色名称")
    private String colorName;

    @ApiModelProperty("颜色编码")
    private String colorCode;

    @ApiModelProperty("商品单位code")
    private String unitCode;

    @ApiModelProperty("商品单位")
    private String unitName;

    @ApiModelProperty("是否是赠品(0否1是)")
    private Byte givePromotion;

    @ApiModelProperty("数量")
    private Long num;

    @ApiModelProperty("总价")
    private BigDecimal amount;

    @ApiModelProperty("单价")
    private BigDecimal price;

    @ApiModelProperty("优惠分摊")
    private BigDecimal preferentialAllocation;

    @ApiModelProperty("实发数量")
    private Long actualDeliverNum;

    @ApiModelProperty("规格")
    private String spec;

    @ApiModelProperty("规格编码")
    private String specCode;

    @ApiModelProperty("赠品行号")
    private Long promotionLineNum;

    @ApiModelProperty("退货数量")
    private Long returnNum;

    @ApiModelProperty("仓库编码")
    private String warehouseCode;

    @ApiModelProperty("仓库名称")
    private String warehouseName;

    @ApiModelProperty("供应单位名称")
    private String supplyCompanyName;

    @ApiModelProperty("供应单位编码")
    private String supplyCompanyCode;

    @ApiModelProperty("活动分摊")
    private BigDecimal activityApportionment;

    @ApiModelProperty("活动编码(多个，隔开）")
    private String activityCode;

    @ApiModelProperty("图片地址")
    private String pictureUrl;

}