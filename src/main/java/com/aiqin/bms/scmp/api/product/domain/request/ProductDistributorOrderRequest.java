package com.aiqin.bms.scmp.api.product.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Createed by sunx on 2019/4/8.<br/>
 */
@Data
@ApiModel("畅缺商品查询实体")
public class ProductDistributorOrderRequest {
    //门店id
    @ApiModelProperty("门店id")
    @JsonProperty("distributor_id")
    private String distributorId;

    //skuCode集合
    @ApiModelProperty("skuCode集合")
    @JsonProperty("sku_codes")
    private List<String> skuCodes;
}
