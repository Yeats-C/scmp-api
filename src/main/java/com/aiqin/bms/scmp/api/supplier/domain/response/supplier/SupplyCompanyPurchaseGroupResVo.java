package com.aiqin.bms.scmp.api.supplier.domain.response.supplier;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author knight.xie
 * @version 1.0
 * @className SupplyCompanyPurchaseGroupResVo
 * @date 2019/6/24 16:31
 */

@Data
@ApiModel("供应商采购组返回实体")
public class SupplyCompanyPurchaseGroupResVo {

    @ApiModelProperty("主键id")
    private String id;

    @ApiModelProperty("供应商编码")
    private String supplyCompanyCode;

    @ApiModelProperty("供应商名称")
    private String supplyCompanyName;

    @ApiModelProperty("采购组编码")
    private String purchasingGroupCode;

    @ApiModelProperty("采购组名称")
    private String purchasingGroupName;
}
