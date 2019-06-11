package com.aiqin.bms.scmp.api.product.domain.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author knight.xie
 * @version 1.0
 * @className UnLockStockReqVo
 * @date 2019/1/10 20:50
 * @description 锁库请求VO
 */
@ApiModel("锁库请求VO")
@Data
public class UnLockStockReqVo implements Serializable {

    @ApiModelProperty("原始单号")
    private String sourceOrderCode;

    @ApiModelProperty("操作人")
    private String operator;
}
