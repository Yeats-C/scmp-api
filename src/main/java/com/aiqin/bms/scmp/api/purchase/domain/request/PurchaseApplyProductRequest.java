package com.aiqin.bms.scmp.api.purchase.domain.request;

import com.aiqin.bms.scmp.api.purchase.domain.PurchaseApplyProduct;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author: zhao shuai
 * @create: 2019-06-28
 **/
@Data
public class PurchaseApplyProductRequest {

    @ApiModelProperty(value="采购申请单商品集合")
    @JsonProperty("apply_products")
    List<PurchaseApplyProduct> applyProducts;

    @ApiModelProperty(value="采购申请单id")
    @JsonProperty("purchase_apply_id")
    private String purchaseApplyId;
}
