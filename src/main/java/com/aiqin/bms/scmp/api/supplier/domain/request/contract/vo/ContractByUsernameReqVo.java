package com.aiqin.bms.scmp.api.supplier.domain.request.contract.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 描述:根据登陆人查询合同请求实体
 *
 * @Author: Kt.w
 * @Date: 2019/1/15
 * @Version 1.0
 * @since 1.0
 */
@ApiModel("根据登陆人查询合同请求实体")
@Data
public class ContractByUsernameReqVo {
    @ApiModelProperty("公司编码")
//    @NotNull(message = "公司编码不能为空")
    private String companyCode;
    @ApiModelProperty("公司编码")
//    @NotNull(message = "公司编码不能为空")
    private String companyName;

    @ApiModelProperty("创建人")
    private String createBy;

    @ApiModelProperty("编号")
    private String contractCode;
}
