package com.aiqin.bms.scmp.api.product.domain.request.dictionary;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 描述:
 *
 * @author zhujunchao
 * @create 2019-01-14 17:52
 */
@Data
@ApiModel("查询vo")
public class ProductDistributorVo {
    @ApiModelProperty(value = "门店id")
    @JsonProperty("distributor_id")
    private String distributorId;

    @ApiModelProperty(value = "sku_code")
    @JsonProperty("sku_code")
    private List<String> skuCode;
}
