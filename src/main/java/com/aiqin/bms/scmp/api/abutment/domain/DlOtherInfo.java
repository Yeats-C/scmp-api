package com.aiqin.bms.scmp.api.abutment.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel
@Data
public class DlOtherInfo {

    @JsonProperty("id")
    private Long id;

    @ApiModelProperty(value="单据编码")
    @JsonProperty("document_code")
    private String documentCode;

    @ApiModelProperty(value="单据类型 1.sku 2.供应商 3.库存 4.门店")
    @JsonProperty("document_type")
    private Integer documentType;

    @ApiModelProperty(value="业务单据类型 0 推送(DL->熙耘)  1.回传(熙耘->DL)")
    @JsonProperty("business_type")
    private Integer businessType;

    @ApiModelProperty(value="库存单据类型")
    @JsonProperty("stock_type")
    private Integer stockType;

    @ApiModelProperty(value="返回状态 0:成功 1:失败")
    @JsonProperty("return_status")
    private Integer returnStatus;

    @ApiModelProperty(value="请求url")
    @JsonProperty("request_url")
    private String requestUrl;

    @ApiModelProperty(value="响应描述")
    @JsonProperty("response_desc")
    private String responseDesc;

    @ApiModelProperty(value="调用次数")
    @JsonProperty("response_count")
    private Integer responseCount;

    @ApiModelProperty(value="创建时间")
    @JsonProperty("create_time")
    private Date createTime;

    @ApiModelProperty(value="修改时间")
    @JsonProperty("update_time")
    private Date updateTime;

    @ApiModelProperty(value="单据推送参数")
    @JsonProperty("document_content")
    private String documentContent;
}