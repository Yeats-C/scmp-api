package com.aiqin.bms.scmp.api.purchase.domain.request;

import com.aiqin.bms.scmp.api.purchase.domain.FileRecord;
import com.aiqin.bms.scmp.api.purchase.domain.PurchaseOrder;
import com.aiqin.bms.scmp.api.purchase.domain.PurchaseOrderDetails;
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

    @ApiModelProperty(value="采购单详情")
    @JsonProperty("order_details")
    private PurchaseOrderDetails orderDetails;

    @ApiModelProperty(value="商品列表")
    @JsonProperty("product_list")
    private List<PurchaseOrderProduct> productList;

    @ApiModelProperty(value="文件信息")
    @JsonProperty("file_list")
    private List<FileRecord> fileList;
}
