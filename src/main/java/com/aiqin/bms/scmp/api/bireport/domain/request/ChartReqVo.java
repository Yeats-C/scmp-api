package com.aiqin.bms.scmp.api.bireport.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("图表request")
@Data
public class ChartReqVo {

    @ApiModelProperty("时间begin")
    @JsonProperty("begin_create_time")
    private String beginCreateTime;

    @ApiModelProperty("日期")
    @JsonProperty("create_time")
    private String createTime;

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

    @ApiModelProperty("销售类型")
    @JsonProperty("sale_type_code")
    private Integer saleTypeCode;


    // 有参去掉beginCreateTime
    public ChartReqVo(String createTime, String productSortCode, String productSortName, String orderCode, String orderOriginal, String storeTypeCode, String storeType, String dataTypeCode, String dataType, Integer saleTypeCode) {
        this.createTime = createTime;
        this.productSortCode = productSortCode;
        this.productSortName = productSortName;
        this.orderCode = orderCode;
        this.orderOriginal = orderOriginal;
        this.storeTypeCode = storeTypeCode;
        this.storeType = storeType;
        this.dataTypeCode = dataTypeCode;
        this.dataType = dataType;
        this.saleTypeCode = saleTypeCode;
    }

    public ChartReqVo() {
    }
}
