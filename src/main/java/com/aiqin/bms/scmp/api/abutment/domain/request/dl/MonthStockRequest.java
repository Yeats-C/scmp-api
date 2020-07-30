package com.aiqin.bms.scmp.api.abutment.domain.request.dl;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("DL- 同步月份批次库存数据")
public class MonthStockRequest {

    @ApiModelProperty(value="sku编码")
    @JsonProperty("sku_code")
    private String skuCode;

    @ApiModelProperty(value="库房编码")
    @JsonProperty("warehouse_code")
    private String warehouseCode;

    @ApiModelProperty(value="库房名称")
    @JsonProperty("warehouse_name")
    private String warehouseName;

    @ApiModelProperty(value="wms库房类型 1 存储 2 退货")
    @JsonProperty("wms_warehouse_type")
    private Integer wmsWarehouseType;

    @ApiModelProperty(value="批次号")
    @JsonProperty("batch_code")
    private String batchCode;

    @ApiModelProperty(value="批次数量")
    @JsonProperty("batch_count")
    private Long batchCount;

    @ApiModelProperty(value="wms类型 1.德邦 2.京东")
    @JsonProperty("wms_type")
    private Integer wmsType;

}
