package com.aiqin.bms.scmp.api.product.domain.request.brand;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Createed by sunx on 2018/12/5.<br/>
 */
@Data
@ApiModel("查询分销机构集合请求参数")
public class QueryBrandDistributionRequest {
    @ApiModelProperty("分销机构id")
    @JsonProperty("distributor_id")
    private String distributorId;

    @ApiModelProperty(value = "是否展示，0为展示，1为不展示")
    @JsonProperty("is_show")
    private Integer isShow;

    @ApiModelProperty(value = "是否优选，0为优选")
    @JsonProperty("brand_top")
    private Integer brandTop;

    @ApiModelProperty(value = "删除标记(0:正常 1:删除)")
    @JsonProperty("del_flag")
    private Integer delFlag;
}
