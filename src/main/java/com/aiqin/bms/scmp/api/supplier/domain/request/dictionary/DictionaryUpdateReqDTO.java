package com.aiqin.bms.scmp.api.supplier.domain.request.dictionary;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@ApiModel("修改供应商字典详细请求实体")
@Data
public class DictionaryUpdateReqDTO{

    @ApiModelProperty("主键id")
    @NotNull
    private Long id;

    @ApiModelProperty("供应商字典名称")
    @NotEmpty(message = "供应商字典名称不能为空")
    private String dictionaryName;

    @ApiModelProperty("供应商字典类型")
//    @NotEmpty(message = "供应商字典类型不能为空")
    private String dictionaryType;

    @ApiModelProperty("删除标记(0:正常 1:删除)")
    @Min(0)
    private Byte delFlag;

    @ApiModelProperty("0启用1禁用")
    private Byte enabled;

    @NotNull
    private List<DictionaryUpdateInfoReqVO> listInfo;
}