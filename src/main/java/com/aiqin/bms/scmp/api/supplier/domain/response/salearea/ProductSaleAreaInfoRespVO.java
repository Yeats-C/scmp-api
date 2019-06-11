package com.aiqin.bms.scmp.api.supplier.domain.response.salearea;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-05-10
 * @time: 14:18
 */
@ApiModel("列表上销售区域详情VO")
@Data
public class ProductSaleAreaInfoRespVO {
    @ApiModelProperty("1区域2门店")
    private Integer type;

    @ApiModelProperty("0禁止1开放")
    private Integer status;

    @ApiModelProperty("区域或门店名称")
    private String name;

    @ApiModelProperty("区域或门店编码")
    private String code;
}
