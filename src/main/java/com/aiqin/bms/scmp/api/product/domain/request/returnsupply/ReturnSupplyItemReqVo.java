package com.aiqin.bms.scmp.api.product.domain.request.returnsupply;

import com.aiqin.bms.scmp.api.product.domain.request.ILockStockItemReqVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author knight.xie
 * @className ReturnSupplyItemReqVo
 * @date 2019/1/8 14:55
 * @description 退供单商品请求vo
 * @version 1.0
 */
@ApiModel("退供单商品请求vo")
@Data
public class ReturnSupplyItemReqVo implements ILockStockItemReqVo {

    @ApiModelProperty("sku编号")
    private String skuCode;

    @ApiModelProperty("sku名称")
    private String skuName;

    @ApiModelProperty("图片地址")
    private String pictureUrl;

    @ApiModelProperty("规格")
    private String norms;

    @ApiModelProperty("单位编号")
    private String unitCode;

    @ApiModelProperty("单位名称")
    private String unitName;

    @ApiModelProperty("颜色名称")
    private String colorName;

    @ApiModelProperty("颜色编码")
    private String colorCode;

    @ApiModelProperty("进货规格")
    private String purchaseNorms;

    @ApiModelProperty("预计出库数量")
    private Long preOutboundNum;

    @ApiModelProperty("预计出库主数量")
    private Long preOutboundMainNum;

    @ApiModelProperty("预计含税进价")
    private BigDecimal preTaxPurchaseAmount;

    @ApiModelProperty("预计含税总价")
    private BigDecimal preTaxAmount;

    @ApiModelProperty("数量")
    private Long num;

    @ApiModelProperty("操作人")
    private String operator;
}
