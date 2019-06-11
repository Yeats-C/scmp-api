package com.aiqin.bms.scmp.api.product.domain.request.basicprice;

import com.aiqin.bms.scmp.api.base.PageReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author knight.xie
 * @version 1.0
 * @className QueryPriceProjectReqVo
 * @date 2019/4/19 12:18
 * @description 价格项目列表请求参数信息
 */
@ApiModel("价格渠道列表请求参数信息")
@Data
public class QueryPriceChannelReqVo extends PageReq {

    @ApiModelProperty("价格渠道编码")
    private String code;

    @ApiModelProperty("价格渠道名称")
    private String name;

    @ApiModelProperty("是否禁用(0:启用 1:禁用 空字符串:全部)")
    private String enable;

    @ApiModelProperty(value = "公司编码",hidden = true)
    private String companyCode;
}
