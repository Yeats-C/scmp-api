package com.aiqin.bms.scmp.api.product.domain.response.variableprice;

import com.aiqin.bms.scmp.api.product.domain.pojo.VariablePriceSku;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@ApiModel("确认变价list")
@Data
public class ConfirmPriceList {
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

    @ApiModelProperty("变价code")
    private String variablePriceCode;

    private List<VariablePriceSku> variablePriceSkus;
}
