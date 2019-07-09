package com.aiqin.bms.scmp.api.purchase.domain.response;

import com.aiqin.bms.scmp.api.purchase.domain.PurchaseApplyProduct;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: zhao shuai
 * @create: 2019-07-06
 **/
@Data
public class PurchaseImportResponse extends PurchaseApplyProduct {

    @ApiModelProperty("采购数量")
    @JsonProperty("purchase_count")
    private String purchaseCount;

    @ApiModelProperty("实物返数量")
    @JsonProperty("return_count")
    private String returnCount;

    @ApiModelProperty(value="实物返件数（整数）")
    @JsonProperty("return_whole")
    private Integer returnWhole;

    @ApiModelProperty(value="实物返件数（零数）")
    @JsonProperty("return_single")
    private Integer returnSingle;

    @ApiModelProperty(value="单品数量")
    @JsonProperty("single_count")
    private Integer singleCount;

    @ApiModelProperty(value="含税总价")
    @JsonProperty("product_total_amount")
    private Integer productTotalAmount;

    @ApiModelProperty(value="税率")
    @JsonProperty("tax_rate")
    private Integer taxRate;
}
