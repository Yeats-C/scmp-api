package com.aiqin.bms.scmp.api.supplier.domain.response.applycontract;

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
@ApiModel("合同品类返回实体")
public class ApplyContractCategoryResVo {

    @ApiModelProperty("主键id")
    private String id;

    @ApiModelProperty("申请合同编号")
    private String appContractCode;

    @ApiModelProperty("品类编码")
    private String categoryCode;

    @ApiModelProperty("品类名称")
    private String categoryName;
}
