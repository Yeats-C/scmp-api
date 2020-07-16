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
public class RejectApplyRecordTransportCenter {

    @JsonProperty("id")
    private Long id;

    @ApiModelProperty(value="退供申请单编码")
    @JsonProperty("reject_apply_record_code")
    private String rejectApplyRecordCode;

    @ApiModelProperty(value="退供申请单名称（审批名称）")
    @JsonProperty("reject_apply_record_name")
    private String rejectApplyRecordName;

    @ApiModelProperty(value="仓库编码")
    @JsonProperty("transport_center_code")
    private String transportCenterCode;

    @ApiModelProperty(value="仓库名称")
    @JsonProperty("transport_center_name")
    private String transportCenterName;

    @ApiModelProperty(value="库房编码")
    @JsonProperty("warehouse_code")
    private String warehouseCode;

    @ApiModelProperty(value="库房名称")
    @JsonProperty("warehouse_name")
    private String warehouseName;

    @ApiModelProperty(value="预计发货日期")
    @JsonProperty("pre_deliver_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date preDeliverTime;

    @ApiModelProperty(value="有效期")
    @JsonProperty("valid_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date validTime;

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

    @ApiModelProperty(value="创建时间")
    @JsonProperty("create_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty(value="修改时间")
    @JsonProperty("update_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

}