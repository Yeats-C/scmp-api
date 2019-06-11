package com.aiqin.bms.scmp.api.supplier.domain.request.dictionary;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DictiReVo {
    @ApiModelProperty("商品字典编号")
    private String dictionaryCode;

    @ApiModelProperty("商品字典名称")
    private String dictionaryName;

    @ApiModelProperty("商品字典类型")
    private String dictionaryType;
}
