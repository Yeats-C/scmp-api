package com.aiqin.bms.scmp.api.purchase.domain.pojo.returngoods;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel("退货订单日志表")
@Data
public class ReturnOrderInfoLog {
    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("订单编码")
    private String orderCode;

    @ApiModelProperty("订单状态")
    private Integer status;

    @ApiModelProperty("状态描述")
    private String statusDesc;

    @ApiModelProperty("内容")
    private String content;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("操作人")
    private String operator;

    @ApiModelProperty("操作时间")
    private Date operatorTime;

    @ApiModelProperty("公司编码")
    private String companyCode;

    @ApiModelProperty("公司名称")
    private String companyName;

    public ReturnOrderInfoLog(Long id, String returnOrderCode, Integer orderStatus, String backgroundOrderStatus, String standardDescription, String remark, String operator, Date date, String companyCode, String companyName) {
    }
}