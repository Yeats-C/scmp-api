package com.aiqin.bms.scmp.api.purchase.domain.request;

import com.aiqin.bms.scmp.api.base.PagesRequest;
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

    @ApiModelProperty(value="采购申请状态: 0 已完成 1 待提交")
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

    @ApiModelProperty(value="商品属性编码")
    @JsonProperty("product_property_code")
    private String productPropertyCode;

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

    @ApiModelProperty(value="有效采购组")
    @JsonProperty("group_code")
    private List<String> groupCode;

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
                                String supplierCode, String transportCenterCode, String brandId, String categoryId, String productPropertyCode,
                                Integer aReplenishType, Integer productReplenishType, Integer aShortageType, Integer productShortageType) {
        this.purchaseApplyId = purchaseApplyId;
        this.purchaseGroupCode = purchaseGroupCode;
        this.skuCode = skuCode;
        this.skuName = skuName;
        this.spuCode = spuCode;
        this.productName = productName;
        this.supplierCode = supplierCode;
        this.transportCenterCode = transportCenterCode;
        this.brandId = brandId;
        this.categoryId = categoryId;
        this.productPropertyCode = productPropertyCode;
        this.aReplenishType = aReplenishType;
        this.productReplenishType = productReplenishType;
        this.aShortageType = aShortageType;
        this.productShortageType = productShortageType;
    }
}
