package com.aiqin.bms.scmp.api.supplier.domain.pojo;

import com.aiqin.bms.scmp.api.common.CommonBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel("制造商品牌关联表")
@Data
public class ManufacturerBrand extends CommonBean {
    @ApiModelProperty("")
    private Long id;

    @ApiModelProperty("品牌code")
    private String brandCode;

    @ApiModelProperty("制造商code")
    private String manufacturerCode;

    @ApiModelProperty("删除标记(0:正常 1:删除)")
    private Byte delFlag;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("创建人")
    private String createBy;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("更新人")
    private String updateBy;

    @ApiModelProperty("品牌名称")
    private String brandName;

    @ApiModelProperty("启用禁用")
    private Byte enable;


}