package com.aiqin.bms.scmp.api.product.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Createed by sunx on 2019/4/8.<br/>
 */
@Data
@ApiModel("订单畅缺商品信息")
public class ProductDistributorOrder {
    @ApiModelProperty(value = "sku_code")
    @JsonProperty("sku_code")
    private String skuCode;

    @ApiModelProperty(value = "分销机构id")
    @JsonProperty("distributor_id")
    private String distributorId;

    @ApiModelProperty(value = "陈列仓位库存")
    @JsonProperty("display_stock")
    private Integer displayStock;

    @ApiModelProperty(value = "退货仓位库存")
    @JsonProperty("return_stock")
    private Integer returnStock;

    @ApiModelProperty(value = "存储仓位库存")
    @JsonProperty("storage_stock")
    private Integer storageStock;

    @ApiModelProperty(value = "锁库库存")
    @JsonProperty("lock_stock")
    private Integer lockStock;

    @ApiModelProperty(value = "商品名称")
    @JsonProperty("product_name")
    private String productName;

    @ApiModelProperty(value = "列表图")
    @JsonProperty("logo")
    private String logo;
}
