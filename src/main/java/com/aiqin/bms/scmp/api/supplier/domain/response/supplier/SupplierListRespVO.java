package com.aiqin.bms.scmp.api.supplier.domain.response.supplier;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @功能说明:供应商集团管理列表返回
 * @author: wangxu
 * @date: 2018/12/7 0007 17:45
 */
@ApiModel("供应商集团管理列表返回")
@Data
public class SupplierListRespVO {

    @ApiModelProperty("供应商集团主键ID")
    private Long id;

    @ApiModelProperty("供应商集团编码")
    private String supplierCode;

    @ApiModelProperty("供应商集团名称")
    private String supplierName;

    @ApiModelProperty("供应商集团简称")
    private String supplierAbbreviation;

    @ApiModelProperty("是否禁用")
    private String enable;

    @ApiModelProperty("创建人")
    private String updateBy;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("创建时间")
    private Date updateTime;

    @ApiModelProperty("下属公司数量")
    private Long subSupplierCompanyCount;

    @ApiModelProperty("申请状态")
    private String applyStatus;


}
