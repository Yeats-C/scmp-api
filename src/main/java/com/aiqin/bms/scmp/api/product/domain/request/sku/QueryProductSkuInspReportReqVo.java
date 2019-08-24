package com.aiqin.bms.scmp.api.product.domain.request.sku;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author knight.xie
 * @version 1.0
 * @className QueryProductSkuInspReportReqVo
 * @date 2019/7/3 18:38
 */
@ApiModel("查询请求接口")
@Data
public class QueryProductSkuInspReportReqVo {

    @ApiModelProperty("生产日期")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date productionDate;

    @ApiModelProperty("sku编码")
    private String skuCode;
}
