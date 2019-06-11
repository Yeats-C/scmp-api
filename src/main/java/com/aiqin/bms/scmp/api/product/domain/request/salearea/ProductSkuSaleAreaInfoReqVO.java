package com.aiqin.bms.scmp.api.product.domain.request.salearea;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-05-09
 * @time: 17:03
 */
@Data
@ApiModel("销售区域保存附表vo")
public class ProductSkuSaleAreaInfoReqVO {

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

    @ApiModelProperty("省编码")
    private String provinceId;

}
