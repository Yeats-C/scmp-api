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
 * @time: 16:15
 */
@ApiModel("供应商相关信息")
@Data
public class supplierInfoVO {
    @ApiModelProperty("供应商编码")
    private String supplierCode;
    @ApiModelProperty("供应商名称")
    private String supplierName;
    @ApiModelProperty("原含税采购价")
    private Long purchasePriceOld;
    @ApiModelProperty("最新采购价")
    private Long purchasePriceNewest;
    @ApiModelProperty("原含税价")
    private Long oldPrice = 0L;
    @ApiModelProperty("新含税价")
    private Long newPrice = 0L;
    @ApiModelProperty("是否缺省（0:否,1：是）")
    private Boolean beDefault;
    @ApiModelProperty("原毛利率")
    private Long oldGrossProfitMargin = 0L;
    @ApiModelProperty("成本")
    private Long taxCost=0L;
    @ApiModelProperty("平均成本")
    private Long avgTaxCost;

    public Long getOldGrossProfitMargin() {
        this.avgTaxCost = this.purchasePriceOld;
        //采购变价
        if(Objects.isNull(this.purchasePriceOld)||this.purchasePriceOld==0){
            return 0L;
        }else {
            return this.oldGrossProfitMargin = (this.purchasePriceOld-taxCost)*100/this.purchasePriceOld;
        }
    }
}
