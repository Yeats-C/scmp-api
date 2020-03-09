package com.aiqin.bms.scmp.api.supplier.domain.excel.im;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author knight.xie
 * @version 1.0
 * @className ContractImportNew
 * @date 2019/9/2 15:10
 */
@Data
@ApiModel("合同新增导入模板")
public class ContractImportNew extends BaseRowModel {

    public static final String HEADER = "ContractImportNew(yearName=合同名称, year=年度, supplierName=供应商名称, contractTypeName=合同类型, startTime=合同开始日期, endTime=合同终止日期, minAmount=最低起订金额, checkoutDate=结账日, deliveryCycle=送货周期, taxRate=税率, discount=折扣, returnGuarantee=退换货保证, returnGuaranteeDay=退换货保证天数, warranty=质保金, categoriesSupplyChannelsName=供货渠道类别, contractCost=合同费用, averageGrossMargin=平均毛利率, contractProperty=合同属性, caReqVos=品类, brandReqVos=品牌, purchaseGroupReqVos=采购组, remark=备注, settlementMethodName=付款方式, paymentPeriod=付款期, shippingFee=配送费, deliveryCharges=送货费承担方, unloadingFee=卸货费承担方, returnRate=固定返利比例, fixedRebateType=计量方式, comment=备注)";

    @ApiModelProperty("合同名称")
    @ExcelProperty(index = 0 , value = "合同名称")
    private String yearName;

    @ApiModelProperty("年度")
    @ExcelProperty(index = 1 , value = "年度")
    private String year;

    @ApiModelProperty("供应商名称")
    @ExcelProperty(index = 2 , value = "供应商名称")
    private String supplierName;

    @ApiModelProperty("合同类型")
    @ExcelProperty(index = 3 , value = "合同类型")
    private String contractTypeName;

    @ApiModelProperty("起始日期")
    @ExcelProperty(index = 4 , value = "起始日期", format = "yyyy-MM-dd")
    private String startTime;

    @ApiModelProperty("结束日期")
    @ExcelProperty(index = 5 , value = "结束日期", format = "yyyy-MM-dd")
    private String endTime;

    @ApiModelProperty("最低起订金额")
    @ExcelProperty(index = 6 , value = "最低起订金额")
    private String minAmount;

    @ApiModelProperty("结账日")
    @ExcelProperty(index = 7 , value = "结账日")
    private String checkoutDate;

    @ApiModelProperty("送货周期")
    @ExcelProperty(index = 8 , value = "送货周期")
    private String deliveryCycle;

    @ApiModelProperty("税率")
    @ExcelProperty(index = 9 , value = "税率")
    private String taxRate;

    @ApiModelProperty("折扣")
    @ExcelProperty(index = 10 , value = "折扣")
    private String discount;

    @ApiModelProperty("退换货保证(0保证 1不保证)")
    @ExcelProperty(index = 11 , value = "退换货保证")
    private String returnGuarantee;

    @ApiModelProperty("退换货保证天数")
    @ExcelProperty(index = 12 , value = "退换货保证天数")
    private String returnGuaranteeDay;

    @ApiModelProperty("质保金")
    @ExcelProperty(index = 13 , value = "质保金")
    private String warranty;

    @ApiModelProperty("供货渠道名称")
    @ExcelProperty(index = 14 , value = "供货渠道名称")
    private String categoriesSupplyChannelsName;

    @ApiModelProperty("合同费用")
    @ExcelProperty(index = 15 , value = "合同费用")
    private String contractCost;

    @ApiModelProperty("平均毛利率")
    @ExcelProperty(index = 16 , value = "平均毛利率")
    private String averageGrossMargin;

    @ApiModelProperty("合同属性")
    @ExcelProperty(index = 17 , value = "合同属性")
    private String contractProperty;

    @ApiModelProperty("品类")
    @ExcelProperty(index = 18 , value = "品类")
    private String caReqVos;

    @ApiModelProperty("品牌")
    @ExcelProperty(index = 19 , value = "品牌")
    private String brandReqVos;

    @ApiModelProperty("采购组")
    @ExcelProperty(index = 20 , value = "采购组")
    private String purchaseGroupReqVos;

    @ApiModelProperty("备注")
    @ExcelProperty(index = 21 , value = "备注")
    private String remark;

    @ApiModelProperty("付款方式")
    @ExcelProperty(index = 22 , value = "付款方式")
    private String settlementMethodName;

    @ApiModelProperty("付款期")
    @ExcelProperty(index = 23 , value = "付款期")
    private String paymentPeriod;

    @ApiModelProperty("配送费")
    @ExcelProperty(index = 24 , value = "配送费")
    private String shippingFee;

    @ApiModelProperty("送货费承担方(甲方,乙方承担)")
    @ExcelProperty(index = 25 , value = "送货费承担方")
    private String deliveryCharges;

    @ApiModelProperty("卸货费(甲方，乙方承担)")
    @ExcelProperty(index = 26 , value = "卸货费")
    private String unloadingFee;

    @ApiModelProperty("固定返利返利率")
    @ExcelProperty(index = 27 , value = "固定返利返利率")
    private String returnRate;

    @ApiModelProperty("固定返利类型(未税,含税)")
    @ExcelProperty(index = 28 , value = "固定返利类型")
    private String fixedRebateType;

    @ApiModelProperty("备注")
    @ExcelProperty(index = 29 , value = "备注")
    private String comment;




}
