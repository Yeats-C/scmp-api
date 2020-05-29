package com.aiqin.bms.scmp.api.purchase.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@ApiModel
@Data
public class RejectApplyRecordDetail {
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty(value="业务id")
    @JsonProperty("reject_apply_record_detail_id")
    private String rejectApplyRecordDetailId;

    @ApiModelProperty(value="退货申请单号")
    @JsonProperty("reject_apply_record_code")
    private String rejectApplyRecordCode;

    @ApiModelProperty(value="采购组编码")
    @JsonProperty("purchase_group_code")
    private String purchaseGroupCode;

    @ApiModelProperty(value="采购组名称")
    @JsonProperty("purchase_group_name")
    private String purchaseGroupName;

    @ApiModelProperty(value="仓编码(物流中心编码)")
    @JsonProperty("transport_center_code")
    private String transportCenterCode;

    @ApiModelProperty(value="仓名称(物流中心名称)")
    @JsonProperty("transport_center_name")
    private String transportCenterName;

    @ApiModelProperty(value="库房编码")
    @JsonProperty("warehouse_code")
    private String warehouseCode;

    @ApiModelProperty(value="库房名称")
    @JsonProperty("warehouse_name")
    private String warehouseName;

    @ApiModelProperty(value="商品 结算方式")
    @JsonProperty("settlement_method_code")
    private String settlementMethodCode;

    @ApiModelProperty(value="商品 结算方式")
    @JsonProperty("settlement_method_name")
    private String settlementMethodName;

    @ApiModelProperty(value="供应商编码")
    @JsonProperty("supplier_code")
    private String supplierCode;

    @ApiModelProperty(value="供应商名称")
    @JsonProperty("supplier_name")
    private String supplierName;

    @ApiModelProperty(value="sku编码")
    @JsonProperty("sku_code")
    private String skuCode;

    @ApiModelProperty(value="SKU名称")
    @JsonProperty("sku_name")
    private String skuName;

    @ApiModelProperty(value="品牌编码")
    @JsonProperty("brand_id")
    private String brandId;

    @ApiModelProperty(value="品牌名称")
    @JsonProperty("brand_name")
    private String brandName;

    @ApiModelProperty(value="品类编码")
    @JsonProperty("category_id")
    private String categoryId;

    @ApiModelProperty(value="品类名称")
    @JsonProperty("category_name")
    private String categoryName;

    @ApiModelProperty(value="商品类型 0商品  1赠品 2实物返回")
    @JsonProperty("product_type")
    private Integer productType;

    @ApiModelProperty(value="条形码")
    @JsonProperty("barcode")
    private String barcode;

    @ApiModelProperty(value="规格")
    @JsonProperty("product_spec")
    private String productSpec;

    @ApiModelProperty(value="颜色")
    @JsonProperty("color_code")
    private String colorCode;

    @ApiModelProperty(value="颜色")
    @JsonProperty("color_name")
    private String colorName;

    @ApiModelProperty(value="型号")
    @JsonProperty("model_number")
    private String modelNumber;

    @ApiModelProperty(value="单位编码")
    @JsonProperty("unit_code")
    private String unitCode;

    @ApiModelProperty(value="单位")
    @JsonProperty("unit_name")
    private String unitName;

    @ApiModelProperty(value="厂商SKU编码")
    @JsonProperty("factory_sku_code")
    private String factorySkuCode;

    @ApiModelProperty(value="数量")
    @JsonProperty("product_count")
    private Long productCount;

    @ApiModelProperty(value="最小单位数量数量")
    @JsonProperty("total_count")
    private Long totalCount;

    @ApiModelProperty(value="含税单价")
    @JsonProperty("product_amount")
    private BigDecimal productAmount;

    @ApiModelProperty(value="含税总价")
    @JsonProperty("product_total_amount")
    private BigDecimal productTotalAmount;

    @ApiModelProperty(value="税率")
    @JsonProperty("tax_rate")
    private BigDecimal taxRate;

    @ApiModelProperty(value="行号")
    @JsonProperty("line_code")
    private Integer lineCode;

    @ApiModelProperty(value="批次号")
    @JsonProperty("batch_code")
    private String batchCode;

    @ApiModelProperty(value="批次备注")
    @JsonProperty("batch_remark")
    private String batchRemark;

    @ApiModelProperty(value="批次编码")
    @JsonProperty("batch_info_code")
    private String batchInfoCode;

    @ApiModelProperty(value="生产日期")
    @JsonProperty("product_date")
    private Date productDate;

    @ApiModelProperty(value="过期日期")
    @JsonProperty("be_overdue_date")
    private Date beOverdueDate;

    @ApiModelProperty(value="批次采购价")
    @JsonProperty("batch_purchase_price")
    private BigDecimal batchPurchasePrice;

    @ApiModelProperty(value="库位号")
    @JsonProperty("location_code")
    private String locationCode;

    @ApiModelProperty(value="库存含税成本")
    @JsonProperty("product_cost")
    private BigDecimal productCost;

    @ApiModelProperty(value="创建人编码")
    @JsonProperty("create_by_id")
    private String createById;

    @ApiModelProperty(value="创建人名称")
    @JsonProperty("create_by_name")
    private String createByName;

    @ApiModelProperty(value="修改人编码")
    @JsonProperty("update_by_id")
    private String updateById;

    @ApiModelProperty(value="修改人名称")
    @JsonProperty("update_by_name")
    private String updateByName;

    @ApiModelProperty(value="创建时间")
    @JsonProperty("create_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty(value="创建时间")
    @JsonProperty("update_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

}