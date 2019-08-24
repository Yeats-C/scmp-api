package com.aiqin.bms.scmp.api.supplier.domain.response.tag;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author knight.xie
 * @version 1.0
 * @date 2019/6/4 10:32

 */
@Data
@ApiModel("标签使用详情")
public class DetailTagUseRespVo {

    @ApiModelProperty("标签编码")
    private String tagCode;

    @ApiModelProperty("标签名称")
    private String tagName;

    @ApiModelProperty("使用对象编码")
    private String useObjectCode;

    @ApiModelProperty("使用对象名称")
    private String useObjectName;

    @ApiModelProperty("标签类型编码")
    private String tagTypeCode;

    @ApiModelProperty("标签类型名称")
    private String tagTypeName;

    @ApiModelProperty("来源单号")
    private String sourceCode;

    @ApiModelProperty("使用次数")
    private Integer useNum;
}
