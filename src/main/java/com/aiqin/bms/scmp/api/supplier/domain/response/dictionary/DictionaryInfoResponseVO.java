package com.aiqin.bms.scmp.api.supplier.domain.response.dictionary;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("供应商详情信息返回实体")
@Data
public class DictionaryInfoResponseVO {
    @ApiModelProperty("主键id")
    private Long id;
    @ApiModelProperty("供应商字典值")
    private String dictionaryDictionaryValue;


    @ApiModelProperty("供应商字典类容")
    private String dictionaryContent;

    @ApiModelProperty("供应商字典值排序")
    private Integer dictionaryWeight;

    @ApiModelProperty("删除标记(0:正常 1:删除)")
    private Byte delFlag;
    @ApiModelProperty("0 启用/1 禁用")
    private Byte enabled;




}