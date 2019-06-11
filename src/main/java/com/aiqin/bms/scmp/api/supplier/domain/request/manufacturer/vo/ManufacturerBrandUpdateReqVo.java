package com.aiqin.bms.scmp.api.supplier.domain.request.manufacturer.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Classname: ManufacturerBrandUpdateReqVo
 * 描述:
 * @Author: Kt.w
 * @Date: 2019/2/14
 * @Version 1.0
 * @Since 1.0
 */
@ApiModel("制造商品牌请求修改实体")
@Data
public class ManufacturerBrandUpdateReqVo {
    @ApiModelProperty("")
    private Long id;

    @ApiModelProperty("品牌code")
    private String brandCode;

    @ApiModelProperty("品牌名称")
    private String brandName;

    @ApiModelProperty("启用禁用")
    private Byte enable;
}
