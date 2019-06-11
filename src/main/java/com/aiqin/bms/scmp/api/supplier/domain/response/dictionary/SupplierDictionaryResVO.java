package com.aiqin.bms.scmp.api.supplier.domain.response.dictionary;

import com.aiqin.bms.scmp.api.common.CommonBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("供应商字典请求体")
@Data
public class SupplierDictionaryResVO extends CommonBean {
    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("供应商字典编号")
    private String supplierCode;

    @ApiModelProperty("供应商字典名称")
    private String supplierName;

    @ApiModelProperty("供应商字典类型")
    private String supplierType;

}