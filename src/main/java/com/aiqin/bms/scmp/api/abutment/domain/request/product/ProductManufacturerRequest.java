package com.aiqin.bms.scmp.api.abutment.domain.request.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("DL- 商品推送价格信息")
public class ProductManufacturerRequest {

    @ApiModelProperty(value="生产厂家编号")
    @JsonProperty("manufacturer_code")
    private String manufacturerCode;

    @ApiModelProperty(value="生产厂家名称")
    @JsonProperty("manufacturer_name")
    private String manufacturerName;

    @ApiModelProperty(value="保修地址")
    @JsonProperty("address")
    private String address;

    @ApiModelProperty(value="factory_product_code")
    @JsonProperty("factory_product_code")
    private String factoryProductCode;

    @ApiModelProperty(value="默认值 0:否,1：是")
    @JsonProperty("is_default")
    private Integer isDefault;

}
