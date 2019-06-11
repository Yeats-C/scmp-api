package com.aiqin.bms.scmp.api.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @功能说明:省市区
 * @author: wangxu
 * @date: 2018/12/24 0024 14:58
 */
@Data
@ApiModel("省市区")
public class AreaBasic {
    @ApiModelProperty("区域id")
    private String area_id;

    @ApiModelProperty("区域名称")
    private String area_name;

    @ApiModelProperty("父级id")
    private String parent_id;

    @ApiModelProperty("父级名称")
    private String parent_area_name;

    @ApiModelProperty("区域等级")
    private String area_type;

    @ApiModelProperty("子级")
    private List<AreaBasic> children;
}
