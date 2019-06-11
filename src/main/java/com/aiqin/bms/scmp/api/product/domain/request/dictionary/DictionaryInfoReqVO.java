package com.aiqin.bms.scmp.api.product.domain.request.dictionary;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("code和名称和类型")
public class DictionaryInfoReqVO {
    @ApiModelProperty("商品字典编号")
    private String dictionaryCode;

    @ApiModelProperty("商品字典名称")
    private String dictionaryName;

    @ApiModelProperty("商品字典类型")
    private String dictionaryType;

    @ApiModelProperty(value = "公司编码", notes = "前端查询接口可以不传,但是其他第三方系统此字段必填", hidden = true)
    private String companyCode;
}
