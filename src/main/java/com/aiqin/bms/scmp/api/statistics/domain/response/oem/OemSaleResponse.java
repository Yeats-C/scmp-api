package com.aiqin.bms.scmp.api.statistics.domain.response.oem;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author: zhao shuai
 * @create: 2019-09-18
 **/
@Data
public class OemSaleResponse {

    @ApiModelProperty(value="OEM总计")
    @JsonProperty("oem_sum")
    private OemSaleInfoResponse oemSum;

    @ApiModelProperty(value="公司总计")
    @JsonProperty("company_sum")
    private OemSaleInfoResponse companySum;

    @ApiModelProperty(value="oem的子集")
    @JsonProperty("subset_list")
    private List<OemSaleInfoResponse> subsetList;
}
