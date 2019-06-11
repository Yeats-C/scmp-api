package com.aiqin.bms.scmp.api.supplier.domain.request.dictionary;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@ApiModel("新增供应商详情信息字典请求体")
@Data
public class DictionaryInfoSaveReqVO {
    @ApiModelProperty("供应商字典值")
    @NotEmpty(message = "供应商字典类型不能为空")
    private String DictionaryValue;

    @ApiModelProperty("供应商字典类容")
    @NotEmpty(message = "供应商字典类型不能为空")
    private String dictionaryContent;

    @ApiModelProperty("供应商字典值排序")
    @Min(0)
    private Integer dictionaryWeight;

}
