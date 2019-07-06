package com.aiqin.bms.scmp.api.product.domain.response.changeprice;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-05-29
 * @time: 14:40
 */
@Data
@ApiModel("价格项目列表vo")
public class PriceChannelForChangePrice {

    @ApiModelProperty("价格项目编码")
    private String priceItemCode;

    @ApiModelProperty("价格项目名称")
    private String priceItemName;

    @ApiModelProperty("价格类型")
    private String priceTypeCode;

    @ApiModelProperty("价格类型名称")
    private String priceTypeName;

    @ApiModelProperty("价格属性编码")
    private String priceAttributeCode;

    @ApiModelProperty("价格数据名称")
    private String priceAttributeName;

    @ApiModelProperty("原含税价")
    private Long oldPrice;

    @ApiModelProperty("新含税价")
    private Long newPrice = 0L;

    @ApiModelProperty("原含税采购价")
    private Long purchasePriceOld = 0L;

    @ApiModelProperty("最新采购价")
    private Long purchasePriceNewest = 0L;

    @ApiModelProperty("原毛利率")
    private Long oldGrossProfitMargin;

    @ApiModelProperty("成本")
    private Long taxCost;

//    public Long getOldGrossProfitMargin() {
//        if (this.oldPrice == 0L) {
//            return 0L;
//        }
//        return this.oldGrossProfitMargin =(this.oldPrice-taxCost)/this.oldPrice;
//    }
}
