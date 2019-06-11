package com.aiqin.bms.scmp.api.product.domain.pojo;

import com.aiqin.bms.scmp.api.common.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@ApiModel("供应商信息")
@Data
public class ProductSkuSupplyUnitDraft extends CommonBean {
    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("供货单位code")
    private String supplyUnitCode;

    @ApiModelProperty("供货单位名称")
    private String supplyUnitName;

    @ApiModelProperty("无税进价")
    private Long noTaxPurchasePrice;

    @ApiModelProperty("含税进价")
    private Long taxIncludedPrice;

    @ApiModelProperty("联营扣率")
    private Long jointFranchiseRate;

    @ApiModelProperty("返点")
    private Long point;

    @ApiModelProperty("是否缺省（0:否,1：是）")
    private Byte isDefault;

    @ApiModelProperty("商品sku code")
    private String productSkuCode;

    @ApiModelProperty("0:未用 1:在用")
    private Byte usageStatus;

    @ApiModelProperty("商品sku 名称")
    private String productSkuName;

    @ApiModelProperty(value ="供货渠道类别code")
    private String categoriesSupplyChannelsCode;

    @ApiModelProperty(value ="供货渠道类别名称")
    private String categoriesSupplyChannelsName;

    @ApiModelProperty(value = "供应商产能信息",name = "productSkuSupplyUnitCapacities",position = 7)
    @JsonProperty("productSkuSupplyUnitCapacities")
    private List<ProductSkuSupplyUnitCapacityDraft> productSkuSupplyUnitCapacityDrafts;
}