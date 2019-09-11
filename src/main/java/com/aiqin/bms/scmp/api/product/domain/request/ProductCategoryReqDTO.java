package com.aiqin.bms.scmp.api.product.domain.request;

import com.aiqin.bms.scmp.api.common.CommonBean;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @功能说明:品类新增请求VO
 * @author wangxu
 * @date 2018/12/12 0012 17:15
 */
@Data
public class ProductCategoryReqDTO extends CommonBean{
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

    @ApiModelProperty("公司编码")
    private String companyCode;

    @ApiModelProperty("公司名称")
    private String companyName;
}
