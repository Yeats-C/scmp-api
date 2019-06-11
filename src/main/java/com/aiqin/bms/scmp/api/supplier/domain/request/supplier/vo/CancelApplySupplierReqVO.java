package com.aiqin.bms.scmp.api.supplier.domain.request.supplier.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @功能说明:
 * @author: wangxu
 * @date: 2018/12/12 0012 9:46
 */
@ApiModel("撤销供应商申请请求")
@Data
public class CancelApplySupplierReqVO {
    @ApiModelProperty("供应商申请主键id")
    private Long id;
}
