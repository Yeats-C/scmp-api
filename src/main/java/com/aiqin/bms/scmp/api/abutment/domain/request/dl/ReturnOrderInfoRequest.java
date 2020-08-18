package com.aiqin.bms.scmp.api.abutment.domain.request.dl;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Data
@ApiModel("DL- 退货单推送")
public class ReturnOrderInfoRequest extends CommonRequest{

    @NotNull(message = "退货单号不能为空")
    @ApiModelProperty(value="退货单号")
    @JsonProperty("return_order_code")
    private String returnOrderCode;

    @NotNull(message = "退货单id")
    @ApiModelProperty(value="退货单id")
    @JsonProperty("return_order_id")
    private String returnOrderId;

    @NotNull(message = "销售单号不能为空")
    @ApiModelProperty(value="销售单号")
    @JsonProperty("order_code")
    private String orderCode;

    @NotNull(message = "供货方式不能为空")
    @ApiModelProperty("供货方式 1配送、2直送、3货架直送、4采购直送")
    @JsonProperty("order_type")
    private Integer orderType;

    @ApiModelProperty(value="仓库编码")
    @JsonProperty("transport_center_code")
    private String transportCenterCode;

    @ApiModelProperty("仓库名称")
    @JsonProperty("transport_center_name")
    private String transportCenterName;

    @ApiModelProperty(value="支付方式 1.转账")
    @JsonProperty("payment_type")
    private Integer paymentType;

    @ApiModelProperty(value="渠道 :爱亲科技、爱亲母婴、萌贝树、小红马")
    @JsonProperty("channel_name")
    private String channelName;

    @ApiModelProperty(value="订单类型 0.B2B 1.B2C")
    @JsonProperty("order_product_type")
    private Integer orderProductType;

    @ApiModelProperty(value="订单类别")
    @JsonProperty("order_category")
    private Integer orderCategory;

    @ApiModelProperty(value="平台 0.爱亲 1.DL")
    @JsonProperty("platform_type")
    private Integer platformType;

    @ApiModelProperty(value="业务形式 1门店、2批发、3线上业务、4线下业务、5优选业务、6天猫业务、7小红马售后")
    @JsonProperty("business_form")
    private Integer businessForm;

    @ApiModelProperty(value="物流公司编码")
    @JsonProperty("transport_company_code")
    private String transportCompanyCode;

    @ApiModelProperty(value="物流公司名称")
    @JsonProperty("transport_company_name")
    private String transportCompanyName;

    @ApiModelProperty(value="物流单号")
    @JsonProperty("transport_company_number")
    private String transportCompanyNumber;

    @ApiModelProperty(value="退货原因")
    @JsonProperty("return_reason")
    private String returnReason;

    @ApiModelProperty(value="商品数量")
    @JsonProperty("product_count")
    private Long productCount;

    @ApiModelProperty(value="体积")
    @JsonProperty("volume")
    private Long volume;

    @ApiModelProperty(value="重量")
    @JsonProperty("weight")
    private Long weight;

    @ApiModelProperty(value="渠道退货金额")
    @JsonProperty("channel_total_amount")
    private BigDecimal channelTotalAmount;

    @ApiModelProperty(value="分销退货金额")
    @JsonProperty("product_total_amount")
    private BigDecimal productTotalAmount;

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

    @ApiModelProperty(value="发货人")
    @JsonProperty("consignor")
    private String consignor;

    @ApiModelProperty(value="发货人手机号")
    @JsonProperty("consignor_phone")
    private String consignorPhone;

    @ApiModelProperty(value="wms库房类型")
    @JsonProperty("wms_warehouse_type")
    private Integer wmsWarehouseType;

    @ApiModelProperty(value="商品信息")
    @JsonProperty("product_list")
    private List<ProductRequest> productList;

}
