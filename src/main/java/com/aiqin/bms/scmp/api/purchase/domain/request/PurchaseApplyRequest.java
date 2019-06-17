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

    @ApiModelProperty(value="商品属性 1.A 2.B 3.C 4.D")
    @JsonProperty("product_nature")
    private Integer productNature;

    @ApiModelProperty(value="14大A品建议补货")
    @JsonProperty("a_replenish")
    private List<String> aReplenish;

    @ApiModelProperty(value="畅销商品建议补货")
    @JsonProperty("product_replenish")
    private List<String> productReplenish;

    @ApiModelProperty(value="14大A品缺货")
    @JsonProperty("a_shortage")
    private List<String> aShortage;

    @ApiModelProperty(value="畅销商品缺货")
    @JsonProperty("product_shortage")
    private List<String> productShortage;

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

    public PurchaseApplyRequest(String purchaseGroupCode, String skuCode, String skuName, String spuCode, String productName,
                                String supplierCode, String transportCenterCode, String brandId, String categoryId, Integer productNature,
                                List<String> aReplenish, List<String> productReplenish, List<String> aShortage, List<String> productShortage) {
        this.purchaseGroupCode = purchaseGroupCode;
        this.skuCode = skuCode;
        this.skuName = skuName;
        this.spuCode = spuCode;
        this.productName = productName;
        this.supplierCode = supplierCode;
        this.transportCenterCode = transportCenterCode;
        this.brandId = brandId;
        this.categoryId = categoryId;
        this.productNature = productNature;
        this.aReplenish = aReplenish;
        this.productReplenish = productReplenish;
        this.aShortage = aShortage;
        this.productShortage = productShortage;
    }
}
