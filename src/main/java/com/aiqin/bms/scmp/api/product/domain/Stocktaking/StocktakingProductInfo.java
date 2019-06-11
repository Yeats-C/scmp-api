package com.aiqin.bms.scmp.api.product.domain.Stocktaking;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author hzy
 * @description 盘点商品信息
 * @date 2019/03/18
 */
@Data
@ApiModel("盘点商品信息")
public class StocktakingProductInfo {
	
	@ApiModelProperty(value = "ID")
    @JsonProperty("Stocktaking_product_id")
    private String StocktakingProductId;
	
	@ApiModelProperty(value = "ID")
    @JsonProperty("stocktaking_id")
    private String stocktakingId;

    @ApiModelProperty(value = "spu_code")
    @JsonProperty("spu_code")
    private String spuCode;

    @ApiModelProperty(value = "sku_code")
    @JsonProperty("sku_code")
    private String skuCode;

//    @ApiModelProperty(value = "分销机构id")
//    @JsonProperty("distributor_id")
//    private String distributorId;
//
//    @ApiModelProperty(value = "分销机构编码")
//    @JsonProperty("distributor_code")
//    private String distributorCode;
//
//    @ApiModelProperty(value = "分销机构名称")
//    @JsonProperty("distributor_name")
//    private String distributorName;

//    @ApiModelProperty(value = "仓库类型，1为门店自有仓，2为爱亲大仓")
//    @JsonProperty("storage_type")
//    private Integer storageType;
//    
//    @ApiModelProperty(value = "仓位类型，1:陈列仓位 2:退货仓位 3:存储仓位")
//    @JsonProperty("stock_type")
//    private Integer stockType;

    @ApiModelProperty(value = "条形码")
    @JsonProperty("bar_code")
    private String barCode;

    @ApiModelProperty(value = "商品id")
    @JsonProperty("product_id")
    private String productId;

    @ApiModelProperty(value = "商品编码")
    @JsonProperty("product_code")
    private String productCode;

    @ApiModelProperty(value = "商品名称")
    @JsonProperty("product_name")
    private String productName;

    @ApiModelProperty(value = "商品分类id")
    @JsonProperty("category_id")
    private String categoryId;

    @ApiModelProperty(value = "商品分类名称")
    @JsonProperty("category_name")
    private String categoryName;

    @ApiModelProperty(value = "规格")
    @JsonProperty("spec")
    private String spec;

    @ApiModelProperty("颜色code")
    @JsonProperty("color_code")
    private String colorCode;

    @ApiModelProperty("颜色")
    @JsonProperty("color_name")
    private String colorName;

    @ApiModelProperty("型号")
    @JsonProperty("model_number")
    private String modelNumber;

    @ApiModelProperty("单位code")
    @JsonProperty("unit_code")
    private String unitCode;

    @ApiModelProperty(value = "单位")
    @JsonProperty("unit")
    private String unit;

    @ApiModelProperty(value = "列表名称")
    @JsonProperty("show_name")
    private String showName;
    
    @ApiModelProperty(value = "列表图")
    @JsonProperty("logo")
    private String logo;

    @ApiModelProperty(value = "封面图")
    @JsonProperty("images")
    private String images;
//    
//    @ApiModelProperty("进货价 = 成本价")
//    @JsonProperty("purchase_price")
//    private Integer purchasePrice;
    
    @ApiModelProperty(value = "库存数量")
    @JsonProperty("total_stock")
    private Integer totalStock;
    
    @ApiModelProperty(value = "盘点数量")
    @JsonProperty("real_stock")
    private Integer realStock;

    @ApiModelProperty(value = "盈亏数量")
    @JsonProperty("differ_amount")
    private Integer differAmount;
    
    @ApiModelProperty(value = "盈亏状态1:正常2:盘亏3:盘赢")
    @JsonProperty("differ_status")
    private Integer differStatus;

    @ApiModelProperty(value = "create_time")
    @JsonProperty("create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @ApiModelProperty(value = "update_time")
    @JsonProperty("update_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    @ApiModelProperty(value = "create_by")
    @JsonProperty("create_by")
    private String createBy;

    @ApiModelProperty(value = "update_by")
    @JsonProperty("update_by")
    private String updateBy;
}
