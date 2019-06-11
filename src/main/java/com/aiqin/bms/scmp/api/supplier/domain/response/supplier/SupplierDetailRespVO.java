package com.aiqin.bms.scmp.api.supplier.domain.response.supplier;

import com.aiqin.bms.scmp.api.supplier.domain.response.LogData;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @功能说明:供应商详情返回
 * @author: wangxu
 * @date: 2018/12/12 0012 15:01
 */
@ApiModel("供应商详情返回")
@Data
public class SupplierDetailRespVO {
    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("供应商编号")
    private String supplierCode;

    @ApiModelProperty("供应商名称")
    private String supplierName;

    @ApiModelProperty("简称")
    private String supplierAbbreviation;

    @ApiModelProperty("是否禁用 0:启用,1:禁用")
    private Byte enable;

    @ApiModelProperty("直属上级编码")
    private String directSupervisorCode;

    @ApiModelProperty("直属上级名称")
    private String directSupervisorName;

    @ApiModelProperty("操作日志列表")
    private List<LogData> logDataList;


}
