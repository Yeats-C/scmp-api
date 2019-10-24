package com.aiqin.bms.scmp.api.product.domain.request.sku.supplier;

import com.aiqin.bms.scmp.api.base.PageReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@ApiModel("SKU供应商管理列表查询")
public class QuerySkuSupplyUnitReqVo extends PageReq {

    @ApiModelProperty("sku编码")
    private String skuCode;

    @ApiModelProperty("sku名称")
    private String skuName;

    @ApiModelProperty("采购组编号")
    private String purchaseGroupCode;

    @ApiModelProperty("采购组名称")
    private String purchaseGroupName;

    @ApiModelProperty("供应商编码")
    private String supplierCode;

    @ApiModelProperty("供应商名称")
    private String supplierName;

    @ApiModelProperty("最小采购价")
    private BigDecimal minPurchasePrice;

    @ApiModelProperty("最大采购价")
    private BigDecimal maxPurchasePrice;

    @ApiModelProperty("供货渠道编码")
    private String categoriesSupplyChannelsCode;

    @ApiModelProperty("供货渠道名称")
    private String categoriesSupplyChannelsName;

    @ApiModelProperty("使用状态 0:未用 1:在用")
    private Byte usageStatus;

    @ApiModelProperty("属性名称")
    private String productPropertyName;

    @ApiModelProperty("属性编码")
    private String productPropertyCode;

    @ApiModelProperty("品类集合编码 1级编码 2级编码 3级编码 4级编码")
    private List<String> productCategoryCodes;

    @ApiModelProperty(value = "1级品类编码",hidden = true)
    private String productCategoryLv1Code;

    @ApiModelProperty(value ="2级品类编码",hidden = true)
    private String productCategoryLv2Code;

    @ApiModelProperty(value ="3级品类编码",hidden = true)
    private String productCategoryLv3Code;

    @ApiModelProperty(value ="4级品类编码",hidden = true)
    private String productCategoryLv4Code;

    @ApiModelProperty("品牌名称")
    private String productBrandName;

    @ApiModelProperty("品牌编码")
    private String productBrandCode;

    @ApiModelProperty(value = "公司编码",hidden = true)
    private String companyCode;

    @ApiModelProperty(value = "人员Id",hidden = true)
    private String personId;

}
