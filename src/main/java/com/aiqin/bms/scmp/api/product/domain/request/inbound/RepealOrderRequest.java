package com.aiqin.bms.scmp.api.product.domain.request.inbound;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by 爱亲 on 2019/7/25.
 */
@Data
@ApiModel("撤销订单参数实体")
public class RepealOrderRequest {

    @ApiModelProperty("撤销订单id")
    private String repealOrderId;

    @ApiModelProperty("撤销时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yy-MM-dd HH:mm:ss")
    private String repealTime;

    @ApiModelProperty("撤销人id")
    private String repealEmpId;

    @ApiModelProperty("备注")
    private String description;

}