package com.aiqin.bms.scmp.api.purchase.domain.response.order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-06-14
 * @time: 16:39
 */
@Data
@ApiModel("商品信息")
public class QueryOrderInfoItemRespVO {

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("sku编号")
    private String skuCode;

    @ApiModelProperty("sku名称")
    private String skuName;

    @ApiModelProperty("图片地址")
    private String pictureUrl;

    @ApiModelProperty("规格")
    private String spec;

    @ApiModelProperty("颜色名称")
    private String colorName;

    @ApiModelProperty("型号")
    private String model;

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

    @ApiModelProperty("渠道单价")
    private Long channelUnitPrice;

    @ApiModelProperty("渠道总价")
    private Long totalChannelPrice;

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
}
