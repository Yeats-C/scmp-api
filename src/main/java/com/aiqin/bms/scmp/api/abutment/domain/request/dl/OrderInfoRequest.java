package com.aiqin.bms.scmp.api.abutment.domain.request.dl;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@ApiModel("DL- 销售单推送")
public class OrderInfoRequest extends CommonRequest{

    @NotNull(message = "销售单号不能为空")
    @ApiModelProperty(value="销售单号")
    @JsonProperty("order_code")
    private String orderCode;

    @ApiModelProperty(value="DL单号标识")
    @JsonProperty("order_id")
    private String orderId;

    @ApiModelProperty(value="销售单支付时间")
    @JsonProperty("payment_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date paymentTime;

    @ApiModelProperty(value="支付方式 1.转账")
    @JsonProperty("payment_type")
    private Integer paymentType;

    @ApiModelProperty("供货方式 1直送、2配送、3辅采直送")
    @JsonProperty("order_type")
    private Integer orderType;

    @ApiModelProperty(value="订单类别")
    @JsonProperty("order_category")
    private String orderCategory;

    @ApiModelProperty(value="订单类别")
    @JsonProperty("order_category_code")
    private String orderCategoryCode;

    @ApiModelProperty(value="渠道 :爱亲科技、爱亲母婴、萌贝树、小红马")
    @JsonProperty("channel_name")
    private String channelName;

    @ApiModelProperty(value="平台 0.爱亲 1.DL")
    @JsonProperty("platform_type")
    private Integer platformType;

    @ApiModelProperty(value="订单类型 0.B2B 1.B2C")
    @JsonProperty("order_product_type")
    private Integer orderProductType;

    @ApiModelProperty(value="业务形式 1门店、2批发、3线上业务、4线下业务、5优选业务、6天猫业务")
    @JsonProperty("business_form")
    private Integer businessForm;

    @ApiModelProperty(value="客户编码")
    @JsonProperty("customer_code")
    private String customerCode;

    @ApiModelProperty(value="客户名称")
    @JsonProperty("customer_name")
    private String customerName;

    @ApiModelProperty(value="加盟商编码")
    @JsonProperty("franchisee_code")
    private String franchiseeCode;

    @ApiModelProperty(value="加盟商名称")
    @JsonProperty("franchisee_name")
    private String franchiseeName;

    @ApiModelProperty(value="合伙人编码")
    @JsonProperty("partner_code")
    private String partnerCode;

    @ApiModelProperty(value="合伙人编码")
    @JsonProperty("partner_name")
    private String partnerName;

    @ApiModelProperty(value="省编码")
    @JsonProperty("province_code")
    private String provinceCode;

    @ApiModelProperty(value="省名称")
    @JsonProperty("province_name")
    private String provinceName;

    @ApiModelProperty(value="市编码")
    @JsonProperty("city_code")
    private String cityCode;

    @ApiModelProperty(value="市")
    @JsonProperty("city_name")
    private String cityName;

    @ApiModelProperty(value="区/县编码")
    @JsonProperty("district_code")
    private String districtCode;

    @ApiModelProperty(value="区/县")
    @JsonProperty("district_name")
    private String districtName;

    @ApiModelProperty(value="详细地址")
    @JsonProperty("detail_address")
    private String detailAddress;

    @ApiModelProperty(value="收货人")
    @JsonProperty("consignee")
    private String consignee;

    @ApiModelProperty(value="收货人手机号")
    @JsonProperty("consignee_phone")
    private String consigneePhone;

    @ApiModelProperty(value="送货方式编码")
    @JsonProperty("distribution_mode_code")
    private String distributionModeCode;

    @ApiModelProperty(value="送货方式名称")
    @JsonProperty("distribution_mode_name")
    private String distributionModeName;

    @ApiModelProperty(value="渠道订单金额")
    @JsonProperty("channel_order_amount")
    private BigDecimal channelOrderAmount;

    @ApiModelProperty(value="分销订单金额")
    @JsonProperty("order_amount")
    private BigDecimal orderAmount;

    @ApiModelProperty(value="渠道商品总金额")
    @JsonProperty("channel_total_amount")
    private BigDecimal channelTotalAmount;

    @ApiModelProperty(value="分销商品总金额")
    @JsonProperty("product_total_amount")
    private BigDecimal productTotalAmount;

    @ApiModelProperty(value="运费")
    @JsonProperty("deliver_amount")
    private BigDecimal deliverAmount;

    @ApiModelProperty(value="运费减免比例")
    @JsonProperty("logistics_remission_ratio")
    private BigDecimal logisticsRemissionRatio;

    @ApiModelProperty(value="活动优惠")
    @JsonProperty("activity_discount")
    private BigDecimal activityDiscount;

    @ApiModelProperty(value="A品券抵扣")
    @JsonProperty("top_coupon_money")
    private BigDecimal topCouponMoney;

    @ApiModelProperty(value="服纺券抵扣")
    @JsonProperty("suit_coupon_money")
    private BigDecimal suitCouponMoney;

    @ApiModelProperty(value="商品数量")
    @JsonProperty("product_count")
    private Long productCount;

    @ApiModelProperty(value="体积")
    @JsonProperty("volume")
    private Long volume;

    @ApiModelProperty(value="重量")
    @JsonProperty("weight")
    private Long weight;

    @ApiModelProperty(value="发票 1.不开，2.增普，3.增专")
    @JsonProperty("invoice_type")
    private Integer invoiceType;

    @ApiModelProperty(value="发票抬头")
    @JsonProperty("invoice_title")
    private String invoiceTitle;

    @ApiModelProperty(value="发票税号")
    @JsonProperty("invoice_tax")
    private String invoiceTax;

    @ApiModelProperty(value="发票地址")
    @JsonProperty("invoice_address")
    private String invoiceAddress;

    @ApiModelProperty(value="发票电话")
    @JsonProperty("invoice_mobile")
    private String invoiceMobile;

    @ApiModelProperty(value="发票开户银行")
    @JsonProperty("invoice_bank")
    private String invoiceBank;

    @ApiModelProperty(value="发票银行账户")
    @JsonProperty("invoice_bank_account")
    private String invoiceBankAccount;

    @ApiModelProperty(value="wms库房类型")
    @JsonProperty("wms_warehouse_type")
    private Integer wmsWarehouseType;

    @ApiModelProperty(value="商品信息")
    @JsonProperty("product_list")
    private List<ProductRequest> productList;

}
