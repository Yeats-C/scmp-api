package com.aiqin.bms.scmp.api.supplier.domain.response.manufacturer;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Classname: ManufacturerBrandResVo
 * 描述:
 * @Author: Kt.w
 * @Date: 2019/2/14
 * @Version 1.0
 * @Since 1.0
 */
@ApiModel("制造商页面查看返回品牌实体")
@Data
public class ManufacturerBrandResVo {
    @ApiModelProperty("")
    private Long id;

    @ApiModelProperty("品牌code")
    private String brandCode;

    @ApiModelProperty("制造商code")
    private String manufacturerCode;

    @ApiModelProperty("删除标记(0:正常 1:删除)")
    private Byte delFlag;

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

    @ApiModelProperty("品牌名称")
    private String brandName;

    @ApiModelProperty("启用禁用")
    private Byte enable;
}
