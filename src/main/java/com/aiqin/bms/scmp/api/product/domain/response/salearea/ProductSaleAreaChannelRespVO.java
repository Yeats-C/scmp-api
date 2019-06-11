package com.aiqin.bms.scmp.api.product.domain.response.salearea;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-06-05
 * @time: 17:13
 */
@Data
@ApiModel("渠道信息")
public class ProductSaleAreaChannelRespVO {

    @ApiModelProperty("价格渠道编码")
    private String priceChannelCode;

    @ApiModelProperty("价格渠道编码")
    private String priceChannelName;
}
