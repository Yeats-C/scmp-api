package com.aiqin.bms.scmp.api.supplier.domain.request.promotesales;

import com.aiqin.bms.scmp.api.base.PageReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author knight.xie
 * @version 1.0
 * @className QueryPromoteSalesReqVo
 * @date 2019/7/3 12:31

 */
@ApiModel("SKU促销查询Vo")
@Data
public class QueryPromoteSalesReqVo extends PageReq {
    @ApiModelProperty("sku编码")
    private String skuCode;

    @ApiModelProperty("sku名称")
    private String skuName;
}
