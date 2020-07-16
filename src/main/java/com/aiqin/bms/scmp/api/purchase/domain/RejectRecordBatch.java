package com.aiqin.bms.scmp.api.purchase.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@ApiModel
@Data
public class RejectRecordBatch {

    @JsonProperty("id")
    private Long id;

    @ApiModelProperty(value="退供单号")
    @JsonProperty("reject_record_code")
    private String rejectRecordCode;

    @ApiModelProperty(value="批次号/wms批次号")
    @JsonProperty("batch_code")
    private String batchCode;

    @ApiModelProperty(value="批次编号")
    @JsonProperty("batch_info_code")
    private String batchInfoCode;

    @ApiModelProperty(value="sku编码")
    @JsonProperty("sku_code")
    private String skuCode;

    @ApiModelProperty(value="SKU名称")
    @JsonProperty("sku_name")
    private String skuName;

    @ApiModelProperty(value="供应商编码")
    @JsonProperty("supplier_code")
    private String supplierCode;

    @ApiModelProperty(value="供应商名称")
    @JsonProperty("supplier_name")
    private String supplierName;

    @ApiModelProperty(value="批次备注")
    @JsonProperty("batch_remark")
    private String batchRemark;

    @ApiModelProperty(value="生产日期")
    @JsonProperty("product_date")
    private String productDate;

    @ApiModelProperty(value="过期日期")
    @JsonProperty("be_overdue_date")
    private String beOverdueDate;

    @ApiModelProperty(value="最小单位数量")
    @JsonProperty("total_count")
    private Long totalCount;

    @ApiModelProperty(value="实际最小单位数量")
    @JsonProperty("actual_total_count")
    private Long actualTotalCount;

    @ApiModelProperty(value="批次含税采购价")
    @JsonProperty("purchase_price")
    private BigDecimal purchasePrice;

    @ApiModelProperty(value="库位号")
    @JsonProperty("location_code")
    private String locationCode;

    @ApiModelProperty(value="行号")
    @JsonProperty("line_code")
    private Integer lineCode;

    @ApiModelProperty(value="创建时间")
    @JsonProperty("create_time")
    private Date createTime;

    @ApiModelProperty(value="修改时间")
    @JsonProperty("update_time")
    private Date updateTime;

}