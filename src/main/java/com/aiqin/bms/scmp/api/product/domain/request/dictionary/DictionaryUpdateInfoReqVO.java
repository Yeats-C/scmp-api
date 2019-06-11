package com.aiqin.bms.scmp.api.product.domain.request.dictionary;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@ApiModel("修改供应商详情信息字典请求体")
public class DictionaryUpdateInfoReqVO {
    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("供应商字典值")
    @NotEmpty(message = "供应商字典值 不能为空")
    private String dictionaryDictionaryValue;

    @ApiModelProperty("供应商字典类容")
    @NotEmpty(message = "供应商字典类容 不能为空")
    private String dictionaryContent;

    @ApiModelProperty("供应商字典值排序")
    @NotNull
    private Integer dictionaryWeight;

    @ApiModelProperty("0 启用/1 禁用")
    @Min(0)
    @NotNull
    private Byte enabled;


}