package com.aiqin.bms.scmp.api.product.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by 爱亲 on 2018/11/20.
 */
@ApiModel("商品消耗周期")
public class ProductCycle {
    @ApiModelProperty(value = "自增主键")
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty(value = "商品id")
    @JsonProperty("product_id")
    private String productId;

    @ApiModelProperty(value = "消耗周期，最小单位为天")
    @JsonProperty("cycle")
    private Integer cycle;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Integer getCycle() {
        return cycle;
    }

    public void setCycle(Integer cycle) {
        this.cycle = cycle;
    }
}