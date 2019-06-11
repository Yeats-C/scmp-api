package com.aiqin.bms.scmp.api.product.domain.request.changeprice;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-05-22
 * @time: 15:06
 */
@ApiModel("图片信息请求vo")
@Data
public class ProductSkuChangePriceImageReqVO {

    @ApiModelProperty("图片名称")
    private String name;

    @ApiModelProperty("图片地址")
    private String url;

}
