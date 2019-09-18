package com.aiqin.bms.scmp.api.product.domain.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;
import org.apache.ibatis.annotations.Case;

@Data
public class SkuAddExport extends BaseRowModel {
    @ExcelProperty(index = 0, value = "编号")
    private String skuCode;

    @ExcelProperty(index = 1, value = "名称")
    private String skuName;

    @ExcelProperty(index = 2, value = "简称")
    private String skuAbbreviation;

    @ExcelProperty(index = 3, value = "助记码")
    private String mnemonicCode;

    @ExcelProperty(index = 4, value = "规格")
    private String spec;

    @ExcelProperty(index = 5, value = "单位")
    private String unitName;

    @ExcelProperty(index = 6, value = "数量小数位")
    private String decimalPlaces;

    @ExcelProperty(index = 7, value = "型号")
    private String applicableMonthAge;

    @ExcelProperty(index = 8, value = "等级")
    private String level;

    @ExcelProperty(index = 9, value = "是否管理保质期")
    private String qualityAssuranceManagement;

    @ExcelProperty(index = 10, value = "保质期")
    private String qualityDate;

    @ExcelProperty(index = 11, value = "保质期单位")
    private String qualityNumber;

    @ExcelProperty(index = 12, value = "是否管理生产日期")
    private String qualityAssuranceManagement2;

    @ExcelProperty(index = 13, value = "毛重")
    private String boxGrossWeight;

    @ExcelProperty(index = 14, value = "毛重单位")
    private String boxGrossWeightUnit  = "千克";

    @ExcelProperty(index = 15, value = "品牌")
    private String productBrandName;

    @ExcelProperty(index = 16, value = "品类编号")
    private String productCategoryCode;

    @ExcelProperty(index = 17, value = "品类名称")
    private String productCategoryName;

    @ExcelProperty(index = 18, value = "录入码标签号")
    private String entryCodeTagNumber = "-";

    @ExcelProperty(index = 19, value = "管理方式")
    private String managementStyleName;

    @ExcelProperty(index = 20, value = "唯一码类别")
    private String salesCode;

    @ExcelProperty(index = 21, value = "拆零系数")
    private String zeroRemovalCoefficient;

    @ExcelProperty(index = 22, value = "销项税率")
    private String outputTaxRate;

    @ExcelProperty(index = 23, value = "进项税率")
    private String inputTaxRate;

    @ExcelProperty(index = 24, value = "结账方式")
    private String settlementMethodName;

    @ExcelProperty(index = 25, value = "采购主管岗位")
    private String procurementSectionName;

    @ExcelProperty(index = 26, value = "商品属性")
    private String productPropertyName;

    @ExcelProperty(index = 27, value = "积分系数")
    private String integralCoefficient;

    @ExcelProperty(index = 28, value = "厂商指导价")
    private String manufacturerGuidePrice;

    @ExcelProperty(index = 29, value = "售价1")
    private String salePrice;

    @ExcelProperty(index = 30, value = "售价2")
    private String memberPrice;

    @ExcelProperty(index = 31, value = "售价3")
    private String salePrice3;

    public String getSalePrice3() {
        return salePrice;
    }

    public String getSalePrice4() {
        return salePrice;
    }

    @ExcelProperty(index = 32, value = "售价4")
    private String salePrice4;

    @ExcelProperty(index = 33, value = "分销机构可自管")
    private String distributionAgencyCanSelfManage = "否";

    @ExcelProperty(index = 34, value = "供货渠道类别")
    private String categoriesSupplyChannelsName;

    @ExcelProperty(index = 35, value = "基本价")
    private String basePrice;

    @ExcelProperty(index = 36, value = "加点数1")
    private String addPoints1;

    public String getAddPoints1() {
        return addPoints2;
    }

    @ExcelProperty(index = 37, value = "加点数2")
    private String addPoints2;

    @ExcelProperty(index = 38, value = "加点数3")
    private String addPoints3;

    @ExcelProperty(index = 39, value = "加点数4")
    private String addPoints4;

    @ExcelProperty(index = 40, value = "加点数5")
    private String addPoints5;

    @ExcelProperty(index = 41, value = "加点数6")
    private String addPoints6;

    @ExcelProperty(index = 42, value = "加点数7")
    private String addPoints7;

    @ExcelProperty(index = 43, value = "加点数8")
    private String addPoints8;

    @ExcelProperty(index = 44, value = "加点数9")
    private String addPoints9;

    @ExcelProperty(index = 45, value = "生产厂家")
    private String manufacturerName;

    @ExcelProperty(index = 46, value = "厂方商品编号")
    private String factoryProductNumber;

    @ExcelProperty(index = 47, value = "保修地址")
    private String address;

    @ExcelProperty(index = 48, value = "进货商品编码")
    private String inProductSkuCode;

    @ExcelProperty(index = 49, value = "进货规格")
    private String inSpec;

    @ExcelProperty(index = 50, value = "进货单位")
    private String inUnitName;

    @ExcelProperty(index = 51, value = "进货码")
    private String inPurchaseCode;

    @ExcelProperty(index = 52, value = "进货基商品含量")
    private String inBaseProductContent;

    @ExcelProperty(index = 53, value = "配送商品编码")
    private String outProductSkuCode;

    @ExcelProperty(index = 54, value = "配送规格")
    private String outSpec;

    @ExcelProperty(index = 55, value = "配送单位")
    private String outUnitName;

    @ExcelProperty(index = 56, value = "配送码")
    private String outPurchaseCode;

    @ExcelProperty(index = 57, value = "配送基商品含量")
    private String outBaseProductContent;

    @ExcelProperty(index = 58, value = "销售商品编码")
    private String saleSkuCode;

    @ExcelProperty(index = 59, value = "销售规格")
    private String saleSpec;

    @ExcelProperty(index = 60, value = "销售单位")
    private String saleUnit;

    @ExcelProperty(index = 61, value = "销售码")
    private String salesCode2;

    @ExcelProperty(index = 62, value = "销售基商品含量")
    private String saleBaseProductContent;

    @ExcelProperty(index = 63, value = "销售长")
    private String saleBoxLength;

    @ExcelProperty(index = 64, value = "销售宽")
    private String saleBoxWidth;

    @ExcelProperty(index = 65, value = "销售高")
    private String saleBoxHeight;

    @ExcelProperty(index = 66, value = "供货单位编号")
    private String supplyUnitCode;

    @ExcelProperty(index = 67, value = "供货单位名称")
    private String supplyUnitName;

    @ExcelProperty(index = 68, value = "含税进价")
    private String taxIncludedPrice;

    @ExcelProperty(index = 69, value = "联营扣率")
    private String jointFranchiseRate;

    @ExcelProperty(index = 70, value = "返点")
    private String point;

    @ExcelProperty(index = 71, value = "状态")
    private String skuStatus;

    @ExcelProperty(index = 72, value = "备注")
    private String remark;

    @ExcelProperty(index = 73, value = "特征")
    private String warehouseTypeName;

    public String getWarehouseTypeName() {
        if(this.productPropertyName.contains("A"))
        return "通货";
        if (this.productPropertyName.contains("B"))
        return "普货";
        return "大货";
    }

    @ExcelProperty(index = 74, value = "颜色")
    private String colorName;

    @ExcelProperty(index = 75, value = "波段")
    private String waveBand = "-";

    @ExcelProperty(index = 76, value = "是否记录唯一码")
    private String uniqueCode;

    @ExcelProperty(index = 77, value = "唯一码位数")
    private String uniqueCodeNumber = "0";

}
