package com.aiqin.bms.scmp.api.purchase.domain.request;

import com.aiqin.bms.scmp.api.base.PagesRequest;
import com.aiqin.bms.scmp.api.supplier.domain.response.purchasegroup.PurchaseGroupVo;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author: zhao shuai
 * @create: 2019-06-13
 **/
@Data
public class PurchaseApplyRequest extends PagesRequest {

    @ApiModelProperty(value="采购申请单id")
    @JsonProperty("purchase_apply_id")
    private String purchaseApplyId;

    @ApiModelProperty(value="采购申请单")
    @JsonProperty("purchase_apply_code")
    private String purchaseApplyCode;

    @ApiModelProperty(value="采购申请类型 0 手动 1自动")
    @JsonProperty("apply_type")
    private Integer applyType;

    @ApiModelProperty(value="采购申请状态: 0 待提交  1 已完成")
    @JsonProperty("apply_status")
    private Integer applyStatus;

    @ApiModelProperty(value="采购组编码")
    @JsonProperty("purchase_group_code")
    private String purchaseGroupCode;

    @ApiModelProperty(value="开始时间")
    @JsonProperty("begin_time")
    private String beginTime;

    @ApiModelProperty(value="结束时间")
    @JsonProperty("finish_time")
    private String finishTime;

    @ApiModelProperty(value="sku编号")
    @JsonProperty("sku_code")
    private String skuCode;

    @ApiModelProperty(value="sku名称")
    @JsonProperty("sku_name")
    private String skuName;

    @ApiModelProperty(value="spu编码")
    @JsonProperty("spu_code")
    private String spuCode;

    @ApiModelProperty(value="spu名称")
    @JsonProperty("product_name")
    private String productName;

    @ApiModelProperty(value="供应商编码")
    @JsonProperty("supplier_code")
    private String supplierCode;

    @ApiModelProperty(value="仓库编码")
    @JsonProperty("transport_center_code")
    private String transportCenterCode;

    @ApiModelProperty(value="品牌id")
    @JsonProperty("brand_id")
    private String brandId;

    @ApiModelProperty(value="品类id")
    @JsonProperty("category_id")
    private String categoryId;

    @ApiModelProperty(value="品牌名称")
    @JsonProperty("brand_name")
    private String brandName;

    @ApiModelProperty(value="品类名称")
    @JsonProperty("category_name")
    private String categoryName;

    @ApiModelProperty(value="商品属性编码")
    @JsonProperty("product_property_code")
    private String productPropertyCode;

    @ApiModelProperty(value="商品属性名称")
    @JsonProperty("product_property_name")
    private String productPropertyName;

    @ApiModelProperty(value="14大A品建议补货传值类型是否有值：0.是 1.否")
    @JsonProperty("a_replenish_type")
    private Integer aReplenishType;

    @ApiModelProperty(value="14大A品建议补货")
    @JsonProperty("a_replenish")
    private List<String> aReplenish;

    @ApiModelProperty(value="畅销商品建议补货传值类型是否有值：0.是 1.否")
    @JsonProperty("product_replenish_type")
    private Integer productReplenishType;

    @ApiModelProperty(value="畅销商品建议补货")
    @JsonProperty("product_replenish")
    private List<String> productReplenish;

    @ApiModelProperty(value="14大A品缺货传值类型是否有值：0.是 1.否")
    @JsonProperty("a_shortage_type")
    private Integer aShortageType;

    @ApiModelProperty(value="14大A品缺货")
    @JsonProperty("a_shortage")
    private List<String> aShortage;

    @ApiModelProperty(value="畅销商品缺货传值类型是否有值：0.是 1.否")
    @JsonProperty("product_shortage_type")
    private Integer productShortageType;

    @ApiModelProperty(value="畅销商品缺货")
    @JsonProperty("product_shortage")
    private List<String> productShortage;

    @ApiModelProperty(value="采购单号")
    @JsonProperty("purchase_order_code")
    private String purchaseOrderCode;

    @ApiModelProperty(value="库房编码")
    @JsonProperty("warehouse_code")
    private String warehouseCode;

    @ApiModelProperty(value="采购单状态 0.待审核 1.审核中 2.审核通过  3.备货确认 4.发货确认  5.入库开始 6.入库中 7.已入库  8.完成 9.取消 10.审核不通过")
    @JsonProperty("purchase_order_status")
    private Integer purchaseOrderStatus;

    @ApiModelProperty(value="仓储状态 0.未开始  1.确认中 2.完成")
    @JsonProperty("storage_status")
    private Integer storageStatus;

    @ApiModelProperty(value="采购方式 0 配送  1.铺采直送")
    @JsonProperty("purchase_mode")
    private Integer purchaseMode;

    @ApiModelProperty(value="不需要传的参数")
    private List<PurchaseGroupVo> groupList;

    @ApiModelProperty(value="关联审批单")
    @JsonProperty("approval_code")
    private String approvalCode;

    @ApiModelProperty(value="去重商品查询")
    @JsonProperty("search_list")
    private List<PurchaseProductSearchRequest>  searchList;

    @ApiModelProperty(value="采购单类型编码 1 普通采购 2 预采购")
    @JsonProperty("purchase_order_type_code")
    private Integer purchaseOrderTypeCode;

    public PurchaseApplyRequest() {
    }

    public PurchaseApplyRequest(String purchaseApplyCode, Integer applyType, Integer applyStatus, String purchaseGroupCode, String beginTime, String finishTime) {
        this.purchaseApplyCode = purchaseApplyCode;
        this.applyType = applyType;
        this.applyStatus = applyStatus;
        this.purchaseGroupCode = purchaseGroupCode;
        this.beginTime = beginTime;
        this.finishTime = finishTime;
    }

    public PurchaseApplyRequest(String purchaseApplyId, String purchaseGroupCode, String skuCode, String skuName, String spuCode, String productName,
                                String supplierCode, String transportCenterCode, String brandId, String brandName, String categoryId, String categoryName,
                                String productPropertyCode, String productPropertyName,Integer aReplenishType, Integer productReplenishType, Integer aShortageType,
                                Integer productShortageType) {
        this.purchaseApplyId = purchaseApplyId;
        this.purchaseGroupCode = purchaseGroupCode;
        this.skuCode = skuCode;
        this.skuName = skuName;
        this.spuCode = spuCode;
        this.productName = productName;
        this.supplierCode = supplierCode;
        this.transportCenterCode = transportCenterCode;
        this.brandId = brandId;
        this.brandName = brandName;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.productPropertyCode = productPropertyCode;
        this.productPropertyName = productPropertyName;
        this.aReplenishType = aReplenishType;
        this.productReplenishType = productReplenishType;
        this.aShortageType = aShortageType;
        this.productShortageType = productShortageType;
    }

    public PurchaseApplyRequest(String purchaseGroupCode, String beginTime, String finishTime, String supplierCode,
                                String transportCenterCode, String purchaseOrderCode, String warehouseCode,
                                Integer purchaseOrderStatus, Integer storageStatus, Integer purchaseMode, String approvalCode,
                                Integer purchaseOrderTypeCode) {
        this.purchaseGroupCode = purchaseGroupCode;
        this.beginTime = beginTime;
        this.finishTime = finishTime;
        this.supplierCode = supplierCode;
        this.transportCenterCode = transportCenterCode;
        this.purchaseOrderCode = purchaseOrderCode;
        this.warehouseCode = warehouseCode;
        this.purchaseOrderStatus = purchaseOrderStatus;
        this.storageStatus = storageStatus;
        this.purchaseMode = purchaseMode;
        this.approvalCode = approvalCode;
        this.purchaseOrderTypeCode = purchaseOrderTypeCode;
    }
}
