package com.aiqin.bms.scmp.api.product.domain.pojo;

import com.aiqin.bms.scmp.api.common.CommonBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@ApiModel("结账信息")
@Data
public class ProductSkuCheckout extends CommonBean {
    @ApiModelProperty("")
    private Long id;

    @ApiModelProperty("sku编码")
    private String skuCode;

    @ApiModelProperty("sku名称")
    private String skuName;

    @ApiModelProperty("销项税率")
    private BigDecimal outputTaxRate;

    @ApiModelProperty("进项税率")
    private BigDecimal inputTaxRate;

    @ApiModelProperty("积分系数")
    private BigDecimal integralCoefficient;

    @ApiModelProperty("返点")
    private BigDecimal rebate;

    @ApiModelProperty("申请编码")
    private String applyCode;

    @ApiModelProperty("物流费奖励比例")
    private BigDecimal logisticsFeeAwardRatio;

    @ApiModelProperty("结算方式编码")
    private String settlementMethodCode;

    @ApiModelProperty("结算方式名称")
    private String settlementMethodName;
}