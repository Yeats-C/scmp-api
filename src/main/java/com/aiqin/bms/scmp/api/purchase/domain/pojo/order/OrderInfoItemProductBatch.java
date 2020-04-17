package com.aiqin.bms.scmp.api.purchase.domain.pojo.order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel("订单商品批次表")
@Data
public class OrderInfoItemProductBatch {
    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("订单编码")
    private String orderCode;

    @ApiModelProperty("库位号")
    private String locationCode;

    @ApiModelProperty("商品行号(要废弃)")
    private Long productLineNum;

    @ApiModelProperty("商品行号(以sku为主的行号)")
    private Long lineCode;

    @ApiModelProperty("原行号（以dl传的行号）")
    private Long originalLineNum;

    @ApiModelProperty("sku编号")
    private String skuCode;

    @ApiModelProperty("sku名称")
    private String skuName;

    @ApiModelProperty("数量(要废弃)")
    private Long num;

    @ApiModelProperty("实发数量(要废弃)")
    private Long actualDeliverNum;

    @ApiModelProperty("数量(最小单位数量)")
    private Long totalCount;

    @ApiModelProperty("实发数量(实际最小单位数量)")
    private Long actualTotalCount;

    @ApiModelProperty("退货数量")
    private Long returnGoodCount;

    @ApiModelProperty("生产日期(要删除)")
    private Date productTime;

    @ApiModelProperty("生产日期")
    private String productDate;

    @ApiModelProperty("批次备注")
    private String batchRemark;

    @ApiModelProperty("批次业务id")
    private String batchId;

    @ApiModelProperty("批次号")
    private String batchNumber;

    @ApiModelProperty("批次号(要删除)")
    private String batchCode;

    @ApiModelProperty("锁定类型（1.下单 2.分配）")
    private Integer lockType;

    @ApiModelProperty("供应商code")
    private String supplierCode;

    @ApiModelProperty("供应商名称")
    private String supplierName;

    @ApiModelProperty("批次编号")
    private String batchInfoCode;

    @ApiModelProperty("wms批次编号")
    private String wmsBatchCode;

    @ApiModelProperty("过期日期")
    private String beOverdueData;

    @ApiModelProperty("创建时间")
    private Date create_time;

    @ApiModelProperty("修改时间")
    private Date update_time;

    @ApiModelProperty("创建人id")
    private String createById;

    @ApiModelProperty("修改人id")
    private String updateById;

    @ApiModelProperty("创建人名称")
    private String createByName;

    @ApiModelProperty("修改人名称")
    private String updateByName;

    @ApiModelProperty("物流中心名称")
    private String transportCenterName;

    @ApiModelProperty("物流中心编码")
    private String transportCenterCode;

    @ApiModelProperty("仓库名称")
    private String warehouseName;

    @ApiModelProperty("仓库编码")
    private String warehouseCode;

    @ApiModelProperty("公司编码")
    private String companyCode;

    @ApiModelProperty("公司名称")
    private String companyName;
}