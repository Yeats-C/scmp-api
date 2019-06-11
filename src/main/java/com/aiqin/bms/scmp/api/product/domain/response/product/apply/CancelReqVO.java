package com.aiqin.bms.scmp.api.product.domain.response.product.apply;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author knight.xie
 * @version 1.0
 * @className CancelReqVO
 * @date 2019/5/27 11:08
 * @description TODO
 */
@ApiModel("申请撤销请求VO")
@Data
public class CancelReqVO {

    @ApiModelProperty("申请编码")
    private String code;

    @ApiModelProperty("审批类型 1:商品 2.配置 3.区域")
    private Integer approvalType;
}
