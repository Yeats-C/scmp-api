package com.aiqin.bms.scmp.api.product.domain.response.changeprice;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-05-29
 * @time: 16:15
 */
@ApiModel("供应商相关信息")
@Data
public class supplierInfoVO {
    @ApiModelProperty("供应商编码")
    private String supplierCode;
    @ApiModelProperty("供应商名称")
    private String supplierName;
    @ApiModelProperty("原含税采购价")
    private Long purchasePriceOld;
    @ApiModelProperty("最新采购价")
    private Long purchasePriceNewest;
    @ApiModelProperty("是否缺省（0:否,1：是）")
    private Boolean isDefault;
}
