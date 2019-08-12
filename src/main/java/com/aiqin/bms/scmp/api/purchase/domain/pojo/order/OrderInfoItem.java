package com.aiqin.bms.scmp.api.purchase.domain.pojo.order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("订单商品信息")
@Data
public class OrderInfoItem {
    @ApiModelProperty("商品主键")
    private Long id;

    @ApiModelProperty("订单主表编码")
    private String orderCode;

    @ApiModelProperty("sku编号")
    private String skuCode;

    @ApiModelProperty("sku名称")
    private String skuName;

    @ApiModelProperty("图片地址")
    private String pictureUrl;

    @ApiModelProperty("规格")
    private String spec;

    @ApiModelProperty("规格编码")
    private String specCode;

    @ApiModelProperty("颜色名称")
    private String colorName;

    @ApiModelProperty("颜色编码")
    private String colorCode;

    @ApiModelProperty("型号")
    private String model;

    @ApiModelProperty("型号编码")
    private String modelCode;

    @ApiModelProperty("商品单位code")
    private String unitCode;

    @ApiModelProperty("商品单位")
    private String unitName;

    @ApiModelProperty("拆零系数")
    private Integer zeroDisassemblyCoefficient;

    @ApiModelProperty("是否是赠品(0否1是)")
    private Integer givePromotion;

    @ApiModelProperty("批次号")
    private String batchNumber;

    @ApiModelProperty("分销单价")
    private Long price;

    @ApiModelProperty("数量")
    private Long num;

    @ApiModelProperty("分销总价")
    private Long amount;

    @ApiModelProperty("活动分摊")
    private Long activityApportionment;

    @ApiModelProperty("优惠分摊")
    private Long preferentialAllocation;

    @ApiModelProperty("实发数量")
    private Long actualDeliverNum;

    @ApiModelProperty("活动编码(多个，隔开）")
    private String activityCode;

    @ApiModelProperty("商品行号")
    private Long productLineNum;

    @ApiModelProperty("赠品行号")
    private Long promotionLineNum;

    @ApiModelProperty("退货数量")
    private Long returnNum;

    @ApiModelProperty("公司名称")
    private String companyName;

    @ApiModelProperty("公司编码")
    private String companyCode;

    @ApiModelProperty("渠道单价")
    private Long channelUnitPrice;

    @ApiModelProperty("渠道总价")
    private Long totalChannelPrice;

    @ApiModelProperty("实际渠道单价")
    private Long actualChannelUnitPrice;

    @ApiModelProperty("实际渠道总价")
    private Long actualTotalChannelPrice;

    @ApiModelProperty("实际分销总价")
    private Long actualAmount;

    @ApiModelProperty("实际分销单价")
    private Long actualPrice;
}