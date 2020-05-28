package com.aiqin.bms.scmp.api.product.domain.request.outbound;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @program: scmp-api
 * @author: zhao shuai
 * @create: 2019-12-27
 **/
@Data
public class OutboundCallBackBatchRequest {

    @ApiModelProperty("sku编号")
    @JsonProperty("sku_code")
    private String skuCode;

    @ApiModelProperty("sku名称")
    @JsonProperty("sku_name")
    private String skuName;

    @ApiModelProperty("批次号")
    @JsonProperty("batch_code")
    private String batchCode;

    @ApiModelProperty(value="批次编号")
    @JsonProperty("batch_info_code")
    private String batchInfoCode;

    @ApiModelProperty(value="生产日期")
    @JsonProperty("product_date")
 //   @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private String productDate;

    @ApiModelProperty("销售数量")
    @JsonProperty("total_count")
    private Long totalCount;

    @ApiModelProperty("实际销售数量")
    @JsonProperty("actual_total_count")
    private Long actualTotalCount;

    @ApiModelProperty("行号")
    @JsonProperty("line_code")
    private Long lineCode;

    @ApiModelProperty("备注")
    @JsonProperty("batch_remark")
    private String batchRemark;
}
