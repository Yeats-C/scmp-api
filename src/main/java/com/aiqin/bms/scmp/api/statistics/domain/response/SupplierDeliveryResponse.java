package com.aiqin.bms.scmp.api.statistics.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author: zhao shuai
 * @create: 2019-08-19
 **/
@Data
@Api(tags = "供应商到货率统计")
public class SupplierDeliveryResponse extends ShareResponse{

    @ApiModelProperty(value="采购组负责人编码")
    @JsonProperty("responsible_person_code")
    private String responsiblePersonCode;

    @ApiModelProperty(value="采购组负责人名称")
    @JsonProperty("responsible_person_name")
    private String responsiblePersonName;

    @ApiModelProperty(value="华北仓订货数量")
    @JsonProperty("hb_goods_count")
    private Long hbGoodsCount;

    @ApiModelProperty(value="华北仓订货金额")
    @JsonProperty("hb_goods_amount")
    private Long hbGoodsAmount;

    @ApiModelProperty(value="华北仓入库数量")
    @JsonProperty("hb_warehouse_count")
    private Long hbWarehouseCount;

    @ApiModelProperty(value="华北仓入库金额")
    @JsonProperty("hb_warehouse_amount")
    private Long hbWarehouseAmount;

    @ApiModelProperty(value="华北仓金额满足率")
    @JsonProperty("hb_amount_rate")
    private BigDecimal hbAmountRate;

    @ApiModelProperty(value="华北仓到货率")
    @JsonProperty("hb_goods_rate")
    private BigDecimal hbGoodsRate;

    @ApiModelProperty(value="华南仓订货数量")
    @JsonProperty("hn_goods_count")
    private Long hnGoodsCount;

    @ApiModelProperty(value="华南仓订货金额")
    @JsonProperty("hn_goods_amount")
    private Long hnGoodsAmount;

    @ApiModelProperty(value="华南仓入库数量")
    @JsonProperty("hn_warehouse_count")
    private Long hnWarehouseCount;

    @ApiModelProperty(value="华南仓入库金额")
    @JsonProperty("hn_warehouse_amount")
    private Long hnWarehouseAmount;

    @ApiModelProperty(value="华南仓金额满足率")
    @JsonProperty("hn_amount_rate")
    private BigDecimal hnAmountRate;

    @ApiModelProperty(value="华南仓到货率")
    @JsonProperty("hn_goods_rate")
    private BigDecimal hnGoodsRate;

    @ApiModelProperty(value="西南仓订货数量")
    @JsonProperty("xn_goods_count")
    private Long xnGoodsCount;

    @ApiModelProperty(value="西南仓订货金额")
    @JsonProperty("xn_goods_amount")
    private Long xnGoodsAmount;

    @ApiModelProperty(value="西南仓入库数量")
    @JsonProperty("xn_warehouse_count")
    private Long xnWarehouseCount;

    @ApiModelProperty(value="西南仓入库金额")
    @JsonProperty("xn_warehouse_amount")
    private Long xnWarehouseAmount;

    @ApiModelProperty(value="西南仓金额满足率")
    @JsonProperty("xn_amount_rate")
    private BigDecimal xnAmountRate;

    @ApiModelProperty(value="西南仓到货率")
    @JsonProperty("xn_goods_rate")
    private BigDecimal xnGoodsRate;

    @ApiModelProperty(value="华中仓订货数量")
    @JsonProperty("hz_goods_count")
    private Long hzGoodsCount;

    @ApiModelProperty(value="华中仓订货金额")
    @JsonProperty("hz_goods_amount")
    private Long hzGoodsAmount;

    @ApiModelProperty(value="华中仓入库数量")
    @JsonProperty("hz_warehouse_count")
    private Long hzWarehouseCount;

    @ApiModelProperty(value="华中仓入库金额")
    @JsonProperty("hz_warehouse_amount")
    private Long hzWarehouseAmount;

    @ApiModelProperty(value="华中仓金额满足率")
    @JsonProperty("hz_amount_rate")
    private BigDecimal hzAmountRate;

    @ApiModelProperty(value="华中仓到货率")
    @JsonProperty("hz_goods_rate")
    private BigDecimal hzGoodsRate;

    @ApiModelProperty(value="华东仓订货数量")
    @JsonProperty("hd_goods_count")
    private Long hdGoodsCount;

    @ApiModelProperty(value="华东仓订货金额")
    @JsonProperty("hd_goods_amount")
    private Long hdGoodsAmount;

    @ApiModelProperty(value="华东仓入库数量")
    @JsonProperty("hd_warehouse_count")
    private Long hdWarehouseCount;

    @ApiModelProperty(value="华东仓入库金额")
    @JsonProperty("hd_warehouse_amount")
    private Long hdWarehouseAmount;

    @ApiModelProperty(value="华东仓金额满足率")
    @JsonProperty("hd_amount_rate")
    private BigDecimal hdAmountRate;

    @ApiModelProperty(value="华东仓到货率")
    @JsonProperty("hd_goods_rate")
    private BigDecimal hdGoodsRate;

    @ApiModelProperty(value="全国仓订货数量")
    @JsonProperty("sum_goods_count")
    private Long sumGoodsCount;

    @ApiModelProperty(value="全国仓订货金额")
    @JsonProperty("sum_goods_amount")
    private Long sumGoodsAmount;

    @ApiModelProperty(value="全国仓入库数量")
    @JsonProperty("sum_warehouse_count")
    private Long sumWarehouseCount;

    @ApiModelProperty(value="全国仓入库金额")
    @JsonProperty("sum_warehouse_amount")
    private Long sumWarehouseAmount;

    @ApiModelProperty(value="全国仓金额满足率")
    @JsonProperty("sum_amount_rate")
    private BigDecimal sumAmountRate;

    @ApiModelProperty(value="全国仓到货率")
    @JsonProperty("sum_goods_rate")
    private BigDecimal sumGoodsRate;

}
