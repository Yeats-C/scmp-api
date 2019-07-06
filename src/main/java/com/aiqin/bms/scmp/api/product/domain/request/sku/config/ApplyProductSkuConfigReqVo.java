package com.aiqin.bms.scmp.api.product.domain.request.sku.config;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Description:
 * @author: knight.xie
 * @date: 2019-05-13
 * @time: 14:57
 */
@Data
@ApiModel("审批流更新申请数据状态")
public class ApplyProductSkuConfigReqVo {

    @ApiModelProperty("审批状态")
    private Byte auditorStatus;

    @ApiModelProperty("审核人")
    private String auditorBy;

    @ApiModelProperty("审核时间")
    private Date auditorTime;

    @ApiModelProperty("申请表单号")
    private String formNo;

    @ApiModelProperty("申请表单号")
    private String applyCode;

}
