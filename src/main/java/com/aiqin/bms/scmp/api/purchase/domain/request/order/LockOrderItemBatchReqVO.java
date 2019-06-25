package com.aiqin.bms.scmp.api.purchase.domain.request.order;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Description:
 * 锁库请求vo
 * @author: NullPointException
 * @date: 2019-06-21
 * @time: 14:31
 */
@Data
public class LockOrderItemBatchReqVO {

    @ApiModelProperty("物流中心名称")
    private String transportCenterName;

    @ApiModelProperty("物流中心编码")
    private String transportCenterCode;

    @ApiModelProperty("仓库名称")
    private String warehouseName;

    @ApiModelProperty("仓库编码")
    private String warehouseCode;

    @ApiModelProperty("订单编码")
    private String orderCode;

    @ApiModelProperty("商品行号")
    private Long productLineNum;

    @ApiModelProperty("原行号")
    private Long originalLineNum;

    @ApiModelProperty("sku编号")
    private String skuCode;

    @ApiModelProperty("sku名称")
    private String skuName;

    @ApiModelProperty("数量")
    private Long num;

}
