package com.aiqin.bms.scmp.api.supplier.domain.request.applycontract.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author knight.xie
 * @version 1.0
 * @className ContractPurchaseGroupResVo
 * @date 2019/6/24 16:31
 * @description TODO
 */

@Data
@ApiModel("合同采购组返回实体")
public class ApplyContractPurchaseGroupReqVo {

    @ApiModelProperty("采购组编码")
    private String purchasingGroupCode;

    @ApiModelProperty("采购组名称")
    private String purchasingGroupName;
}
