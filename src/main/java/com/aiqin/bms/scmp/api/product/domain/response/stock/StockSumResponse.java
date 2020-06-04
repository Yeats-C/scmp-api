package com.aiqin.bms.scmp.api.product.domain.response.stock;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author: zhao shuai
 * @create: 2020-06-04
 **/
@Data
public class StockSumResponse {

    @ApiModelProperty("销售库-本页库存数")
    @JsonProperty(value = "sale_count")
    private Long saleCount;

    @ApiModelProperty("销售库-本页锁定库存数")
    @JsonProperty(value = "sale_lock_count")
    private Long saleLockCount;

    @ApiModelProperty("销售库-本页库存含税总金额")
    @JsonProperty(value = "sale_amount")
    private BigDecimal saleAmount;

    @ApiModelProperty("销售库-本页在途数")
    @JsonProperty(value = "sale_way_count")
    private Long saleWayCount;

    @ApiModelProperty("销售库-本页采购在途数")
    @JsonProperty(value = "sale_purchase_way_count")
    private Long salePurchaseWayCount;

    @ApiModelProperty("销售库-总计库存数")
    @JsonProperty(value = "total_sale_count")
    private Long totalSaleCount;

    @ApiModelProperty("销售库-总计锁定库存数")
    @JsonProperty(value = "total_sale_lock_count")
    private Long totalSaleLockCount;

    @ApiModelProperty("销售库-总计库存含税总金额")
    @JsonProperty(value = "total_sale_amount")
    private BigDecimal totalSaleAmount;

    @ApiModelProperty("销售库-总计在途数")
    @JsonProperty(value = "total_sale_way_count")
    private Long totalSaleWayCount;

    @ApiModelProperty("销售库-总计采购在途数")
    @JsonProperty(value = "total_sale_purchase_way_count")
    private Long totalSalePurchaseWayCount;

    @ApiModelProperty("特卖库-本页库存数")
    @JsonProperty(value = "special_count")
    private Long specialCount;

    @ApiModelProperty("特卖库-本页锁定库存数")
    @JsonProperty(value = "special_lock_count")
    private Long specialLockCount;

    @ApiModelProperty("特卖库-本页库存含税总金额")
    @JsonProperty(value = "special_amount")
    private BigDecimal specialAmount;

    @ApiModelProperty("特卖库-本页在途数")
    @JsonProperty(value = "special_way_count")
    private Long specialWayCount;

    @ApiModelProperty("特卖库-总计库存数")
    @JsonProperty(value = "total_special_count")
    private Long totalSpecialCount;

    @ApiModelProperty("特卖库-总计锁定库存数")
    @JsonProperty(value = "total_special_lock_count")
    private Long totalSpecialLockCount;

    @ApiModelProperty("特卖库-总计库存含税总金额")
    @JsonProperty(value = "total_special_amount")
    private BigDecimal totalSpecialAmount;

    @ApiModelProperty("特卖库-总计在途数")
    @JsonProperty(value = "total_special_way_count")
    private Long totalSpecialWayCount;

    @ApiModelProperty("残品库-本页库存数")
    @JsonProperty(value = "bad_count")
    private Long badCount;

    @ApiModelProperty("残品库-本页锁定库存数")
    @JsonProperty(value = "bad_lock_count")
    private Long badLockCount;

    @ApiModelProperty("残品库-本页库存含税总金额")
    @JsonProperty(value = "bad_amount")
    private BigDecimal badAmount;

    @ApiModelProperty("残品库-本页在途数")
    @JsonProperty(value = "bad_way_count")
    private Long badWayCount;

    @ApiModelProperty("残品库-总计库存数")
    @JsonProperty(value = "total_bad_count")
    private Long totalBadCount;

    @ApiModelProperty("残品库-总计锁定库存数")
    @JsonProperty(value = "total_bad_lock_count")
    private Long totalBadLockCount;

    @ApiModelProperty("残品库-总计库存含税总金额")
    @JsonProperty(value = "total_bad_amount")
    private BigDecimal totalBadAmount;

    @ApiModelProperty("残品库-总计在途数")
    @JsonProperty(value = "total_bad_way_count")
    private Long totalBadWayCount;

}