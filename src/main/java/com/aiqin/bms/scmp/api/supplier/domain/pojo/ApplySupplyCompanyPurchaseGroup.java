package com.aiqin.bms.scmp.api.supplier.domain.pojo;

import com.aiqin.bms.scmp.api.common.CommonBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author knight.xie
 * @version 1.0
 * @className ApplySupplyCompanyPurchaseGroup
 * @date 2019/8/23 10:47
 */
@Data
@ApiModel
public class ApplySupplyCompanyPurchaseGroup extends CommonBean {

    @ApiModelProperty("主键ID")
    private Long id;
    @ApiModelProperty("采购组编码")
    private String purchasingGroupCode;
    @ApiModelProperty("采购组名称")
    private String purchasingGroupName;
    @ApiModelProperty("申请供应商编码")
    private String applySupplyCompanyCode;
    @ApiModelProperty("申请供应商名称")
    private String applySupplyCompanyName;
}
