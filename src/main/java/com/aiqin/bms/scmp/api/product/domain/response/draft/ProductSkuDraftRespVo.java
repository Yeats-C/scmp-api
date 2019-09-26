package com.aiqin.bms.scmp.api.product.domain.response.draft;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author knight.xie
 * @version 1.0
 * @className ProductSkuDraftRespVo
 * @date 2019/5/10 09:53
 * @description 商品申请单管理-商品信息数据
 */
@ApiModel("商品申请单管理-商品信息数据")
@Data
public class ProductSkuDraftRespVo {

    @ApiModelProperty(value = "申请类型")
    private Byte applyType;

    @ApiModelProperty(value = "申请类型名称")
    private String applyTypeName;

    @ApiModelProperty(value = "编号")
    private String code;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty("采购组编码")
    private String purchaseGroupCode;

    @ApiModelProperty("采购组名称")
    private String purchaseGroupName;

    @ApiModelProperty("商品品牌code")
    private String productBrandCode;

    @ApiModelProperty("商品品牌")
    private String productBrandName;

    @ApiModelProperty("商品品类code")
    private String productCategoryCode;

    @ApiModelProperty("商品品类名称")
    private String productCategoryName;

    @ApiModelProperty("所属商品编码")
    private String productCode;

    @ApiModelProperty("所属商品名称")
    private String productName;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("创建人")
    private String createBy;

    @ApiModelProperty(value = "图片文件夹编码")
    private String picFolderCode;

    @ApiModelProperty(value = "改变内容",hidden = true)
    private String changeContent;

    @ApiModelProperty(value = "来源 0供应链1供应商平台",hidden = true)
    private Integer originalCode;

    @ApiModelProperty("类型")
    private String goodsGifts;
    @ApiModelProperty("SKU编号")
    private String skuCode;
    @ApiModelProperty("SKU名称")
    private String skuName;
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
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("修改时间")
    private Date updateTime;

    @ApiModelProperty("修改人")
    private String updateBy;

}
