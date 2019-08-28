package com.aiqin.bms.scmp.api.product.domain.request.basicprice;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author knight.xie
 * @version 1.0
 * @className AddPriceChannelReqVo
 * @date 2019/4/19 16:08
 */
@ApiModel("修改价格渠道")
@Data
public class UpdatePriceChannelReqVo extends CommonPriceChannelReqVo{

    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("名称")
    @NotEmpty(message = "名称不能为空")
    private String priceChannelName;

    @ApiModelProperty("排序")
    private Integer priceChannelOrder;

    @ApiModelProperty("是否禁用(0:启用 1:禁用)")
    private Byte priceChannelEnable;
}
