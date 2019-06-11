package com.aiqin.bms.scmp.api.product.domain.request.sku.oms;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * ProductProperty
 *
 * @author zhangtao
 * @createTime 2019/1/4 下午7:10
 * @description
 */
@Data
public class ProductPropertyDTO implements Serializable {

    private static final long serialVersionUID = 4179758398748540217L;

    @JsonProperty("category_id")
    @ApiModelProperty("品类ID")
    private String categoryId;

    @JsonProperty("brand_id")
    @ApiModelProperty("品牌ID")
    private String brandId;
}
