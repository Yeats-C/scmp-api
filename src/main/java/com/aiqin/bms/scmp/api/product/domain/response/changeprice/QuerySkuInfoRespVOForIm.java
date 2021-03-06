package com.aiqin.bms.scmp.api.product.domain.response.changeprice;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel("导出选商品")
public class QuerySkuInfoRespVOForIm extends QuerySkuInfoRespVO {

    @ApiModelProperty("最新采购价")
    private BigDecimal purchasePriceNewest = BigDecimal.ZERO;

    @ApiModelProperty("供应商编码")
    private String supplierCode;

    @ApiModelProperty("供应商名称")
    private String supplierName;

    @ApiModelProperty("是否默认(0否1是)")
    private Integer beDefault;

    @ApiModelProperty("原含税采购价")
    private BigDecimal purchasePriceOld = BigDecimal.ZERO;

    @ApiModelProperty("开始生效时间")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date effectiveTimeStart;

    @ApiModelProperty("结束生效时间")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date effectiveTimeEnd;

    @ApiModelProperty("新的含税采购价")
    private BigDecimal purchasePriceNew=BigDecimal.ZERO;

    @ApiModelProperty("原含税价")
    private BigDecimal oldPrice=BigDecimal.ZERO;

    @ApiModelProperty("新含税价")
    private BigDecimal newPrice=BigDecimal.ZERO;

    @ApiModelProperty("原毛利率")
    private BigDecimal oldGrossProfitMargin=BigDecimal.ZERO;

    @ApiModelProperty("现毛利率")
    private BigDecimal newGrossProfitMargin = BigDecimal.ZERO;

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

    @ApiModelProperty("价格属性名称")
    private String priceAttributeName;

    @ApiModelProperty("错误原因")
    private String error;

}
