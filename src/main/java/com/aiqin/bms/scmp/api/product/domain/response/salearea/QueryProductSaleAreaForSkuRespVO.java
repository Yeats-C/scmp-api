package com.aiqin.bms.scmp.api.product.domain.response.salearea;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-06-06
 * @time: 10:51
 */
@Data
@ApiModel("选择sku时的返回vo")
public class QueryProductSaleAreaForSkuRespVO {
    @ApiModelProperty("sku编码")
    private String skuCode;

    @ApiModelProperty("sku编码")
    private String skuName;

    @ApiModelProperty("品类")
    private String productCategoryName;

    @ApiModelProperty("品牌")
    private String productBrandName;

    @ApiModelProperty("规格")
    private String spec;

    @ApiModelProperty("颜色")
    private String colorName;

    @ApiModelProperty("型号")
    private String modelNumber;

    @ApiModelProperty("进项单位")
    private String unitName;

    @ApiModelProperty("类别")
    private String productSortName;

    @ApiModelProperty("是否赠品0商品1赠品")
    private Byte goodsGifts;

    @ApiModelProperty("商品属性")
    private String productPropertyName;

    @ApiModelProperty("渠道")
    private String priceChannelName;

    @ApiModelProperty("采购组编码")
    private String procurementSectionCode;

    @ApiModelProperty("采购组名称")
    private String procurementSectionName;

    @ApiModelProperty("供应商集合")
    private List<ProductSaleAreaSupplierInfo> supplierList;
}
