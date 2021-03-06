package com.aiqin.bms.scmp.api.abutment.domain.request.dl;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("对接DL供应商主表")
public class BatchRequest {

    @ApiModelProperty(value="行号")
    @JsonProperty("line_code")
    private Integer lineCode;

    @ApiModelProperty(value="sku编码")
    @JsonProperty("sku_code")
    private String skuCode;

    @ApiModelProperty(value="批次号")
    @JsonProperty("batch_code")
    private String batchCode;

    @ApiModelProperty(value="生产日期")
    @JsonProperty("product_date")
    private String productDate;

    @ApiModelProperty(value="过期日期")
    @JsonProperty("be_overdue_date")
    private String beOverdueDate;

//    @ApiModelProperty(value="批次备注")
//    @JsonProperty("batch_remark")
//    private String batchRemark;

    @ApiModelProperty(value="最小单位数量")
    @JsonProperty("total_count")
    private Long totalCount;

    @ApiModelProperty(value="实际最小单位数量：回传用到")
    @JsonProperty("actual_total_count")
    private Long actualTotalCount;

    @ApiModelProperty(value="库房编码")
    @JsonProperty("warehouse_code")
    private String warehouseCode;

    @ApiModelProperty("库房名称")
    @JsonProperty("warehouse_name")
    private String warehouseName;

    @ApiModelProperty(value="wms库房类型")
    @JsonProperty("wms_warehouse_type")
    private Integer wmsWarehouseType;
}
