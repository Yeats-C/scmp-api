package com.aiqin.bms.scmp.api.supplier.domain.response.contract;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author knight.xie
 * @version 1.0
 * @className ContractPurchaseResVo
 * @date 2019/7/1 20:14
 * @description TODO
 */
@ApiModel("外部调用合同返回数据")
@Data
public class ContractPurchaseResVo {

    @ApiModelProperty("合同编码")
    private String contractCode;

    @ApiModelProperty("合同名称")
    private String contractName;
}
