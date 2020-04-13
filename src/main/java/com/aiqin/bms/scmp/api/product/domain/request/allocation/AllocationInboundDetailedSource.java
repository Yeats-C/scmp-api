package com.aiqin.bms.scmp.api.product.domain.request.allocation;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@ApiModel(value = "调拨出入库推送源数据明细")
@Data
public class AllocationInboundDetailedSource implements Serializable {

    /****/
    @ApiModelProperty(value="Sku编码")
    @JsonProperty("sku")
    private String sku;

    @ApiModelProperty(value="调拨数量")
    @JsonProperty("qty")
    private Integer qty;

    @ApiModelProperty(value="商品生产日期 (yyyy-MM-dd)", example = "2001-01-01")
    @JSONField(format = "yyyy-MM-dd")
    @JsonProperty("product_date")
    private Date productDate;

    @ApiModelProperty(value="商品过期日期(yyyy-MM-dd)", example = "2001-01-01")
    @JSONField(format = "yyyy-MM-dd")
    @JsonProperty("expire_date")
    private Date expireDate;

    @ApiModelProperty(value="生产批号")
    @JsonProperty("produce_code")
    private String produceCode;

    @ApiModelProperty(value="批次编码")
    @JsonProperty("batchCode")
    private String batchcode;

    @ApiModelProperty(value = "备用字段1 Sku行号")
    @JsonProperty("gwf1")
    private String gwf1;

    @ApiModelProperty(value = "备用字段2")
    @JsonProperty("gwf2")
    private String gwf2;

    @ApiModelProperty(value = "备用字段3")
    @JsonProperty("gwf3")
    private String gwf3;

    @ApiModelProperty(value = "备用字段4")
    @JsonProperty("gwf4")
    private String gwf4;

    @ApiModelProperty(value = "备用字段5")
    @JsonProperty("gwf5")
    private String gwf5;

    /**加上的**/
    /*@ApiModelProperty(value="Sku行号")
    @JsonProperty("line_num")
    private String lineNum;*/
}
