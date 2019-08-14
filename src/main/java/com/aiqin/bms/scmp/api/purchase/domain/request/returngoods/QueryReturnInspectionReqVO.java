package com.aiqin.bms.scmp.api.purchase.domain.request.returngoods;

import com.aiqin.bms.scmp.api.base.PageReq;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-06-20
 * @time: 10:12
 */
@Data
@ApiModel("退货验货请求vo")
public class QueryReturnInspectionReqVO extends PageReq {

    @ApiModelProperty("退货订单编码")
    private String returnOrderCode;

    @ApiModelProperty("订单编码(订单号)")
    private String orderCode;

    @ApiModelProperty("创建时间")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDateStart;

    @ApiModelProperty("创建时间")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDateEnd;

    @ApiModelProperty("物流中心编码")
    private String transportCenterCode;

    @ApiModelProperty("仓库编码")
    private String warehouseCode;

    @ApiModelProperty("客户编码")
    private String customerCode;

    @ApiModelProperty("订单状态")
    private Integer orderStatus;

    @ApiModelProperty("公司编码")
    private String companyCode;

}