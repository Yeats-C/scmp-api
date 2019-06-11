package com.aiqin.bms.scmp.api.product.domain.request;

import com.aiqin.bms.scmp.api.base.PagesRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("库存request")
@Data
public class StockRequest extends PagesRequest {
    @ApiModelProperty("物流中心CodeOrName")
    @JsonProperty(value = "transport_center_text")
    private String transportCenterText;

    @ApiModelProperty("仓库CodeOrName")
    @JsonProperty(value = "warehouse_text")
    private String warehouseText;

    @ApiModelProperty("sku号")
    @JsonProperty(value = "sku_code")
    private String skuCode;

    @ApiModelProperty("sku名称")
    @JsonProperty(value = "sku_name")
    private String skuName;

    @ApiModelProperty("进货状态")
    @JsonProperty(value = "purchase_status")
    private Integer purchaseStatus;

    @ApiModelProperty("销售状态")
    @JsonProperty(value = "sale_status")
    private Integer saleStatus;

    @ApiModelProperty("品类编码CodeOrName")
    @JsonProperty(value = "product_category_text")
    private String productCategoryText;

    @ApiModelProperty("品牌编码CodeOrName")
    @JsonProperty(value = "brand_text")
    private String brandText;

    @ApiModelProperty("属性编码CodeOrName")
    @JsonProperty(value = "property_text")
    private String propertyText;

    @ApiModelProperty("库存数begin")
    @JsonProperty(value = "inventory_begin_num")
    private Long inventoryBeginNum;

    @ApiModelProperty("库存数finish")
    @JsonProperty(value = "inventory_finish_num")
    private Long inventoryFinishNum;

    @ApiModelProperty("可用库存数begin")
    @JsonProperty(value = "available_begin_num")
    private Long availableBeginNum;

    @ApiModelProperty("可用库存数finish")
    @JsonProperty(value = "available_finish_num")
    private Long availableFinishNum;

    @ApiModelProperty("公司编码：备用")
    @JsonProperty(value = "company_code")
    private Long companyCode;
}