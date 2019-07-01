package com.aiqin.bms.scmp.api.supplier.domain.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel("质检报告")
@Data
public class ProductSkuInspReportDraft {
    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("sku编码")
    private String skuCode;

    @ApiModelProperty("sku名称")
    private String skuName;

    @ApiModelProperty("生产日期")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date productionDate;

    @ApiModelProperty("质检报告文件路径")
    private String inspectionReportPath;
}