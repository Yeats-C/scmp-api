package com.aiqin.bms.scmp.api.supplier.domain.request.dictionary;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@ApiModel("账户启用禁用")
@Data
public class EnabledSave {
    @ApiModelProperty("主键id")
    @NotNull
    @Min(0)
    private Long id;
    @ApiModelProperty("0 启用/1 禁用")
    @NotNull
    @Min(0)
    private Byte status;
}
