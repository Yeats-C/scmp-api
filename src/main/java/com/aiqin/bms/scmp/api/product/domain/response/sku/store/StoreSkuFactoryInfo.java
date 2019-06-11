package com.aiqin.bms.scmp.api.product.domain.response.sku.store;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @功能说明:
 * @author wangxu
 * @date 2018/12/30 0030 14:23
 */
@ApiModel("门店sku所属厂家")
@Data
public class StoreSkuFactoryInfo {

    @ApiModelProperty("生产厂家")
    @JsonProperty("manufacturer_name")
    private String manufacturerName;

    @ApiModelProperty("厂方商品编号")
    @JsonProperty("factory_product_number")
    private String factoryProductNumber;

    @ApiModelProperty("保修地址")
    @JsonProperty("address")
    private String address;

}
