package com.aiqin.bms.scmp.api.product.domain.pojo;

import com.aiqin.bms.scmp.api.base.PropertyMsg;
import com.aiqin.bms.scmp.api.common.CommonBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@ApiModel("结算信息")
@Data
public class ProductSkuCheckoutDraft extends CommonBean {

    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("sku编码")
    private String skuCode;

    @ApiModelProperty("sku名称")
    private String skuName;

    @ApiModelProperty("结算方式编码")
    private String settlementMethodCode;

    @ApiModelProperty("结算方式名称")
    @PropertyMsg("结算方式")
    private String settlementMethodName;

    @ApiModelProperty("销项税率")
    // @PropertyMsg("销项税率")
    private BigDecimal outputTaxRate;

    @ApiModelProperty("进项税率")
    // @PropertyMsg("进项税率")
    private BigDecimal inputTaxRate;

    @ApiModelProperty("积分系数")
    @PropertyMsg("积分系数")
    private BigDecimal integralCoefficient;

    @ApiModelProperty(value = "返点", hidden = true)
    private BigDecimal rebate;

    @ApiModelProperty("物流费奖励比例")
    // @PropertyMsg("物流费奖励比例")
    private BigDecimal logisticsFeeAwardRatio;

}