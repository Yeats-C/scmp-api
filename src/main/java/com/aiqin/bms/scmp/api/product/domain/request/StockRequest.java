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

    @ApiModelProperty("sku号")
    @JsonProperty(value = "sku_code")
    private String skuCode;

    @ApiModelProperty("sku名称")
    @JsonProperty(value = "sku_name")
    private String skuName;

    @ApiModelProperty("状态")
    @JsonProperty(value = "config_status")
    private Integer configStatus;

    @ApiModelProperty("属性编码")
    @JsonProperty(value = "product_property_code")
    private String productPropertyCode;

    @ApiModelProperty("品类编码")
    @JsonProperty(value = "product_category_code")
    private String productCategoryCode;

    @ApiModelProperty("品牌编码")
    @JsonProperty(value = "product_brand_code")
    private String productBrandCode;

    @ApiModelProperty("库存数begin")
    @JsonProperty(value = "inventory_begin_count")
    private Long inventoryBeginCount;

    @ApiModelProperty("库存数finish")
    @JsonProperty(value = "inventory_finish_count")
    private Long inventoryFinishCount;

    @ApiModelProperty("可用库存数begin")
    @JsonProperty(value = "available_begin_count")
    private Long availableBeginCount;

    @ApiModelProperty("可用库存数finish")
    @JsonProperty(value = "available_finish_count")
    private Long availableFinishCount;

    @ApiModelProperty("销售库存数begin")
    @JsonProperty(value = "sale_begin_count")
    private Long saleBeginCount;

    @ApiModelProperty("销售库存数finish")
    @JsonProperty(value = "sale_finish_count")
    private Long saleFinishCount;

    @ApiModelProperty("特卖库存数begin")
    @JsonProperty(value = "special_sale_begin_count")
    private Long specialSaleBeginCount;

    @ApiModelProperty("特卖库存数finish")
    @JsonProperty(value = "special_sale_finish_count")
    private Long specialSaleFinishCount;

    @ApiModelProperty("残品库存数begin")
    @JsonProperty(value = "bad_begin_count")
    private Long badBeginCount;

    @ApiModelProperty("残品库存数finish")
    @JsonProperty(value = "bad_finish_count")
    private Long badFinishCount;

    @ApiModelProperty("仓库")
    @JsonProperty(value = "transport_center_code")
    private String transportCenterCode;

    @ApiModelProperty("库房")
    @JsonProperty(value = "warehouse_code")
    private String warehouseCode;

    @ApiModelProperty("公司编码")
    @JsonProperty(value = "company_code")
    private Long companyCode;

    @ApiModelProperty("在途数begin")
    @JsonProperty(value = "total_way_begin_count")
    private Long totalWayBeginCount;

    @ApiModelProperty("在途数finish")
    @JsonProperty(value = "total_way_finish_count")
    private Long totalWayFinishCount;

    @ApiModelProperty("采购组编码")
    @JsonProperty(value = "purchase_group_code")
    private String purchaseGroupCode;

    @ApiModelProperty("采购组名称")
    @JsonProperty(value = "purchase_group_name")
    private String purchaseGroupName;

    @ApiModelProperty(value="不需要传的参数")
    private List<PurchaseGroupVo> groupList;

    @ApiModelProperty("销售条形码")
    @JsonProperty(value = "sales_code")
    private String salesCode;

    @ApiModelProperty("汇总方式 0.商品 1.品类 2.品牌")
    @JsonProperty(value = "collect_type")
    private Integer collectType;

}