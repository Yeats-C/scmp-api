package com.aiqin.bms.scmp.api.product.domain.response.sku.purchase;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @功能说明:
 * @author wangxu
 * @date 2018/12/28 0028 15:52
 */
@ApiModel("校验采购单商品对象返回vo")
@Data
public class CheckPurchaseItemRespVO {

    @ApiModelProperty("sku编号")
    private String skuCode;

    @ApiModelProperty("sku状态，0为启用，1为禁用")
    private Byte skuStatus;

    @ApiModelProperty("删除标记(0:正常 1:删除)")
    private Byte delFlag;
}
