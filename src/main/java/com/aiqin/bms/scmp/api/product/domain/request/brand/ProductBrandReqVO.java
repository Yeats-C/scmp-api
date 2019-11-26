package com.aiqin.bms.scmp.api.product.domain.request.brand;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author zth
 * 商品品牌类请求VO
 */
@Data
@ApiModel("商品品牌请求vo")
public class ProductBrandReqVO{

    @ApiModelProperty(value = "自增主键(新增不用传入，修改时传入)")
    private Long id;

//    @ApiModelProperty(value = "品牌类型id")
//    private String brandId;

    @ApiModelProperty(value = "品牌类型名称")
    @NotNull(message ="品牌类型名称不能为空")
    private String brandName;

    @ApiModelProperty(value = "'品牌logo文件(base64编码)")
    private String brandLogo;

//    @ApiModelProperty(value = "是否优选，0为优选")
//    private Integer brandTop;

//    @ApiModelProperty(value = "品牌标签（A、B、C）")
//    private String brandTag;

//    @ApiModelProperty(value = "品牌code")
//    private String brandCode;

    @ApiModelProperty(value = "品牌文件名称")
    private String brandLogoName;

    @ApiModelProperty(value = "品牌首字母")
    @NotNull(message ="品牌首字母不能为空")
    private String brandInitials;

    @ApiModelProperty(value = "品牌介绍")
    private String brandIntroduction;

    @ApiModelProperty(value = "持有人")
    private String holder;

    @ApiModelProperty(value = "状态，0为启用，1为禁用")
    private Integer brandStatus;

    @ApiModelProperty(value = "创建人员工号")
    private String createById;

    @ApiModelProperty(value = "创建人员名称")
    private String createBy;

    @ApiModelProperty(value = "修改人员工号")
    private String updateById;

    @ApiModelProperty(value = "修改人员名称")
    private String updateBy;

    @ApiModelProperty(value = "公司编码")
    private String companyCode;

    @ApiModelProperty(value = "公司名称")
    private String companyName;
}