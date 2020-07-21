package com.aiqin.bms.scmp.api.product.domain.request.profitloss;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("损溢wms回传商品批次表")
@Data
public class ProfitLossBatchWmsReqVo {

    @ApiModelProperty(value = "行号")
    @JsonProperty("line_code")
    private Long lineCode;

    @ApiModelProperty(value = "SKU编号")
    @JsonProperty("sku_code")
    private String skuCode;

    @ApiModelProperty(value = "批次号")
    @JsonProperty("batch_code")
    private String batchCode;

    @ApiModelProperty(value = "生产日期")
    @JsonProperty("product_date")
    private String productDate;

    @ApiModelProperty(value = "过期日期")
    @JsonProperty("be_overdue_data")
    private String beOverdueData;

    @ApiModelProperty(value = "批次备注")
    @JsonProperty("batch_remark")
    private String batchRemark;

    @ApiModelProperty(value = "实际最小单位数量")
    @JsonProperty("actual_total_count")
    private Long actualTotalCount;

    @ApiModelProperty(value = "供应商编码")
    @JsonProperty("supplier_code")
    private String supplierCode;

    @ApiModelProperty(value = "供应商名称")
    @JsonProperty("supplier_name")
    private String supplierName;
}
