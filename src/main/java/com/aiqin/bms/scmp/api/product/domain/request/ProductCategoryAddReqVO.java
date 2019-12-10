package com.aiqin.bms.scmp.api.product.domain.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @功能说明:品类新增大对象
 * @author wangxu
 * @date 2018/12/12 0012 19:01
 */
@ApiModel("品类新增")
@Data
public class ProductCategoryAddReqVO {
    @ApiModelProperty("父节点分类id")
    private String parentId;

    @ApiModelProperty("所选节点分类id")
    private String currentCategoryId;

    @ApiModelProperty(value = "所选节点级别，1、2、3、4级")
    private Byte currentLevel;

    @ApiModelProperty("同级分类集合")
    List<ProductCategoryReqVO> sameLevelList;

    @ApiModelProperty("下级分类集合")
    List<ProductCategoryReqVO> lowerLevelList;

    @ApiModelProperty(value = "公司编码")
    private String companyCode;

    @ApiModelProperty(value = "公司名称")
    private String companyName;

}
