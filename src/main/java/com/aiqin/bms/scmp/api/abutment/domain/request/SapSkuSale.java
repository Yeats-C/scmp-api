package com.aiqin.bms.scmp.api.abutment.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author sunx
 * @description
 * @date 2019-08-05
 * VKORG	CHAR	4	0	销售组织
 * VTWEG	CHAR	2	0	分销渠道
 * KTGRM	CHAR	2	0	该物料的科目设置组
 * MTPOS	CHAR	4	0	来自物料主文件的项目类别组
 * VMSTA	CHAR	2	0	指定分销链的物料状态
 * TAXKM	CHAR	1	0	物料的税分类
 */
@Data
@ApiModel("scmp物料主数据-销售视图")
public class SapSkuSale {

    @ApiModelProperty("该物料的科目设置组,14")
    @JsonProperty("KTGRM")
    private String subjectGroupCode;

    @ApiModelProperty("来自物料主文件的项目类别组,NORM")
    @JsonProperty("MTPOS")
    private String mainCategoryCode;

    @ApiModelProperty("物料的税分类,4")
    @JsonProperty("TAXKM")
    private String taxCategoryCode;

    @ApiModelProperty("销售组织,1000")
    @JsonProperty("VKORG")
    private String saleOrg;

    @ApiModelProperty("分销渠道,00")
    @JsonProperty("VTWEG")
    private String distributionChannelCode;
}
