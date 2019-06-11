package com.aiqin.bms.scmp.api.supplier.domain.response.salearea;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-05-15
 * @time: 15:39
 */
@Data
@ApiModel("销售区域正式详情")
public class ProductSaleAreaForOfficialRespVO {
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

    @ApiModelProperty("正式表编码")
    private String saleAreaCode;

    @ApiModelProperty("区域信息")
    private List<ProductSaleAreaInfoRespVO> areaList;
}
