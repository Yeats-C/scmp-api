package com.aiqin.bms.scmp.api.supplier.domain.request.dictionary;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("供应商字典请求体")
@Data
public class SupplierDictionaryReqVO{

    @ApiModelProperty("供应商字典编号")
    private Long supplierDictionaryCode;

    @ApiModelProperty("供应商字典名称")
    private String supplierDictionaryName;

    @ApiModelProperty("供应商字典类型")
    private String supplierType;

}