package com.aiqin.bms.scmp.api.bireport.domain.request;

import com.aiqin.bms.scmp.api.base.PageReq;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel("门店复购率request")
@Data
public class StoreRepurchaseRateReqVo extends PageReq implements Serializable {

    @ApiModelProperty("省区")
    @JsonProperty("province_name")
    private String provinceName;

    @ApiModelProperty("市")
    @JsonProperty("city_name")
    private String cityName;

    @ApiModelProperty("区")
    @JsonProperty("district_name")
    private String districtName;

    @ApiModelProperty("品类编码")
    @JsonProperty("product_category_code")
    private String productCategoryCode;

    @ApiModelProperty("品类名称")
    @JsonProperty("product_category_name")
    private String productCategoryName;

    @ApiModelProperty("渠道编码")
    @JsonProperty("order_original_code")
    private String orderOriginalCode;

    @ApiModelProperty("渠道名称")
    @JsonProperty("order_original_name")
    private String orderOriginalName;

    @ApiModelProperty("时间begin")
    @JsonProperty("begin_run_time")
    private String beginRunTime;

    @ApiModelProperty("时间finish")
    @JsonProperty("finish_run_time")
    private String finishRunTime;

    public StoreRepurchaseRateReqVo(String provinceName, String cityName, String districtName, String productCategoryCode, String productCategoryName, String orderOriginalCode, String orderOriginalName, String beginRunTime, String finishRunTime) {
        this.provinceName = provinceName;
        this.cityName = cityName;
        this.districtName = districtName;
        this.productCategoryCode = productCategoryCode;
        this.productCategoryName = productCategoryName;
        this.orderOriginalCode = orderOriginalCode;
        this.orderOriginalName = orderOriginalName;
        this.beginRunTime = beginRunTime;
        this.finishRunTime = finishRunTime;
    }

    public StoreRepurchaseRateReqVo() {
    }
}
