package com.aiqin.bms.scmp.api.product.domain.response.sku;

import com.aiqin.bms.scmp.api.common.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author knight.xie
 * @version 1.0
 * @className ProductSkuChannelRespVo
 * @date 2019/5/14 15:07
 * @description TODO
 */
@ApiModel("SKU渠道信息返回")
@Data
public class ProductSkuChannelRespVo extends CommonBean {
    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("sku编码")
    private String skuCode;

    @ApiModelProperty("sku名称")
    private String skuName;

    @ApiModelProperty("价格渠道编码")
    private String priceChannelCode;

    @ApiModelProperty("价格渠道名称")
    private String priceChannelName;
}
