package com.aiqin.bms.scmp.api.product.domain.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel("销售区域主表")
@Data
public class ProductSkuSaleAreaMainDraft {
    @ApiModelProperty(" 主键")
    private Long id;

    @ApiModelProperty("限制区域名称")
    private String code;

    @ApiModelProperty("限制区域名称")
    private String name;

    @ApiModelProperty("正式限制区域编码")
    private String officialCode;

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

    @ApiModelProperty("1新增2修改")
    private Integer applyType;

    @ApiModelProperty("公司编码")
    private String companyCode;

    @ApiModelProperty("公司名称")
    private String companyName;
}