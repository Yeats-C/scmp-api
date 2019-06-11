package com.aiqin.bms.scmp.api.supplier.domain.response.order;

import com.aiqin.bms.scmp.api.base.InvoiceType;
import com.aiqin.bms.scmp.api.base.OrderStatus;
import com.aiqin.bms.scmp.api.supplier.domain.response.LogData;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Description:
 *
 * @author: zth
 * @date: 2018-12-25
 * @time: 14:01
 */
@Data
@ApiModel("订单详情返回vo")
public class SupplyOrderInfoRespVO {
    @ApiModelProperty("订单编码(订单号)")
    private String orderCode;

    @ApiModelProperty("类型：直送、配送、首单、首单赠送")
    private String orderType;

    @ApiModelProperty("订单类型编码")
    private String orderTypeCode;

    @ApiModelProperty("客户名称")
    private String customerName;

    @ApiModelProperty("客户编码")
    private String customerCode;

    @ApiModelProperty("创建时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;

    @ApiModelProperty("订单状态(状态有点多，后面补)")
    private String orderStatus;

    @ApiModelProperty("是否锁定(0否1是）")
    private Long beLock;

    @ApiModelProperty("是否是异常订单(0否1是)")
    private Byte beException;

    @ApiModelProperty("是否删除(0否1是)")
    private Long beDelete;

    @ApiModelProperty("支付状态")
    private String paymentStatus;

    @ApiModelProperty("物流中心名称")
    private String transportCenterName;

    @ApiModelProperty("物流中心编码")
    private String transportCenterCode;

    @ApiModelProperty("仓库名称")
    private String warehouseName;

    @ApiModelProperty("仓库编码")
    private String warehouseCode;

    @ApiModelProperty("供应商名称")
    private String supplierName;

    @ApiModelProperty("供应商编码")
    private String supplierCode;

    @ApiModelProperty("配送方式")
    private String distributionMode;



    @ApiModelProperty("配送方式编码")
    private String distributionModeCode;

    @ApiModelProperty("收货人")
    private String consignee;

    @ApiModelProperty("收货人手机号")
    private String consigneePhone;

    @ApiModelProperty("省编码")
    private String provinceCode;

    @ApiModelProperty("省名称")
    private String provinceName;

    @ApiModelProperty("市编码")
    private String cityCode;

    @ApiModelProperty("市名称")
    private String cityName;

    @ApiModelProperty("区编码")
    private String districtCode;

    @ApiModelProperty("区名称")
    private String districtName;

    @ApiModelProperty("地址")
    private String address;

    @ApiModelProperty("详细地址")
    private String detailAddress;

    @ApiModelProperty("邮编")
    private String zipCode;

    @ApiModelProperty("支付方式")
    private String paymentType;

    @ApiModelProperty("支付方式编码")
    private String paymentTypeCode;

    @ApiModelProperty("运费")
    private Long deliverAmount;

    @ApiModelProperty("商品总金额")
    private Long productTotalAmount;

    @ApiModelProperty("优惠额度")
    private Long discountAmount;

    @ApiModelProperty("订单金额")
    private Long orderAmount;

    @ApiModelProperty("商品数量")
    private Long productNum;

    @ApiModelProperty("支付日期")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date paymentTime;

    @ApiModelProperty("发货时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date deliveryTime;

    @ApiModelProperty("收货时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date receivingTime;

    @ApiModelProperty("不开、增普、增专")
    private String invoiceType;

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = InvoiceType.getAllStatus().get(invoiceType).getDesc();
    }

    @ApiModelProperty("发票类型编码")
    private String invoiceTypeCode;

    @ApiModelProperty("发票抬头")
    private String invoiceTitle;

    @ApiModelProperty("操作人")
    private String operator;

    @ApiModelProperty("操作人编码")
    private String operatorCode;

    @ApiModelProperty("操作时间")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date operatorTime;

    @ApiModelProperty("活动优惠")
    private Long activityDiscount;

    @ApiModelProperty("重量")
    private Long weight;

    @ApiModelProperty("是否父订单(0不是1是)")
    private Byte beFather;

    @ApiModelProperty("父订单号")
    private String fatherOrderCode;

    @ApiModelProperty("订单来源")
    private String orderOriginal;

    @ApiModelProperty("备注")
    private String remake;

    @ApiModelProperty("商品信息列表")
    private List<SupplyOrderProductItemRespVO> orderItems;

    public String getAddress() {
        return this.provinceName + this.cityName + this.districtName;
    }

    public String getOrderStatus() {
        return Optional.ofNullable(OrderStatus.getAllStatus().get(orderStatus).getFrontOrderStatus()).orElse("");
    }
    @ApiModelProperty("日志信息")
    private List<LogData> logs;
}
