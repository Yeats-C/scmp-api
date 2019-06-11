package com.aiqin.bms.scmp.api.product.domain.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 商品品牌类型详情返回vo
 * @author zth
 * @date 2018/12/13
 */
@ApiModel("商品品牌类型详情返回vo")
@Data
public class ProductBrandRespVO {
    @ApiModelProperty(value = "自增主键")
    private Long id;

    @ApiModelProperty(value = "品牌编码")
    private String brandId;

    @ApiModelProperty(value = "品牌类型名称")
    private String brandName;

    @ApiModelProperty(value = "'品牌logo(图片url地址)")
    private String brandLogo;

    @ApiModelProperty(value = "品牌文件名称")
    private String brandLogoName;

    @ApiModelProperty(value = "品牌首字母")
    private String brandInitials;

    @ApiModelProperty(value = "品牌介绍")
    private String brandIntroduction;

    @ApiModelProperty(value = "持有人")
    private String holder;

    @ApiModelProperty(value = "状态，0为启用，1为禁用")
    private Integer brandStatus;

}