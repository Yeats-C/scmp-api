package com.aiqin.bms.scmp.api.product.domain.response.basicprice;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author knight.xie
 * @version 1.0
 * @className PriceChannelRespVo
 * @date 2019/4/19 16:03
 */
@Data
@ApiModel("价格渠道返回Vo")
public class PriceChannelRespVo {

    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("编码")
    private String priceChannelCode;

    @ApiModelProperty("名称")
    private String priceChannelName;

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

    @ApiModelProperty("排序")
    private Integer priceChannelOrder;

    @ApiModelProperty("是否禁用(0:启用 1:禁用)")
    private Byte priceChannelEnable;


}
