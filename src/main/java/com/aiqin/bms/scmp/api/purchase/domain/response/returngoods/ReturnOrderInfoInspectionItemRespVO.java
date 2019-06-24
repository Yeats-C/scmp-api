package com.aiqin.bms.scmp.api.purchase.domain.response.returngoods;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-06-20
 * @time: 15:03
 */
@ApiModel("验货列表")
@Data
public class ReturnOrderInfoInspectionItemRespVO {

    @ApiModelProperty("商品主键")
    private Long id;

    @ApiModelProperty("订单主表编码")
    private String returnOrderCode;

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

    @ApiModelProperty("商品单位code")
    private String unitCode;

    @ApiModelProperty("商品单位")
    private String unitName;

    @ApiModelProperty("拆零系数")
    private Integer zeroDisassemblyCoefficient;

    @ApiModelProperty("是否是赠品(0否1是)")
    private Integer givePromotion;

    @ApiModelProperty("批次号")
    private String batchNumber;

    @ApiModelProperty("数量")
    private Long num;

    @ApiModelProperty("商品行号")
    private Long productLineNum;

    @ApiModelProperty("原sku的行号")
    private Integer originalLineNum;

    @ApiModelProperty("商品状态1新品2残品")
    private Integer productStatus;

    @ApiModelProperty("仓库编码")
    private String warehouseCode;

    @ApiModelProperty("仓库名称")
    private String warehouseName;

    @ApiModelProperty("是否管理保质期")
    private Byte qualityAssuranceManagement;

    @ApiModelProperty("保质期基数")
    private String qualityNumber;

    @ApiModelProperty("保质(1:天 2:月 3:年)")
    private String qualityDate;

}
