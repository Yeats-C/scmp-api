package com.aiqin.bms.scmp.api.abutment.domain.request.dl;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
@ApiModel("DL- 回传单据主参数")
public class EchoOrderRequest {

    @NotNull(message = "dl -id标识不能为空")
    @ApiModelProperty(value="dl -id标识单号")
    @JsonProperty("order_id")
    private String orderId;

    @NotNull(message = "单号不能为空")
    @ApiModelProperty(value="单据单号")
    @JsonProperty("order_code")
    private String orderCode;

    @ApiModelProperty(value="操作时间")
    @JsonProperty("operation_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
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

    @ApiModelProperty(value="业务形式 1门店、2批发、3线上业务、4线下业务、5优选业务、6天猫业务")
    @JsonProperty("business_form")
    private Integer businessForm;

    @ApiModelProperty(value="商品信息")
    @JsonProperty("product_list")
    private List<ProductRequest> productList;

}
