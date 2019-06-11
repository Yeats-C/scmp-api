package com.aiqin.bms.scmp.api.supplier.domain.response.changeprice;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-05-23
 * @time: 10:35
 */
@Data
@ApiModel("图片详情")
public class ProductSkuChangePriceImageRespVO {

    @ApiModelProperty("图片名称")
    private String name;

    @ApiModelProperty("图片地址")
    private String url;

}
