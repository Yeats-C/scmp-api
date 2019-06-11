package com.aiqin.bms.scmp.api.supplier.domain.request.tag;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author knight.xie
 * @version 1.0
 * @className UpdateTagReqVo
 * @date 2019/4/29 14:48
 * @description TODO
 */

@ApiModel("修改标签")
@Data
public class UpdateTagReqVo {

    @ApiModelProperty("主键Id")
    private Long id;

    @ApiModelProperty("标签编码")
    private String tagCode;

    @ApiModelProperty("标签名称")
    private String tagName;

    @ApiModelProperty("标签类型编码  从供应商字典表里面取")
    private String tagTypeCode;

    @ApiModelProperty("标签类型名称  从供应商字典表里面取")
    private String tagTypeName;

    @ApiModelProperty("启用禁用状态 0:启用 1:禁用")
    private Byte enable;
}
