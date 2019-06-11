package com.aiqin.bms.scmp.api.product.domain.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author knight.xie
 * @className VerifyReturnSupplyErrorRespVo
 * @date 2019/1/8 15:01
 * @description 退供单错误返回VO
 * @version 1.0
 */
@Data
@ApiModel("退供单错误返回VO")
public class VerifyReturnSupplyErrorRespVo implements Serializable {

    @ApiModelProperty("sku编号")
    private String skuCode;

    @ApiModelProperty("错误原因")
    private String errorReason;

    public VerifyReturnSupplyErrorRespVo(String skuCode, String errorReason) {
        this.skuCode = skuCode;
        this.errorReason = errorReason;
    }
}
