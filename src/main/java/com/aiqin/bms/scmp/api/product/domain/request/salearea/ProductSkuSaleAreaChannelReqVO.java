package com.aiqin.bms.scmp.api.product.domain.request.salearea;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-06-04
 * @time: 14:03
 */
@ApiModel("渠道相关")
@Data
public class ProductSkuSaleAreaChannelReqVO {

    @ApiModelProperty("价格渠道编码")
    private String priceChannelCode;

    @ApiModelProperty("价格渠道编码")
    private String priceChannelName;

}
