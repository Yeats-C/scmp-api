package com.aiqin.bms.scmp.api.supplier.domain.response.supplier;

import com.aiqin.bms.scmp.api.supplier.domain.response.LogData;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @功能说明:供应商申请详情返回
 * @author: wangxu
 * @date: 2018/12/7 0007 11:25
 */
@ApiModel("供应商申请详情返回")
@Data
public class ApplySupplierDetailRespVO {
    @ApiModelProperty("申请信息")
    private ApplyInfoRespVO applyInfoRespVO;

    @ApiModelProperty("供应商申请信息")
    private ApplySupplierInfoRespVO applySupplierInfoRespVO;

    @ApiModelProperty("操作日志列表")
    private List<LogData> logDataList;



}
