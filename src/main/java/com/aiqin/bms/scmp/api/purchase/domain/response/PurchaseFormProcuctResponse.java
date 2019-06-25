package com.aiqin.bms.scmp.api.purchase.domain.response;

import com.aiqin.bms.scmp.api.purchase.domain.PurchaseOrderProduct;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author: zhao shuai
 * @create: 2019-06-24
 **/
@Data
public class PurchaseFormProcuctResponse {

    @ApiModelProperty(value="商品列表")
    @JsonProperty("product_list")
    private List<PurchaseApplyDetailResponse> productList;

    @ApiModelProperty(value="采购申请单商品列表")
    @JsonProperty("apply_list")
    private List<PurchaseFormResponse> purchaseFormResponseList;
}
