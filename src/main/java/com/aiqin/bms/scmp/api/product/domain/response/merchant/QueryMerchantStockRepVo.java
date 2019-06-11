package com.aiqin.bms.scmp.api.product.domain.response.merchant;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author knight.xie
 * @version 1.0
 * @className QueryMerchantStockRepVo
 * @date 2019/1/14 17:08
 * @description 门店订货sku库存查询返回VO
 */
@ApiModel("门店订货sku库存查询返回VO")
@Data
public class QueryMerchantStockRepVo implements Serializable {

    @ApiModelProperty("sku编码")
    @JsonProperty("sku_code")
    private String skuCode;

    @ApiModelProperty("sku名称")
    @JsonProperty("sku_name")
    private String skuName;

    @ApiModelProperty("库房编码")
    @JsonProperty("warehouse_code")
    private String warehouseCode;

    @ApiModelProperty("库房名称")
    @JsonProperty("warehouse_name")
    private String warehouseName;

    @ApiModelProperty("库存数量")
    @JsonProperty("num")
    private Long num;
}
