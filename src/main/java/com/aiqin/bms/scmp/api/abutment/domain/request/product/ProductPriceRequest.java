package com.aiqin.bms.scmp.api.abutment.domain.request.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel("DL- 商品推送价格信息")
public class ProductPriceRequest {

    @ApiModelProperty(value="价格")
    @JsonProperty("product_amount")
    private BigDecimal productAmount;

    @ApiModelProperty(value="价格项目")
    @JsonProperty("price_item_name")
    private String priceItemName;

    @ApiModelProperty(value="价格类型 1.采购 2.渠道 3分销 4.零售 ")
    @JsonProperty("price_type")
    private Integer priceType;

    @ApiModelProperty(value="价格属性 1.采购 2.销售 3.临时类 4.批发类")
    @JsonProperty("price_attribute")
    private Integer priceAttribute;

}
