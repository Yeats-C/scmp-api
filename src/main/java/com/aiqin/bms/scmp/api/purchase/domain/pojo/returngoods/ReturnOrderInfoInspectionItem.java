package com.aiqin.bms.scmp.api.purchase.domain.pojo.returngoods;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel("退货验货保存vo信息（参数有多要就传多少）")
@Data
public class ReturnOrderInfoInspectionItem {
    @ApiModelProperty("商品主键")
    private Long id;

    @ApiModelProperty("退货订单主表编码")
    private String returnOrderCode;

    @ApiModelProperty("sku编号")
    private String skuCode;

    @ApiModelProperty("sku名称")
    private String skuName;

    @ApiModelProperty("图片地址")
    private String pictureUrl;

    @ApiModelProperty("规格")
    private String spec;

    @ApiModelProperty("规格编码")
    private String specCode;

    @ApiModelProperty("颜色名称")
    private String colorName;

    @ApiModelProperty("颜色编码")
    private String colorCode;

    @ApiModelProperty("型号")
    private String model;

    @ApiModelProperty("型号编码")
    private String modelCode;

    @ApiModelProperty("基商品含量")
    private Integer baseProductContent;

    @ApiModelProperty("商品单位code")
    private String unitCode;

    @ApiModelProperty("商品单位")
    private String unitName;

    @ApiModelProperty("拆零系数")
    private Integer zeroDisassemblyCoefficient;

    @ApiModelProperty("是否是赠品(0否1是)")
    private Integer givePromotion;

    @ApiModelProperty("批次号")
    private String batchCode;

    @ApiModelProperty("数量")
    private Long num;

    @ApiModelProperty("活动编码(多个，隔开）")
    private String activityCode;

    @ApiModelProperty("商品行号")
    private Long productLineNum;

    @ApiModelProperty("赠品行号")
    private Long promotionLineNum;

    @ApiModelProperty("原sku的行号")
    private Integer originalLineNum;

    @ApiModelProperty("商品状态1新品2残品")
    private Integer productStatus;

    @ApiModelProperty("实际入库数量")
    private Integer actualInboundNum;

    @ApiModelProperty("公司名称")
    private String companyName;

    @ApiModelProperty("公司编码")
    private String companyCode;

    @ApiModelProperty("物流中心名称")
    private String transportCenterName;

    @ApiModelProperty("物流中心编码")
    private String transportCenterCode;

    @ApiModelProperty("仓库名称")
    private String warehouseName;

    @ApiModelProperty("仓库编码")
    private String warehouseCode;

    @ApiModelProperty("供应商编码")
    private String  supplierCode;

    @ApiModelProperty("供应商名称")
    private String  supplierName;

    @ApiModelProperty("如果手动输入批次号，那么必须传入生产日期")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    private Date productDate;

    @ApiModelProperty(value = "批次编码")
    private String batchInfoCode;

    @ApiModelProperty(value = "库位号")
    private String locationCode;

    @ApiModelProperty(value = "过期日期")
    private String beOverdueDate;
}