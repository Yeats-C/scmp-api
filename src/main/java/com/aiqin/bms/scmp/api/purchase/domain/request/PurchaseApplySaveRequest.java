package com.aiqin.bms.scmp.api.purchase.domain.request;

import com.aiqin.bms.scmp.api.purchase.domain.FileRecord;
import com.aiqin.bms.scmp.api.purchase.domain.PurchaseApply;
import com.aiqin.bms.scmp.api.purchase.domain.PurchaseApplyProduct;
import com.aiqin.bms.scmp.api.purchase.domain.PurchaseApplyTransportCenter;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class PurchaseApplySaveRequest {

    @ApiModelProperty(value="采购单的通用信息")
    @JsonProperty("purchase_apply")
    private PurchaseApply purchaseApply;

    @ApiModelProperty(value="采购单的仓库信息")
    @JsonProperty("purchase_transport_list")
    private List<PurchaseApplyTransportCenter> purchaseTransportList;

    @ApiModelProperty(value="采购单的商品信息")
    @JsonProperty("product_list")
    private List<PurchaseApplyProduct> productList;

    @ApiModelProperty(value="文件信息")
    @JsonProperty("file_list")
    private List<FileRecord> fileList;

    @ApiModelProperty(value="保存方式 0.保存 1.提交审核 2.编辑")
    @JsonProperty("save_mode")
    private Integer saveMode;
}
