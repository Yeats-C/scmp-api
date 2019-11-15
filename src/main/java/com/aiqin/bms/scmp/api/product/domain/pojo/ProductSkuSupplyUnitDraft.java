package com.aiqin.bms.scmp.api.product.domain.pojo;

import com.aiqin.bms.scmp.api.common.CommonBean;
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

    @ApiModelProperty("税率")
    private Long taxRate;

    @ApiModelProperty("厂商SKU编码")
    private String factorySkuCode;

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

    @ApiModelProperty("错误信息")
    private String error;

    @ApiModelProperty("申请类型")
    private Byte applyType;

    @ApiModelProperty("公司名称")
    private String companyCode;

    @ApiModelProperty("公司编码")
    private String companyName;

    @ApiModelProperty("是否显示此条记录(0:显示 1:不显示)")
    private Byte applyShow;
}