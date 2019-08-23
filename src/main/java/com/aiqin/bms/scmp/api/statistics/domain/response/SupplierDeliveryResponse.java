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

    @ApiModelProperty(value="华北仓订货数量小计")
    @JsonProperty("hb_subtotal_goods_count")
    private Long hbSubtotalGoodsCount;

    @ApiModelProperty(value="华北仓订货金额小计")
    @JsonProperty("hb_subtotal_goods_amount")
    private Long hbSubtotalGoodsAmount;

    @ApiModelProperty(value="华北仓入库数量小计")
    @JsonProperty("hb_subtotal_warehouse_count")
    private Long hbSubtotalWarehouseCount;

    @ApiModelProperty(value="华北仓入库金额小计")
    @JsonProperty("hb_subtotal_warehouse_amount")
    private Long hbSubtotalWarehouseAmount;

    @ApiModelProperty(value="华北仓金额满足率小计")
    @JsonProperty("hb_subtotal_amount_rate")
    private BigDecimal hbSubtotalAmountRate;

    @ApiModelProperty(value="华北仓到货率小计")
    @JsonProperty("hb_subtotal_goods_rate")
    private BigDecimal hbSubtotalGoodsRate;

    @ApiModelProperty(value="华北仓订货数量合计")
    @JsonProperty("hb_total_goods_count")
    private Long hbTotalGoodsCount;

    @ApiModelProperty(value="华北仓订货金额合计")
    @JsonProperty("hb_total__goods_amount")
    private Long hbTotalGoodsAmount;

    @ApiModelProperty(value="华北仓入库数量合计")
    @JsonProperty("hb_total_warehouse_count")
    private Long hbTotalWarehouseCount;

    @ApiModelProperty(value="华北仓入库金额合计")
    @JsonProperty("hb_total_warehouse_amount")
    private Long hbTotalWarehouseAmount;

    @ApiModelProperty(value="华北仓金额满足率合计")
    @JsonProperty("hb_total_amount_rate")
    private BigDecimal hbTotalAmountRate;

    @ApiModelProperty(value="华北仓到货率合计")
    @JsonProperty("hb_total_goods_rate")
    private BigDecimal hbTotalGoodsRate;

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

    @ApiModelProperty(value="华南仓订货数量小计")
    @JsonProperty("hn_subtotal_goods_count")
    private Long hnSubtotalGoodsCount;

    @ApiModelProperty(value="华南仓订货金额小计")
    @JsonProperty("hn_subtotal_goods_amount")
    private Long hnSubtotalGoodsAmount;

    @ApiModelProperty(value="华南仓入库数量小计")
    @JsonProperty("hn_subtotal_warehouse_count")
    private Long hnSubtotalWarehouseCount;

    @ApiModelProperty(value="华南仓入库金额小计")
    @JsonProperty("hn_subtotal_warehouse_amount")
    private Long hnSubtotalWarehouseAmount;

    @ApiModelProperty(value="华南仓金额满足率小计")
    @JsonProperty("hn_subtotal_amount_rate")
    private BigDecimal hnSubtotalAmountRate;

    @ApiModelProperty(value="华南仓到货率小计")
    @JsonProperty("hn_subtotal_goods_rate")
    private BigDecimal hnSubtotalGoodsRate;

    @ApiModelProperty(value="华南仓订货数量合计")
    @JsonProperty("hn_total_goods_count")
    private Long hnTotalGoodsCount;

    @ApiModelProperty(value="华南仓订货金额合计")
    @JsonProperty("hn_total__goods_amount")
    private Long hnTotalGoodsAmount;

    @ApiModelProperty(value="华南仓入库数量合计")
    @JsonProperty("hn_total_warehouse_count")
    private Long hnTotalWarehouseCount;

    @ApiModelProperty(value="华南仓入库金额合计")
    @JsonProperty("hn_total_warehouse_amount")
    private Long hnTotalWarehouseAmount;

    @ApiModelProperty(value="华南仓金额满足率合计")
    @JsonProperty("hn_total_amount_rate")
    private BigDecimal hnTotalAmountRate;

    @ApiModelProperty(value="华南仓到货率合计")
    @JsonProperty("hn_total_goods_rate")
    private BigDecimal hnTotalGoodsRate;

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

    @ApiModelProperty(value="西南仓订货数量小计")
    @JsonProperty("xn_subtotal_goods_count")
    private Long xnSubtotalGoodsCount;

    @ApiModelProperty(value="西南仓订货金额小计")
    @JsonProperty("xn_subtotal_goods_amount")
    private Long xnSubtotalGoodsAmount;

    @ApiModelProperty(value="西南仓入库数量小计")
    @JsonProperty("xn_subtotal_warehouse_count")
    private Long xnSubtotalWarehouseCount;

    @ApiModelProperty(value="西南仓入库金额小计")
    @JsonProperty("xn_subtotal_warehouse_amount")
    private Long xnSubtotalWarehouseAmount;

    @ApiModelProperty(value="西南仓金额满足率小计")
    @JsonProperty("xn_subtotal_amount_rate")
    private BigDecimal xnSubtotalAmountRate;

    @ApiModelProperty(value="西南仓到货率小计")
    @JsonProperty("xn_subtotal_goods_rate")
    private BigDecimal xnSubtotalGoodsRate;

    @ApiModelProperty(value="西南仓订货数量合计")
    @JsonProperty("xn_total_goods_count")
    private Long xnTotalGoodsCount;

    @ApiModelProperty(value="西南仓订货金额合计")
    @JsonProperty("xn_total__goods_amount")
    private Long xnTotalGoodsAmount;

    @ApiModelProperty(value="西南仓入库数量合计")
    @JsonProperty("xn_total_warehouse_count")
    private Long xnTotalWarehouseCount;

    @ApiModelProperty(value="西南仓入库金额合计")
    @JsonProperty("xn_total_warehouse_amount")
    private Long xnTotalWarehouseAmount;

    @ApiModelProperty(value="西南仓金额满足率合计")
    @JsonProperty("xn_total_amount_rate")
    private BigDecimal xnTotalAmountRate;

    @ApiModelProperty(value="西南仓到货率合计")
    @JsonProperty("xn_total_goods_rate")
    private BigDecimal xnTotalGoodsRate;

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

    @ApiModelProperty(value="华中仓订货数量小计")
    @JsonProperty("hz_subtotal_goods_count")
    private Long hzSubtotalGoodsCount;

    @ApiModelProperty(value="华中仓订货金额小计")
    @JsonProperty("hz_subtotal_goods_amount")
    private Long hzSubtotalGoodsAmount;

    @ApiModelProperty(value="华中仓入库数量小计")
    @JsonProperty("hz_subtotal_warehouse_count")
    private Long hzSubtotalWarehouseCount;

    @ApiModelProperty(value="华中仓入库金额小计")
    @JsonProperty("hz_subtotal_warehouse_amount")
    private Long hzSubtotalWarehouseAmount;

    @ApiModelProperty(value="华中仓金额满足率小计")
    @JsonProperty("hz_subtotal_amount_rate")
    private BigDecimal hzSubtotalAmountRate;

    @ApiModelProperty(value="华中仓到货率小计")
    @JsonProperty("hz_subtotal_goods_rate")
    private BigDecimal hzSubtotalGoodsRate;

    @ApiModelProperty(value="华中仓订货数量合计")
    @JsonProperty("hz_total_goods_count")
    private Long hzTotalGoodsCount;

    @ApiModelProperty(value="华中仓订货金额合计")
    @JsonProperty("hz_total__goods_amount")
    private Long hzTotalGoodsAmount;

    @ApiModelProperty(value="华中仓入库数量合计")
    @JsonProperty("hz_total_warehouse_count")
    private Long hzTotalWarehouseCount;

    @ApiModelProperty(value="华中仓入库金额合计")
    @JsonProperty("hz_total_warehouse_amount")
    private Long hzTotalWarehouseAmount;

    @ApiModelProperty(value="华中仓金额满足率合计")
    @JsonProperty("hz_total_amount_rate")
    private BigDecimal hzTotalAmountRate;

    @ApiModelProperty(value="华中仓到货率合计")
    @JsonProperty("hz_total_goods_rate")
    private BigDecimal hzTotalGoodsRate;

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

    @ApiModelProperty(value="华东仓订货数量小计")
    @JsonProperty("hd_subtotal_goods_count")
    private Long hdSubtotalGoodsCount;

    @ApiModelProperty(value="华东仓订货金额小计")
    @JsonProperty("hd_subtotal_goods_amount")
    private Long hdSubtotalGoodsAmount;

    @ApiModelProperty(value="华东仓入库数量小计")
    @JsonProperty("hd_subtotal_warehouse_count")
    private Long hdSubtotalWarehouseCount;

    @ApiModelProperty(value="华东仓入库金额小计")
    @JsonProperty("hd_subtotal_warehouse_amount")
    private Long hdSubtotalWarehouseAmount;

    @ApiModelProperty(value="华东仓金额满足率小计")
    @JsonProperty("hd_subtotal_amount_rate")
    private BigDecimal hdSubtotalAmountRate;

    @ApiModelProperty(value="华东仓到货率小计")
    @JsonProperty("hd_subtotal_goods_rate")
    private BigDecimal hdSubtotalGoodsRate;

    @ApiModelProperty(value="华东仓订货数量合计")
    @JsonProperty("hd_total_goods_count")
    private Long hdTotalGoodsCount;

    @ApiModelProperty(value="华东仓订货金额合计")
    @JsonProperty("hd_total__goods_amount")
    private Long hdTotalGoodsAmount;

    @ApiModelProperty(value="华东仓入库数量合计")
    @JsonProperty("hd_total_warehouse_count")
    private Long hdTotalWarehouseCount;

    @ApiModelProperty(value="华东仓入库金额合计")
    @JsonProperty("hd_total_warehouse_amount")
    private Long hdTotalWarehouseAmount;

    @ApiModelProperty(value="华东仓金额满足率合计")
    @JsonProperty("hd_total_amount_rate")
    private BigDecimal hdTotalAmountRate;

    @ApiModelProperty(value="华东仓到货率合计")
    @JsonProperty("hd_total_goods_rate")
    private BigDecimal hdTotalGoodsRate;

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

    @ApiModelProperty(value="全国仓订货数量小计")
    @JsonProperty("sum_subtotal_goods_count")
    private Long sumSubtotalGoodsCount;

    @ApiModelProperty(value="全国仓订货金额小计")
    @JsonProperty("sum_subtotal_goods_amount")
    private Long sumSubtotalGoodsAmount;

    @ApiModelProperty(value="全国仓入库数量小计")
    @JsonProperty("sum_subtotal_warehouse_count")
    private Long sumSubtotalWarehouseCount;

    @ApiModelProperty(value="全国仓入库金额小计")
    @JsonProperty("sum_subtotal_warehouse_amount")
    private Long sumSubtotalWarehouseAmount;

    @ApiModelProperty(value="全国仓金额满足率小计")
    @JsonProperty("sum_subtotal_amount_rate")
    private BigDecimal sumSubtotalAmountRate;

    @ApiModelProperty(value="全国仓到货率小计")
    @JsonProperty("sum_subtotal_goods_rate")
    private BigDecimal sumSubtotalGoodsRate;

    @ApiModelProperty(value="全国仓订货数量合计")
    @JsonProperty("sum_total_goods_count")
    private Long sumTotalGoodsCount;

    @ApiModelProperty(value="全国仓订货金额合计")
    @JsonProperty("sum_total__goods_amount")
    private Long sumTotalGoodsAmount;

    @ApiModelProperty(value="全国仓入库数量合计")
    @JsonProperty("sum_total_warehouse_count")
    private Long sumTotalWarehouseCount;

    @ApiModelProperty(value="全国仓入库金额合计")
    @JsonProperty("sum_total_warehouse_amount")
    private Long sumTotalWarehouseAmount;

    @ApiModelProperty(value="全国仓金额满足率合计")
    @JsonProperty("sum_total_amount_rate")
    private BigDecimal sumTotalAmountRate;

    @ApiModelProperty(value="全国仓到货率合计")
    @JsonProperty("sum_total_goods_rate")
    private BigDecimal sumTotalGoodsRate;

}
