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
    @JsonProperty("matter_single_sum")
    private Integer matterSingleSum;

    @ApiModelProperty(value="未税采购金额")
    @JsonProperty("not_tax_sum")
    private Integer notTaxSum;

    @ApiModelProperty(value="含税采购金额")
    @JsonProperty("product_tax_sum")
    private Integer productTaxSum;

    @ApiModelProperty(value="实物返金额")
    @JsonProperty("matter_tax_sum")
    private Integer matterTaxSum;
}
