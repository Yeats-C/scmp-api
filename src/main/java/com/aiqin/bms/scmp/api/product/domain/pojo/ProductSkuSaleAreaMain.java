package com.aiqin.bms.scmp.api.product.domain.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel("销售区域主表")
@Data
public class ProductSkuSaleAreaMain {
    @ApiModelProperty(" 主键")
    private Long id;

    @ApiModelProperty("限制区域名称")
    private String code;

    @ApiModelProperty("限制区域名称")
    private String name;

    @ApiModelProperty("是否禁用(0禁用1启用)")
    private Integer beDisable;

    @ApiModelProperty("创建人")
    private String createBy;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("修改人")
    private String updateBy;

    @ApiModelProperty("修改时间")
    private Date updateTime;

    @ApiModelProperty("申请编码")
    private String applyCode;

    @ApiModelProperty("是否生效(0未生效1已生效）")
    private Integer beEffective;

    @ApiModelProperty("是否使用生效时间(0:是1:否)")
    private Byte selectionEffectiveTime;

    @ApiModelProperty("生效开始时间")
    private Date selectionEffectiveStartTime;

    @ApiModelProperty("公司编码")
    private String companyCode;

    @ApiModelProperty("公司名称")
    private String companyName;
}