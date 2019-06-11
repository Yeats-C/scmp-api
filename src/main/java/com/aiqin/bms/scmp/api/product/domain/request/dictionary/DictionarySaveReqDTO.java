package com.aiqin.bms.scmp.api.product.domain.request.dictionary;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@ApiModel("新增商品字典详细请求实体")
@Data
public class DictionarySaveReqDTO {
    @ApiModelProperty("商品字典名称")
    @NotEmpty(message = "商品字典名称不能为空")
    private String dictionaryName;

    @ApiModelProperty("商品字典类型")
    @NotEmpty(message = "商品字典类型不能为空")
    private String dictionaryType;

    @ApiModelProperty("供应商详细字典")
    private List<DictionaryInfoSaveReqVO> listInfo;

}
