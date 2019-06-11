package com.aiqin.bms.scmp.api.supplier.domain.response.manufacturer;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Classname: QueryManufacturerResVo
 * 描述: 制造商列表展示返回实体
 * @Author: Kt.w
 * @Date: 2019/2/14
 * @Version 1.0
 * @Since 1.0
 */
@ApiModel("制造商列表查看返回实体")
@Data
public class QueryManufacturerResVo  {
    @ApiModelProperty("")
    private Long id;

    @ApiModelProperty("制造商编号")
    private String manufacturerCode;

    @ApiModelProperty("制造商名称")
    private String name;

   @ApiModelProperty("启用禁用状态")
    private Byte enable;

    @ApiModelProperty("创建时间")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty("创建人")
    private String createBy;

    @ApiModelProperty("更新时间")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @ApiModelProperty("更新人")
    private String updateBy;
}
