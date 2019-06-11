package com.aiqin.bms.scmp.api.product.domain.request.sku.store;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @功能说明:
 * @author: wangxu
 * @date: 2019/2/21 0021 16:10
 */
@Data
@ApiModel("查询质检报告请求参数")
public class QueryInspectionReportReqVO {
    @ApiModelProperty(value = "销售码")
    @JsonProperty("sale_code")
    private String saleCode;

    @ApiModelProperty("生产日期")
    @JsonProperty("production_date")
    private Date productionDate;
}
