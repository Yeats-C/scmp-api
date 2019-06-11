package com.aiqin.bms.scmp.api.product.domain.request;

import com.aiqin.bms.scmp.api.base.PagesRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wuyongqiang
 * @description 库存分布查询参数
 * @date 2018/11/21 16:21
 */
@ApiModel("库存分布查询参数")
@Data
public class InventoryDistributionRequest extends PagesRequest {
    @ApiModelProperty(value = "所属门店")
    @JsonProperty("store_id")
    private String storeId;

    @ApiModelProperty(value = "商品SKU码")
    @JsonProperty("product_sku")
    private String productSku;

    @ApiModelProperty(value = "库存类别")
    @JsonProperty("storage_type")
    private Integer storageType;

    public InventoryDistributionRequest(String storeId, String productSku, Integer storageType) {
        this.storeId = storeId;
        this.productSku = productSku;
        this.storageType = storageType;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getProductSku() {
        return productSku;
    }

    public void setProductSku(String productSku) {
        this.productSku = productSku;
    }

    public Integer getStorageType() {
        return storageType;
    }

    public void setStorageType(Integer storageType) {
        this.storageType = storageType;
    }
}
