package com.aiqin.bms.scmp.api.product.domain.dto.salearea;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-05-13
 * @time: 14:17
 */
@Data
public class ApplyProductSkuSaleAreaInfoDTO {

    @ApiModelProperty("主表编码")
    private String saleAreaCode;

    @ApiModelProperty("sku编码")
    private String skuCode;

    @ApiModelProperty("1区域2门店")
    private Integer type;

    @ApiModelProperty("0禁止1开放")
    private Integer status;

    @ApiModelProperty("区域或门店名称")
    private String name;

    @ApiModelProperty("区域或门店编码")
    private String code;

    @ApiModelProperty("公司编码")
    private String companyCode;

    @ApiModelProperty("公司名称")
    private String companyName;
}
