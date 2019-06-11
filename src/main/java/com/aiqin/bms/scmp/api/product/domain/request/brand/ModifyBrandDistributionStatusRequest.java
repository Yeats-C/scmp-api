package com.aiqin.bms.scmp.api.product.domain.request.brand;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Createed by sunx on 2018/12/5.<br/>
 */
@Data
@ApiModel("修改分销机构状态实体")
public class ModifyBrandDistributionStatusRequest {
    @ApiModelProperty("品牌id")
    @JsonProperty("brand_id")
    private String brandId;

    @ApiModelProperty("分销机构id")
    @JsonProperty("distributor_id")
    private String distributorId;

    @ApiModelProperty("操作类型,0-删除，1-编辑优选，2-编辑是否展示")
    @JsonProperty("opt_type")
    private Integer optType;

    @ApiModelProperty("操作类型,0否，1是【0-正常，优选，展示】【1-删除、不优选、不展示】")
    @JsonProperty("opt_value")
    private Integer optValue;

    @ApiModelProperty("操作人id")
    @JsonProperty("update_by")
    private String updateBy;
}
