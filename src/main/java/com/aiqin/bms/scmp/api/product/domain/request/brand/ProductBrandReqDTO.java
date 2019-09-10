package com.aiqin.bms.scmp.api.product.domain.request.brand;

import com.aiqin.bms.scmp.api.common.CommonBean;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Description:
 *商品品牌类型DTO
 * @author zth
 * @date 2018-12-13
 * @time: 10:27
 */
@Data
public class ProductBrandReqDTO extends CommonBean {
    @ApiModelProperty(value = "自增主键")
    private Long id;

    @ApiModelProperty(value = "品牌类型id")
    private String brandId;

    @ApiModelProperty(value = "品牌类型名称")
    private String brandName;

    @ApiModelProperty(value = "状态，0为启用，1为禁用")
    private Integer brandStatus;

    @ApiModelProperty(value = "'品牌logo")
    private String brandLogo;

    @ApiModelProperty(value = "是否优选，0为优选")
    private Integer brandTop;

    @ApiModelProperty(value = "品牌标签（A、B、C）")
    private String brandTag;

    @ApiModelProperty(value = "品牌code")
    private String brandCode;

    @ApiModelProperty(value = "品牌文件名称")
    private String brandLogoName;

    @ApiModelProperty(value = "品牌首字母")
    private String brandInitials;

    @ApiModelProperty(value = "品牌介绍")
    private String brandIntroduction;

    @ApiModelProperty(value = "持有人")
    private String holder;

    @ApiModelProperty(value = "旧的logoUrl连接")
    private String oldBrandLogoUrl;
}
