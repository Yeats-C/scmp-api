package com.aiqin.bms.scmp.api.purchase.domain.request.returngoods;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-06-24
 * @time: 14:35
 */
@Data
@ApiModel("退货验货保存请求vo")
public class ReturnInspectionReq {

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

    @ApiModelProperty("批次号")
    private String batchNumber;

    @ApiModelProperty("数量")
    private Long num;

    @ApiModelProperty("原sku的行号")
    private Integer productLineNum;

    @ApiModelProperty("原sku的行号")
    private Integer originalLineNum;

    @ApiModelProperty("商品状态1新品2残品")
    private Integer productStatus;

    @ApiModelProperty("仓库名称")
    private String warehouseName;

    @ApiModelProperty("仓库编码")
    private String warehouseCode;

    @ApiModelProperty("物流中心编码")
    private String transportCenterCode;

    @ApiModelProperty("物流中心名称")
    private String transportCenterName;

}
