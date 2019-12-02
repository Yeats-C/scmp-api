package com.aiqin.bms.scmp.api.product.domain.request.inbound;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author HuangLong
 * @date 2018/12/24
 */
@ApiModel("采购单商品对象")
@Data
public class InboundItemReqVo {
    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("采购主表编码")
    private String purchaseCode;

    @ApiModelProperty("sku编号")
    private String skuCode;

    @ApiModelProperty("sku名称")
    private String skuName;

    @ApiModelProperty("规格")
    private String spec;

    @ApiModelProperty("商品code")
    private String productCode;

    @ApiModelProperty("商品名称")
    private String productName;

    @ApiModelProperty("商品类别code")
    private String productCategoryCode;

    @ApiModelProperty("商品类别名称")
    private String productCategoryName;

    @ApiModelProperty("商品类型名称")
    private String productTypeName;

    @ApiModelProperty("商品类型编码")
    private String productTypeCode;

    @ApiModelProperty("商品品牌编码")
    private String productBrandCode;

    @ApiModelProperty("商品品牌名称")
    private String productBrandName;

    @ApiModelProperty("单位编码")
    private String unitCode;

    @ApiModelProperty("单位名称")
    private String unitName;

    @ApiModelProperty("颜色编码")
    private String colorCode;

    @ApiModelProperty("颜色名称")
    private String colorName;

    @ApiModelProperty("商品类别编码")
    private String productSortCode;

    @ApiModelProperty("商品类别名字")
    private String productSortName;

    @ApiModelProperty("税率")
    private BigDecimal taxRate;

    @ApiModelProperty("含税单价")
    private BigDecimal price;

    @ApiModelProperty("采购数量")
    private Long num;

    @ApiModelProperty("采购含税总价")
    private BigDecimal totalPrice;

    @ApiModelProperty("实际到货数量")
    private Long actualNum;

    @ApiModelProperty("实际到货含税总价")
    private BigDecimal actualTotalPrice;

    @ApiModelProperty("规格编码")
    private String specCode;

    @ApiModelProperty("型号")
    private String modelNumber;

    @ApiModelProperty("销售单位编码")
    private String saleUnitCode;

    @ApiModelProperty("销售单位名称")
    private String saleUnitName;

    @ApiModelProperty("不含税单价")
    private BigDecimal noTaxPrice;

    @ApiModelProperty("不含税总价")
    private BigDecimal noTaxTotalPrice;

    @ApiModelProperty("主单位和采购单位间的转换系数")
    private Long convertNum;
}
