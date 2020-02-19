package com.aiqin.bms.scmp.api.purchase.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@ApiModel
@Data
public class PurchaseApplyTransportCenter {
    @ApiModelProperty(value="主键id")
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty(value="采购申请单id")
    @JsonProperty("purchase_apply_id")
    private String purchaseApplyId;

    @ApiModelProperty(value="采购申请单编码")
    @JsonProperty("purchase_apply_code")
    private String purchaseApplyCode;

    @ApiModelProperty(value="采购申请单名称（审批名称）")
    @JsonProperty("purchase_apply_name")
    private String purchaseApplyName;

    @ApiModelProperty(value="仓库编码")
    @JsonProperty("transport_center_code")
    private String transportCenterCode;

    @ApiModelProperty(value="仓库名称")
    @JsonProperty("transport_center_name")
    private String transportCenterName;

    @ApiModelProperty(value="预计到货日期")
    @JsonProperty("pre_arrival_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date preArrivalTime;

    @ApiModelProperty(value="有效期")
    @JsonProperty("valid_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date validTime;

    @ApiModelProperty(value="付款方式 0.预付款 1.货到付款 2.月结 3.实销实结")
    @JsonProperty("payment_mode")
    private Integer paymentMode;

    @ApiModelProperty(value="付款期")
    @JsonProperty("payment_time")
    private Integer paymentTime;

    @ApiModelProperty(value="最小单位数量")
    @JsonProperty("total_count")
    private Long totalCount;

    @ApiModelProperty(value="商品含税金额")
    @JsonProperty("product_tax_amount")
    private BigDecimal productTaxAmount;

    @ApiModelProperty(value="实物返含税金额")
    @JsonProperty("return_tax_amount")
    private BigDecimal returnTaxAmount;

    @ApiModelProperty(value="赠品含税金额")
    @JsonProperty("gift_tax_amount")
    private BigDecimal giftTaxAmount;

    @ApiModelProperty(value="最多入库次数")
    @JsonProperty("inbound_line")
    private Integer inboundLine;

    @ApiModelProperty(value="创建时间")
    @JsonProperty("create_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty(value="修改时间")
    @JsonProperty("update_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @ApiModelProperty(value="创建人编码")
    @JsonProperty("create_by_id")
    private String createById;

    @ApiModelProperty(value="创建人名称")
    @JsonProperty("create_by_name")
    private String createByName;

    @ApiModelProperty(value="修改人编码")
    @JsonProperty("update_by_id")
    private String updateById;

    @ApiModelProperty(value="修改人名称")
    @JsonProperty("update_by_name")
    private String updateByName;

}