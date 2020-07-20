package com.aiqin.bms.scmp.api.product.domain.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel
@Data
public class StockMonthBatch {
    @ApiModelProperty(value="")
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty(value="sku编码")
    @JsonProperty("sku_code")
    private String skuCode;

    @ApiModelProperty(value="库房编码")
    @JsonProperty("warehouse_code")
    private String warehouseCode;

    @ApiModelProperty(value="批次号")
    @JsonProperty("batch_code")
    private String batchCode;

    @ApiModelProperty(value="批次数量")
    @JsonProperty("batch_count")
    private Long batchCount;

    @ApiModelProperty(value="创建时间")
    @JsonProperty("create_time")
    private Date createTime;

    @ApiModelProperty(value="修改时间")
    @JsonProperty("update_time")
    private Date updateTime;

    @ApiModelProperty(value="同步时间戳")
    @JsonProperty("synchr_time")
    private Long synchrTime;

    @ApiModelProperty(value="wms类型")
    @JsonProperty("wms_type")
    private Integer wmsType;

    @ApiModelProperty("批次行号")
    @JsonProperty("line_code")
    private Long lineCode;

}