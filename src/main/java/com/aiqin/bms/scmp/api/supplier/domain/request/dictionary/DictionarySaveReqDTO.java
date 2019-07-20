package com.aiqin.bms.scmp.api.supplier.domain.request.dictionary;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@ApiModel("新增供应商字典详细请求实体")
@Data
public class DictionarySaveReqDTO {
    @ApiModelProperty("供应商字典名称")
    @NotEmpty(message = "供应商字典名称不能为空")
    private String dictionaryName;

    @ApiModelProperty("供应商字典类型")
//    @NotEmpty(message = "供应商字典类型不能为空supplyChain->供应链,logistics->物流，商品，purchase->采购")
    private String dictionaryType;

    @ApiModelProperty("供应商详细字典")
    @NotNull
    private List<DictionaryInfoSaveReqVO> listInfo;

}
