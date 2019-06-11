package com.aiqin.bms.scmp.api.product.domain.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 *
 * 功能描述: SKU销量增加VO
 *
 * @auther knight.xie
 * @date 2019/1/3 11:33
 */
@ApiModel("sku销量")
@Data
public class SkuSaleVolumeVo {

    @ApiModelProperty("sku名称")
    @JsonProperty("sku_code")
    @NotEmpty(message = "SKU编码不能为空")
    private String skuCode;

    @ApiModelProperty("销量")
    @JsonProperty("sale_volume")
    @NotNull(message = "销量不能为空")
    private Long saleVolume = 0L;

}