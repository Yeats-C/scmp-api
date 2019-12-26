package com.aiqin.bms.scmp.api.product.domain.response.sku.supplier;

import com.aiqin.bms.scmp.api.common.CommonBean;
import com.aiqin.bms.scmp.api.product.domain.response.sku.ProductSkuSupplyUnitCapacityRespVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author knight.xie
 * @version 1.0
 * @className ProductSkuSupplyUnitRespVo
 * @date 2019/5/14 15:46
 */
@ApiModel("SKU供应商列表返回信息")
@Data
public class QueryProductSkuSupplyUnitsRespVo extends CommonBean {
    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("供应商编码")
    private String supplyUnitCode;

    @ApiModelProperty("供应商名称")
    private String supplyUnitName;

    @ApiModelProperty("无税进价")
    private BigDecimal noTaxPurchasePrice;

    @ApiModelProperty("含税进价")
    private BigDecimal taxIncludedPrice;

    @ApiModelProperty("联营扣率")
    private BigDecimal jointFranchiseRate;

    @ApiModelProperty("返点")
    private BigDecimal point;

    @ApiModelProperty("税率")
    private BigDecimal taxRate;

    @ApiModelProperty("厂商SKU编码")
    private String factorySkuCode;

    @ApiModelProperty("是否缺省（0:否,1：是）")
    private Byte isDefault;

    @ApiModelProperty("0:未用 1:在用")
    private Byte usageStatus;

    @ApiModelProperty("商品sku code")
    private String productSkuCode;

    @ApiModelProperty("商品sku 名称")
    private String productSkuName;

    @ApiModelProperty(value ="供货渠道类别code")
    private String categoriesSupplyChannelsCode;

    @ApiModelProperty(value ="供货渠道类别名称")
    private String categoriesSupplyChannelsName;

    @ApiModelProperty("采购组编号")
    private String purchaseGroupCode;

    @ApiModelProperty("采购组编号")
    private String purchaseGroupName;

    @ApiModelProperty("品牌名称")
    private String productBrandName;

    @ApiModelProperty("品牌编码")
    private String productBrandCode;

    @ApiModelProperty("属性名称")
    private String productPropertyName;

    @ApiModelProperty("属性编码")
    private String productPropertyCode;

    @ApiModelProperty("品类编码")
    private String productCategoryCode;

    @ApiModelProperty("品类名称")
    private String productCategoryName;

    @ApiModelProperty("申请类型 1:新增 2:修改")
    private Byte applyType;

    @ApiModelProperty("产能")
    private List<ProductSkuSupplyUnitCapacityRespVo> capacityList;

    @ApiModelProperty("原含税采购价")
    private BigDecimal originTaxIncludedPrice;

    @ApiModelProperty("原毛利率")
    private BigDecimal originRateOfMargin;

    @ApiModelProperty("毛利率")
    private BigDecimal rateOfMargin;

    @ApiModelProperty("分销价")
    private BigDecimal distributionPrice;
}
