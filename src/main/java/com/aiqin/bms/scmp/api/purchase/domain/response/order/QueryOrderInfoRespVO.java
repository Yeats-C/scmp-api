package com.aiqin.bms.scmp.api.purchase.domain.response.order;

import com.aiqin.bms.scmp.api.base.OrderStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-06-14
 * @time: 15:18
 */
@Data
@ApiModel("订单详情")
public class QueryOrderInfoRespVO {

    @ApiModelProperty("订单编码(订单号)")
    private String orderCode;

    @ApiModelProperty("类型：直送、配送、首单、首单赠送.辅采直送")
    private String orderType;

    @ApiModelProperty("客户名称")
    private String customerName;

    @ApiModelProperty("客户编码")
    private String customerCode;

    @ApiModelProperty("创建时间")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;

    @ApiModelProperty("订单状态")
    private String orderStatus;

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = OrderStatus.getAllStatus().get(orderStatus).getBackgroundOrderStatus();
    }

    @ApiModelProperty("是否锁定(0否1是）")
    private Integer beLock;

    @ApiModelProperty("是否是异常订单(0否1是)")
    private Integer beException;

    @ApiModelProperty("支付状态(0未1已)")
    private Integer paymentStatus;

    @ApiModelProperty("物流中心名称")
    private String transportCenterName;

    @ApiModelProperty("仓库名称")
    private String warehouseName;

    @ApiModelProperty("供应商名称")
    private String supplierName;

    @ApiModelProperty("配送方式")
    private String distributionMode;

    @ApiModelProperty("收货人")
    private String consignee;

    @ApiModelProperty("收货人手机号")
    private String consigneePhone;

    @ApiModelProperty("省名称")
    private String provinceName;

    @ApiModelProperty("市名称")
    private String cityName;

    @ApiModelProperty("区名称")
    private String districtName;

    @ApiModelProperty("详细地址")
    private String detailAddress;

    @ApiModelProperty("邮编")
    private String zipCode;

    @ApiModelProperty("支付方式")
    private String paymentType;

    @ApiModelProperty("运费")
    private BigDecimal deliverAmount;

    @ApiModelProperty("商品分销价总金额")
    private BigDecimal productTotalAmount;

    @ApiModelProperty("商品渠道价总金额")
    private BigDecimal productChannelTotalAmount;

    @ApiModelProperty("优惠额度")
    private BigDecimal discountAmount;

    @ApiModelProperty("订单金额（分销金额）")
    private BigDecimal orderAmount;

    @ApiModelProperty("商品数量")
    private Long productNum;

    @ApiModelProperty("支付日期")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date paymentTime;

    @ApiModelProperty("发货时间")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date deliveryTime;

    @ApiModelProperty("发运时间")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date transportTime;

    @ApiModelProperty("收货时间")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date receivingTime;

    @ApiModelProperty("不开、增普、增专")
    private String invoiceType;

    @ApiModelProperty("发票抬头")
    private String invoiceTitle;

    @ApiModelProperty("操作人")
    private String operator;

    @ApiModelProperty("操作时间")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date operatorTime;

    @ApiModelProperty("活动优惠")
    private BigDecimal activityDiscount;

    @ApiModelProperty("重量")
    private Long weight;

    @ApiModelProperty("体积")
    private Long volume;

    @ApiModelProperty("是否父订单(0不是1是)")
    private Integer beMasterOrder;

    @ApiModelProperty("父订单号")
    private String masterOrderCode;

    @ApiModelProperty("订单来源")
    private String orderOriginal;

    @ApiModelProperty("备注")
    private String remake;

    @ApiModelProperty("门店类型编码")
    private String storeTypeCode;

    @ApiModelProperty("门店类型")
    private String storeType;

    @ApiModelProperty("订单类别名称")
    private String orderCategory;

    @ApiModelProperty("订单类别编码")
    private String orderCategoryCode;

    @ApiModelProperty("创建人")
    private String createByName;

    @ApiModelProperty("创建时间")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty("修改人")
    private String updateByName;

    @ApiModelProperty("修改时间")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @ApiModelProperty("实际渠道总价")
    private BigDecimal actualProductChannelTotalAmount;

    @ApiModelProperty("实际订单金额（实际分销金额）")
    private BigDecimal actualOrderAmount;

    @ApiModelProperty("实际发货数量")
    private Long actualProductNum;

    @ApiModelProperty("订单来源名称(渠道)")
    private String orderOriginalName;

    @ApiModelProperty("实际重量")
    private Long actualWeight;

    @ApiModelProperty("实际体积")
    private Long actualVolume;

    @ApiModelProperty("实际分销总价")
    private BigDecimal actualProductTotalAmount;

    @ApiModelProperty("减免比例")
    private Integer logisticsRemissionRatio;

    @ApiModelProperty("运输公司")
    private String transportCompany;

    @ApiModelProperty("运输公司编码")
    private String transportCompanyCode;

    @ApiModelProperty("运输单号")
    private String transportNumber;

    @ApiModelProperty("发票地址")
    private String invoiceAddress;

    @ApiModelProperty("发票电话")
    private String invoiceMobile;

    @ApiModelProperty("发票开户银行")
    private String invoiceBank;

    @ApiModelProperty("发票银行账号")
    private String invoiceBankAccount;

    @ApiModelProperty("业务形式(熙云:批发业务  爱亲母婴:门店业务,批发业务,天猫业务,优选业务  爱亲科技:门店业务,批发业务  小红马:线上业务,线下业务  萌贝树: 门店业务)")
    private String businessForm;

    @ApiModelProperty("平台(0:爱亲(新系统) 1:DL)")
    private String platformType;

    @ApiModelProperty("税号")
    private String taxId;

    @ApiModelProperty(value = "门店编码")
//    @JsonProperty("store_code")
    private String storeCode;

    @ApiModelProperty(value = "门店名称")
//    @JsonProperty("store_name")
    private String storeName;

    @ApiModelProperty(value = "加盟商编码")
//    @JsonProperty("franchisee_code")
    private String franchiseeCode;

    @ApiModelProperty(value = "加盟商名称")
//    @JsonProperty("franchisee_name")
    private String franchiseeName;

    @ApiModelProperty("订单产品类型 0.B2B 1.B2C")
//    @JsonProperty("order_product_type")
    private Integer orderProductType;

    @ApiModelProperty("合伙人编码")
//    @JsonProperty("partner_code")
    private String partnerCode;

    @ApiModelProperty("合伙人名称")
//    @JsonProperty("partner_name")
    private String partnerName;

    @ApiModelProperty(value = "A品券优惠金额")
//    @JsonProperty("suit_coupon_money")
    private BigDecimal suitCouponMoney;

    @ApiModelProperty(value = "实际A品券优惠金额")
//    @JsonProperty("actual_suit_coupon_money")
    private BigDecimal actualSuitCouponMoney;

    @ApiModelProperty(value = "服纺券优惠金额")
//    @JsonProperty("top_coupon_money")
    private BigDecimal topCouponMoney;

    @ApiModelProperty(value = "实际服纺券优惠金额")
//    @JsonProperty("actual_top_coupon_money")
    private BigDecimal actualTopCouponMoney;

    @ApiModelProperty(value = "实际运费")
//    @JsonProperty("actual_deliver_amount")
    private BigDecimal actualDeliverAmount;

    @ApiModelProperty(value = "渠道订单金额")
//    @JsonProperty("channel_order_amount")
    private BigDecimal channelOrderAmount;

    @ApiModelProperty(value = "实际渠道订单金额")
//    @JsonProperty("actual_channel_order_amount")
    private BigDecimal actualChannelOrderAmount;

    @ApiModelProperty("商品信息")
    private List<QueryOrderInfoItemRespVO> productList;

    @ApiModelProperty("批次信息")
    private List<QueryOrderInfoItemBatchRespVO> batchList;

    @ApiModelProperty("日志信息")
    private List<QueryOrderInfoLogRespVO> logs;
}
