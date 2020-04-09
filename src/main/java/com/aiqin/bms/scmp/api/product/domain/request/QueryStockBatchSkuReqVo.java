package com.aiqin.bms.scmp.api.product.domain.request;

import com.aiqin.bms.scmp.api.base.PagesRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@ApiModel("查询批次库存商品sku请求VO")
public class QueryStockBatchSkuReqVo extends PagesRequest implements Serializable {

    @ApiModelProperty("仓库编码")
    //@NotEmpty(message = "物流中心不能为空")
    @JsonProperty("transport_center_code")
    private String transportCenterCode;

    @ApiModelProperty("库房编码")
    //@NotEmpty(message = "库房不能为空")
    @JsonProperty("warehouse_code")
    private String warehouseCode;

    @ApiModelProperty("sku编码")
    @JsonProperty("sku_code")
    private String skuCode;

    @ApiModelProperty("sku名称")
    @JsonProperty("sku_name")
    private String skuName;

    @ApiModelProperty("状态 0:再用 1:停止进货 2:停止配送 3:停止销售")
    @JsonProperty("config_status")
    private Integer configStatus;

    @ApiModelProperty("商品属性")
    @JsonProperty("product_property_code")
    private String productPropertyCode;

    @ApiModelProperty("商品属性名称")
    @JsonProperty("product_property_name")
    private String productPropertyName;

    @ApiModelProperty("品类编码")
    @JsonProperty("product_category_code")
    private String productCategoryCode;

    @ApiModelProperty("品类名称")
    @JsonProperty("product_category_name")
    private String productCategoryName;

    @ApiModelProperty("品牌编码")
    @JsonProperty("product_brand_code")
    private String productBrandCode;

    @ApiModelProperty("品牌名称")
    @JsonProperty("product_brand_name")
    private String productBrandName;

    @ApiModelProperty("供应商code")
    @JsonProperty("supplier_code")
    private String supplierCode;

    @ApiModelProperty("采购组")
    //@NotEmpty(message = "采购组不能为空")
    @JsonProperty("purchase_group_code")
    private String purchaseGroupCode;

    @ApiModelProperty("批次号")
    @JsonProperty("batch_code")
    private String batchCode;

    @ApiModelProperty("开始库存数量")
    @JsonProperty("begin_inventory_count")
    private Long beginInventoryCount;

    @ApiModelProperty("结束库存数量")
    @JsonProperty("finish_inventory_count")
    private Long finishInventoryCount;

    @ApiModelProperty("开始可用库存数")
    @JsonProperty("begin_available_count")
    private Long beginAvailableCount;

    @ApiModelProperty("结束可用库存数")
    @JsonProperty("finish_available_count")
    private Long finishAvailableCount;

    @ApiModelProperty("生产日期")
    @JsonProperty("product_date")
    private String productDate;

    @ApiModelProperty("批次库存编码")
    @JsonProperty("stock_batch_code")
    private String stockBatchCode;

    public QueryStockBatchSkuReqVo(String transportCenterCode, String warehouseCode, String skuCode, String skuName,
                                   Integer configStatus, String productPropertyCode, String productPropertyName,
                                   String productCategoryCode, String productCategoryName,
                                   String productBrandCode, String productBrandName,
                                   String purchaseGroupCode, String batchCode, Long beginInventoryCount,
                                   Long finishInventoryCount, Long beginAvailableCount, Long finishAvailableCount,
                                   String productDate, String supplierCode) {
        this.transportCenterCode = transportCenterCode;
        this.warehouseCode = warehouseCode;
        this.skuCode = skuCode;
        this.skuName = skuName;
        this.configStatus = configStatus;
        this.productPropertyCode = productPropertyCode;
        this.productPropertyName = productPropertyName;
        this.productCategoryCode = productCategoryCode;
        this.productCategoryName = productCategoryName;
        this.productBrandCode = productBrandCode;
        this.productBrandName = productBrandName;
        this.purchaseGroupCode = purchaseGroupCode;
        this.batchCode = batchCode;
        this.beginInventoryCount = beginInventoryCount;
        this.finishInventoryCount = finishInventoryCount;
        this.beginAvailableCount = beginAvailableCount;
        this.finishAvailableCount = finishAvailableCount;
        this.productDate = productDate;
        this.supplierCode = supplierCode;
    }

    public QueryStockBatchSkuReqVo() {
    }
}
