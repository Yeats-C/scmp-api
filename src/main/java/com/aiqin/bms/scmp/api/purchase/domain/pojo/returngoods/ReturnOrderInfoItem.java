package com.aiqin.bms.scmp.api.purchase.domain.pojo.returngoods;

import com.aiqin.bms.scmp.api.purchase.domain.pojo.order.OrderInfoItemProductBatch;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@ApiModel("订单商品信息")
@Data
public class ReturnOrderInfoItem {
    @ApiModelProperty("商品主键")
    private Long id;

    @ApiModelProperty("订单主表编码")
    @JsonProperty("return_order_code")
    private String returnOrderCode;

    @ApiModelProperty("sku编号")
    @JsonProperty("sku_code")
    private String skuCode;

    @ApiModelProperty("sku名称")
    @JsonProperty("sku_name")
    private String skuName;

    @ApiModelProperty("图片地址")
    @JsonProperty("picture_url")
    private String pictureUrl;

    @ApiModelProperty("规格")
    private String spec;

    @ApiModelProperty("规格编码")
    @JsonProperty("spec_code")
    private String specCode;

    @ApiModelProperty("颜色名称")
    @JsonProperty("color_name")
    private String colorName;

    @ApiModelProperty("颜色编码")
    @JsonProperty("color_code")
    private String colorCode;

    @ApiModelProperty("型号")
    private String model;

    @ApiModelProperty("型号编码")
    @JsonProperty("model_code")
    private String modelCode;

    @ApiModelProperty("基商品含量")
    @JsonProperty("base_product_content")
    private Integer baseProductContent;

    @ApiModelProperty("商品单位编码")
    @JsonProperty("unit_code")
    private String unitCode;

    @ApiModelProperty("商品单位")
    @JsonProperty("unit_name")
    private String unitName;

    @ApiModelProperty("拆零系数")
    @JsonProperty("zero_disassembly_coefficient")
    private Integer zeroDisassemblyCoefficient;

    @ApiModelProperty("是否是赠品(0否1是)")
    @JsonProperty("give_promotion")
    private Integer givePromotion;

    @ApiModelProperty("分销单价")
    @JsonProperty("product_amount")
    private BigDecimal price;

    @ApiModelProperty("数量")
    @JsonProperty("product_count")
    private Long num;

    @ApiModelProperty("分销总价")
    @JsonProperty("total_product_amount")
    private BigDecimal amount;

    @ApiModelProperty("活动编码(多个，隔开）")
    @JsonProperty("activity_code")
    private String activityCode;

    @ApiModelProperty("商品行号")
    @JsonProperty("line_code")
    private Long productLineNum;

    @ApiModelProperty("赠品行号")
    @JsonProperty("gift_line_code")
    private Long promotionLineNum;

    @ApiModelProperty("商品状态1新品2残品")
    @JsonProperty("product_status")
    private Integer productStatus;

    @ApiModelProperty("实际入库数量")
    @JsonProperty("actual_product_count")
    private Integer actualInboundNum;

    @ApiModelProperty("公司名称")
    @JsonProperty("company_name")
    private String companyName;

    @ApiModelProperty("公司编码")
    @JsonProperty("company_code")
    private String companyCode;

    @ApiModelProperty("渠道单价")
    @JsonProperty("channel_amount")
    private BigDecimal channelUnitPrice;

    @ApiModelProperty("渠道总价")
    @JsonProperty("total_channel_amount")
    private BigDecimal totalChannelPrice;

    @ApiModelProperty("实际渠道单价")
    @JsonProperty("actual_channel_amount")
    private BigDecimal actualChannelUnitPrice;

    @ApiModelProperty("实际渠道总价")
    @JsonProperty("actual_total_channel_amount")
    private BigDecimal actualTotalChannelPrice;

    @ApiModelProperty("实际单价")
    @JsonProperty("actual_amount")
    private BigDecimal actualAmount;

    @ApiModelProperty("实际总价")
    @JsonProperty("actual_total_amount")
    private BigDecimal actualPrice;

    /**以下字段为了dl回调销售单生成出库单和库存变动需要*/

    @ApiModelProperty("库房名称")
    @JsonProperty("warehouse_name")
    private String warehouseName;

    @ApiModelProperty("库房编码")
    @JsonProperty("warehouse_code")
    private String warehouseCode;

    @ApiModelProperty("供应商code")
    @JsonProperty("supplier_code")
    private String supplyCode;

    @ApiModelProperty("供应商name")
    @JsonProperty("supplier_name")
    private String supplyName;

    @ApiModelProperty("税率")
    private BigDecimal tax;

    @ApiModelProperty("是否为验货之后新增商品 0 是 1 否")
    @JsonProperty("insert_type")
    private Integer insertType;

}