package com.aiqin.bms.scmp.api.supplier.domain.request.apply;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author knight.xie
 * @version 1.0
 * @className RequsetParamReqVo
 * @date 2019/8/9 14:53
 */
@ApiModel("获取请求参数vo")
@Data
public class RequsetParamReqVo {

    @ApiModelProperty("功能项 1:供应商 2:供应商集团 3:账户 5:调拨移库")
    @NotEmpty(message = "功能项不能为空")
    private String itemCode;

    @NotEmpty(message = "流程编号不能为空")
    @ApiModelProperty("流程编号")
    private String formNo;
}
