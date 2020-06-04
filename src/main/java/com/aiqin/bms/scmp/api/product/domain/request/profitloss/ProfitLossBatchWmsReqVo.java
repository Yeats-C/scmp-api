package com.aiqin.bms.scmp.api.product.domain.request.profitloss;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("损溢wms回传商品批次表")
@Data
public class ProfitLossBatchWmsReqVo {

    @ApiModelProperty(value = "行号")
    private Long lineCode;

    @ApiModelProperty(value = "SKU编号")
    private String skuCode;

    @ApiModelProperty(value = "批次号")
    private String batchCode;

    @ApiModelProperty(value = "生产日期")
    private String productDate;

    @ApiModelProperty(value = "过期日期")
    private String beOverdueData;

    @ApiModelProperty(value = "批次备注")
    private String batchRemark;

    @ApiModelProperty(value = "实际最小单位数量")
    private Long actualTotalCount;

    @ApiModelProperty("供应商名称")
    private String supplierName;

    @ApiModelProperty("供应商编码")
    private String supplierCode;
}
