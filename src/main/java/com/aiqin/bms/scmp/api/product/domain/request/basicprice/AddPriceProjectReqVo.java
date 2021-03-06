package com.aiqin.bms.scmp.api.product.domain.request.basicprice;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author knight.xie
 * @version 1.0
 * @className AddPriceProjectReqVo
 * @date 2019/4/19 13:44
 */
@Data
@ApiModel("新增价格项目VO")
public class AddPriceProjectReqVo {

    @ApiModelProperty("名称")
    @NotEmpty(message = "名称不能为空")
    private String priceProjectName;

    @ApiModelProperty("价格类型编码")
    private String priceTypeCode;

    @ApiModelProperty("价格类型名称")
    private String priceTypeName;

    @ApiModelProperty("价格大类编码")
    private String priceCategoryCode;

    @ApiModelProperty("价格大类名称")
    private String priceCategoryName;

    @ApiModelProperty("排序")
    private Integer priceProjectOrder;
}
