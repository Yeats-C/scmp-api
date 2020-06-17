package com.aiqin.bms.scmp.api.purchase.domain.pojo.returngoods;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("退货验货保存vo信息（参数有多要就传多少）")
@Data
public class ReturnOrderInfoInspectionItem {
    @ApiModelProperty(value = "商品主键")
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty(value = "订单主表编码")
    @JsonProperty("return_order_code")
    private String returnOrderCode;

    @ApiModelProperty(value = "sku编号")
    @JsonProperty("sku_code")
    private String skuCode;

    @ApiModelProperty(value = "SKU名称")
    @JsonProperty("sku_name")
    private String skuName;

    @ApiModelProperty(value = "图片地址")
    @JsonProperty("picture_url")
    private String pictureUrl;

    @ApiModelProperty(value = "规格")
    @JsonProperty("spec")
    private String spec;

    @ApiModelProperty(value = "颜色名称")
    @JsonProperty("color_name")
    private String colorName;

    @ApiModelProperty(value = "颜色编码")
    @JsonProperty("color_code")
    private String colorCode;

    @ApiModelProperty(value = "型号")
    @JsonProperty("model")
    private String model;

    @ApiModelProperty(value = "基商品含量")
    @JsonProperty("base_product_content")
    private Integer baseProductContent;

    @ApiModelProperty(value = "商品单位code")
    @JsonProperty("unit_code")
    private String unitCode;

    @ApiModelProperty(value = "商品单位")
    @JsonProperty("unit_name")
    private String unitName;

    @ApiModelProperty(value = "拆零系数")
    @JsonProperty("zero_disassembly_coefficient")
    private Integer zeroDisassemblyCoefficient;

    @ApiModelProperty(value = "商品状态1新品2残品")
    @JsonProperty("product_status")
    private Integer productStatus;

    @ApiModelProperty(value = "是否是赠品(0否1是)")
    @JsonProperty("give_promotion")
    private Integer givePromotion;

    @ApiModelProperty(value = "行号")
    @JsonProperty("line_code")
    private Long lineCode;

    @ApiModelProperty(value = "仓库编码")
    @JsonProperty("transport_center_code")
    private String transportCenterCode;

    @ApiModelProperty(value = "仓库名称")
    @JsonProperty("transport_center_name")
    private String transportCenterName;

    @ApiModelProperty(value = "库房编码")
    @JsonProperty("warehouse_code")
    private String warehouseCode;

    @ApiModelProperty(value = "库房名称")
    @JsonProperty("warehouse_name")
    private String warehouseName;

    @ApiModelProperty(value = "供应商编码")
    @JsonProperty("supplier_code")
    private String supplierCode;

    @ApiModelProperty(value = "供应商名称")
    @JsonProperty("supplier_name")
    private String supplierName;

    @ApiModelProperty(value = "数量")
    @JsonProperty("product_count")
    private Long productCount;

    @ApiModelProperty(value = "实际入库数量")
    @JsonProperty("actual_product_count")
    private Long actualProductCount;

    @ApiModelProperty(value = "批次号")
    @JsonProperty("batch_code")
    private String batchCode;

    @ApiModelProperty(value = "批次编号")
    @JsonProperty("batch_info_code")
    private String batchInfoCode;

    @ApiModelProperty(value = "生产日期")
    @JsonProperty("product_date")
    private String productDate;

    @ApiModelProperty(value = "过期日期")
    @JsonProperty("be_overdue_date")
    private String beOverdueDate;

    @ApiModelProperty(value = "库位号")
    @JsonProperty("location_code")
    private String locationCode;

    @ApiModelProperty(value = "可退数量")
    @JsonProperty("return_product_count")
    private Long returnProductCount;

    @ApiModelProperty(value = "批次备注")
    @JsonProperty("batch_remark")
    private String batchRemark;

    @ApiModelProperty(value = "锁定类型 1.下单锁定 2.分配锁定")
    @JsonProperty("lock_type")
    private Integer lockType;
}