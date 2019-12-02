package com.aiqin.bms.scmp.api.product.domain.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author knight.xie
 * @version 1.0
 * @className UpdateOutBoundReqVO
 * @date 2019/1/10 17:17
 * @description 更新出库信息请求VO
 */
@ApiModel("更新出库信息请求VO")
@Data
public class UpdateOutBoundReqVO implements Serializable {

    @ApiModelProperty("来源单号")
    private String sourceOrderCode;

    @ApiModelProperty("状态编码")
    private Byte stockStatusCode;

    @ApiModelProperty("状态名称")
    private String stockStatusName;

    @ApiModelProperty("WMS单据号")
    private String wmsDocumentCode;

    @ApiModelProperty("实际出库数量")
    private Long praOutboundNum;

    @ApiModelProperty("实际出库主单位数量")
    private Long praMainUnitNum;

    @ApiModelProperty("实际含税总金额")
    private BigDecimal praTaxAmount;

    @ApiModelProperty("实际无税总金额")
    private BigDecimal praAmount;

    @ApiModelProperty("实际税额")
    private BigDecimal praTax;

    @ApiModelProperty("操作人")
    private String operator;

    @ApiModelProperty(hidden = true)
    private Date currentDate;

    @ApiModelProperty("更新出库商品信息请求VO")
    private List<UpdateOutboundProductReqVO> updateOutboundProductReqVOs;

}
