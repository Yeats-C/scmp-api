package com.aiqin.bms.scmp.api.supplier.domain.response.contract;

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
@ApiModel("合同采购组返回实体")
public class ContractPurchaseGroupResVo {

    @ApiModelProperty("主键id")
    private String id;

    @ApiModelProperty("所属合同code")
    private String contractCode;

    @ApiModelProperty("采购组编码")
    private String purchasingGroupCode;

    @ApiModelProperty("采购组名称")
    private String purchasingGroupName;
}
