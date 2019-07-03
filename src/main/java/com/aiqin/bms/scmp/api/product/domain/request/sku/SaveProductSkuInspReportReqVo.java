package com.aiqin.bms.scmp.api.product.domain.request.sku;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author knight.xie
 * @version 1.0
 * @className SaveProductSkuInspReportReqVo
 * @date 2019/7/3 17:39
 * @description TODO
 */
@ApiModel("质检报告上传请求VO")
@Data
public class SaveProductSkuInspReportReqVo {

    @ApiModelProperty("sku编码")
    private String skuCode;

    @ApiModelProperty("sku名称")
    private String skuName;

    @ApiModelProperty("质检报告信息")
    private List<SaveProductSkuInspReportItemReqVo> itemList;

}
