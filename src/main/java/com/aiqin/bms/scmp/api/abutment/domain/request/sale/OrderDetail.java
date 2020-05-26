package com.aiqin.bms.scmp.api.abutment.domain.request.sale;

import java.math.BigDecimal;
import java.util.List;

import com.aiqin.bms.scmp.api.abutment.domain.request.purchase.ScmpPurchaseBatch;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author sunx
 * @description
 * @date 2019-08-02
 */
@Data
@ApiModel("scmp销售单或退货单据明细信息1")
public class OrderDetail {

    /**
     * 订单id
     */
    @JsonProperty("order_id")
    @ApiModelProperty(value = "订单id", hidden = true)
    private String orderId;
    /**
     * 订单类型
     */
    @JsonProperty("order_type")
    @ApiModelProperty(value = "订单类型", hidden = true)
    private Integer orderType;
    /**
     * sku编码
     */
    @JsonProperty("sku_code")
    @ApiModelProperty("sku编码")
    private String skuCode;
    /**
     * sku名称
     */
    @JsonProperty("sku_name")
    @ApiModelProperty("sku名称")
    private String skuName;
    /**
     * 颜色规格型号冗余信息
     */
    @JsonProperty("sku_desc")
    @ApiModelProperty("颜色规格型号冗余信息")
    private String skuDesc;
    /**
     * 单位
     */
    @ApiModelProperty("单位")
    private String unit;
    /**
     * 拆零系数
     */
    @JsonProperty("scattered_unit")
    @ApiModelProperty("拆零系数")
    private Integer scatteredUnit;
    /**
     * 是否赠品
     */
    @JsonProperty("gift_flag")
    @ApiModelProperty("是否赠品")
    private Integer giftFlag;
//    /**
//     * 批次号
//     */
//    @JsonProperty("batch_no")
//    @ApiModelProperty("批次号")
//    private String batchNo;
    /**
     * 渠道单价
     */
    @JsonProperty("channel_price")
    @ApiModelProperty("渠道单价")
    private BigDecimal channelPrice;
    /**
     * 分销单价
     */
    @JsonProperty("distribution_price")
    @ApiModelProperty("分销单价")
    private BigDecimal distributionPrice;
    /**
     * 数量
     */
    @JsonProperty("single_count")
    @ApiModelProperty("数量")
    private Integer singleCount;
    /**
     * 活动分摊金额
     */
    @JsonProperty("activity_share_amount")
    @ApiModelProperty("活动分摊金额")
    private BigDecimal activityShareAmount;
    /**
     * 优惠分摊
     */
    @JsonProperty("discount_share_amount")
    @ApiModelProperty("优惠分摊")
    private BigDecimal discountShareAmount;
    /**
     * 实发数
     */
    @JsonProperty("delivery_count")
    @ApiModelProperty("实发数")
    private Integer deliveryCount;
    /**
     * 活动编码
     */
    @JsonProperty("activity_no")
    @ApiModelProperty("活动编码")
    private String activityNo;
    /**
     * 赠品行号
     */
    @JsonProperty("gift_line_no")
    @ApiModelProperty("赠品行号")
    private String giftLineNo;
    /**
     * 退货数量
     */
    @JsonProperty("return_count")
    @ApiModelProperty("退货数量")
    private Integer returnCount;

    /**
     * 供应商编码
     */
    @JsonProperty("supplier_code")
    @ApiModelProperty("供应商编码")
    private String supplierCode;
    /**
     * 供应商名称
     */
    @JsonProperty("supplier_name")
    @ApiModelProperty("供应商名称")
    private String supplierName;

    @JsonProperty("brand_code")
    @ApiModelProperty("品牌编码")
    private String brandCode;

    @JsonProperty("brand_name")
    @ApiModelProperty("品牌名称")
    private String brandName;

    @JsonProperty("category_code")
    @ApiModelProperty("品类编码")
    private String categoryCode;

    @JsonProperty("category_name")
    @ApiModelProperty("品类名称")
    private String categoryName;

    /**
     * 税率
     */
    @JsonProperty("input_rate")
    @ApiModelProperty("税率")
    private BigDecimal inputRate;

    @JsonProperty("batch_list")
    @ApiModelProperty("批次信息")
    private List<ScmpPurchaseBatch> batchList;

}
