package com.aiqin.bms.scmp.api.product.domain.request;

import com.aiqin.bms.scmp.api.base.PageReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@ApiModel("查询条件")
@Data
public class OperationLogVo extends PageReq {
    @ApiModelProperty("对象类型(供应商，合同等)")
    @NotNull
    private Byte objectType;
    @ApiModelProperty("对象id")
    private String objectId;
}
