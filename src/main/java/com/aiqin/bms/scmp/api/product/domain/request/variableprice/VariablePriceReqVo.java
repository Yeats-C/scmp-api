package com.aiqin.bms.scmp.api.product.domain.request.variableprice;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@ApiModel("新增变价")
@Data
public class VariablePriceReqVo {

    @ApiModelProperty("采购组名称")
    private String variablePriceCode;

    @NotEmpty(message = "价格类型code不能为空")
    @ApiModelProperty("价格类型code")
    private String priceTypeCode;

    @ApiModelProperty("价格类型")
    private String priceTypeName;

    @ApiModelProperty("变价名称")
    private String variablePriceName;

    @ApiModelProperty("采购组编码")
    private String procurementSectionCode;

    @ApiModelProperty("采购组名称")
    private String procurementSectionName;

    @ApiModelProperty("备注")
    private String remark;
   //保存后状态是待提交，提交后状态是带审核
    @ApiModelProperty("(0:保存->待提交,1:提交->待审核，2：审核通过3:审核不通过)")
    private Byte status;

    private List<VariablePriceListReqVo>variablePriceListReqVos;
}
