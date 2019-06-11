package com.aiqin.bms.scmp.api.supplier.domain.response.order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Description:
 *
 * @author: zth
 * @date: 2018-12-28
 * @time: 15:07
 */
@Data
@ApiModel("商品返回VO")
public class SupplyOrderProductItemRespVO {
    @ApiModelProperty("sku编号")
    private String skuCode;

    @ApiModelProperty("sku名称")
    private String skuName;

    @ApiModelProperty("商品行号")
    private Long productLineNum;

    @ApiModelProperty("颜色名称")
    private String colorName;

    @ApiModelProperty("商品单位")
    private String unitName;

    @ApiModelProperty("是否是赠品(0否1是)")
    private Byte givePromotion;

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

    @ApiModelProperty("赠品行号")
    private Long promotionLineNum;

    @ApiModelProperty("退货数量")
    private Long returnNum;

    @ApiModelProperty("仓库名称")
    private String warehouseName;

    @ApiModelProperty("供应单位名称")
    private String supplyCompanyName;

    @ApiModelProperty("活动分摊")
    private Long activityApportionment;

    @ApiModelProperty("活动编码(多个，隔开）")
    private String activityCode;

    @ApiModelProperty("图片地址")
    private String pictureUrl;
}
