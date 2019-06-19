package com.aiqin.bms.scmp.api.purchase.domain.response.returngoods;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-06-19
 * @time: 19:33
 */
@Data
@ApiModel("批次列表返回vo")
public class ReturnOrderInfoApplyInboundDetailRespVO {

    @ApiModelProperty("sku编号")
    private String skuCode;

    @ApiModelProperty("sku名称")
    private String skuName;

    @ApiModelProperty("图片地址")
    private String pictureUrl;

    @ApiModelProperty("规格")
    private String spec;

    @ApiModelProperty("颜色名称")
    private String colorName;

    @ApiModelProperty("型号")
    private String model;

    @ApiModelProperty("基商品含量")
    private Integer baseProductContent;

    @ApiModelProperty("商品单位")
    private String unitName;

    @ApiModelProperty("拆零系数")
    private Integer zeroDisassemblyCoefficient;

    @ApiModelProperty("批次号")
    private String batchNumber;

    @ApiModelProperty("单价")
    private Long price;

    @ApiModelProperty("数量")
    private Long num;

    @ApiModelProperty("总价")
    private Long amount;

    @ApiModelProperty("商品状态1新品2残品")
    private Integer productStatus;
}
