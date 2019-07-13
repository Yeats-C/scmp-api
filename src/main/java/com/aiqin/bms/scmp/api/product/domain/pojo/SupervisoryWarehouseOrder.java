package com.aiqin.bms.scmp.api.product.domain.pojo;

import com.aiqin.bms.scmp.api.common.CommonBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@ApiModel("监管仓订单")
@Data
public class SupervisoryWarehouseOrder extends CommonBean {
    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("订单编号")
    private String orderCode;

    @ApiModelProperty("订单类型(1:入库 2:出库)")
    private Byte orderType;

    @ApiModelProperty("订单类型名称")
    private String orderTypeName;

    @ApiModelProperty("客户编码")
    private String customerCode;

    @ApiModelProperty("客户名称")
    private String customerName;

    @ApiModelProperty("客户订单号")
    private String customerOrderCode;

    @ApiModelProperty("订单日期")
    private Date orderTime;

    @ApiModelProperty("仓编码(物流中心编码)")
    private String transportCenterCode;

    @ApiModelProperty("仓名称(物流中心名称)")
    private String transportCenterName;

    @ApiModelProperty("库房编码")
    private String warehouseCode;

    @ApiModelProperty("库房名称")
    private String warehouseName;

    @ApiModelProperty("业务编号")
    private String businessCode;

    @ApiModelProperty("业务名称")
    private String businessName;

    @ApiModelProperty("预约时间")
    private Date subscribeTime;

    @ApiModelProperty("有效期")
    private Date validTime;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("公司")
    private String companyCode;

    @ApiModelProperty("公司名称")
    private String companyName;

    @ApiModelProperty("开单人")
    private String openOrderName;

    @ApiModelProperty("商品数据")
    private List<SupervisoryWarehouseOrderProduct> records;
}