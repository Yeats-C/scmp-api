package com.aiqin.bms.scmp.api.product.domain.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@ApiModel("变价附表")
@Data
public class ProductSkuChangePriceInfo {
    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("主表编码")
    private String code;

    @ApiModelProperty("sku编码")
    private String skuCode;

    @ApiModelProperty("sku名称")
    private String skuName;

    @ApiModelProperty("最新采购价")
    private BigDecimal purchasePriceNewest;

    @ApiModelProperty("供应商编码")
    private String supplierCode;

    @ApiModelProperty("供应商名称")
    private String supplierName;

    @ApiModelProperty("是否默认(0否1是)")
    private Integer beDefault;

    @ApiModelProperty("原含税采购价")
    private BigDecimal purchasePriceOld;

    @ApiModelProperty("开始生效时间")
    private Date effectiveTimeStart;

    @ApiModelProperty("结束生效时间")
    private Date effectiveTimeEnd;

    @ApiModelProperty("新的含税采购价")
    private BigDecimal purchasePriceNew;

    @ApiModelProperty("原含税价")
    private BigDecimal oldPrice;

    @ApiModelProperty("新含税价")
    private BigDecimal newPrice;

    @ApiModelProperty("仓库批次号编码")
    private String warehouseBatchNumber;

    @ApiModelProperty("仓库批次号名称")
    private String warehouseBatchName;

    @ApiModelProperty("调价原因编码")
    private String changePriceReasonCode;

    @ApiModelProperty("调价原因描述")
    private String changePriceReasonName;

    @ApiModelProperty("临时含税价")
    private BigDecimal temporaryPrice;

    @ApiModelProperty("价格项目编码")
    private String priceItemCode;

    @ApiModelProperty("价格项目名称")
    private String priceItemName;

    @ApiModelProperty("价格类型")
    private String priceTypeCode;

    @ApiModelProperty("价格类型名称")
    private String priceTypeName;

    @ApiModelProperty("价格属性编码")
    private String priceAttributeCode;

    @ApiModelProperty("价格数据名称")
    private String priceAttributeName;

    @ApiModelProperty("公司名称")
    private String companyCode;

    @ApiModelProperty("公司编码")
    private String companyName;

    @ApiModelProperty("仓库编码")
    private String transportCenterCode;

    @ApiModelProperty("仓库名称")
    private String transportCenterName;

    @ApiModelProperty("库房编码")
    private String warehouseCode;

    @ApiModelProperty("库房名称")
    private String warehouseName;

    @ApiModelProperty("批次备注")
    private String batchRemark;

    @ApiModelProperty("正式编码")
    private String officialCode;

    @ApiModelProperty("生产日期")
    private Date productTime;

    @ApiModelProperty("是否同步(0否1是)")
    private Integer beSynchronize;

    @ApiModelProperty("原毛利率")
    private Long oldGrossProfitMargin;

    @ApiModelProperty("现毛利率")
    private Long newGrossProfitMargin;

    @ApiModelProperty("进项税率")
    private Long inTax;

    @ApiModelProperty("销项税率")
    private Long outTax;
}