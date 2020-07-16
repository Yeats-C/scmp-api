package com.aiqin.bms.scmp.api.product.domain.request.basicprice;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author knight.xie
 * @version 1.0
 * @className AddPriceChannelReqVo
 * @date 2019/4/19 16:08
 */
@ApiModel("新增价格渠道")
@Data
//public class AddPriceChannelReqVo extends CommonPriceChannelReqVo{
public class AddPriceChannelReqVo {
    @ApiModelProperty("名称")
    @NotEmpty(message = "名称不能为空")
    private String priceChannelName;

    @ApiModelProperty("排序")
    private Integer priceChannelOrder;

    @ApiModelProperty("价格项目列表")
    private List<CommonPriceChannelReqVo> priceList;
}
