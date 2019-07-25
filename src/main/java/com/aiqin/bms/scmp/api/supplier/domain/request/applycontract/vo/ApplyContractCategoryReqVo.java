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
@ApiModel("合同品类请求实体")
public class ApplyContractCategoryReqVo {

    @ApiModelProperty("品类编码")
    private String categoryCode;

    @ApiModelProperty("品类名称")
    private String categoryName;
}
