package com.aiqin.bms.scmp.api.product.domain.request.sku;

import com.aiqin.bms.scmp.api.base.PageReq;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ConfigSearchVo extends PageReq {

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

    @ApiModelProperty("sku名称")
    private String skuName;

    @ApiModelProperty("仓库编码")
    private String  warehouseCode;

    @ApiModelProperty("仓库名称")
    private String  warehouseName;

    @ApiModelProperty("供应商编码")
    private String  supplierCode;

    @ApiModelProperty("供应商名称")
    private String  supplierName;

    @ApiModelProperty("公司编码")
    private String  companyCode;

    @ApiModelProperty(value = "当前登录人",hidden = true)
    private String personId;

}
