package com.aiqin.bms.scmp.api.bireport.domain.request;

import com.aiqin.bms.scmp.api.base.PageReq;
import com.aiqin.bms.scmp.api.base.PagesRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel("月销售情况request")
@Data
public class MonthlySalesReqVo extends PagesRequest implements Serializable {

    @ApiModelProperty("所属部门编码")
    @JsonProperty("product_sort_code")
    private String productSortCode;

    @ApiModelProperty("所属部门")
    @JsonProperty("product_sort_name")
    private String productSortName;

    @ApiModelProperty("渠道编码")
    @JsonProperty("order_code")
    private String orderCode;

    @ApiModelProperty("渠道")
    @JsonProperty("order_original")
    private String orderOriginal;

    @ApiModelProperty("门店类型code")
    @JsonProperty("store_type_code")
    private String storeTypeCode;

    @ApiModelProperty("门店类型名称")
    @JsonProperty("store_type")
    private String storeType;

    @ApiModelProperty("数据类型code")
    @JsonProperty("data_type_code")
    private String dataTypeCode;

    @ApiModelProperty("数据类型name")
    @JsonProperty("data_type")
    private String dataType;

    @ApiModelProperty("时间begin")
    @JsonProperty("begin_create_time")
    private String beginCreateTime;

    @ApiModelProperty("时间finish")
    @JsonProperty("finish_create_time")
    private String finishCreateTime;

    public MonthlySalesReqVo(String productSortCode, String productSortName, String orderCode, String orderOriginal, String storeTypeCode, String storeType, String dataTypeCode, String dataType, String beginCreateTime, String finishCreateTime) {
        this.productSortCode = productSortCode;
        this.productSortName = productSortName;
        this.orderCode = orderCode;
        this.orderOriginal = orderOriginal;
        this.storeTypeCode = storeTypeCode;
        this.storeType = storeType;
        this.dataTypeCode = dataTypeCode;
        this.dataType = dataType;
        this.beginCreateTime = beginCreateTime;
        this.finishCreateTime = finishCreateTime;
    }

    public MonthlySalesReqVo() {
    }
}
