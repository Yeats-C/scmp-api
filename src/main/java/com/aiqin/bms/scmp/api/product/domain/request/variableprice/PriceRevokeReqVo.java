package com.aiqin.bms.scmp.api.product.domain.request.variableprice;

import com.aiqin.bms.scmp.api.common.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@ApiModel("变价撤销")
public class PriceRevokeReqVo {
    @ApiModelProperty("变价code")
    @NotEmpty(message = "变价code不能为空")
    private String variablePriceCode;
    @ApiModelProperty("1:撤销)")
    private Byte priceRevoke= HandlingExceptionCode.ONE;
}
