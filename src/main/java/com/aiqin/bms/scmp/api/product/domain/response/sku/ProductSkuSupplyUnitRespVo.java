package com.aiqin.bms.scmp.api.product.domain.response.sku;

import com.aiqin.bms.scmp.api.common.CommonBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author knight.xie
 * @version 1.0
 * @className ProductSkuSupplyUnitRespVo
 * @date 2019/5/14 15:46
 */
@ApiModel("SKU供应商信息返回")
@Data
public class ProductSkuSupplyUnitRespVo extends CommonBean {
    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("供应商编码")
    private String supplyUnitCode;

    @ApiModelProperty("供应商名称")
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

    @ApiModelProperty(value = "供应商产能信息",position = 7)
    private List<ProductSkuSupplyUnitCapacityRespVo> productSkuSupplyUnitCapacities;
}
