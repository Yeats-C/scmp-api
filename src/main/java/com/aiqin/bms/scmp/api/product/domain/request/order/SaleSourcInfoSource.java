package com.aiqin.bms.scmp.api.product.domain.request.order;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@ApiModel(value = "销售出库推送源数据（wms）")
@Data
public class SaleSourcInfoSource implements Serializable {

    @ApiModelProperty(value="配货单号")
    @JsonProperty("order_code")
    private String orderCode;

    @ApiModelProperty(value="订单类型 EBUS:线上订单 OFFLINE:线下订单 SPECIAL:特殊订单 EBUS：线上订单 OFFLINE：线下订单 SPECIAL：特殊订单 EBUS：线上订单 OFFLINE：线下订单 SPECIAL：特殊订单")
    @JsonProperty("bity")
    private String bity;

    @ApiModelProperty(value="仓库编码")
    @JsonProperty("warehouse_code")
    private String warehouseCode;

    @ApiModelProperty(value="来源类型")
    @JsonProperty("from_type")
    private String fromType;

    @ApiModelProperty(value="来源单号")
    @JsonProperty("from_code")
    private String fromCode;

    @ApiModelProperty(value="来源平台编码")
    @JsonProperty("platform_code")
    private String platformCode;

    @ApiModelProperty(value="来源平台名称")
    @JsonProperty("platform_name")
    private String platformName;

    @ApiModelProperty(value="是否紧急(0普通,1紧急)")
    @JsonProperty("is_urgency")
    private String isUrgency;

    @ApiModelProperty(value="下单时间 (yyyy-MM-dd HH:mm:ss)", example = "2001-01-01 01:01:01")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("down_date")
    private Date downDate;

    @ApiModelProperty(value="支付时间(yyyy-MM-dd HH:mm:ss)", example = "2001-01-01 01:01:01")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("pay_time")
    private Date payTime;

    @ApiModelProperty(value="审核时间（即生成通知单的时间）(yyyy-MM-dd HH:mm:ss)", example = "2001-01-01 01:01:01")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("audit_time")
    private Date auditTime;

    @ApiModelProperty(value="是否货到付款(true/false)")
    @JsonProperty("is_delivery_pay")
    private Boolean isDeliveryPay;

    @ApiModelProperty(value="店铺编码")
    @JsonProperty("shop_code")
    private String shopCode;

    @ApiModelProperty(value="店铺名称")
    @JsonProperty("shop_name")
    private String shopName;

    @ApiModelProperty(value="会员昵称")
    @JsonProperty("bunick")
    private String bunick;

    @ApiModelProperty(value="收货人姓名")
    @JsonProperty("consignee")
    private String consignee;

    @ApiModelProperty(value="省名称")
    @JsonProperty("province_name")
    private String provinceName;

    @ApiModelProperty(value="市名称")
    @JsonProperty("city_name")
    private String cityName;

    @ApiModelProperty(value="区名称")
    @JsonProperty("area_name")
    private String areaName;

    @ApiModelProperty(value="收件地址")
    @JsonProperty("address")
    private String address;

    @ApiModelProperty(value="移动电话")
    @JsonProperty("mobile")
    private String mobile;

    @ApiModelProperty(value="固定电话")
    @JsonProperty("tel")
    private String tel;

    @ApiModelProperty(value="订单金额")
    @JsonProperty("order_price")
    private Double orderPrice;

    @ApiModelProperty(value="应收金额")
    @JsonProperty("amount_receivable")
    private Double amountReceivable;

    @ApiModelProperty(value="是否需要发票true/false")
    @JsonProperty("isInvoice")
    private Boolean isinvoice;

    @ApiModelProperty(value="发票抬头")
    @JsonProperty("invoice_name")
    private String invoiceName;

    @ApiModelProperty(value="货主")
    @JsonProperty("goods_owner")
    private String goodsOwner;

    @ApiModelProperty(value="备注")
    @JsonProperty("remark")
    private String remark;

    @ApiModelProperty(value = "备用字段1")
    @JsonProperty("gwf1")
    private String gwf1;

    @ApiModelProperty(value = "备用字段2")
    @JsonProperty("gwf2")
    private String gwf2;

    @ApiModelProperty(value = "备用字段3")
    @JsonProperty("gwf3")
    private String gwf3;

    @ApiModelProperty(value = "备用字段4")
    @JsonProperty("gwf4")
    private String gwf4;

    @ApiModelProperty(value = "备用字段5")
    @JsonProperty("gwf5")
    private String gwf5;


    @ApiModelProperty(value="销售出库推送源数据明细")
    @JsonProperty("sale_outbound_detailed_source")
    private List<SaleOutboundDetailedSource> saleOutboundDetailedSource;
}
