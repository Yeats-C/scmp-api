package com.aiqin.bms.scmp.api.product.domain.request.sku;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author knight.xie
 * @version 1.0
 * @className SaveProductSkuInspReportReqVo
 * @date 2019/7/3 17:39
 */
@ApiModel("质检报告上传请求ItemVO")
@Data
public class SaveProductSkuInspReportItemReqVo {

    @ApiModelProperty("生产日期")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date productionDate;

    @ApiModelProperty("质检报告文件路径")
    private String inspectionReportPath;
}
