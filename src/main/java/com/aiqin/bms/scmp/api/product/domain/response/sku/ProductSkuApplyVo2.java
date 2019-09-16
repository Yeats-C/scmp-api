package com.aiqin.bms.scmp.api.product.domain.response.sku;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel("审批详情列表sku信息")
public class ProductSkuApplyVo2 {
    @ApiModelProperty("申请类型")
    private String applyType;
    @ApiModelProperty("类型")
    private String goodsGifts;
    @ApiModelProperty("SKU编号")
    private String skuCode;
    @ApiModelProperty("SKU名称")
    private String skuName;
    @ApiModelProperty("SPU名称")
    private String productName;
    @ApiModelProperty("品类")
    private String productCategoryName;
    @ApiModelProperty("品牌")
    private String productBrandName;
    @ApiModelProperty("商品属性")
    private String productPropertyName;
    @ApiModelProperty("覆盖渠道")
    private String coverage;
    @ApiModelProperty("结算方式")
    private String settlementMethodName;
    @ApiModelProperty("厂商指导价")
    private Integer manufacturerGuidePrice;
    @ApiModelProperty("含税采购价")
    private Integer taxIncludedPrice;
    @ApiModelProperty("爱亲渠道价")
    private Integer aiqinDistributionPrice;
    @ApiModelProperty("萌贝树渠道价")
    private Integer mengbeishuDistributionPrice;
    @ApiModelProperty("小红马渠道价")
    private Integer xiaohongmaDistributionPrice;
    @ApiModelProperty("爱亲分销价")
    private Integer aiqinChannelPrice;
    @ApiModelProperty("萌贝树分销价")
    private Integer mengbeishuChannelPrice;
    @ApiModelProperty("小红马分销价")
    private Integer xiaohongmaChannelPrice;
    @ApiModelProperty("售价")
    private Integer salePrice;
    @ApiModelProperty("会员价")
    private Integer memberPrice;
    @ApiModelProperty("进项税率")
    private Integer outputTaxRate;
    @ApiModelProperty("销项税率")
    private Integer inputTaxRate;
    @ApiModelProperty("颜色")
    private String colorName;
    @ApiModelProperty("型号")
    private String modelNumber;
    @ApiModelProperty("保质天数")
    private Integer qualityDate;
    @ApiModelProperty("保质天数单位")
    private String qualityNumber;
    @ApiModelProperty("适用起始月龄")
    private String applicableMonthAge;
    @ApiModelProperty("唯一码管理")
    private String uniqueCode;
    @ApiModelProperty("供应商")
    private String supplyUnitName;
    @ApiModelProperty("库存规格")
    private String spec;
    @ApiModelProperty("库存单位")
    private String unitName;
    @ApiModelProperty("采购单位含量")
    private Integer inBaseProductContent;
    @ApiModelProperty("分销交易倍数")
    private Integer zeroRemovalCoefficient;
    @ApiModelProperty("销售条码")
    private String salesCode;
    @ApiModelProperty("改变值")
    private String changeContent;
    @ApiModelProperty("审批备注")
    private String approvalRemark;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("创建人")
    private String createBy;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("修改时间")
    private Date updateTime;

    @ApiModelProperty("修改人")
    private String updateBy;

    @ApiModelProperty("申请状态")
    private Integer applyStatus;

    @ApiModelProperty("表单号")
    private String formNo;

    @ApiModelProperty("审核人")
    private String auditorBy;

    @ApiModelProperty("审核时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date auditorTime;

    @ApiModelProperty("申请编码")
    private String applyCode;

    @ApiModelProperty("申请编码")
    private String purchaseGroupName;

    @ApiModelProperty("是否使用生效时间(0:是1:否)")
    private Byte selectionEffectiveTime;

    @ApiModelProperty("生效开始时间")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date selectionEffectiveStartTime;
}
