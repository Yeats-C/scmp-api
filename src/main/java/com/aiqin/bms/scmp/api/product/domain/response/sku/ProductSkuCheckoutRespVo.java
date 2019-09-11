package com.aiqin.bms.scmp.api.product.domain.response.sku;

import com.aiqin.bms.scmp.api.common.CommonBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author knight.xie
 * @version 1.0
 * @className ProductSkuCheckoutRespVo
 * @date 2019/5/14 15:45
 */
@ApiModel("SKU结算信息返回")
@Data
public class ProductSkuCheckoutRespVo extends CommonBean {
    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("sku编码")
    private String skuCode;

    @ApiModelProperty("sku名称")
    private String skuName;

    @ApiModelProperty("结算方式编码")
    private String settlementMethodCode;

    @ApiModelProperty("结算方式名称")
    private String settlementMethodName;

    @ApiModelProperty("销项税率")
    private Long outputTaxRate;

    @ApiModelProperty("进项税率")
    private Long inputTaxRate;

    @ApiModelProperty("积分系数")
    private Long integralCoefficient;

    @ApiModelProperty("物流费奖励比例")
    private BigDecimal logisticsFeeAwardRatio;
}
