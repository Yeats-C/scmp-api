package com.aiqin.bms.scmp.api.purchase.domain.request.returngoods;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-06-19
 * @time: 15:07
 */
@Data
public class ReturnOrderInfoReqVO {

    @ApiModelProperty("退货订单编码")
    private String returnOrderCode;

    @ApiModelProperty("订单编码(订单号)")
    private String orderCode;

    @ApiModelProperty("创建时间")
    private Date createDate;

    @ApiModelProperty("类型：直送、配送、首单、首单赠送")
    private String orderType;

    @ApiModelProperty("订单类型编码")
    private Integer orderTypeCode;

    @ApiModelProperty("退货类型：客户退货、缺货退货、售后退货")
    private String returnOrderType;

    @ApiModelProperty("退货类型编码")
    private Integer returnOrderTypeCode;

    @ApiModelProperty("支付状态0未支付1已支付")
    private Integer paymentStatus;

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

    @ApiModelProperty("客户名称")
    private String customerName;

    @ApiModelProperty("客户编码")
    private String customerCode;

    @ApiModelProperty("收货人")
    private String consignee;

    @ApiModelProperty("收货人手机号")
    private String consigneePhone;

    @ApiModelProperty("地址")
    private String address;

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

    @ApiModelProperty("详细地址")
    private String detailAddress;

    @ApiModelProperty("邮编")
    private String zipCode;

    @ApiModelProperty("配送方式")
    private String distributionMode;

    @ApiModelProperty("配送方式编码")
    private String distributionModeCode;

    @ApiModelProperty("退货订单状态")
    private Integer orderStatus;

    @ApiModelProperty("支付方式")
    private String paymentType;

    @ApiModelProperty("支付方式编码")
    private String paymentTypeCode;

    @ApiModelProperty("运费")
    private Long deliverAmount;

    @ApiModelProperty("商品数量")
    private Long productNum;

    @ApiModelProperty("商品总金额")
    private Long productTotalAmount;

    @ApiModelProperty("退货金额")
    private Long returnOrderAmount;

    @ApiModelProperty("重量")
    private Long weight;

    @ApiModelProperty("发货时间")
    private Date deliveryTime;

    @ApiModelProperty("收货时间")
    private Date receivingTime;

    @ApiModelProperty("操作人")
    private String operator;

    @ApiModelProperty("操作人编码")
    private String operatorCode;

    @ApiModelProperty("操作时间")
    private Date operatorTime;

    @ApiModelProperty("公司编码")
    private String companyCode;

    @ApiModelProperty("公司名称")
    private String companyName;

    @ApiModelProperty("运输公司编码")
    private String transportCompanyCode;

    @ApiModelProperty("运输公司")
    private String transportCompany;

    @ApiModelProperty("运输单号")
    private String transportNumber;

    @ApiModelProperty("退货原因编码")
    private String returnReasonCode;

    @ApiModelProperty("退货原因描述")
    private String returnReasonContent;

    @ApiModelProperty("备注")
    private String remake;

    @ApiModelProperty("商品列表")
    List<ReturnOrderInfoItemReqVO> itemReqVOList;
}
