package com.aiqin.bms.scmp.api.purchase.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: zhao shuai
 * @create: 2019-07-12
 **/
@Data
public class PurchaseAfloatResponse {

    @ApiModelProperty(value="在途个数")
    @JsonProperty("afloat_count")
    private Integer afloatCount;

    @ApiModelProperty(value="在途天数")
    @JsonProperty("afloat_day")
    private Integer afloatDay;
}
