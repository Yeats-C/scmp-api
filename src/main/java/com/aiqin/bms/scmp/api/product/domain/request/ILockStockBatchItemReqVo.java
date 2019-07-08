package com.aiqin.bms.scmp.api.product.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @className ILockStockBatchItemReqVo
 * @date 2019/1/9 10:46
 * @description 批次库存锁定明细请求VO
 */
@ApiModel("库存锁定明细请求VO")
@Data
public class ILockStockBatchItemReqVo {

    @ApiModelProperty("sku编码")
    @JsonProperty(value = "sku_code")
    private String skuCode;

    @ApiModelProperty("sku名称")
    @JsonProperty(value = "sku_name")
    private String skuName;

    @ApiModelProperty("批次号")
    @JsonProperty(value = "batch_code")
    private String batchCode;

    @ApiModelProperty("数量")
    @JsonProperty(value = "num")
    private Long num;

    @ApiModelProperty("单据类型")
    @JsonProperty(value = "document_type")
    private String documentType;

    @ApiModelProperty("单据号")
    @JsonProperty(value = "document_num")
    private Long documentNum;

    @ApiModelProperty("来源单据类型")
    @JsonProperty(value = "source_document_type")
    private String sourceDocumentType;

    @ApiModelProperty("来源单据号")
    @JsonProperty(value = "source_document_num")
    private Long sourceDocumentNum;

    @ApiModelProperty("操作时间")
    @JsonProperty(value = "operating_time")
    private String operatingTime;

    @ApiModelProperty("操作人")
    @JsonProperty(value = "operating_by")
    private String operatingBy;

    @ApiModelProperty("商品备注")
    @JsonProperty(value = "remark")
    private String remark;

    @ApiModelProperty("操作类型")
    @JsonProperty(value = "operator")
    private String operator;
}
