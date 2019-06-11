package com.aiqin.bms.scmp.api.supplier.domain.request.supplier.vo;

import com.aiqin.bms.scmp.api.base.PageReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @功能说明:
 * @author: wangxu
 * @date: 2018/12/3 0003 19:59
 */
@Data
@ApiModel("供应商集团查询")
public class QuerySupplierReqVO extends PageReq{

    @ApiModelProperty("供应商集团编码")
    private String supplierCode;

    @ApiModelProperty("供应商集团名称/简称")
    private String supplierNameOrShort;


    @ApiModelProperty("是否禁用,全部:空字符串,禁用:1,启用:0")
    private String enable;

    @ApiModelProperty(value = "公司编码", notes = "前端查询接口可以不传,但是其他第三方系统此字段必填", hidden = true)
    private String companyCode;

}
