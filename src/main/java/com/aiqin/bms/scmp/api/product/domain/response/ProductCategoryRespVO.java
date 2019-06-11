package com.aiqin.bms.scmp.api.product.domain.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @功能说明:品类返回VO
 * @author wangxu
 * @date 2018/12/12 0012 17:15
 */
@ApiModel("品类列表返回属性")
@Data
public class ProductCategoryRespVO {
    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty(value = "分类名称")
    private String categoryName;

    @ApiModelProperty("分类ID")
    private String categoryId;

    @ApiModelProperty(value = "状态，0为启用，1为禁用")
    private Byte categoryStatus;

    @ApiModelProperty(value = "父级id")
    private String parentId;

    @ApiModelProperty(value = "级别，1、2、3、4级")
    private Byte categoryLevel;

    @ApiModelProperty(value = "图片路径")
    private String picturePath;

    @ApiModelProperty(value = "排序")
    private Integer weight;

    @ApiModelProperty(value = "图片名称")
    private String pictureName;

    @ApiModelProperty(value = "子节点集合")
    private List<ProductCategoryRespVO> productCategoryRespVOS;

}
