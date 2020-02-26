package com.aiqin.bms.scmp.api.purchase.domain.response;

import com.aiqin.bms.scmp.api.purchase.domain.PurchaseApplyProduct;
import com.aiqin.bms.scmp.api.purchase.domain.PurchaseApplyTransportCenter;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author: zhao shuai
 * @create: 2020-02-23 09:47
 **/

@Data
public class PurchaseApplyDeatailResponse extends PurchaseApplyProduct {

    @ApiModelProperty(value="采购申请单名称/审批名称")
    @JsonProperty("purchase_apply_name")
    private String purchaseApplyName;

    @ApiModelProperty(value = "仓库列表")
    @JsonProperty("transport_list")
    private List<PurchaseApplyTransportCenter> transportList;

    @ApiModelProperty(value = "商品详情")
    @JsonProperty("product_list")
    private List<PurchaseApplyProduct> productList;
}
