package com.aiqin.bms.scmp.api.product.domain.request.inbound;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("入库单回调请求实体")
public class InboundCallBackRequest {

    @ApiModelProperty("公司编码")
    @JsonProperty(value = "company_code")
    private String companyCode;

    @ApiModelProperty("公司名称")
    @JsonProperty(value = "company_name")
    private String companyName;

    @ApiModelProperty("入库单编号")
    @JsonProperty(value = "inbound_oder_code")
    private String inboundOderCode;

    @ApiModelProperty("入库类型编码 1.采购 2.调拨 3.退货  4.移库")
    @JsonProperty(value = "inbound_type_code")
    private Integer inboundTypeCode;

    @ApiModelProperty("入库类型名称")
    @JsonProperty(value = "inbound_type_name")
    private String inboundTypeName;

    @ApiModelProperty(value="备注")
    private String remark;

    @ApiModelProperty("操作人")
    @JsonProperty(value = "operator_id")
    private String operatorId;

    @ApiModelProperty("操作人名称")
    @JsonProperty(value = "operator_name")
    private String operatorName;

    @ApiModelProperty("商品列表")
    @JsonProperty(value = "product_list")
    private List<InboundProductCallBackRequest> productList;

    @ApiModelProperty("批次列表")
    @JsonProperty(value = "batch_list")
    private List<InboundBatchCallBackRequest> batchList;

}
