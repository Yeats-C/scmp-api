package com.aiqin.bms.scmp.api.supplier.domain.request.dictionary;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@ApiModel("code和名称和类型")
public class DictionaryInfoReqVO {
    @ApiModelProperty("商品字典编号")
    private String dictionaryCode;

    @ApiModelProperty("商品字典名称")
    private String dictionaryName;
    @NotEmpty(message = "供应商字典类型不能为空supplyChain->供应链,logistics->物流，商品，purchase->采购")
    @ApiModelProperty("商品字典类型")
    private String dictionaryType;

    @ApiModelProperty(value = "公司编码", notes = "前端查询接口可以不传,但是其他第三方系统此字段必填", hidden = true)
    private String companyCode;
}
