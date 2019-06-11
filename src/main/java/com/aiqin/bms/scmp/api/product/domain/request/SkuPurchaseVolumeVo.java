package com.aiqin.bms.scmp.api.product.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 *
 * 功能描述: SKU进货量增加VO
 *
 * @auther knight.xie
 * @date 2019/1/3 14:37
 */
@ApiModel("sku进货量")
@Data
public class SkuPurchaseVolumeVo {


    @ApiModelProperty("sku名称")
    @JsonProperty("sku_code")
    @NotEmpty(message = "SKU编码不能为空")
    private String skuCode;

    @ApiModelProperty("进货量")
    @JsonProperty("purchase_volume")
    @NotNull(message = "进货量不能为空")
    private Long purchaseVolume = 0L;

}