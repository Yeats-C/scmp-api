package com.aiqin.bms.scmp.api.product.domain.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author knight.xie
 * @version 1.0
 * @className UpdateOutboundProductReqVO
 * @date 2019/1/10 17:22
 * @description 更新出库商品信息请求VO
 */

@ApiModel("更新出库商品信息请求VO")
@Data
public class UpdateOutboundProductReqVO implements Serializable {

    @ApiModelProperty(hidden = true)
    private Long id;

    @ApiModelProperty("sku")
    private String skuCode;

    @ApiModelProperty("实际出库数量")
    private Long praOutboundNum;

    @ApiModelProperty("实际出库主数量")
    private Long praOutboundMainNum;

    @ApiModelProperty("实际含税进价")
    private BigDecimal praTaxPurchaseAmount;

    @ApiModelProperty("实际含税总价")
    private BigDecimal praTaxAmount;

    @ApiModelProperty("操作人")
    private String operator;

    @ApiModelProperty(hidden = true)
    private Date currentDate;

}
