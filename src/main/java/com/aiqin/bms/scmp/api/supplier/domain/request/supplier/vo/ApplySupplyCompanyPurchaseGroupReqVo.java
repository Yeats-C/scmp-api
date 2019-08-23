package com.aiqin.bms.scmp.api.supplier.domain.request.supplier.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author knight.xie
 * @version 1.0
 * @className ContractPurchaseGroupResVo
 * @date 2019/6/24 16:31
 */

@Data
@ApiModel("供应商采购组请求实体")
public class ApplySupplyCompanyPurchaseGroupReqVo {

    @ApiModelProperty("采购组编码")
    private String purchasingGroupCode;

    @ApiModelProperty("采购组名称")
    private String purchasingGroupName;
}
