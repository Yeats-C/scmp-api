package com.aiqin.bms.scmp.api.supplier.domain.pojo;

import com.aiqin.bms.scmp.api.common.CommonBean;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel("商品分类")
@Data
public class ProductCategory extends CommonBean {
    @ApiModelProperty(value = "自增主键")
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty(value = "分类id")
    @JsonProperty("category_id")
    private String categoryId;

    @ApiModelProperty(value = "分类名称")
    @JsonProperty("category_name")
    private String categoryName;

    @ApiModelProperty(value = "状态，0为启用，1为禁用")
    @JsonProperty("category_status")
    private Integer categoryStatus;

    @ApiModelProperty(value = "父级id")
    @JsonProperty("parent_id")
    private String parentId;

    @ApiModelProperty(value = "级别，1、2、3级")
    @JsonProperty("category_level")
    private Integer categoryLevel;

    @ApiModelProperty(value = "商品分类编号")
    @JsonProperty("product_category_code")
    private String productCategoryCode;

    @ApiModelProperty(value = "图片路径")
    @JsonProperty("picture_path")
    private String picturePath;

    @ApiModelProperty(value = "排序")
    @JsonProperty("weight")
    private Integer weight;

    @ApiModelProperty(value = "图片名称")
    @JsonProperty("picture_name")
    private String pictureName;

    @ApiModelProperty(value = "删除标记(0:正常 1:删除)")
    @JsonProperty("del_flag")
    private Byte delFlag;

    @ApiModelProperty(value = "create_time")
    @JsonProperty("create_time")
    private Date createTime;

    @ApiModelProperty(value = "update_time")
    @JsonProperty("update_time")
    private Date updateTime;

    @ApiModelProperty(value = "create_by")
    @JsonProperty("create_by")
    private String createBy;

    @ApiModelProperty(value = "update_by")
    @JsonProperty("update_by")
    private String updateBy;


}