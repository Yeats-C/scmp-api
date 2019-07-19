package com.aiqin.bms.scmp.api.product.domain.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @功能说明:品类新增请求VO
 * @author wangxu
 * @date 2018/12/12 0012 17:15
 */
@ApiModel("品类属性")
@Data
public class ProductCategoryReqVO{
    @ApiModelProperty(value = "品类id",notes = "修改必传")
    private Long id;

    @NotEmpty(message ="名称不能为空")
    @ApiModelProperty(value = "分类名称")
    private String categoryName;

    @ApiModelProperty("分类ID")
    private String categoryId;

    @NotNull(message ="状态不能为空")
    @ApiModelProperty(value = "状态，0为启用，1为禁用")
    private Byte categoryStatus;

    @ApiModelProperty(value = "父级id")
    private String parentId;

    @NotNull(message ="级别不能为空")
    @ApiModelProperty(value = "级别，1、2、3、4级")
    private Byte categoryLevel;

    @ApiModelProperty(value = "图片路径")
    private String picturePath;

    @NotNull(message ="排序不能为空")
    @ApiModelProperty(value = "排序")
    private Integer weight;

    @NotEmpty(message ="图片名称不能为空")
    @ApiModelProperty(value = "图片名称")
    private String pictureName;
}
