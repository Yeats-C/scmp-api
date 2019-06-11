package com.aiqin.bms.scmp.api.supplier.domain.request.purchasegroup.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "人员表信息请求参数")
public class UserPositionsRequest {

    @ApiModelProperty(value = "岗位名称")
    @JsonProperty(value = "position_name")
    private String positionName;

    @ApiModelProperty(value = "岗位级别名称")
    @JsonProperty(value = "position_level_name")
    private String positionLevelName;

    @ApiModelProperty(value = "员工名称")
    @JsonProperty(value = "person_name")
    private String personName;

    @ApiModelProperty(value = "第几页")
    @JsonProperty(value = "person_name")
    private Integer pageNo=1;

    @ApiModelProperty(value = "页面条数")
    @JsonProperty(value = "person_name")
    private Integer pageSize=10;


}
