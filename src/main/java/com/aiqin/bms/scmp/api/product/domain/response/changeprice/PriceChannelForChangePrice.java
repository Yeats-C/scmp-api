package com.aiqin.bms.scmp.api.product.domain.response.changeprice;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
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
    private BigDecimal oldPrice= BigDecimal.ZERO;

    @ApiModelProperty("新含税价")
    private BigDecimal newPrice = BigDecimal.ZERO;

    @ApiModelProperty("原含税采购价")
    private BigDecimal purchasePriceOld = BigDecimal.ZERO;

    @ApiModelProperty("最新采购价")
    private BigDecimal purchasePriceNewest = BigDecimal.ZERO;

    @ApiModelProperty("原毛利率")
    private BigDecimal oldGrossProfitMargin=BigDecimal.ZERO;

    @ApiModelProperty("成本")
    private BigDecimal taxCost=BigDecimal.ZERO;

    @ApiModelProperty("平均成本")
    private BigDecimal avgTaxCost=BigDecimal.ZERO;

    public BigDecimal getOldGrossProfitMargin() {
        this.avgTaxCost = this.oldPrice;
      BigDecimal num=  this.oldPrice;
        BigDecimal num2=  this.taxCost;
        //销售类的变价
        if(Objects.isNull(this.oldPrice.stripTrailingZeros())||this.oldPrice.stripTrailingZeros().equals(BigDecimal.ZERO)){
            return BigDecimal.ZERO;
        }else if(this.taxCost.stripTrailingZeros().equals(BigDecimal.ZERO)) {
            return  this.oldGrossProfitMargin = new BigDecimal(100);
        }else {
            BigDecimal divide = (num.subtract(num2)).divide(num, 4, BigDecimal.ROUND_HALF_UP);
            return   this.oldGrossProfitMargin = divide.multiply(new BigDecimal(100));
        }
    }
}
