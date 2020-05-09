package com.aiqin.bms.scmp.api.product.domain.pojo;

import com.aiqin.bms.scmp.api.common.CommonBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@ApiModel("损益批次商品管理")
@Data
public class ProfitLossProductBatch extends CommonBean {
    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("调拨单编码")
    private String orderCode;

    @ApiModelProperty("sku编号")
    private String skuCode;

    @ApiModelProperty("sku名称")
    private String skuName;

    @ApiModelProperty("品类")
    private String category;

    @ApiModelProperty("品牌")
    private String brand;

    @ApiModelProperty("颜色")
    private String color;

    @ApiModelProperty("规格")
    private String specification;

    @ApiModelProperty("型号")
    private String model;

    @ApiModelProperty("单位(销售单位)")
    private String unit;

    @ApiModelProperty("类别")
    private String classes;

    @ApiModelProperty("类型")
    private String type;

    @ApiModelProperty("库存单位")
    private String inventoryUnit;

    @ApiModelProperty("库存")
    private Long inventory;

    @ApiModelProperty("税率")
    private BigDecimal tax;

    @ApiModelProperty("含税成本")
    private BigDecimal taxPrice;

    @ApiModelProperty("数量")
    private Long quantity;

    @ApiModelProperty("含税总成本")
    private BigDecimal taxAmount;

    @ApiModelProperty("图片地址")
    private String pictureUrl;

    @ApiModelProperty("行号")
    private Long lineNum;

    @ApiModelProperty("批次号")
    private String batchNumber;

    @ApiModelProperty("批次备注")
    private String batchRemark;

    @ApiModelProperty("生产日期")
    private String productDate;

    @ApiModelProperty("供应商名称")
    private String supplierName;

    @ApiModelProperty("供应商code")
    private String supplierCode;
}