package com.aiqin.bms.scmp.api.supplier.domain.pojo;

import com.aiqin.bms.scmp.api.common.CommonBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("标签使用记录")
@Data
public class UseTagRecord extends CommonBean {


    @ApiModelProperty("主键ID")
    private Long id;

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


}