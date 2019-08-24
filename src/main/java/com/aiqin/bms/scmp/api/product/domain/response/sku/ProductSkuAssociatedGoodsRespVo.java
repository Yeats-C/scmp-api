package com.aiqin.bms.scmp.api.product.domain.response.sku;

import com.aiqin.bms.scmp.api.common.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author knight.xie
 * @version 1.0
 * @className ProductSkuAssociatedGoodsRespVo
 * @date 2019/5/14 16:02
 */
@ApiModel("SKU关联商品信息返回")
@Data
public class ProductSkuAssociatedGoodsRespVo extends CommonBean {

    @ApiModelProperty("主键Id")
    private Long id;

    @ApiModelProperty("主商品编码")
    private String mainSkuCode;

    @ApiModelProperty("主商品名称")
    private String mainSkuName;

    @ApiModelProperty("子商品编码")
    private String subSkuCode;

    @ApiModelProperty("子商品名称")
    private String subSkuName;

    @ApiModelProperty("商品品牌code")
    private String productBrandCode;

    @ApiModelProperty("商品品牌")
    private String productBrandName;

    @ApiModelProperty("商品品类code")
    private String productCategoryCode;

    @ApiModelProperty("商品品类名称")
    private String productCategoryName;

    @ApiModelProperty("采购组编码")
    private String procurementSectionCode;

    @ApiModelProperty("采购组名称")
    private String procurementSectionName;

    @ApiModelProperty("颜色code")
    private String colorCode;

    @ApiModelProperty("颜色名称")
    private String colorName;

    @ApiModelProperty("型号")
    private String modelNumber;

    @ApiModelProperty("商品类别(所属部门)code")
    private String productSortCode;

    @ApiModelProperty("商品类别(所属部门)名称")
    private String productSortName;
}
