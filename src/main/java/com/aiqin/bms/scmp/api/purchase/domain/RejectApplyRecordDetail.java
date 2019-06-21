package com.aiqin.bms.scmp.api.purchase.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel
@Data
public class RejectApplyRecordDetail {
    @ApiModelProperty(value = "")
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty(value = "业务id")
    @JsonProperty("reject_apply_record_detail_id")
    private String rejectApplyRecordDetailId;

    @ApiModelProperty(value = "退货申请单号")
    @JsonProperty("reject_apply_record_code")
    private String rejectApplyRecordCode;

    @ApiModelProperty(value = "申请单类型: 0 手动 1自动")
    @JsonProperty("apply_type")
    private Integer applyType;

    @ApiModelProperty(value = "采购组 code")
    @JsonProperty("purchase_group_code")
    private String purchaseGroupCode;

    @ApiModelProperty(value = "")
    @JsonProperty("purchase_group_name")
    private String purchaseGroupName;

    @ApiModelProperty(value = "退供申请单状态: 0  已完成 1 待提交")
    @JsonProperty("apply_record_status")
    private Integer applyRecordStatus;

    @ApiModelProperty(value = "")
    @JsonProperty("sku_code")
    private String skuCode;

    @ApiModelProperty(value = "")
    @JsonProperty("sku_name")
    private String skuName;

    @ApiModelProperty(value = "")
    @JsonProperty("product_id")
    private String productId;

    @ApiModelProperty(value = "品类id")
    @JsonProperty("category_id")
    private String categoryId;

    @ApiModelProperty(value = "")
    @JsonProperty("category_name")
    private String categoryName;

    @ApiModelProperty(value = "品牌id")
    @JsonProperty("brand_id")
    private String brandId;

    @ApiModelProperty(value = "")
    @JsonProperty("brand_name")
    private String brandName;

    @ApiModelProperty(value = "商品类型 0赠品 1商品 2实物返回")
    @JsonProperty("product_type")
    private Integer productType;

    @ApiModelProperty(value = "规格")
    @JsonProperty("product_spec")
    private String productSpec;

    @ApiModelProperty(value="单位")
    @JsonProperty("unit_code")
    private String unitCode;

    @ApiModelProperty(value="单位")
    @JsonProperty("unit_name")
    private String unitName;

    @ApiModelProperty(value = "库存数量")
    @JsonProperty("stock_count")
    private Integer stockCount;

    @ApiModelProperty(value = "数量")
    @JsonProperty("product_count")
    private Integer productCount;

    @ApiModelProperty(value = "税率")
    @JsonProperty("tax_rate")
    private Integer taxRate;

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

    @ApiModelProperty(value = "含税单价")
    @JsonProperty("product_amount")
    private Long productAmount;

    @ApiModelProperty(value = "含税总价")
    @JsonProperty("product_total_amount")
    private Long productTotalAmount;

    @ApiModelProperty(value = "")
    @JsonProperty("product_cost")
    private Long productCost;

    @ApiModelProperty(value = "商品批次号")
    @JsonProperty("batch_no")
    private String batchNo;

    @ApiModelProperty(value = "批次备注")
    @JsonProperty("batch_remark")
    private String batchRemark;

    @ApiModelProperty(value = "批次备注")
    @JsonProperty("batch_create_time")
    private Date batchCreateTime;

    @ApiModelProperty(value = "商品 结算方式")
    @JsonProperty("settlement_method")
    private String settlementMethod;

    @ApiModelProperty(value = "供应商code")
    @JsonProperty("supplier_code")
    private String supplierCode;

    @ApiModelProperty(value = "")
    @JsonProperty("supplier_name")
    private String supplierName;

    @ApiModelProperty(value = "收货区域 :省")
    @JsonProperty("province_id")
    private String provinceId;

    @ApiModelProperty(value = "")
    @JsonProperty("province_name")
    private String provinceName;

    @ApiModelProperty(value = "市")
    @JsonProperty("city_id")
    private String cityId;

    @ApiModelProperty(value = "")
    @JsonProperty("city_name")
    private String cityName;

    @ApiModelProperty(value = "县")
    @JsonProperty("district_id")
    private String districtId;

    @ApiModelProperty(value = "")
    @JsonProperty("district_name")
    private String districtName;

    @ApiModelProperty(value = "收货地址")
    @JsonProperty("address")
    private String address;

    @ApiModelProperty(value = "")
    @JsonProperty("create_by_id")
    private String createById;

    @ApiModelProperty(value = "")
    @JsonProperty("create_by_name")
    private String createByName;

    @ApiModelProperty(value = "")
    @JsonProperty("update_by_id")
    private String updateById;

    @ApiModelProperty(value = "")
    @JsonProperty("update_by_name")
    private String updateByName;

    @ApiModelProperty(value = "")
    @JsonProperty("create_time")
    private Date createTime;

    @ApiModelProperty(value = "")
    @JsonProperty("update_time")
    private Date updateTime;

}