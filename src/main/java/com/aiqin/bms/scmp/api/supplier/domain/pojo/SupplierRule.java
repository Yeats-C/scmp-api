package com.aiqin.bms.scmp.api.supplier.domain.pojo;

import com.aiqin.bms.scmp.api.common.CommonBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@ApiModel("供应商规则")
@Data
public class SupplierRule extends CommonBean {
    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("自动退货天数")
    private Integer autoReturnGoodsDay;

    @ApiModelProperty("公司编码")
    private String companyCode;

    @ApiModelProperty("公司名称")
    private String companyName;

    @ApiModelProperty("订单体积计算系数")
    private BigDecimal orderVolumeCoefficient;

    @ApiModelProperty("订单重量计算系数")
    private BigDecimal orderWeightCoefficient;

    @ApiModelProperty("采购流程天数")
    private int purchaseProcessDay;

    @ApiModelProperty("采购流程审核天数")
    private int purchaseProcessReviewDay;

    @ApiModelProperty("采购流程财务付款天数")
    private int purchaseProcessPaymentDay;

    @ApiModelProperty("采购流程供应商确认天数")
    private int purchaseProcessSupplierConfirmDay;


}