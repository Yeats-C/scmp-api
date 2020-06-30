package com.aiqin.bms.scmp.api.purchase.domain.pojo.transport;

import com.aiqin.bms.scmp.api.common.CommonBean;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Transport extends CommonBean {
    @ApiModelProperty("主键")
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty("运输单号(发运单号)")
//    @JsonProperty("transport_code")
    private String transportCode;

    @ApiModelProperty("仓库code")
//    @JsonProperty("transport_center_code")
    private String transportCenterCode;

    @ApiModelProperty("仓库名称")
//    @JsonProperty("transport_center_name")
    private String transportCenterName;

    @ApiModelProperty("客户编码")
//    @JsonProperty("customer_code")
    private String customerCode;

    @ApiModelProperty("客户名称")
//    @JsonProperty("customer_name")
    private String customerName;

    @ApiModelProperty("运输单金额")
//    @JsonProperty("transport_amount")
    private BigDecimal transportAmount;

    @ApiModelProperty("包装数")
//    @JsonProperty("packing_num")
    private Long packingNum;

    @ApiModelProperty("订单商品总件数")
//    @JsonProperty("order_commodity_num")
    private Long orderCommodityNum;

    @ApiModelProperty("运输单状态")
//    @JsonProperty("status")
    private Integer status;

    @ApiModelProperty("物流公司编码")
//    @JsonProperty("logistics_company")
    private String logisticsCompany;

    @ApiModelProperty("物流公司名称")
//    @JsonProperty("logistics_company_name")
    private String logisticsCompanyName;

    @ApiModelProperty("物流单号")
//    @JsonProperty("logistics_number")
    private String logisticsNumber;

    @ApiModelProperty("物流费用")
//    @JsonProperty("logistics_fee")
    private BigDecimal logisticsFee;

    @ApiModelProperty("标准物流费用")
//    @JsonProperty("standard_logistics_fee")
    private BigDecimal standardLogisticsFee;

    @ApiModelProperty("选加物流费用")
//    @JsonProperty("additional_logistics_fee")
    private BigDecimal additionalLogisticsFee;

    @ApiModelProperty("总体积")
//    @JsonProperty("total_volume")
    private BigDecimal totalVolume;

    @ApiModelProperty("总重量")
//    @JsonProperty("total_weight")
    private BigDecimal totalWeight;

    @ApiModelProperty("发货至")
//    @JsonProperty("deliver_to")
    private String deliverTo;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @ApiModelProperty("发运时间")
//    @JsonProperty("transport_time")
    private Date transportTime;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @ApiModelProperty("签收时间")
//    @JsonProperty("sign_time")
    private Date signTime;

    @ApiModelProperty("收货人")
//    @JsonProperty("consignee_name")
    private String consigneeName;

    @ApiModelProperty("收货人手机")
//    @JsonProperty("consignee_phone")
    private String consigneePhone;

    @ApiModelProperty("地址")
//    @JsonProperty("consignee_address")
    private String consigneeAddress;

    @ApiModelProperty("详细地址")
//    @JsonProperty("detailed_address")
    private String detailedAddress;

    @ApiModelProperty("邮编")
    @JsonProperty("zip")
    private String zip;

    @ApiModelProperty("备注")
    @JsonProperty("remark")
    private String remark;

}
