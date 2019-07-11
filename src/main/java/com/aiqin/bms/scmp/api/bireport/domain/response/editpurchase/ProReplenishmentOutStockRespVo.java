package com.aiqin.bms.scmp.api.bireport.domain.response.editpurchase;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@ApiModel("采购补货缺货返回Vo")
@Data
public class ProReplenishmentOutStockRespVo  implements Serializable {

    @ApiModelProperty("sku号")
    @JsonProperty(value = "sku_code")
    private List<String> skuCode;
}
