package com.aiqin.bms.scmp.api.supplier.domain.request.apply;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author knight.xie
 * @version 1.0
 * @className DetailApplyReqVo
 * @date 2019/4/8 09:59

 */
@ApiModel("查看详情请求参数")
@Data
public class DetailApplyReqVo {

    @ApiModelProperty("功能项 1:供应商 2:供应商集团 3:账户")
    @NotEmpty(message = "功能项不能为空")
    private String itemCode;

    @NotNull(message = "主键Id不能为空")
    @ApiModelProperty("主键Id")
    private Long id;

    @NotEmpty(message = "申请编码不能为空")
    @ApiModelProperty("申请编码")
    private String applyCode;
}
