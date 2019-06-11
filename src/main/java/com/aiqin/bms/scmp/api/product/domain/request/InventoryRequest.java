package com.aiqin.bms.scmp.api.product.domain.request;

import com.aiqin.bms.scmp.api.base.PagesRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wuyongqiang
 * @description 库存查询对象
 * @date 2018/11/20 13:50
 */
@Data
@ApiModel("库存查询参数")
@NoArgsConstructor
@AllArgsConstructor
public class InventoryRequest extends PagesRequest {
    @ApiModelProperty(value = "所属门店")
    @JsonProperty("store_id")
    private String storeId;

    @ApiModelProperty(value = "商品标识（商品销售码/名称）")
    @JsonProperty("product_key")
    private String productKey;

    @ApiModelProperty(value = "最小库存数")
    @JsonProperty("min_storage")
    private Integer minStorage;

    @ApiModelProperty(value = "最大库存数")
    @JsonProperty("max_storage")
    private Integer maxStorage;

    @ApiModelProperty(value = "仓库类型")
    @JsonProperty("storage_type")
    private Integer storageType;

    @ApiModelProperty(value = "商品分类")
    @JsonProperty("product_type_id")
    private String productTypeId;

    @ApiModelProperty(value = "预警状态（1：库存安全、2：低库存、3：停止预警）")
    @JsonProperty("warning_type")
    private Integer warningType;

    @ApiModelProperty(value = "可售库存排序（1：升序排序、2：降序排序）")
    @JsonProperty("sellable_storage_sort")
    private Integer sellableStorageSort;
}
