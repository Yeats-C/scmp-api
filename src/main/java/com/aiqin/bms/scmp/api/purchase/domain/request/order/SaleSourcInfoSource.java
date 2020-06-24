package com.aiqin.bms.scmp.api.purchase.domain.request.order;

import com.aiqin.bms.scmp.api.purchase.domain.pojo.order.OrderInfoItemProductBatch;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

@ApiModel(value = "销售出库推送源数据（wms）")
@Data
@JsonInclude(JsonInclude.Include.ALWAYS)
public class SaleSourcInfoSource implements Serializable {

    @ApiModelProperty(value = "销售单号")
    @JsonProperty("order_store_code")
    private String orderStoreCode;

    @NotBlank(message = "库房编码不能为空")
    @ApiModelProperty(value = "库房编码")
    @JsonProperty("warehouse_code")
    private String warehouseCode;

    @ApiModelProperty(value = "库房名称")
    @JsonProperty("warehouse_name")
    private String warehouseName;

    //@ApiModelProperty(value = "销售单创建时间 (yyyy-MM-dd HH:mm:ss)", example = "2001-01-01 01:01:01")
    @JsonProperty("create_time")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private String createTime;

    @ApiModelProperty(value = "销售单支付时间")
    @JsonProperty("pay_time")
    private String payTime;

    @ApiModelProperty(value = "客户编号")
    @JsonProperty("customer_code")
    private String customerCode;

    @ApiModelProperty(value = "客户名称")
    @JsonProperty("customer_name")
    private String customerName;

    @ApiModelProperty(value = "收货区域-省编号")
    @JsonProperty("province_id")
    private String provinceId;

    @NotBlank(message = "收货区域-省名称不能为空")
    @ApiModelProperty(value = "收货区域-省名称")
    @JsonProperty("province_name")
    private String provinceName;

    @ApiModelProperty(value = "收货区域-市编号")
    @JsonProperty("city_id")
    private String cityId;

    @ApiModelProperty(value = "收货区域-市名称")
    @JsonProperty("city_name")
    private String cityName;

    @ApiModelProperty(value = "收货区域-县编号")
    @JsonProperty("district_id")
    private String districtId;

    @ApiModelProperty(value = "收货区域-县名称")
    @JsonProperty("district_name")
    private String districtName;

    @ApiModelProperty(value = "收货详细地址")
    @JsonProperty("receive_address")
    private String receiveAddress;

    @ApiModelProperty(value = "收货人")
    @JsonProperty("receive_person")
    private String receivePerson;

    @ApiModelProperty(value = "收货人手机号")
    @JsonProperty("receive_mobile")
    private String receiveMobile;

    @ApiModelProperty(value = "送货方式编号")
    @JsonProperty("distribution_mode_code")
    private String distributionModeCode;

    @ApiModelProperty(value = "送货方式名称")
    @JsonProperty("distribution_mode_name")
    private String distributionModeName;

    @ApiModelProperty(value = "分销订单金额")
    @JsonProperty("order_amount")
    private String orderAmount;

    @ApiModelProperty(value = "发票")
    @JsonProperty("isInvoice")
    private String isInvoice;

    @ApiModelProperty(value = "发票抬头")
    @JsonProperty("invoice_title")
    private String invoiceTitle;

    @ApiModelProperty(value = "发票税号")
    @JsonProperty("invoice_tax_no")
    private String invoiceTaxNo;

    @ApiModelProperty(value = "发票地址")
    @JsonProperty("invoice_address")
    private String invoiceAddress;

    @ApiModelProperty("发票电话")
    @JsonProperty("invoice_mobile")
    private String invoiceMobile;

    @ApiModelProperty("发票开户银行")
    @JsonProperty("invoice_bank")
    private String invoiceBank;

    @ApiModelProperty("发票银行账号")
    @JsonProperty("invoice_bank_account")
    private String invoiceBankAccount;

    @ApiModelProperty("备注")
    @JsonProperty("remake")
    private String remake;

    @ApiModelProperty("出库单号")
    @JsonProperty("outbound_oder_code")
    private String outboundOderCode;


    @ApiModelProperty(value="销售出库推送源商品数据明细")
    @JsonProperty("detail_list")
    private List<SaleOutboundDetailedSource> detailList;

    @ApiModelProperty(value="销售出库推送源商品批次数据明细")
    @JsonProperty("batch_info")
    private List<BatchWmsInfo> batchInfo;
}
