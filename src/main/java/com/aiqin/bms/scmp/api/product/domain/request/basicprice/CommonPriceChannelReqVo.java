package com.aiqin.bms.scmp.api.product.domain.request.basicprice;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author knight.xie
 * @version 1.0
 * @className CommonPriceChannelReqVo
 * @date 2019/6/3 16:29
 */
@Data
public class CommonPriceChannelReqVo {

    @ApiModelProperty("渠道价格编码")
    private String channelPriceCode;

    @ApiModelProperty("渠道价格名称")
    private String channelPriceName;

    @ApiModelProperty("临时渠道价格编码")
    private String temporaryChannelPriceCode;

    @ApiModelProperty("临时渠道价格名称")
    private String temporaryChannelPriceName;

    @ApiModelProperty("分销价格编码")
    private String distributionPriceCode;

    @ApiModelProperty("分销价格名称")
    private String distributionPriceName;

    @ApiModelProperty("临时分销价格编码")
    private String temporaryDistributionPriceCode;

    @ApiModelProperty("临时分销价格名称")
    private String temporaryDistributionPriceName;

    @ApiModelProperty("销售价格编码")
    private String salePriceCode;

    @ApiModelProperty("销售价格名称")
    private String salePriceName;

    @ApiModelProperty("临时销售价格编码")
    private String temporarySalePriceCode;

    @ApiModelProperty("临时销售价格名称")
    private String temporarySalePriceName;
}
