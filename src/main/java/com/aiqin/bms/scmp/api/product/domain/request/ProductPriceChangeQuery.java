package com.aiqin.bms.scmp.api.product.domain.request;

import com.aiqin.bms.scmp.api.base.PagesRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Joker
 * @date 2019/3/4 上午10:57
 */
@Data
@ApiModel("商品变价查询VO")
public class ProductPriceChangeQuery extends PagesRequest implements Serializable {

    private static final long serialVersionUID = -5777484589811005873L;

    @ApiModelProperty(value = "分销机构编码")
    @JsonProperty("distributor_id")
    private String distributorId;

    @ApiModelProperty(value = "sku_code")
    @JsonProperty("sku_code")
    private String skuCode;
}
