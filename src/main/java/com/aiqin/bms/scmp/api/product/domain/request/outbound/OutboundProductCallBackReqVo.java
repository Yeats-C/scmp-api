package com.aiqin.bms.scmp.api.product.domain.request.outbound;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Classname: OutboundProductCallBackReqVo
 * 描述: 出库单skuWMS回调申请实体
 */
@Data
@ApiModel("出库单skuWMS回调申请实体")
public class OutboundProductCallBackReqVo {

    @ApiModelProperty("sku编号")
    @JsonProperty(value = "sku_code")
    private String skuCode;

    @ApiModelProperty("sku名称")
    @JsonProperty(value = "sku_name")
    private String skuName;

    @ApiModelProperty("实际最小单位数量")
    @JsonProperty(value = "actual_total_count")
    private Long actualTotalCount;

    @ApiModelProperty("行号")
    @JsonProperty(value = "line_code")
    private Long lineCode;

    @ApiModelProperty(value="备注")
    @JsonProperty(value = "remark")
    private String remark;

}
