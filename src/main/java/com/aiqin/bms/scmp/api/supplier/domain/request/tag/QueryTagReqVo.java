package com.aiqin.bms.scmp.api.supplier.domain.request.tag;

import com.aiqin.bms.scmp.api.base.PageReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author knight.xie
 * @version 1.0
 * @className QueryTagReqVo
 * @date 2019/4/29 14:38
 * @description TODO
 */
@ApiModel("列表查询请求参数")
@Data
public class QueryTagReqVo extends PageReq {

    @ApiModelProperty("标签编码")
    private String tagCode;

    @ApiModelProperty("标签名称")
    private String tagName;

    @ApiModelProperty("标签类型编码")
    private String tagTypeCode;

    @ApiModelProperty("启用禁用状态(0 启用 1 禁用)")
    private Byte enable;

    @ApiModelProperty(value = "公司编码",hidden = true)
    private String companyCode;
}
