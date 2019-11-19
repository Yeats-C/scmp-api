package com.aiqin.bms.scmp.api.product.domain.request;

import com.aiqin.bms.scmp.api.base.PagesRequest;
import com.aiqin.bms.scmp.api.supplier.domain.response.purchasegroup.PurchaseGroupVo;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

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

    @ApiModelProperty("状态")
    @JsonProperty(value = "config_status")
    private Integer configStatus;

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

    @ApiModelProperty("销售库存数begin")
    @JsonProperty(value = "sale_begin_num")
    private Long saleBeginNum;

    @ApiModelProperty("销售库存数finish")
    @JsonProperty(value = "sale_finish_num")
    private Long saleFinishNum;

    @ApiModelProperty("特卖库存数begin")
    @JsonProperty(value = "special_sale_begin_num")
    private Long specialSaleBeginNum;

    @ApiModelProperty("特卖库存数finish")
    @JsonProperty(value = "special_sale_finish_num")
    private Long specialSaleFinishNum;

    @ApiModelProperty("残品库存数begin")
    @JsonProperty(value = "bad_begin_num")
    private Long badBeginNum;

    @ApiModelProperty("残品库存数finish")
    @JsonProperty(value = "bad_finish_num")
    private Long badFinishNum;

    @ApiModelProperty("公司编码：备用")
    @JsonProperty(value = "company_code")
    private Long companyCode;

    @ApiModelProperty("在途数begin")
    @JsonProperty(value = "total_way_begin_num")
    private Long totalWayBeginNum;

    @ApiModelProperty("在途数finish")
    @JsonProperty(value = "total_way_finish_num")
    private Long totalWayFinishNum;

    @ApiModelProperty("采购组编码")
    @JsonProperty(value = "procurement_section_code")
    private String procurementSectionCode;

    @ApiModelProperty("采购组名称")
    @JsonProperty(value = "procurement_section_name")
    private String procurementSectionName;

    @ApiModelProperty(value="不需要传的参数")
    private List<PurchaseGroupVo> groupList;

}