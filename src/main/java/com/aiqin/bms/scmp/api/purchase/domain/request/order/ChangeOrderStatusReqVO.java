package com.aiqin.bms.scmp.api.purchase.domain.request.order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Description:
 * 修改订单传入的vo
 * @author: NullPointException
 * @date: 2019-06-15
 * @time: 10:20
 */
@ApiModel("修改订单状态vo")
@Data
public class ChangeOrderStatusReqVO {

    @ApiModelProperty("订单状态")
    private Integer orderStatus;

    @ApiModelProperty("订单编码")
    private String orderCode;

    @ApiModelProperty("操作人")
    private String operator;

    @ApiModelProperty("操作人编码")
    private String operatorCode;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("发货时间")
    private Date deliveryTime;

    @ApiModelProperty("物流公司")
    private String transportCompany;

    @ApiModelProperty("物流公司编码")
    private String transportCompanyCode;

    @ApiModelProperty("物流公司单号")
    private String transportNumber;
}
