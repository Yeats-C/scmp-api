package com.aiqin.bms.scmp.api.product.domain.response.changeprice;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Objects;

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
    private Long oldPrice=0L;

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

    @ApiModelProperty("平均成本")
    private Long avgTaxCost;

    public Long getOldGrossProfitMargin() {
        this.avgTaxCost = this.oldPrice;
        //销售类的变价
        if(Objects.isNull(this.oldPrice)||this.oldPrice==0){
            return 0L;
        }else {
            return this.oldGrossProfitMargin = (this.oldPrice-taxCost)*100/this.oldPrice;
        }
    }
}
