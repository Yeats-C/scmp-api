package com.aiqin.bms.scmp.api.product.domain.request.sku.config;

import com.aiqin.bms.scmp.api.common.CommonBean;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuSupplyUnitCapacityDraft;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author knight.xie
 * @version 1.0
 * @className UpdateProductSkuSupplyUnitReqVo
 * @date 2019/7/4 15:17
 */
@ApiModel("修改SKU供应商信息VO")
@Data
public class UpdateProductSkuSupplyUnitReqVo extends CommonBean {

    @ApiModelProperty("供应商code")
    private String supplyUnitCode;

    @ApiModelProperty("供应商名称")
    private String supplyUnitName;

    @ApiModelProperty("是否缺省（0:否,1：是）")
    private Byte isDefault;

    @ApiModelProperty("含税进价")
    private BigDecimal taxIncludedPrice;

    @ApiModelProperty("无税进价")
    private BigDecimal noTaxPurchasePrice;

    @ApiModelProperty("联营扣率")
    private BigDecimal jointFranchiseRate;

    @ApiModelProperty("返点")
    private BigDecimal point;

    @ApiModelProperty("厂商SKU编码")
    private String factorySkuCode;

    @ApiModelProperty(value ="供货渠道类别code")
    private String categoriesSupplyChannelsCode;

    @ApiModelProperty(value ="供货渠道类别名称")
    private String categoriesSupplyChannelsName;

    @ApiModelProperty(value ="申请类型 1：新增，2：修改")
    private Byte applyType;

    @ApiModelProperty("0:未用 1:在用")
    private Byte usageStatus;

    @ApiModelProperty(value = "供应商产能信息",name = "productSkuSupplyUnitCapacities",position = 7)
    @JsonProperty("productSkuSupplyUnitCapacities")
    private List<ProductSkuSupplyUnitCapacityDraft> productSkuSupplyUnitCapacityDrafts;

    @ApiModelProperty("原含税采购价")
    private BigDecimal originTaxIncludedPrice;

    @ApiModelProperty("原毛利率")
    private BigDecimal originRateOfMargin;

    @ApiModelProperty("毛利率")
    private BigDecimal rateOfMargin;
}
