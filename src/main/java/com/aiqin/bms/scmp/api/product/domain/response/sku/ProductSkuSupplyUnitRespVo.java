package com.aiqin.bms.scmp.api.product.domain.response.sku;

import com.aiqin.bms.scmp.api.common.CommonBean;
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

    @ApiModelProperty("是否生效(0未生效1已生效）")
    private Integer beEffective;

    @ApiModelProperty("是否使用生效时间(0:是1:否)")
    private Byte selectionEffectiveTime;

    @ApiModelProperty("生效开始时间")
    private Date selectionEffectiveStartTime;

    @ApiModelProperty("申请状态(0:待审 1:审核中 2:审核通过 3:审核未通过 4:已撤销)")
    private Byte auditorStatus;

    @ApiModelProperty("表单号")
    private String formNo;

    @ApiModelProperty("申请编码")
    private String applyCode;

    @ApiModelProperty("申请备注")
    private String approvalRemark;

    @ApiModelProperty("申请名称")
    private String approvalName;

    @ApiModelProperty("申请类型")
    private Byte applyType;

    @ApiModelProperty("采购组名称")
    private String purchaseGroupName;

    @ApiModelProperty(value = "供应商产能信息",position = 7)
    private List<ProductSkuSupplyUnitCapacityRespVo> productSkuSupplyUnitCapacities;

    @ApiModelProperty("原含税采购价")
    private BigDecimal originTaxIncludedPrice;

    @ApiModelProperty("原毛利率")
    private BigDecimal originRateOfMargin;

    @ApiModelProperty("毛利率")
    private BigDecimal rateOfMargin;

    @ApiModelProperty("分销价")
    private BigDecimal distributionPrice;
}
