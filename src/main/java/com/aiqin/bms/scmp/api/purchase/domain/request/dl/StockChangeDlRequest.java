package com.aiqin.bms.scmp.api.purchase.domain.request.dl;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@ApiModel("DL- 库存变动推送")
public class StockChangeDlRequest {

    @NotNull(message = "单据不能为空")
    @ApiModelProperty(value="单据号")
    @JsonProperty("order_code")
    private String orderCode;

    @ApiModelProperty(value="单据类型 1.销售 2.退货 3.调拨 4.移库 5.损益 6.预订单")
    @JsonProperty("order_type")
    private Integer orderType;

    @ApiModelProperty(value="操作类型 1. 加库存  2.减库存")
    @JsonProperty("operation_type")
    private Integer operationType;

    @ApiModelProperty(value="商品信息")
    @JsonProperty("product_list")
    private List<ProductRequest> productList;

}
