package com.aiqin.bms.scmp.api.product.domain.request.dictionary;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 描述:
 *
 * @author zhujunchao
 * @create 2019-03-19 11:03
 */
@Data
@ApiModel("根据所属门店和商品标识商品分类查询Vo")
public class ProductDistributorQuVo  {
    @ApiModelProperty(value = "所属门店")
    @JsonProperty("store_id")
    private String storeId;

    @ApiModelProperty(value = "商品标识（商品销售码/名称）")
    @JsonProperty("product_key")
    private String productKey;

    @ApiModelProperty(value = "商品分类")
    @JsonProperty("product_type_id")
    private String productTypeId;

}
