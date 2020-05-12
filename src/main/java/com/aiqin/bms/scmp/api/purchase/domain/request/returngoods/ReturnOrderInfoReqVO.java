package com.aiqin.bms.scmp.api.purchase.domain.request.returngoods;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("return_order_code")
    private String returnOrderCode;

    @ApiModelProperty("订单编码(订单号)")
    @JsonProperty("order_code")
    private String orderCode;

    @ApiModelProperty("创建时间")
    @JsonProperty("create_date")
    private Date createDate;

    @ApiModelProperty("类型：直送、配送、首单、首单赠送")
    @JsonProperty("order_type")
    private String orderType;

    @ApiModelProperty("订单类型编码")
    @JsonProperty("order_type_code")
    private Integer orderTypeCode;

    @ApiModelProperty("退货类型：客户退货、缺货退货、售后退货")
    @JsonProperty("return_order_type")
    private String returnOrderType;

    @ApiModelProperty("退货类型编码")
    @JsonProperty("return_order_type_code")
    private Integer returnOrderTypeCode;

    @ApiModelProperty("支付状态0未支付1已支付")
    @JsonProperty("payment_status")
    private Integer paymentStatus;

    @ApiModelProperty("物流中心名称")
    @JsonProperty("transport_center_name")
    private String transportCenterName;

    @ApiModelProperty("物流中心编码")
    @JsonProperty("transport_center_code")
    private String transportCenterCode;

    @ApiModelProperty("仓库名称")
    @JsonProperty("warehouse_name")
    private String warehouseName;

    @ApiModelProperty("仓库编码")
    @JsonProperty("warehouse_code")
    private String warehouseCode;

    @ApiModelProperty("供应商名称")
    @JsonProperty("supplier_name")
    private String supplierName;

    @ApiModelProperty("供应商编码")
    @JsonProperty("supplier_code")
    private String supplierCode;

    @ApiModelProperty("客户名称")
    @JsonProperty("customer_name")
    private String customerName;

    @ApiModelProperty("客户编码")
    @JsonProperty("customer_code")
    private String customerCode;

    @ApiModelProperty("收货人")
    @JsonProperty("consignee")
    private String consignee;

    @ApiModelProperty("收货人手机号")
    @JsonProperty("consignee_phone")
    private String consigneePhone;

    @ApiModelProperty("地址")
    @JsonProperty("address")
    private String address;

    @ApiModelProperty("省编码")
    @JsonProperty("province_code")
    private String provinceCode;

    @ApiModelProperty("省名称")
    @JsonProperty("province_name")
    private String provinceName;

    @ApiModelProperty("市编码")
    @JsonProperty("city_code")
    private String cityCode;

    @ApiModelProperty("市名称")
    @JsonProperty("city_name")
    private String cityName;

    @ApiModelProperty("区编码")
    @JsonProperty("district_code")
    private String districtCode;

    @ApiModelProperty("区名称")
    @JsonProperty("district_name")
    private String districtName;

    @ApiModelProperty("详细地址")
    @JsonProperty("detail_address")
    private String detailAddress;

    @ApiModelProperty("邮编")
    @JsonProperty("zip_code")
    private String zipCode;

    @ApiModelProperty("配送方式")
    @JsonProperty("distribution_mode")
    private String distributionMode;

    @ApiModelProperty("配送方式编码")
    @JsonProperty("distribution_mode_code")
    private String distributionModeCode;

    @ApiModelProperty("退货订单状态")
    @JsonProperty("order_status")
    private Integer orderStatus;

    @ApiModelProperty("支付方式")
    @JsonProperty("payment_type")
    private String paymentType;

    @ApiModelProperty("支付方式编码")
    @JsonProperty("payment_type_code")
    private String paymentTypeCode;

    @ApiModelProperty("运费")
    @JsonProperty("deliver_amount")
    private Long deliverAmount;

    @ApiModelProperty("商品数量")
    @JsonProperty("product_num")
    private Long productNum;

    @ApiModelProperty("商品总金额")
    @JsonProperty("product_total_amount")
    private Long productTotalAmount;

    @ApiModelProperty("退货金额")
    @JsonProperty("return_order_amount")
    private Long returnOrderAmount;

    @ApiModelProperty("重量")
    @JsonProperty("weight")
    private Long weight;

    @ApiModelProperty("发货时间")
    @JsonProperty("delivery_time")
    private Date deliveryTime;

    @ApiModelProperty("收货时间")
    @JsonProperty("receiving_time")
    private Date receivingTime;

    @ApiModelProperty("操作人")
    @JsonProperty("operator")
    private String operator;

    @ApiModelProperty("操作人编码")
    @JsonProperty("operator_code")
    private String operatorCode;

    @ApiModelProperty("操作时间")
    @JsonProperty("operator_time")
    private Date operatorTime;

    @ApiModelProperty("公司编码")
    @JsonProperty("company_code")
    private String companyCode;

    @ApiModelProperty("公司名称")
    @JsonProperty("company_name")
    private String companyName;

    @ApiModelProperty("运输公司编码")
    @JsonProperty("transport_company_code")
    private String transportCompanyCode;

    @ApiModelProperty("运输公司")
    @JsonProperty("transport_company")
    private String transportCompany;

    @ApiModelProperty("运输单号")
    @JsonProperty("transport_number")
    private String transportNumber;

    @ApiModelProperty("退货原因编码")
    @JsonProperty("return_reason_code")
    private String returnReasonCode;

    @ApiModelProperty("退货原因描述")
    @JsonProperty("return_reason_content")
    private String returnReasonContent;

    @ApiModelProperty("备注")
    @JsonProperty("remake")
    private String remake;

    @ApiModelProperty("商品列表")
    @JsonProperty("item_reqVO_list")
    List<ReturnOrderInfoItemReqVO> itemReqVOList;
}
