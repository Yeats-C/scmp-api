package com.aiqin.bms.scmp.api.purchase.domain.response.reject;

import com.aiqin.bms.scmp.api.product.domain.pojo.StockBatch;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@ApiModel("接受从库存查询出来的数据")
@Data
public class RejectApplyDetailHandleResponse {

    @ApiModelProperty(value = "业务id")
    @JsonProperty("reject_apply_record_detail_id")
    private String rejectApplyRecordDetailId;

    @ApiModelProperty(value = "退货申请单号")
    @JsonProperty("reject_apply_record_code")
    private String rejectApplyRecordCode;

    @ApiModelProperty(value = "sku编码")
    @JsonProperty("sku_code")
    private String skuCode;

    @ApiModelProperty(value = "sku名称")
    @JsonProperty("sku_name")
    private String skuName;

    @ApiModelProperty(value = "供应商编码")
    @JsonProperty("supplier_code")
    private String supplierCode;

    @ApiModelProperty(value = "供应商名称")
    @JsonProperty("supplier_name")
    private String supplierName;

    @ApiModelProperty(value = "仓编码(物流中心编码)")
    @JsonProperty("transport_center_code")
    private String transportCenterCode;

    @ApiModelProperty(value = "仓名称(物流中心名称)")
    @JsonProperty("transport_center_name")
    private String transportCenterName;

    @ApiModelProperty(value = "库房编码")
    @JsonProperty("warehouse_code")
    private String warehouseCode;

    @ApiModelProperty(value = "库房名称")
    @JsonProperty("warehouse_name")
    private String warehouseName;

    @ApiModelProperty(value="单位")
    @JsonProperty("unit_code")
    private String unitCode;

    @ApiModelProperty(value="单位")
    @JsonProperty("unit_name")
    private String unitName;

    @ApiModelProperty(value = "可用库存数")
    @JsonProperty("available_count")
    private Long availableCount;

    @ApiModelProperty(value = "库存数量")
    @JsonProperty("stock_count")
    private Long stockCount;

    @ApiModelProperty(value = "含税成本")
    @JsonProperty("product_cost")
    private BigDecimal productCost;

    @ApiModelProperty(value = "最新采购价")
    @JsonProperty("new_purchase_price")
    private BigDecimal newPurchasePrice;

    @ApiModelProperty(value = "最高采购价")
    @JsonProperty("purchase_max")
    private BigDecimal purchaseMax;

    @ApiModelProperty(value = "品类编码")
    @JsonProperty("category_code")
    private String categoryId;

    @ApiModelProperty(value = "品类名称")
    @JsonProperty("category_name")
    private String categoryName;

    @ApiModelProperty(value = "品牌编码")
    @JsonProperty("brand_id")
    private String brandId;

    @ApiModelProperty(value = "品牌名称")
    @JsonProperty("brand_name")
    private String brandName;

    @ApiModelProperty(value = "规格")
    @JsonProperty("product_spec")
    private String productSpec;

    @ApiModelProperty(value="颜色编码")
    @JsonProperty("color_code")
    private String colorCode;

    @ApiModelProperty(value="颜色")
    @JsonProperty("color_name")
    private String colorName;

    @ApiModelProperty(value="型号")
    @JsonProperty("model_number")
    private String modelNumber;

    @ApiModelProperty(value = "商品类型 0商品 1赠品 2实物返")
    @JsonProperty("product_type")
    private Integer productType;

    @ApiModelProperty(value = "所属部门编码")
    @JsonProperty("product_sort_code")
    private String productSortCode;

    @ApiModelProperty(value = "所属部门名称")
    @JsonProperty("product_sort_name")
    private String productSortName;

    @ApiModelProperty(value = "税率")
    @JsonProperty("tax_rate")
    private BigDecimal taxRate;

    @ApiModelProperty(value="条形码")
    @JsonProperty("barcode")
    private String barcode;

    @ApiModelProperty(value = "采购组编码")
    @JsonProperty("purchase_group_code")
    private String purchaseGroupCode;

    @ApiModelProperty(value = "采购组名称")
    @JsonProperty("purchase_group_name")
    private String purchaseGroupName;

    @ApiModelProperty(value = "商品结算方式编码")
    @JsonProperty("settlement_method_code")
    private String settlementMethodCode;

    @ApiModelProperty(value = "商品结算")
    @JsonProperty("settlement_method_name")
    private String settlementMethodName;

    @ApiModelProperty(value="厂商sku")
    @JsonProperty("factory_sku_code")
    private String factorySkuCode;

    @ApiModelProperty(value = "spu编码")
    @JsonProperty("spu_code")
    private String spuCode;

    @ApiModelProperty(value = "spu名称")
    @JsonProperty("spu_name")
    private String spuName;

    @ApiModelProperty(value = "批次号列表")
    @JsonProperty("batch_code_list")
    private List<StockBatch> batchCodeList;


//    @ApiModelProperty(value = "申请单类型: 0 手动 1自动")
//    @JsonProperty("apply_type")
//    private Integer applyType;
//
//    @ApiModelProperty(value = "退供申请单状态: 0  已完成 1 待提交")
//    @JsonProperty("apply_record_status")
//    private Integer applyRecordStatus;
//
//    @ApiModelProperty(value = "数量")
//    @JsonProperty("product_count")
//    private Integer productCount;
//
//    @ApiModelProperty(value = "单品数量")
//    @JsonProperty("single_count")
//    private Integer singleCount;
//
//    @ApiModelProperty(value = "含税单价")
//    @JsonProperty("product_amount")
//    private BigDecimal productAmount;
//
//    @ApiModelProperty(value = "含税总价")
//    @JsonProperty("product_total_amount")
//    private BigDecimal productTotalAmount;
//
//    @ApiModelProperty(value = "商品批次号")
//    @JsonProperty("batch_code")
//    private String batchNo;
//
//    @ApiModelProperty(value = "批次备注")
//    @JsonProperty("batch_remark")
//    private String batchRemark;
//
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    @ApiModelProperty(value = "批次创建时间")
//    @JsonProperty("production_date")
//    private Date batchCreateTime;
//
//
//    @ApiModelProperty(value = "")
//    @JsonProperty("create_by_id")
//    private String createById;
//
//    @ApiModelProperty(value = "")
//    @JsonProperty("create_by_name")
//    private String createByName;
//
//    @ApiModelProperty(value = "")
//    @JsonProperty("update_by_id")
//    private String updateById;
//
//    @ApiModelProperty(value = "")
//    @JsonProperty("update_by_name")
//    private String updateByName;
//
//    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
//    @ApiModelProperty(value = "")
//    @JsonProperty("create_time")
//    private Date createTime;
//
//    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
//    @ApiModelProperty(value = "")
//    @JsonProperty("update_time")
//    private Date updateTime;

}
