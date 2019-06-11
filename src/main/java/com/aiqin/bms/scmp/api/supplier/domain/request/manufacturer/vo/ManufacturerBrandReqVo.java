package com.aiqin.bms.scmp.api.supplier.domain.request.manufacturer.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @Classname: ManufacturerBrandReqVo
 * 描述:
 * @Author: Kt.w
 * @Date: 2019/2/14
 * @Version 1.0
 * @Since 1.0
 */
@ApiModel("制造商品牌保存请求实体")
@Data
public class ManufacturerBrandReqVo {

    @ApiModelProperty("品牌code")
    @NotEmpty(message = "品牌编码不能为空")
    private String brandCode;

    @ApiModelProperty("品牌名称")
    @NotEmpty(message = "品牌名称不能为空")
    private String brandName;
}
