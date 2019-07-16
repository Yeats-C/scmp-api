package com.aiqin.bms.scmp.api.product.domain.response.salearea;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-05-10
 * @time: 14:09
 */
@ApiModel("销售区域列表数据返回vo")
@Data
public class QueryProductSaleAreaRespVO {

    @ApiModelProperty("sku编码")
    private String skuCode;

    @ApiModelProperty("sku名称")
    private String skuName;

    @ApiModelProperty("是否禁用(0禁用1启用)")
    private Integer beDisable;

    @ApiModelProperty("供货渠道类别编码")
    private String categoriesSupplyChannelsCode;

    @ApiModelProperty("供货渠道类别名称")
    private String categoriesSupplyChannelsName;

    @ApiModelProperty("直送供应商编码")
    private String directDeliverySupplierCode;

    @ApiModelProperty("直送供应商名称")
    private String directDeliverySupplierName;

    @ApiModelProperty("颜色")
    private String color;

    @ApiModelProperty("规格")
    private String spec;

    @ApiModelProperty("型号")
    private String model;

    @ApiModelProperty("供应商集合")
    private List<ProductSaleAreaSupplierInfo> supplierList;

}
