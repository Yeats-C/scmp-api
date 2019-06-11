package com.aiqin.bms.scmp.api.supplier.domain.response.supplier;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @功能说明:供应商申请信息返回对象
 * @author: wangxu
 * @date: 2018/12/7 0007 15:32
 */
@ApiModel("供应商申请信息返回对象")
@Data
public class ApplySupplierInfoRespVO {
    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("申请供应商名称")
    private String applySupplierName;

    @ApiModelProperty("简称")
    private String applySupplierAbbreviation;

    @ApiModelProperty("申请供应商集团编码")
    private String supplierCode;

    @ApiModelProperty("是否禁用")
    private String enable;

    @ApiModelProperty("直属上级编码")
    private String directSupervisorCode;

    @ApiModelProperty("直属上级名称")
    private String directSupervisorName;
}
