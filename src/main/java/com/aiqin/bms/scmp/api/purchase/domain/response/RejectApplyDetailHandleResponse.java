package com.aiqin.bms.scmp.api.purchase.domain.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * <p>
 * ━━━━━━神兽出没━━━━━━
 * 　　┏┓　　　┏┓+ +
 * 　┏┛┻━━━┛┻┓ + +
 * 　┃　　　　　　　┃
 * 　┃　　　━　　　┃ ++ + + +
 * ████━████ ┃+
 * 　┃　　　　　　　┃ +
 * 　┃　　　┻　　　┃
 * 　┃　　　　　　　┃
 * 　┗━┓　　　┏━┛
 * 　　　┃　　　┃                  神兽保佑, 永无BUG!
 * 　　　┃　　　┃
 * 　　　┃　　　┃     Code is far away from bug with the animal protecting
 * 　　　┃　 　　┗━━━┓
 * 　　　┃ 　　　　　　　┣┓
 * 　　　┃ 　　　　　　　┏┛
 * 　　　┗┓┓┏━┳┓┏┛
 * 　　　　┃┫┫　┃┫┫
 * 　　　　┗┻┛　┗┻┛
 * ━━━━━━感觉萌萌哒━━━━━━
 * <p>
 * <p>
 * 思维方式*热情*能力
 */
@ApiModel("接受从库存查询出来的数据")
@Data
public class RejectApplyDetailHandleResponse {

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
    @JsonProperty("procurement_section_code")
    private String purchaseGroupCode;

    @ApiModelProperty(value = "")
    @JsonProperty("procurement_section_name")
    private String purchaseGroupName;

    @ApiModelProperty(value = "退供申请单状态: 0  已完成 1 待提交")
    @JsonProperty("apply_record_status")
    private Integer applyRecordStatus;

    @ApiModelProperty(value="条形码")
    @JsonProperty("barcode")
    private String barcode;

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
    @JsonProperty("product_category_code")
    private String categoryId;

    @ApiModelProperty(value = "")
    @JsonProperty("product_category_name")
    private String categoryName;

    @ApiModelProperty(value = "品牌id")
    @JsonProperty("product_brand_code")
    private String brandId;

    @ApiModelProperty(value = "")
    @JsonProperty("product_brand_name")
    private String brandName;

    @ApiModelProperty(value = "商品类型 0赠品 1商品 2实物返回")
    @JsonProperty("goods_gifts")
    private Integer productType;

    @ApiModelProperty(value="颜色编码")
    @JsonProperty("color_code")
    private String colorCode;

    @ApiModelProperty(value="厂商sku")
    @JsonProperty("factory_sku_code")
    private String factorySkuCode;

    @ApiModelProperty(value="颜色")
    @JsonProperty("color_name")
    private String colorName;

    @ApiModelProperty(value="型号")
    @JsonProperty("model_number")
    private String modelNumber;

    @ApiModelProperty(value = "规格")
    @JsonProperty("spec")
    private String productSpec;

    @ApiModelProperty(value="单位")
    @JsonProperty("unit_code")
    private String unitCode;

    @ApiModelProperty(value="单位")
    @JsonProperty("unit_name")
    private String unitName;

    @ApiModelProperty(value = "可用库存数")
    @JsonProperty("available_num")
    private Integer stockCount;

    @ApiModelProperty(value = "数量")
    @JsonProperty("product_count")
    private Integer productCount;

    @ApiModelProperty(value = "单品数量")
    @JsonProperty("single_count")
    private Integer singleCount;

    @ApiModelProperty(value = "税率")
    @JsonProperty("input_tax_rate")
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

    @ApiModelProperty(value = "含税成本")
    @JsonProperty("tax_cost")
    private Long productCost;

    @ApiModelProperty(value = "商品批次号")
    @JsonProperty("batch_code")
    private String batchNo;

    @ApiModelProperty(value = "批次备注")
    @JsonProperty("batch_remark")
    private String batchRemark;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "批次创建时间")
    @JsonProperty("production_date")
    private Date batchCreateTime;

    @ApiModelProperty(value = "商品 结算方式")
    @JsonProperty("settlement_method_code")
    private String settlementMethodCode;

    @ApiModelProperty(value = "商品 结算方式")
    @JsonProperty("settlement_method_name")
    private String settlementMethodName;

    @ApiModelProperty(value = "供应商code")
    @JsonProperty("supplier_code")
    private String supplierCode;

    @ApiModelProperty(value = "")
    @JsonProperty("supplier_name")
    private String supplierName;

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

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "")
    @JsonProperty("create_time")
    private Date createTime;
    
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "")
    @JsonProperty("update_time")
    private Date updateTime;

}
