package com.aiqin.bms.scmp.api.purchase.domain.request;

import com.aiqin.bms.scmp.api.base.PagesRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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
@Data
public class RejectProductRequest extends PagesRequest {

    @ApiModelProperty(value="采购组 code")
    @JsonProperty("purchase_group_code")
    private String purchaseGroupCode;

    @ApiModelProperty(value="供应商编码")
    @JsonProperty("supplier_code")
    private String supplierCode;

    @ApiModelProperty(value="仓库编码")
    @JsonProperty("transport_center_code")
    private String transportCenterCode;

    @ApiModelProperty(value = "库房编码")
    @JsonProperty("warehouse_code")
    private String warehouseCode;

    @ApiModelProperty(value = "sku编码")
    @JsonProperty("sku_code")
    private String skuCode;

    @ApiModelProperty(value = "sku名称")
    @JsonProperty("sku_name")
    private String skuName;

    @ApiModelProperty(value = "品类名称")
    @JsonProperty("category_name")
    private String categoryName;

    @ApiModelProperty(value = "品牌名称")
    @JsonProperty("brand_name")
    private String brandName;

    @ApiModelProperty(value = "品类id")
    @JsonProperty("category_id")
    private String categoryId;

    @ApiModelProperty(value = "品牌id")
    @JsonProperty("brand_id")
    private String brandId;

    @ApiModelProperty(value = "商品属性")
    @JsonProperty("product_property_name")
    private String productPropertyName;

    @ApiModelProperty(value = "商品code")
    @JsonProperty("product_property_code")
    private String productPropertyCode;

    public RejectProductRequest() {
    }

    public RejectProductRequest(String categoryId,String brandId,String purchaseGroupCode, String supplierCode, String transportCenterCode, String warehouseCode, String skuCode, String skuName, String categoryName, String brandName, String productPropertyName,String productPropertyCode) {
        this.categoryId = categoryId;
        this.brandId = brandId;
        this.purchaseGroupCode = purchaseGroupCode;
        this.supplierCode = supplierCode;
        this.transportCenterCode = transportCenterCode;
        this.warehouseCode = warehouseCode;
        this.skuCode = skuCode;
        this.skuName = skuName;
        this.categoryName = categoryName;
        this.brandName = brandName;
        this.productPropertyName = productPropertyName;
        this.productPropertyCode = productPropertyCode;
    }
}
