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

    @ApiModelProperty("实发数量")
    private Long actualDeliverNum;

    @ApiModelProperty("生产日期")
    private Date productTime;

    @ApiModelProperty("过期日期")
    private String beOverdueData;

    @ApiModelProperty("批次备注")
    private String batchRemark;

    @ApiModelProperty("批次号")
    private String batchNumber;

    @ApiModelProperty("锁定类型")
    private Integer lockType;

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

    @ApiModelProperty("供应商code")
    private String supplierCode;

    @ApiModelProperty("供应商名称")
    private String supplierName;

}