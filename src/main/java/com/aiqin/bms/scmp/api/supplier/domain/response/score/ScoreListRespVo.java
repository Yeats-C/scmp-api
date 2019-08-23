package com.aiqin.bms.scmp.api.supplier.domain.response.score;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author knight.xie
 * @version 1.0
 * @className ScoreListRespVo
 * @date 2019/5/23 15:48

 */
@ApiModel("评分列表查询返回VO")
@Data
public class ScoreListRespVo {

    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("评分编号")
    private String scoreCode;

    @ApiModelProperty("供应商编码")
    private String supplierCode;

    @ApiModelProperty("供应商名称")
    private String supplierName;

    @ApiModelProperty("采购组编码")
    private String purchasingGroupCode;

    @ApiModelProperty("采购组名称")
    private String purchasingGroupName;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("创建人")
    private String createBy;
}
