package com.aiqin.bms.scmp.api.supplier.domain.response.returnsupply;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author HuangLong
 * @date 2019/1/3
 */
@Data
@ApiModel("退供单商品详情返回vo")
public class ReturnSupplyItemRespVo {
    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("退供主表编码")
    private String returnSupplyCode;

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

    @ApiModelProperty("商品品类code")
    private String productCategoryCode;

    @ApiModelProperty("商品品类名称")
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
    private Long taxRate;

    @ApiModelProperty("含税单价")
    private Long price;

    @ApiModelProperty("退供数量(库存)")
    private Long num;

    @ApiModelProperty("退供含税总价")
    private Long totalPrice;

    @ApiModelProperty("实际退供数量")
    private Long actualNum;

    @ApiModelProperty("实际退供含税总价")
    private Long actualTotalPrice;

    @ApiModelProperty("规格编码")
    private String specCode;

    @ApiModelProperty("型号")
    private String modelNumber;

    @ApiModelProperty("销售单位编码")
    private String saleUnitCode;

    @ApiModelProperty("销售单位名称")
    private String saleUnitName;

    @ApiModelProperty("不含税单价")
    private Long noTaxPrice;

    @ApiModelProperty("不含税总价")
    private Long noTaxTotalPrice;

    @ApiModelProperty("主单位和采购单位间的转换系数")
    private Long convertNum;

    @ApiModelProperty("错误原因")
    private String errorReason;

    @ApiModelProperty("库存数量")
    private Long stockNum;

    public ReturnSupplyItemRespVo(String skuCode, String errorReason) {
        this.skuCode = skuCode;
        this.errorReason = errorReason;
    }
    public ReturnSupplyItemRespVo(String skuCode, String skuName, String spec, String productCode, String productName, String productCategoryCode, String productCategoryName, String productTypeName, String productTypeCode, String productBrandCode, String productBrandName, String unitCode, String unitName, String colorCode, String colorName, String productSortCode, String productSortName, Long taxRate, Long price,  String specCode,String modelNumber,String saleUnitCode,String saleUnitName,Long convertNum) {
        this.skuCode = skuCode;
        this.skuName = skuName;
        this.spec = spec;
        this.productCode = productCode;
        this.productName = productName;
        this.productCategoryCode = productCategoryCode;
        this.productCategoryName = productCategoryName;
        this.productTypeName = productTypeName;
        this.productTypeCode = productTypeCode;
        this.productBrandCode = productBrandCode;
        this.productBrandName = productBrandName;
        this.unitCode = unitCode;
        this.unitName = unitName;
        this.colorCode = colorCode;
        this.colorName = colorName;
        this.productSortCode = productSortCode;
        this.productSortName = productSortName;
        this.taxRate = taxRate;
        this.price = price;
        this.specCode = specCode;
        this.modelNumber=modelNumber;
        this.saleUnitCode=saleUnitCode;
        this.saleUnitName=saleUnitName;
        this.convertNum=convertNum;
    }
}
