package com.aiqin.bms.scmp.api.supplier.domain.response.apply;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author knight.xie
 * @version 1.0
 * @className DetailRequestRespVo
 * @date 2019/8/9 14:39
 */
@ApiModel("返回详情接口请求参数")
@Data
public class DetailRequestRespVo {

    @ApiModelProperty("申请编码")
    private String applyCode;

    @ApiModelProperty("功能项 1:供应商 2:供应商集团 3:账户")
    private String itemCode;

    @ApiModelProperty("主键Id")
    private Long id;
}
