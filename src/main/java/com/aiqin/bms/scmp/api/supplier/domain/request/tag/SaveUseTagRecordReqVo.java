package com.aiqin.bms.scmp.api.supplier.domain.request.tag;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author knight.xie
 * @version 1.0
 * @className SaveUseTagRecordReqVo
 * @date 2019/4/30 14:45

 */
@ApiModel("保存标签使用信息")
@Data
public class SaveUseTagRecordReqVo {

    @ApiModelProperty(value = "使用对象编码")
    private String useObjectCode;

    @ApiModelProperty(value = "使用对象名称")
    private String useObjectName;

    @ApiModelProperty("标签类型编码")
    private String tagTypeCode;

    @ApiModelProperty("标签类型名称")
    private String tagTypeName;

    @ApiModelProperty("来源单号")
    private String sourceCode;

    @ApiModelProperty("标签类型名称")
    List<SaveUseTagRecordItemReqVo> itemReqVos;
}
