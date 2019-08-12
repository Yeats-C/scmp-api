package com.aiqin.bms.scmp.api.supplier.domain.request.dictionary;

import com.aiqin.bms.scmp.api.base.PageReq;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel("供应商字典详细查询")
@Data
public class QueryDictionaryReqVO extends PageReq {
    @ApiModelProperty("创建时间开始 yyyy-MM-dd hh:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd hh:mm:ss")
    private Date createTimeStart;

    @ApiModelProperty("创建时间结束 yyyy-MM-dd hh:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd hh:mm:ss")
    private Date createTimeEnd;

    @ApiModelProperty("创建人")
    private String createBy;

    @ApiModelProperty("修改人")
    private String updateBy;

    @ApiModelProperty("字典类型")
    private String dictionaryType;

    @ApiModelProperty("字典名称")
    private String dictionaryName;

    @ApiModelProperty("字典名称")
    private String dictionaryCode;

    @ApiModelProperty("0 启用/1 禁用")
    private Byte enabled;

    @ApiModelProperty(value = "公司编码",notes = "前端查询接口可以不传,但是其他第三方系统此字段必填",hidden = true)
    private String companyCode;

}