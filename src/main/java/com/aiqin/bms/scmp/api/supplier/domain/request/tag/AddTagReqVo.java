package com.aiqin.bms.scmp.api.supplier.domain.request.tag;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author knight.xie
 * @version 1.0
 * @className AddTagReqVo
 * @date 2019/4/29 14:48
 * @description TODO
 */

@ApiModel("新增标签")
@Data
public class AddTagReqVo {

    @ApiModelProperty("标签名称")
    private String tagName;

    @ApiModelProperty("标签类型编码  从供应商字典表里面取")
    private String tagTypeCode;

    @ApiModelProperty("标签类型名称  从供应商字典表里面取")
    private String tagTypeName;
}
