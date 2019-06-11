package com.aiqin.bms.scmp.api.product.domain.request.returngoods;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Description:
 * 退货订单的商品信息
 * @author: zth
 * @date: 2018-12-30
 * @time: 17:12
 */
@ApiModel("退货订单的商品信息")
@Data
public class SupplyReturnOrderProductItemReqVO {
    @ApiModelProperty("退货订单主表编码")
    private String returnOrderCode;

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
    private Byte bePromotion;

    @ApiModelProperty("数量")
    private Long num;

    @ApiModelProperty("总价")
    private Long amount;

    @ApiModelProperty("单价")
    private Long price;

    @ApiModelProperty("优惠分摊")
    private Long preferentialAllocation;

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
    private Long activityApportionment;

    @ApiModelProperty("活动编码(多个，隔开）")
    private String activityCode;

    @ApiModelProperty("图片地址")
    private String pictureUrl;
}
