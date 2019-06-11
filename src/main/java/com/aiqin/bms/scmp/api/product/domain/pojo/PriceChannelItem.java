package com.aiqin.bms.scmp.api.product.domain.pojo;

import com.aiqin.bms.scmp.api.common.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("价格渠道管理")
@Data
public class PriceChannelItem extends CommonBean {
    @ApiModelProperty("主键Id")
    private Long id;

    @ApiModelProperty("价格渠道编码")
    private String priceChannelCode;

    @ApiModelProperty("价格渠道编码")
    private String priceChannelName;

    @ApiModelProperty("价格项目编码")
    private String priceProjectCode;

    @ApiModelProperty("价格编码名称")
    private String priceProjectName;

    @ApiModelProperty("价格类型编码")
    private String priceTypeCode;

    @ApiModelProperty("价格类型名称")
    private String priceTypeName;

    @ApiModelProperty("价格大类编码")
    private String priceCategoryCode;

    @ApiModelProperty("价格大类名称")
    private String priceCategoryName;
}