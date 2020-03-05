package com.aiqin.bms.scmp.api.purchase.domain.response;

import com.aiqin.bms.scmp.api.purchase.domain.PurchaseApply;
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
public class PurchaseApplyDeatailResponse extends PurchaseApply {

    @ApiModelProperty(value="采购申请单名称/审批名称")
    @JsonProperty("purchase_apply_name")
    private String purchaseApplyName;

    @ApiModelProperty(value="联系人（供应商的联系人）")
    @JsonProperty("supplier_person")
    private String supplierPerson;

    @ApiModelProperty(value="供应商电话")
    @JsonProperty("supplier_mobile")
    private String supplierMobile;

    @ApiModelProperty(value = "仓库列表")
    @JsonProperty("transport_list")
    private List<PurchaseApplyTransportCenter> transportList;

    @ApiModelProperty(value = "商品详情")
    @JsonProperty("product_list")
    private List<PurchaseApplyProduct> productList;
}
