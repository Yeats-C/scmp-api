package com.aiqin.bms.scmp.api.product.domain.request.dictionary;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@ApiModel("商品字典详细")
public class DictionaryInfoSaveReqVO{

    @ApiModelProperty("商品字典值")
    @NotEmpty(message = "商品字典值不能为空")
    private String DictionaryValue;

    @ApiModelProperty("商品字典类容")
    @NotEmpty(message = "商品字典类容不能为空")
    private String dictionaryContent;

    @ApiModelProperty("商品字典值排序")
    @Min(0)
    @NotNull
    private Integer dictionaryWeight;
}
