package com.aiqin.bms.scmp.api.supplier.domain.pojo;

import com.aiqin.bms.scmp.api.common.CommonBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("申请合同采购组")
@Data
public class ApplyContractPurchaseGroup extends CommonBean {

    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("申请合同编号")
    private String applyContractCode;

    @ApiModelProperty("采购组编码")
    private String purchasingGroupCode;

    @ApiModelProperty("采购组名称")
    private String purchasingGroupName;

}