package com.aiqin.bms.scmp.api.supplier.domain.response.dictionary;

import com.aiqin.bms.scmp.api.common.CommonBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("供应商字典详细信息表")
@Data
public class QuerySupplierDictionaryInfo extends CommonBean{
    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("供应商字典值")
    private String supplierDictionaryValue;

    @ApiModelProperty("供应商字典内容")
    private String supplierContent;

    @ApiModelProperty("供应商字典值排序")
    private Integer supplierWeight;

    @ApiModelProperty("供应商字典code")
    private String supplierDictionaryCode;

    /*@ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("创建人")
    private String createBy;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("更新人")
    private String updateBy;

    @ApiModelProperty("删除标记(0:正常 1:删除)")
    private Byte delFlag;

    */

    @ApiModelProperty("供应商字典名称")
    private String supplierDictionaryName;
}