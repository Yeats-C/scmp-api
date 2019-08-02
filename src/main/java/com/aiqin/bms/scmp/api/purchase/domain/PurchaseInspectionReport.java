package com.aiqin.bms.scmp.api.purchase.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel
@Data
public class PurchaseInspectionReport {
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty(value="sku编码")
    @JsonProperty("sku_code")
    private String skuCode;

    @ApiModelProperty(value="sku名称")
    @JsonProperty("sku_name")
    private String skuName;

    @ApiModelProperty(value="采购单id")
    @JsonProperty("purchase_order_id")
    private String purchaseOrderId;

    @ApiModelProperty(value="质检报告名称")
    @JsonProperty("inspection_report_name")
    private String inspectionReportName;

    @ApiModelProperty(value="质检报告路径")
    @JsonProperty("inspection_report_path")
    private String inspectionReportPath;

    @ApiModelProperty(value="生产日期")
    @JsonProperty("production_date")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date productionDate;

    @ApiModelProperty(value="0. 禁用  1.启用")
    @JsonProperty("status")
    private Integer status;

    @ApiModelProperty(value="上传时间")
    @JsonProperty("create_time")
    private Date createTime;

    @ApiModelProperty(value="创建人id")
    @JsonProperty("create_by_id")
    private String createById;

    @ApiModelProperty(value="创建人名称")
    @JsonProperty("create_by_name")
    private String createByName;

    @ApiModelProperty(value="修改时间")
    @JsonProperty("update_time")
    private Date updateTime;

    @ApiModelProperty(value="修改人")
    @JsonProperty("update_by_id")
    private String updateById;

    @ApiModelProperty(value="修改人名称")
    @JsonProperty("update_by_name")
    private String updateByName;
}