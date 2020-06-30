package com.aiqin.bms.scmp.api.purchase.domain.request.dl;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@ApiModel("DL- 回传单据主参数")
public class EchoOrderRequest {

    @ApiModelProperty(value="单据单号")
    @JsonProperty("order_code")
    private String orderCode;

    @ApiModelProperty(value="操作时间")
    @JsonProperty("operation_time")
    private Date operationTime;

    @ApiModelProperty(value="操作类型 1.采购 2.退供 3.销售 4.退货")
    @JsonProperty("operation_type")
    private Integer operationType;

    @ApiModelProperty(value="操作人编码")
    @JsonProperty("operation_code")
    private String operationCode;

    @ApiModelProperty(value="操作人名称")
    @JsonProperty("operation_name")
    private String operationName;

    @ApiModelProperty(value="商品信息")
    @JsonProperty("product_list")
    private List<ProductRequest> productList;

}
