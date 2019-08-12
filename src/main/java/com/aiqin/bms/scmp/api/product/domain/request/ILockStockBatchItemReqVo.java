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
    private Integer documentType;

    @ApiModelProperty("单据号")
    @JsonProperty(value = "document_num")
    private String documentNum;

    @ApiModelProperty("来源单据类型")
    @JsonProperty(value = "source_document_type")
    private Integer sourceDocumentType;

    @ApiModelProperty("来源单据号")
    @JsonProperty(value = "source_document_num")
    private String sourceDocumentNum;

    @ApiModelProperty("商品备注")
    @JsonProperty(value = "remark")
    private String remark;

    @ApiModelProperty("更新人code")
    @JsonProperty(value = "update_by_code")
    private String updateByCode;

    @ApiModelProperty("更新人名称")
    @JsonProperty(value = "update_by_name")
    private String updateByName;

    @ApiModelProperty("操作类型")
    @JsonProperty(value = "operator")
    private String operator;
}
