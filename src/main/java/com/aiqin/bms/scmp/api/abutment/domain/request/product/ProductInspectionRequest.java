package com.aiqin.bms.scmp.api.abutment.domain.request.product;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel("DL- 商品推送质检报告信息")
public class ProductInspectionRequest {

    @ApiModelProperty(value="sku编码")
    @JsonProperty("sku_code")
    private String skuCode;

    @ApiModelProperty(value="生产日期")
    @JsonProperty("product_date")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date productDate;

    @ApiModelProperty(value="质检报告路径")
    @JsonProperty("inspection_report_path")
    private String inspectionReportPath;

}
