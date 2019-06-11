package com.aiqin.bms.scmp.api.product.domain.request.dictionary;

import com.aiqin.bms.scmp.api.base.PagesRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 描述:
 *
 * @author zhujunchao
 * @create 2019-03-21 14:53
 */
@Data
@ApiModel("根据所属门店和商品标识商品分类查询分页Vo")
public class ProductDistributorQuVoPage extends PagesRequest {
    @ApiModelProperty(value = "所属门店")
    @JsonProperty("store_id")
    private String storeId;

    @ApiModelProperty(value = "商品标识（商品销售码/名称）")
    @JsonProperty("product_key")
    private String productKey;

    @ApiModelProperty(value = "商品分类")
    @JsonProperty("product_type_id")
    private String productTypeId;


    @ApiModelProperty(value = "仓库类型 1为门店自有仓，2为爱亲大仓")
    @JsonProperty("storage_type")
    private Integer storageType;

    @ApiModelProperty(value = "仓位 1:陈列仓位 2:退货仓位 3:存储仓位")
    @JsonProperty("dorr_stock")
    private Integer dorrStock;
}
