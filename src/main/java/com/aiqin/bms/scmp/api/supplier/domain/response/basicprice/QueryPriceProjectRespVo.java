package com.aiqin.bms.scmp.api.supplier.domain.response.basicprice;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author knight.xie
 * @version 1.0
 * @className QueryPriceProjectRespVo
 * @date 2019/4/19 12:24
 * @description 价格项目列表返回列表信息
 */
@Data
@ApiModel("价格项目列表返回列表信息")
public class QueryPriceProjectRespVo {

    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("编码")
    private String priceProjectCode;

    @ApiModelProperty("名称")
    private String priceProjectName;

    @ApiModelProperty("价格类型编码")
    private String priceTypeCode;

    @ApiModelProperty("价格类型名称")
    private String priceTypeName;

    @ApiModelProperty("价格大类编码")
    private String priceCategoryCode;

    @ApiModelProperty("价格大类名称")
    private String priceCategoryName;

    @ApiModelProperty("排序")
    private Integer priceProjectOrder;

    @ApiModelProperty("是否禁用")
    private String priceProjectEnable;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("创建人")
    private String createBy;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("修改时间/操作时间")
    private Date updateTime;

    @ApiModelProperty("修改人/操作人")
    private String updateBy;
}
