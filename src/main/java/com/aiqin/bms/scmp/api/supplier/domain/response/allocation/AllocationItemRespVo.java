package com.aiqin.bms.scmp.api.supplier.domain.response.allocation;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 描述:调拨单sku导入返回实体
 *
 * @Author: Kt.w
 * @Date: 2019/1/11
 * @Version 1.0
 * @since 1.0
 */
@Data
@ApiModel("调拨单sku导入返回实体")
public class AllocationItemRespVo {
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
    private Long taxRate;

    @ApiModelProperty("含税单价")
    private Long price;

    @ApiModelProperty("库存")
    private Long stockNum;

    @ApiModelProperty("数量")
    @JsonProperty("quantity")
    private Long number;
    @ApiModelProperty("采购含税总价")
    @JsonProperty("taxAmount")
    private Long totalPrice;

    @ApiModelProperty("实际到货数量")
    private Long actualNum;

    @ApiModelProperty("实际到货含税总价")
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

    @ApiModelProperty("错误原因")
    private String errorReason;

     public AllocationItemRespVo(){}

    public AllocationItemRespVo(String skuCode,String skuName, String errorReason) {
        this.skuCode = skuCode;
        this.skuName = skuName;
        this.errorReason = errorReason;
    }
}
