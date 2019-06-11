package com.aiqin.bms.scmp.api.supplier.domain.response.order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Description:
 *
 * @author: zth
 * @date: 2019-01-02
 * @time: 16:59
 */
@Data
@ApiModel("退货订单商品信息")
public class SupplyReturnOrderProductItemRespVO {

    @ApiModelProperty("sku编号")
    private String skuCode;

    @ApiModelProperty("sku名称")
    private String skuName;

    @ApiModelProperty("商品行号")
    private Long productLineNum;

    @ApiModelProperty("颜色名称")
    private String colorName;

    @ApiModelProperty("颜色编码")
    private String colorCode;

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

    @ApiModelProperty("规格")
    private String spec;

    @ApiModelProperty("赠品行号")
    private Long promotionLineNum;

    @ApiModelProperty("活动编码(多个，隔开）")
    private String activityCode;

    @ApiModelProperty("图片地址")
    private String pictureUrl;

}
