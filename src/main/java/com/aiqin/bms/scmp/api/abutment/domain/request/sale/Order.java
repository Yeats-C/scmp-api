package com.aiqin.bms.scmp.api.abutment.domain.request.sale;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * @author sunx
 * @description 业务单据信息
 * @date 2019-08-02
 */
@Data
@ApiModel("scmp销售单或退货据信息1")
public class Order {

    @JsonProperty("order_id")
    @ApiModelProperty("订单号或退货单id")
    private String orderId;
    /**
     * 订单或退货单编码
     */
    @JsonProperty("order_code")
    @ApiModelProperty("订单或退货单编码")
    private String orderCode;
    /**
     * 父订单id
     */
    @JsonProperty("parent_order_id")
    @ApiModelProperty("父订单id")
    private String parentOrderId;
    /**
     * 父订单编码
     */
    @JsonProperty("parent_order_code")
    @ApiModelProperty("父订单编码")
    private String parentOrderCode;

    @JsonProperty("order_type")
    @ApiModelProperty("单据类型 10 配送订单 15 直送订单 20 辅采订单  25 售后退货")
    @NotNull(message = "单据类型不能为空")
    private String orderType;
    /**
     * 订单类型描述
     */
    @JsonProperty("order_type_desc")
    @ApiModelProperty("订单类型描述")
    private String orderTypeDesc;
    /**
     * 支付状态
     */
    @JsonProperty("pay_status")
    @ApiModelProperty("支付状态 1 是未支付 2 是已支付")
    private Integer payStatus;
    /**
     * 支付方式编码
     */
    @JsonProperty("pay_type")
    @ApiModelProperty("支付方式编码 1  转账")
    private String payType;
    /**
     * 支付方式描述
     */
    @JsonProperty("pay_type_desc")
    @ApiModelProperty("支付方式描述")
    private String payTypeDesc;
    /**
     * 支付时间
     */
    @JsonProperty("pay_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("支付时间")
    private Date payTime;
    /**
     * 订单类别编码
     */
    @JsonProperty("order_category_code")
    @ApiModelProperty("订单类别编码")
    private String orderCategoryCode;
    /**
     * 订单类别描述  正常补货
     */
    @JsonProperty("order_category_desc")
    @ApiModelProperty("订单类别描述  正常补货")
    private String orderCategoryDesc;
    /**
     * 订单来源编码
     */
    @JsonProperty("source_code")
    @ApiModelProperty("订单来源编码")
    private String sourceCode;
    /**
     * 仓库编码
     */
    @JsonProperty("storage_code")
    @ApiModelProperty("仓库编码")
    private String storageCode;
    /**
     * 仓库名称
     */
    @JsonProperty("storage_name")
    @ApiModelProperty("仓库名称")
    private String storageName;
    /**
     * 库房编码
     */
    @JsonProperty("warehouse_code")
    @ApiModelProperty("库房编码")
    private String warehouseCode;
    /**
     * 库房名称
     */
    @JsonProperty("warehouse_name")
    @ApiModelProperty("库房名称")
    private String warehouseName;

    /**
     * 商品数量
     */
    @JsonProperty("order_count")
    @ApiModelProperty("商品数量")
    private Integer orderCount;
    /**
     * 重量
     */
    @ApiModelProperty("重量")
    private String weight;
    /**
     * 体积
     */
    @ApiModelProperty("体积")
    private String volume;
    /**
     * 是否开票
     */
    @JsonProperty("invoice_flag")
    @ApiModelProperty("是否开票")
    private Integer invoiceFlag;
    /**
     * 开票抬头
     */
    @JsonProperty("invoice_title")
    @ApiModelProperty("开票抬头")
    private String invoiceTitle;
    /**
     * 发货时间
     */
    @JsonProperty("delivery_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("发货时间")
    private Date deliveryTime;
    /**
     * 发运时间
     */
    @JsonProperty("freight_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("发运时间")
    private Date freightTime;
    /**
     * 收货时间
     */
    @JsonProperty("receipt_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("收货时间")
    @NotNull(message = "收货时间不能为空")
    private Date receiptTime;
    /**
     * 客户编码
     */
    @JsonProperty("customer_code")
    @ApiModelProperty("客户编码,对应的sap的编码")
    @NotBlank(message = "客户编码不能为空")
    private String customerCode;
    /**
     * 客户名称
     */
    @JsonProperty("customer_name")
    @ApiModelProperty("客户名称")
    private String customerName;
    /**
     * 配送方式编码
     */
    @JsonProperty("delivery_type_code")
    @ApiModelProperty("配送方式编码")
    private String deliveryTypeCode;
    /**
     * 配送方式描述
     */
    @JsonProperty("delivery_type_desc")
    @ApiModelProperty("配送方式描述")
    private String deliveryTypeDesc;
    /**
     * 收货人id
     */
    @JsonProperty("receipt_user_id")
    @ApiModelProperty("收货人id")
    private String receiptUserId;
    /**
     * 收货人
     */
    @JsonProperty("receipt_user_name")
    @ApiModelProperty("收货人")
    private String receiptUserName;
    /**
     * 收货人手机号
     */
    @JsonProperty("receipt_mobile")
    @ApiModelProperty("收货人手机号")
    private String receiptMobile;
    /**
     * 邮编
     */
    @JsonProperty("post_code")
    @ApiModelProperty("邮编")
    private String postCode;
    /**
     * 收货人省市区冗余信息
     */
    @JsonProperty("receipt_addr")
    @ApiModelProperty("收货人省市区冗余信息")
    private String receiptAddr;
    /**
     * 收货详细地址
     */
    @JsonProperty("receipt_address")
    @ApiModelProperty("收货详细地址")
    private String receiptAddress;
    /**
     * 商品渠道总金额
     */
    @JsonProperty("pay_channel_amount")
    @ApiModelProperty("商品渠道总金额")
    private String payChannelAmount;
    /**
     * 商品分销总金额
     */
    @JsonProperty("pay_distribution_amount")
    @ApiModelProperty("商品分销总金额")
    private String payDistributionAmount;
    /**
     * 运费
     */
    @JsonProperty("freight_fee")
    @ApiModelProperty("运费")
    private String freightFee;
    /**
     * 运费减免比例
     */
    @JsonProperty("freight_deduction_rate")
    @ApiModelProperty("运费减免比例")
    private Integer freightDeductionRate;
    /**
     * 活动优惠金额
     */
    @JsonProperty("activity_discount_amount")
    @ApiModelProperty("活动优惠金额")
    private String activityDiscountAmount;
    /**
     * 优惠额度
     */
    @JsonProperty("discount_amount")
    @ApiModelProperty("优惠额度")
    private String discountAmount;
    /**
     * 订单金额
     */
    @ApiModelProperty("订单金额")
    private String amount;

    @JsonProperty("order_channel_code")
    @ApiModelProperty("订单渠道编码")
    @NotBlank(message = "订单渠道编码不能为空")
    private String orderChannelCode;

    @JsonProperty("order_channel_name")
    @ApiModelProperty("订单渠道名称")
    private String orderChannelName;

    /**
     * 同步时间
     */
    @JsonProperty("sync_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("同步时间")
    private Date createTime;

    /**
     * 最终操作人id
     */
    @JsonProperty("user_id")
    @ApiModelProperty("最终操作人id")
    private String createById;

    /**
     * 最终操作人
     */
    @JsonProperty("user_name")
    @ApiModelProperty("最终操作人")
    private String createByName;

    @JsonProperty("order_status")
    @ApiModelProperty("订单状态编码")
    private String orderStatus;

    @JsonProperty("order_status_desc")
    @ApiModelProperty("订单状态描述")
    private String orderStatusDesc;

    /**
     * 平台类型
     */
    @JsonProperty("platform_type")
    @ApiModelProperty("平台类型 0.新系统，1.DL")
    private Integer platform_type;

    /**
     * 业务形式
     */
    @JsonProperty("business_form")
    @ApiModelProperty("业务形式")
    private String businessForm;

    /**
     * 税号
     */
    @JsonProperty("tax_id")
    @ApiModelProperty("税号")
    private String taxId;

    /**
     * 业务单据明细信息
     */
    @JsonProperty("details")
    @ApiModelProperty("业务单据明细信息")
    private List<OrderDetail> details;
}
