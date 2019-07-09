package com.aiqin.bms.scmp.api.purchase.domain.response.purchase;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: zhao shuai
 * @create: 2019-07-02
 **/
@Data
public class PurchaseCountAmountResponse {

    @ApiModelProperty(value="商品数量")
    @JsonProperty("product_count")
    private Integer productCount;

    @ApiModelProperty(value="单品数量")
    @JsonProperty("single_count")
    private Integer singleCount;

    @ApiModelProperty(value="实物返数量")
    @JsonProperty("return_count")
    private Integer returnCount;

    @ApiModelProperty(value="未税采购金额")
    @JsonProperty("not_tax_amount")
    private Integer notTaxAmount;

    @ApiModelProperty(value="含税采购金额")
    @JsonProperty("tax_amount")
    private Integer taxAmount;

    @ApiModelProperty(value="实物返金额")
    @JsonProperty("return_amount")
    private Integer returnAmount;
}
