package com.aiqin.bms.scmp.api.product.domain.pojo;

import com.aiqin.bms.scmp.api.common.CommonBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel("商品sku申请表")
@Data
public class ApplyProductSku extends CommonBean {
    @ApiModelProperty("")
    private Long id;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("创建人")
    private String createBy;

    @ApiModelProperty("修改时间")
    private Date updateTime;

    @ApiModelProperty("修改人")
    private String updateBy;

    @ApiModelProperty("申请编码")
    private String applyCode;

    @ApiModelProperty("删除标记(0:正常 1:删除)")
    private Byte delFlag;

    @ApiModelProperty("所属商品编码")
    private String productCode;

    @ApiModelProperty("商品名称")
    private String productName;

    @ApiModelProperty("商品品类code")
    private String productCategoryCode;

    @ApiModelProperty("商品品类名称")
    private String productCategoryName;

    @ApiModelProperty("sku编号")
    private String skuCode;

    @ApiModelProperty("规格")
    private String spec;

    @ApiModelProperty("单位code")
    private String unitCode;

    @ApiModelProperty("商品属性code")
    private String productPropertyCode;

    @ApiModelProperty("商品属性名称")
    private String productPropertyName;

    @ApiModelProperty("商品品牌code")
    private String productBrandCode;

    @ApiModelProperty("商品品牌")
    private String productBrandName;

    @ApiModelProperty("sku简称")
    private String skuAbbreviation;

    @ApiModelProperty("颜色code")
    private String colorCode;

    @ApiModelProperty("颜色名称")
    private String colorName;

    @ApiModelProperty("型号")
    private String modelNumber;

    @ApiModelProperty("单位名称")
    private String unitName;

    @ApiModelProperty("保质管理（0:管理 1:不管理）")
    private Byte qualityAssuranceManagement;

    @ApiModelProperty("商品类别code")
    private String productSortCode;

    @ApiModelProperty("商品类别名称")
    private String productSortName;

    @ApiModelProperty("保质数量")
    private String qualityNumber;

    @ApiModelProperty("保质日期")
    private String qualityDate;

    @ApiModelProperty("管理方式code")
    private String managementStyleCode;

    @ApiModelProperty("管理方式名称")
    private String managementStyleName;

    @ApiModelProperty("供货渠道类别code")
    private String categoriesSupplyChannelsCode;

    @ApiModelProperty("是否上架，0为未上架，1为上架")
    private Byte onSale;

    @ApiModelProperty("供货渠道类别名称")
    private String categoriesSupplyChannelsName;

    @ApiModelProperty("箱规")
    private String boxGauge;

    @ApiModelProperty("适用起始月龄")
    private String applicableMonthAge;

    @ApiModelProperty("返点")
    private String point;

    @ApiModelProperty("条码")
    private String barCode;

    @ApiModelProperty("助记码")
    private String mnemonicCode;

    @ApiModelProperty("是否使用生效时间(0:是1:否)")
    private Byte selectionEffectiveTime;

    @ApiModelProperty("生效起始时间")
    private String selectionEffectiveStartTime;

    @ApiModelProperty("生效结束时间")
    private String selectionEffectiveEndTime;

    @ApiModelProperty("商品/赠品(0:商品，1:赠品)")
    private Byte goodsGifts;

    @ApiModelProperty("sku名称")
    private String skuName;

    @ApiModelProperty("申请状态(0:待审 1:审核中 2:审核通过 3:审核未通过 4:已撤销)")
    private Byte applyStatus;

    @ApiModelProperty("审核人")
    private String auditorBy;

    @ApiModelProperty("审核时间")
    private Date auditorTime;

    @ApiModelProperty("是否爱亲主推(0:否，1:是)")
    private Byte isMainPush;

    @ApiModelProperty("是否新品(0:否，1:是)")
    private Byte newProduct;

    @ApiModelProperty("厂家指导价")
    private Long manufacturerGuidePrice;

    @ApiModelProperty("拆零系数")
    private Long zeroRemovalCoefficient;

    @ApiModelProperty("采购组编码")
    private String procurementSectionCode;

    @ApiModelProperty("采购组名称")
    private String procurementSectionName;

    @ApiModelProperty("审批流程编码")
    private String formNo;

    @ApiModelProperty("库存模式(0:有库存销售 1:无库存销售)")
    private Byte inventoryModel;

    @ApiModelProperty("使用时长")
    private Integer useTime;

    @ApiModelProperty("唯一码管理(0:是 1:否)")
    private Byte uniqueCode;

}