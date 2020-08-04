package com.aiqin.bms.scmp.api.product.domain.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel
@Data
public class StockFlowFail {
    @ApiModelProperty(value="")
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty(value="流水编码")
    @JsonProperty("flow_code")
    private String flowCode;

    @ApiModelProperty(value="sku编码")
    @JsonProperty("sku_code")
    private String skuCode;

    @ApiModelProperty(value="SKU名称")
    @JsonProperty("sku_name")
    private String skuName;

    @ApiModelProperty(value="操作类型1.锁定库存 2.减库存并解锁 3.解锁库存. 4.减库存 5.加并锁定库存 6.加库存 7.加在途 8.减在途 9.锁转移(锁定库存移入/移出)")
    @JsonProperty("operation_type")
    private Integer operationType;

    @ApiModelProperty(value="来源单据号")
    @JsonProperty("source_document_code")
    private String sourceDocumentCode;

    @ApiModelProperty(value="来源单据类型 0出库 1入库 2退供 3采购 4调拨 5退货 6移库 7监管仓入库 8报废 9订单 10监管仓出库 11损溢")
    @JsonProperty("source_document_type")
    private Integer sourceDocumentType;

    @ApiModelProperty(value="修改数")
    @JsonProperty("change_count")
    private Long changeCount;

    @ApiModelProperty(value="锁定变更数")
    @JsonProperty("lock_count")
    private Long lockCount;

    @ApiModelProperty(value="库存类型 1 库存 2 批次库存")
    @JsonProperty("stock_type")
    private Integer stockType;

    @ApiModelProperty(value="创建时间")
    @JsonProperty("create_time")
    private Date createTime;
    
}