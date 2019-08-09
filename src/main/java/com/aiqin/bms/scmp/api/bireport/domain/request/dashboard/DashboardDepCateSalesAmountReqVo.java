package com.aiqin.bms.scmp.api.bireport.domain.request.dashboard;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("当月各部门品类下的销售情况reqVo")
@Data
public class DashboardDepCateSalesAmountReqVo {

    @ApiModelProperty("年月")
    @JsonProperty("stat_month")
    private String statMonth;

    @ApiModelProperty("部门编码")
    @JsonProperty("product_sort_code")
    private String productSortCode;

    @ApiModelProperty("部门")
    @JsonProperty("product_sort_name")
    private String productSortName;

    public DashboardDepCateSalesAmountReqVo() {
    }

    public DashboardDepCateSalesAmountReqVo(String statMonth, String productSortCode, String productSortName) {
        this.statMonth = statMonth;
        this.productSortCode = productSortCode;
        this.productSortName = productSortName;
    }
}
