package com.aiqin.bms.scmp.api.supplier.domain.request;

import com.aiqin.bms.scmp.api.base.PageReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("申请信息查询vo")
@Data
public class QueryApplySupplyListComReqVO extends PageReq {

    @ApiModelProperty("供货单位编码")
    private String supplyCode;

    @ApiModelProperty("申请类型1新增2修改")
    private Byte applyType;

    @ApiModelProperty("供货单位编码")
    private String supplyName;

    @ApiModelProperty("采购组")
    private String purchaseGroupCode;

    @ApiModelProperty("采购组名称")
    private String purchaseGroupName;

    @ApiModelProperty("省Id")
    private String provinceId;

    @ApiModelProperty("市Id")
    private String cityId;

    @ApiModelProperty("区县Id")
    private String districtId;

    @ApiModelProperty("公司编码")
    private String companyCode;

    @ApiModelProperty(value = "当前登录人",hidden = true)
    private String personId;

}
