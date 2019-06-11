package com.aiqin.bms.scmp.api.supplier.domain.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("字典类型集合")
@Data
public class DictionaryType {
    @ApiModelProperty("供应商字典类型code")
    private String dictionaryType;

    @ApiModelProperty("供应商字典类型name")
    private String dictionaryTypeName;

}
