package com.aiqin.bms.scmp.api.bireport.domain.request;

import com.aiqin.bms.scmp.api.base.PageReq;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel("高低库存request")
@Data
public class HighLowInventoryReqVo extends PageReq implements Serializable {

    @ApiModelProperty("采购组编码")
    @JsonProperty("procurement_section_code")
    private String procurementSectionCode;

    @ApiModelProperty("采购组")
    @JsonProperty("procurement_section_name")
    private String procurementSectionName;

    @ApiModelProperty("时间begin")
    @JsonProperty("begin_run_time")
    private String beginRunTime;

    @ApiModelProperty("时间finish")
    @JsonProperty("finish_run_time")
    private String finishRunTime;

    @ApiModelProperty("部门编码")
    @JsonProperty("product_sort_code")
    private String productSortCode;

    @ApiModelProperty("所属部门")
    @JsonProperty("product_sort_name")
    private String productSortName;


    public HighLowInventoryReqVo(String procurementSectionCode, String procurementSectionName, String beginRunTime, String finishRunTime, String productSortCode, String productSortName) {
        this.procurementSectionCode = procurementSectionCode;
        this.procurementSectionName = procurementSectionName;
        this.beginRunTime = beginRunTime;
        this.finishRunTime = finishRunTime;
        this.productSortCode = productSortCode;
        this.productSortName = productSortName;
    }

    public HighLowInventoryReqVo() {
    }
}
