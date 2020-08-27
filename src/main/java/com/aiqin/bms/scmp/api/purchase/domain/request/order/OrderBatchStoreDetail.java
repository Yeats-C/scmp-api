package com.aiqin.bms.scmp.api.purchase.domain.request.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;

@Data
public class OrderBatchStoreDetail implements Serializable {

    @ApiModelProperty(value = "行号")
    @JsonProperty("line_code")
    private Long lineCode;

    @ApiModelProperty(value = "SKU编码")
    @JsonProperty("sku_code")
    @NotEmpty(message = "SKU编码不能为空")
    private String skuCode;

    @ApiModelProperty(value = "SKU名称")
    @JsonProperty("sku_name")
    @NotEmpty(message = "SKU名称不能为空")
    private String skuName;

    @ApiModelProperty(value = "批次编号")
    @JsonProperty("batch_code")
    @NotEmpty(message = "批次编号不能为空")
    private String batchCode;

    @ApiModelProperty(value="批次编码")
    @JsonProperty("batch_info_code")
    private String batchInfoCode;

    @ApiModelProperty(value="生产日期")
    @JsonProperty("product_date")
    private String productDate;

    @ApiModelProperty("销售数量")
    @JsonProperty("total_count")
    private Long totalCount;

    @ApiModelProperty("实际销售数量")
    @JsonProperty("actual_total_count")
    private Long actualTotalCount;
}
