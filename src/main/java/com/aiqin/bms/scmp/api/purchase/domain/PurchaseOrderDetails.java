package com.aiqin.bms.scmp.api.purchase.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel
@Data
public class PurchaseOrderDetails {
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty(value="采购申请详情id")
    @JsonProperty("purchase_details_id")
    private String purchaseDetailsId;

    @ApiModelProperty(value="采购单id")
    @JsonProperty("purchase_order_id")
    private String purchaseOrderId;

    @ApiModelProperty(value="采购单编码")
    @JsonProperty("purchase_order_code")
    private String purchaseOrderCode;

    @ApiModelProperty(value="账户编码")
    @JsonProperty("supplier_code")
    private String supplierCode;

    @ApiModelProperty(value="账户名称（供应商名称）")
    @JsonProperty("supplier_name")
    private String supplierName;

    @ApiModelProperty(value="合同编码")
    @JsonProperty("contract_code")
    private String contractCode;

    @ApiModelProperty(value="合同名称")
    @JsonProperty("contract_name")
    private String contractName;

    @ApiModelProperty(value="负责人")
    @JsonProperty("charge_person")
    private String chargePerson;

    @ApiModelProperty(value="联系人")
    @JsonProperty("contact_person")
    private String contactPerson;

    @ApiModelProperty(value="电话")
    @JsonProperty("mobile")
    private String mobile;

    @ApiModelProperty(value="预计到货时间")
    @JsonProperty("expect_arrival_time")
    private Date expectArrivalTime;

    @ApiModelProperty(value="有效期")
    @JsonProperty("valid_time")
    private Integer validTime;

    @ApiModelProperty(value="直属上级编码")
    @JsonProperty("direct_supervisor_code")
    private String directSupervisorCode;

    @ApiModelProperty(value="直属上级名称")
    @JsonProperty("direct_supervisor_name")
    private String directSupervisorName;

    @ApiModelProperty(value="付款方式  0.预付款  1.货到付款 2.月结 3.实销实结")
    @JsonProperty("payment_type")
    private Boolean paymentType;

    @ApiModelProperty(value="预付款金额")
    @JsonProperty("advance_payment")
    private Integer advancePayment;

    @ApiModelProperty(value="到付金额")
    @JsonProperty("amount_payable")
    private Integer amountPayable;

    @ApiModelProperty(value="月结金额")
    @JsonProperty("month_amount")
    private Integer monthAmount;

    @ApiModelProperty(value="到付付款期")
    @JsonProperty("payable_time")
    private Date payableTime;

    @ApiModelProperty(value="月结付款期")
    @JsonProperty("month_time")
    private Date monthTime;

    @ApiModelProperty(value="收货地(仓库)")
    @JsonProperty("receipt")
    private String receipt;

    @ApiModelProperty(value="收货具体地址")
    @JsonProperty("receipt_address")
    private String receiptAddress;

    @ApiModelProperty(value="发货地(供应商)")
    @JsonProperty("delivery")
    private String delivery;

    @ApiModelProperty(value="发货具体地址")
    @JsonProperty("delivery_address")
    private String deliveryAddress;

    @ApiModelProperty(value="备注")
    @JsonProperty("remark")
    private String remark;

    @ApiModelProperty(value="0. 启用 1.禁用  ")
    @JsonProperty("details_status")
    private Boolean detailsStatus;

    @ApiModelProperty(value="创建时间")
    @JsonProperty("create_time")
    private Date createTime;

    @ApiModelProperty(value="修改时间")
    @JsonProperty("update_time")
    private Date updateTime;

    @ApiModelProperty(value="创建人")
    @JsonProperty("create_by")
    private String createBy;

    @ApiModelProperty(value="修改人2")
    @JsonProperty("update_by")
    private String updateBy;
}