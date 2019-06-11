package com.aiqin.bms.scmp.api.supplier.domain.response.basicprice;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author knight.xie
 * @version 1.0
 * @className PriceProjetGroupType
 * @date 2019/4/19 17:29
 * @description TODO
 */

@Data
@ApiModel("价格项目按照价格类型分组")
public class PriceProjetGroupType {

    @ApiModelProperty("渠道价")
    private List<QueryPriceProjectRespVo> channelPriceList;

    @ApiModelProperty("临时渠道价")
    private List<QueryPriceProjectRespVo> temporaryChannelPriceList;

    @ApiModelProperty("分销价")
    private List<QueryPriceProjectRespVo> distributionPriceList;

    @ApiModelProperty("临时分销价")
    private List<QueryPriceProjectRespVo> temporaryDistributionPriceList;

    @ApiModelProperty("销售价")
    private List<QueryPriceProjectRespVo> salePriceList;

    @ApiModelProperty("临时销售价")
    private List<QueryPriceProjectRespVo> temporarySalePriceList;
}
