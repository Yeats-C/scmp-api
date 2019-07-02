package com.aiqin.bms.scmp.api.product.domain.response.profitloss;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author knight.xie
 * @version 1.0
 * @className DetailProfitLossRespVo
 * @date 2019/6/28 12:10
 * @description TODO
 */
@ApiModel("详情数据返回")
@Data
public class DetailProfitLossRespVo {

    @ApiModelProperty("主键Id")
    private Long id;

    @ApiModelProperty("订单号")
    private String orderCode;

    @ApiModelProperty("订单类型(0:报损 1:报益)")
    private Byte orderType;

    @ApiModelProperty("订单类型名称")
    private String orderTypeName;

    @ApiModelProperty("仓库编号")
    private String logisticsCenterCode;

    @ApiModelProperty("仓库名称")
    private String logisticsCenterName;

    @ApiModelProperty("库房编号")
    private String warehouseCode;

    @ApiModelProperty("库房名称")
    private String warehouseName;

    @ApiModelProperty("数量")
    private Long quantity;

    @ApiModelProperty("含税总成本")
    private Long totalCostRate;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("创建时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty("修改时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;


    @ApiModelProperty("商品列表")
    private List<ProfitLossProductRespVo> products;

    @ApiModelProperty("批次商品列表")
    private List<ProfitLossProductBatchRespVo> batchProducts;



}
