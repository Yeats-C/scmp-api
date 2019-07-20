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
@ApiModel("供应商查询")
public class QuerySupplyComReqVO extends PageReq{
    @ApiModelProperty("供应商编号")
    private String supplyCompanyCode;

    @ApiModelProperty("供应商名称/简称")
    private String supplyComNameOrShort;

    @ApiModelProperty("采购组编码")
    private String purchasingGroupCode;

    @ApiModelProperty("是否禁用 启用:0, 禁用:1 全部:空字符串")
    private String enable;

    @ApiModelProperty("省Id")
    private String provinceId;

    @ApiModelProperty("市Id")
    private String cityId;

    @ApiModelProperty("区县Id")
    private String districtId;

    @ApiModelProperty(value = "公司编码", notes = "前端查询接口可以不传,但是其他第三方系统此字段必填", hidden = true)
    private String companyCode;

    @ApiModelProperty("供应商集团编号")
    private String supplierCode;

    @ApiModelProperty(value = "当前登录人",hidden = true)
    private String personId;
}
