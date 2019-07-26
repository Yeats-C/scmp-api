package com.aiqin.bms.scmp.api.purchase.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@ApiModel
@Data
public class BiAClassification {
    @JsonProperty("product_category_code")
    private String productCategoryCode;

    @ApiModelProperty(value="品类名称")
    @JsonProperty("product_category_name")
    private String productCategoryName;

    @ApiModelProperty(value="可用库存数")
    @JsonProperty("available_num")
    private Long availableNum;

    @ApiModelProperty(value="")
    @JsonProperty("a_classification")
    private BigDecimal aClassification;

    @ApiModelProperty(value="创建时间")
    @JsonProperty("create_time")
    private Date createTime;

    @ApiModelProperty(value="更新时间")
    @JsonProperty("update_time")
    private Date updateTime;

    @ApiModelProperty(value="")
    @JsonProperty("run_time")
    private Date runTime;

}