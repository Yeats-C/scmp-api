package com.aiqin.bms.scmp.api.purchase.domain.request.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * wms回传订单批次商品明细行实体
 */
@Data
public class WmsOrderBatchItem {

    @ApiModelProperty(value = "行号")
    @JsonProperty("line_code")
    private String lineCode;

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
    private String actualTotalCount;

}
