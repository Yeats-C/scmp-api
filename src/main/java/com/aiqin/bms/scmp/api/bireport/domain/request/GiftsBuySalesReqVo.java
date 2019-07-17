package com.aiqin.bms.scmp.api.bireport.domain.request;

import com.aiqin.bms.scmp.api.base.PageReq;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel("赠品进销存request")
@Data
public class GiftsBuySalesReqVo extends PageReq implements Serializable {

    @ApiModelProperty("sku编码")
    @JsonProperty("sku_code")
    private String skuCode;

    @ApiModelProperty("sku名称")
    @JsonProperty("sku_name")
    private String skuName;

    @ApiModelProperty("仓库编码")
    @JsonProperty("transport_center_code")
    private String transportCenterCode;

    @ApiModelProperty("仓库名称")
    @JsonProperty("transport_center_name")
    private String transportCenterName;

    @ApiModelProperty("品类名称")
    @JsonProperty("product_category_name")
    private String productCategoryName;

    @ApiModelProperty("品类编码")
    @JsonProperty("product_category_code")
    private String productCategoryCode;

    @ApiModelProperty("库存日期(天)")
    @JsonProperty("inbound_days")
    private Integer inboundDays;

    @ApiModelProperty("部门编码")
    @JsonProperty("product_sort_code")
    private String productSortCode;

    @ApiModelProperty("所属部门")
    @JsonProperty("product_sort_name")
    private String productSortName;

    @ApiModelProperty("时间begin")
    @JsonProperty("begin_run_time")
    private String beginRunTime;

    @ApiModelProperty("时间finish")
    @JsonProperty("finish_run_time")
    private String finishRunTime;

    @ApiModelProperty("库房编码")
    @JsonProperty("warehouse_code")
    private String warehouseCode;

    @ApiModelProperty("库房名称")
    @JsonProperty("warehouse_name")
    private String warehouseName;

    @ApiModelProperty("周转天数begin")
    @JsonProperty("begin_turnover_days")
    private Integer beginTurnoverDays;

    @ApiModelProperty("周转天数finish")
    @JsonProperty("finish_turnover_days")
    private Integer finishTurnoverDays;

    @ApiModelProperty("一级品类")
    @JsonProperty("product_category_one")
    private String productCategoryOne;

    @ApiModelProperty("二级品类")
    @JsonProperty("product_category_two")
    private String productCategoryTwo;

    @ApiModelProperty("三级品类")
    @JsonProperty("product_category_three")
    private String productCategoryThree;

    @ApiModelProperty("四级品类")
    @JsonProperty("product_category_four")
    private String productCategoryFour;

    public GiftsBuySalesReqVo(String skuCode, String skuName, String transportCenterCode, String transportCenterName, String productCategoryName, String productCategoryCode, Integer inboundDays, String productSortCode, String productSortName, String beginRunTime, String finishRunTime, String warehouseCode, String warehouseName, Integer beginTurnoverDays, Integer finishTurnoverDays, String productCategoryOne, String productCategoryTwo, String productCategoryThree, String productCategoryFour) {
        this.skuCode = skuCode;
        this.skuName = skuName;
        this.transportCenterCode = transportCenterCode;
        this.transportCenterName = transportCenterName;
        this.productCategoryName = productCategoryName;
        this.productCategoryCode = productCategoryCode;
        this.inboundDays = inboundDays;
        this.productSortCode = productSortCode;
        this.productSortName = productSortName;
        this.beginRunTime = beginRunTime;
        this.finishRunTime = finishRunTime;
        this.warehouseCode = warehouseCode;
        this.warehouseName = warehouseName;
        this.beginTurnoverDays = beginTurnoverDays;
        this.finishTurnoverDays = finishTurnoverDays;
        this.productCategoryOne = productCategoryOne;
        this.productCategoryTwo = productCategoryTwo;
        this.productCategoryThree = productCategoryThree;
        this.productCategoryFour = productCategoryFour;
    }

    public GiftsBuySalesReqVo() {
    }
}
