package com.aiqin.bms.scmp.api.product.domain.request.returnsupply;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Description:
 * 通过退供生成出库单vo
 * @author: zth
 * @date: 2019-03-01
 * @time: 16:58
 */
@Data
@ApiModel("通过退供生成出库单vo")
public class ReturnSupplyToOutBoundReqVo {

    @ApiModelProperty("主表数据")
    ReturnSupply returnSupply;

    @ApiModelProperty("商品数据")
    List<ReturnSupplyItem> returnSupplyItems;
}
