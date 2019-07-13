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

    @ApiModelProperty("渠道")
    @JsonProperty("order_original")
    private String orderOriginal;

    @ApiModelProperty("入库时间begin")
    @JsonProperty("begin_create_time")
    private String beginCreateTime;

    @ApiModelProperty("入库时间finish")
    @JsonProperty("finish_create_time")
    private String finishCreateTime;

    public StoreRepurchaseRateReqVo(String provinceName, String cityName, String districtName, String productCategoryCode, String productCategoryName, String orderOriginal, String beginCreateTime, String finishCreateTime) {
        this.provinceName = provinceName;
        this.cityName = cityName;
        this.districtName = districtName;
        this.productCategoryCode = productCategoryCode;
        this.productCategoryName = productCategoryName;
        this.orderOriginal = orderOriginal;
        this.beginCreateTime = beginCreateTime;
        this.finishCreateTime = finishCreateTime;
    }

    public StoreRepurchaseRateReqVo() {
    }
}
