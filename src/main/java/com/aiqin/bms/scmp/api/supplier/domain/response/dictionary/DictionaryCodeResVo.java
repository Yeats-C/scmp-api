package com.aiqin.bms.scmp.api.supplier.domain.response.dictionary;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("任意code查询")
@Data
public class DictionaryCodeResVo {

    @ApiModelProperty("供应商字典值")
    private String dictionaryDictionaryValue;


    @ApiModelProperty("供应商字典类容")
    private String dictionaryContent;

    @ApiModelProperty("供应商字典值排序")
    private Integer dictionaryWeight;


    @ApiModelProperty("供应商详细code")
    private String dictionaryInfoCode;
}
