package com.aiqin.bms.scmp.api.purchase.domain.pojo.returngoods;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("订单商品信息")
@Data
public class ReturnOrderInfoItem {
    @ApiModelProperty("商品主键")
    private Long id;

    @ApiModelProperty("订单主表编码")
    private String returnOrderCode;

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

    @ApiModelProperty("基商品含量")
    private Integer baseProductContent;

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

    @ApiModelProperty("活动编码(多个，隔开）")
    private String activityCode;

    @ApiModelProperty("商品行号")
    private Long productLineNum;

    @ApiModelProperty("赠品行号")
    private Long promotionLineNum;

    @ApiModelProperty("商品状态1新品2残品")
    private Integer productStatus;

    @ApiModelProperty("实际入库数量")
    private Integer actualInboundNum;

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

    @ApiModelProperty("实际单价")
    private Long actualAmount;

    @ApiModelProperty("实际总价")
    private Long actualPrice;

    /**以下字段为了dl回调销售单生成出库单和库存变动需要*/

    @ApiModelProperty("仓库名称")
    private String warehouseName;

    @ApiModelProperty("仓库编码")
    private String warehouseCode;

    @ApiModelProperty("供应商code")
    private String supplyCode;

    @ApiModelProperty("供应商name")
    private String supplyName;

    @ApiModelProperty("税率")
    private Long tax;
}