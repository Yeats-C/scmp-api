package com.aiqin.bms.scmp.api.product.domain.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel
@Data
public class StockMonthBatchFlow {
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

    @ApiModelProperty(value="日期类型 1.月份 2.日期")
    @JsonProperty("day_type")
    private Boolean dayType;

    @ApiModelProperty(value="修改前的批次数量")
    @JsonProperty("before_batch_count")
    private Long beforeBatchCount;

    @ApiModelProperty(value="修改后的批次数量")
    @JsonProperty("after_batch_count")
    private Long afterBatchCount;

    @ApiModelProperty(value="创建时间")
    @JsonProperty("create_time")
    private Date createTime;

    @ApiModelProperty(value="修改时间")
    @JsonProperty("update_time")
    private Date updateTime;

}