package com.aiqin.bms.scmp.api.purchase.domain.response.order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-06-24
 * @time: 17:22
 */
@ApiModel("订单批次信息")
@Data
public class QueryOrderInfoItemBatchRespVO {

    @ApiModelProperty("sku编号")
    private String skuCode;

    @ApiModelProperty("sku名称")
    private String skuName;

    @ApiModelProperty("数量")
    private Long num;

    @ApiModelProperty("实发数量")
    private Long actualDeliverNum;

    @ApiModelProperty("生产日期")
    private Date productTime;

    @ApiModelProperty("批次备注")
    private String batchRemark;

    @ApiModelProperty("批次号")
    private String batchNumber;

    @ApiModelProperty("锁定类型")
    private Integer lockType;

    @ApiModelProperty("供应商编码")
    private String supplierCode;

    @ApiModelProperty("供应商名称")
    private String supplierName;
}
