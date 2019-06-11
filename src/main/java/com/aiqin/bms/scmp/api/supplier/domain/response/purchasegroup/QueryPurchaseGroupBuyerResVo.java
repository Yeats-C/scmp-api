package com.aiqin.bms.scmp.api.supplier.domain.response.purchasegroup;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 描述: 采购组人员列表返回实体
 *
 * @Author: Kt.w
 * @Date: 2018/12/25
 * @Version 1.0
 * @since 1.0
 */
@Data
@ApiModel("采购组人员列表返回实体")
public class QueryPurchaseGroupBuyerResVo {
    @ApiModelProperty("采购人员名称")
    private String buyerName;
}
