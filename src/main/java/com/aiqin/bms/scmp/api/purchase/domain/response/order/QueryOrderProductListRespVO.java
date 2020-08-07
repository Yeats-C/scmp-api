package com.aiqin.bms.scmp.api.purchase.domain.response.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-06-17
 * @time: 10:09
 */
@Data
@ApiModel("订单商品查询返回vo")
public class QueryOrderProductListRespVO {

    @ApiModelProperty("订单来源")
    private String orderOriginal;

    @ApiModelProperty("客户名称")
    private String customerName;

    @ApiModelProperty("客户编码")
    private String customerCode;

    @ApiModelProperty("sku编号")
    private String skuCode;

    @ApiModelProperty("sku名称")
    private String skuName;

    @ApiModelProperty("规格")
    private String spec;

    @ApiModelProperty("商品单位")
    private String unitName;

    @ApiModelProperty("单价")
    private BigDecimal price;

    @ApiModelProperty("数量")
    private Long num;

    @ApiModelProperty("总价")
    private BigDecimal amount;

    @ApiModelProperty("订单编码(订单号)")
    private String orderCode;

    @ApiModelProperty("物流中心名称")
    private String transportCenterName;

    @ApiModelProperty("仓库名称")
    private String warehouseName;

    @ApiModelProperty("商品唯一码")
    private String uniqueCode;

    @ApiModelProperty("是否是赠品(0否1是)")
    private Integer givePromotion;

    @ApiModelProperty("创建时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;

    @ApiModelProperty("状态")
    private Integer status;

    @ApiModelProperty("创建人")
    private String createByName;

    @ApiModelProperty("创建时间")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty("修改人")
    private String updateByName;

    @ApiModelProperty("修改时间")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}
