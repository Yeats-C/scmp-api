package com.aiqin.bms.scmp.api.product.domain.response.stock;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("库存流水respVo")
@Data
public class StockFlowRespVo {

    @ApiModelProperty("主键")
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty("锁状态")
    @JsonProperty(value = "lock_status")
    private Integer lockStatus;

    @ApiModelProperty("变化数")
    @JsonProperty(value = "change_num")
    private Long changeNum;

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

    @ApiModelProperty("操作时间")
    @JsonProperty(value = "update_time")
    private String updateTime;

    @ApiModelProperty("操作人")
    @JsonProperty(value = "update_by")
    private String updateBy;

    @ApiModelProperty("商品备注")
    @JsonProperty(value = "remark")
    private String remark;
}
