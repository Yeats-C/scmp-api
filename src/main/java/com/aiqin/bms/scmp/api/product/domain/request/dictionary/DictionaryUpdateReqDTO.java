package com.aiqin.bms.scmp.api.product.domain.request.dictionary;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@ApiModel("修改商品字典详细请求实体")
public class DictionaryUpdateReqDTO {
    @ApiModelProperty("主键id")
    @NotNull
    private Long id;

    @ApiModelProperty("商品字典名称")
    @NotEmpty(message = "商品字典名称不能为空")
    private String dictionaryName;

    @ApiModelProperty("商品字典类型")
    @NotEmpty(message = "商品字典类型不能为空")
    private String dictionaryType;

    @ApiModelProperty("删除标记(0:正常 1:删除)")
    @Min(0)
    private Byte delFlag;

    @ApiModelProperty("商品详细字典")
    private List<DictionaryUpdateInfoReqVO> listInfo;
}
