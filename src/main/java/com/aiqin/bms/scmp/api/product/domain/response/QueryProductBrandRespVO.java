package com.aiqin.bms.scmp.api.product.domain.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 商品品牌类型列表返回vo
 * @author zth
 * @date 2018/12/13
 */
@ApiModel("商品品牌类型列表返回vo")
@Data
public class QueryProductBrandRespVO {
    @ApiModelProperty(value = "自增主键")
    private Long id;

    @ApiModelProperty(value = "品牌类型名称")
    private String brandName;

    @ApiModelProperty(value = "状态，0为启用，1为禁用")
    private Integer brandStatus;

    @ApiModelProperty(value = "品牌类型id")
    private String brandId;

    @ApiModelProperty(value = "'品牌logo(图片url地址)")
    private String brandLogo;

    @ApiModelProperty(value = "品牌文件名称")
    private String brandLogoName;

    @ApiModelProperty(value = "品牌首字母")
    private String brandInitials;

//    @ApiModelProperty(value = "品牌介绍")
//    private String brandIntroduction;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @ApiModelProperty(value = "创建人")
    private String createBy;

    @ApiModelProperty(value = "修改人")
    private String updateBy;

}