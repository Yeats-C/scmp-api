package com.aiqin.bms.scmp.api.purchase.domain.request;

import com.aiqin.bms.scmp.api.purchase.domain.PurchaseOrder;
import com.aiqin.bms.scmp.api.purchase.domain.PurchaseOrderProduct;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author: zhao shuai
 * @create: 2019-06-25
 **/
@Data
public class PurchaseOrderRequest {

    @ApiModelProperty(value="采购单")
    @JsonProperty("purchase_order")
    private PurchaseOrder purchaseOrder;

    @ApiModelProperty(value="采购单商品")
    @JsonProperty("product_list")
    private List<PurchaseOrderProduct> productList;

}
