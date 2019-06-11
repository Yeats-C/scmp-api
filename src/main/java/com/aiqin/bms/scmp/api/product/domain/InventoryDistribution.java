package com.aiqin.bms.scmp.api.product.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wuyongqiang
 * @description 库存分布对象
 * @date 2018/11/21 16:31
 */
@Data
@ApiModel("库存分布内容")
public class InventoryDistribution {
    @ApiModelProperty(value = "库存类别")
    @JsonProperty("storage_type")
    private Integer storageType;

    @ApiModelProperty(value = "总库存")
    @JsonProperty("total_storage")
    private Integer totalStorage;

    @ApiModelProperty(value = "可售库存")
    @JsonProperty("sellable_storage")
    private Integer sellableStorage;

    @ApiModelProperty(value = "锁库存")
    @JsonProperty("lock_storage")
    private Integer lockStorage;

    @ApiModelProperty(value = "退货仓库存")
    @JsonProperty("return_storage")
    private Integer returnStorage;

    public Integer getStorageType() {
        return storageType;
    }

    public void setStorageType(Integer storageType) {
        this.storageType = storageType;
    }

    public Integer getTotalStorage() {
        return totalStorage;
    }

    public void setTotalStorage(Integer totalStorage) {
        this.totalStorage = totalStorage;
    }

    public Integer getSellableStorage() {
        return sellableStorage;
    }

    public void setSellableStorage(Integer sellableStorage) {
        this.sellableStorage = sellableStorage;
    }

    public Integer getLockStorage() {
        return lockStorage;
    }

    public void setLockStorage(Integer lockStorage) {
        this.lockStorage = lockStorage;
    }

    public Integer getReturnStorage() {
        return returnStorage;
    }

    public void setReturnStorage(Integer returnStorage) {
        this.returnStorage = returnStorage;
    }
}
