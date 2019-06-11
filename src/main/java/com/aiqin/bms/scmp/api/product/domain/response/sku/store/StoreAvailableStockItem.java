package com.aiqin.bms.scmp.api.product.domain.response.sku.store;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @功能说明:
 * @author wangxu
 * @date 2018/12/29 0029 11:15
 */
@ApiModel("门店sku各仓库可配库存")
@Data
public class StoreAvailableStockItem {

    @ApiModelProperty("仓库可配库存")
    @JsonProperty("stock_quantity")
    private Long stockQuantity;

    @ApiModelProperty("仓库名称")
    @JsonProperty("warehouse_name")
    private String warehouseName;
}
