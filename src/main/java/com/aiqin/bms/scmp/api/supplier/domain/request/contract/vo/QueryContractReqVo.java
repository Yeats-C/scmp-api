package com.aiqin.bms.scmp.api.supplier.domain.request.contract.vo;

import com.aiqin.bms.scmp.api.base.PageReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * @description:
 * @author:曾兴旺
 * @date: 2018/11/29
 */
@Data
@ApiModel("合同列表请求")
public class QueryContractReqVo  extends PageReq {

    @ApiModelProperty("合同编号")
    private String contractCode;

    @ApiModelProperty("供应商编号")
    private String supplierCode;

    @ApiModelProperty("供应商名称")
    private String supplierName;

    @ApiModelProperty("采购组编号")
    private String purchasingGroupCode;

    @ApiModelProperty(value = "公司编码", notes = "前端查询接口可以不传,但是其他第三方系统此字段必填", hidden = true)
    private String companyCode;

    @ApiModelProperty("合同名称")
    private String yearName;
}
