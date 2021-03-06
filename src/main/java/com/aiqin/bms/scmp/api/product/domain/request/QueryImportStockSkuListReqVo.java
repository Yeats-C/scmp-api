package com.aiqin.bms.scmp.api.product.domain.request;

import com.aiqin.bms.scmp.api.base.PageReq;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @className QueryStockBatchSkuReqVo
 * @date 2019/6/28 11:01
 * @description 库房管理新增调拨,移库,报废列表查询
 *
 */
@Data
@ApiModel("查询库房管理商品sku请求VO")
public class QueryImportStockSkuListReqVo extends PageReq implements Serializable {

    @ApiModelProperty("调出仓库")
    @NotEmpty(message = "调出仓库不能为空")
    @JsonProperty("out_transport_center_code")
    private String outTransportCenterCode;

    @ApiModelProperty("调出库房")
    @NotEmpty(message = "调出库房不能为空")
    @JsonProperty("out_warehouse_code")
    private String outWarehouseCode;

    @ApiModelProperty("采购组")
    @NotEmpty(message = "采购组不能为空")
    @JsonProperty("procurement_section_code")
    private String procurementSectionCode;

    @ApiModelProperty("调入仓库")
    @NotEmpty(message = "调入仓库不能为空")
    @JsonProperty("in_transport_center_code")
    private String inTransportCenterCode;

    @ApiModelProperty("调入库房")
    @NotEmpty(message = "调入库房不能为空")
    @JsonProperty("in_warehouse_code")
    private String inWarehouseCode;

    @ApiModelProperty("sku编号")
    @JsonProperty("sku_code")
    private String skuCode;

    @ApiModelProperty("sku名称")
    @JsonProperty("sku_name")
    private String skuName;

    @ApiModelProperty("品牌")
    @JsonProperty("product_brand_code")
    private String productBrandCode;

    @ApiModelProperty("品类")
    @JsonProperty("product_category_code")
    private String productCategoryCode;

    @ApiModelProperty("商品属性")
    @JsonProperty("product_property_code")
    private String productPropertyCode;

    @ApiModelProperty("sku编码集合")
    @JsonProperty("sku_list")
    private List<String> skuList;

    public QueryImportStockSkuListReqVo(@NotEmpty(message = "调出仓库不能为空") String outTransportCenterCode, @NotEmpty(message = "调出库房不能为空") String outWarehouseCode, @NotEmpty(message = "采购组不能为空") String procurementSectionCode, @NotEmpty(message = "调入仓库不能为空") String inTransportCenterCode, @NotEmpty(message = "调入库房不能为空") String inWarehouseCode, String skuCode, String skuName, String productBrandCode, String productCategoryCode, String productPropertyCode, List<String> skuList) {
        this.outTransportCenterCode = outTransportCenterCode;
        this.outWarehouseCode = outWarehouseCode;
        this.procurementSectionCode = procurementSectionCode;
        this.inTransportCenterCode = inTransportCenterCode;
        this.inWarehouseCode = inWarehouseCode;
        this.skuCode = skuCode;
        this.skuName = skuName;
        this.productBrandCode = productBrandCode;
        this.productCategoryCode = productCategoryCode;
        this.productPropertyCode = productPropertyCode;
        this.skuList = skuList;
    }

    public QueryImportStockSkuListReqVo() {
    }

}
