package com.aiqin.bms.scmp.api.purchase.domain.request.order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-06-24
 * @time: 17:47
 */
@Data
@ApiModel("发货请求vo")
public class DeliveryReqVO {

    @ApiModelProperty("订单编号")
    private String orderCode;

    @ApiModelProperty("运费")
    private BigDecimal deliverAmount;

    @ApiModelProperty("物流公司")
    private String transportCompany;

    @ApiModelProperty("物流公司编码")
    private String transportCompanyCode;

    @ApiModelProperty("物流公司单号")
    private String transportNumber;

    protected List<DeliveryItemReqVo> itemList;

}
