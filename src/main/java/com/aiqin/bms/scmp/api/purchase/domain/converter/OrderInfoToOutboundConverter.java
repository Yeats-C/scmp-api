package com.aiqin.bms.scmp.api.purchase.domain.converter;

import com.aiqin.bms.scmp.api.base.InOutStatus;
import com.aiqin.bms.scmp.api.common.OutboundTypeEnum;
import com.aiqin.bms.scmp.api.product.domain.request.outbound.OutboundProductReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.outbound.OutboundReqVo;
import com.aiqin.bms.scmp.api.product.domain.response.sku.purchase.PurchaseItemRespVo;
import com.aiqin.bms.scmp.api.product.service.SkuService;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.order.OrderInfo;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.order.OrderInfoItem;
import com.aiqin.bms.scmp.api.supplier.domain.response.supplier.SupplyComDetailByCodeRespVO;
import com.aiqin.bms.scmp.api.supplier.service.SupplyComService;
import com.aiqin.ground.util.exception.GroundRuntimeException;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 * ━━━━━━神兽出没━━━━━━
 * 　　┏┓　　　┏┓+ +
 * 　┏┛┻━━━┛┻┓ + +
 * 　┃　　　　　　　┃
 * 　┃　　　━　　　┃ ++ + + +
 * ████━████ ┃+
 * 　┃　　　　　　　┃ +
 * 　┃　　　┻　　　┃
 * 　┃　　　　　　　┃
 * 　┗━┓　　　┏━┛
 * 　　　┃　　　┃                  神兽保佑, 永无BUG!
 * 　　　┃　　　┃
 * 　　　┃　　　┃     Code is far away from bug with the animal protecting
 * 　　　┃　 　　┗━━━┓
 * 　　　┃ 　　　　　　　┣┓
 * 　　　┃ 　　　　　　　┏┛
 * 　　　┗┓┓┏━┳┓┏┛
 * 　　　　┃┫┫　┃┫┫
 * 　　　　┗┻┛　┗┻┛
 * ━━━━━━感觉萌萌哒━━━━━━
 * <p>
 * <p>
 * 思维方式*热情*能力
 */
public class OrderInfoToOutboundConverter implements Converter<OrderInfo, OutboundReqVo> {

    private SkuService skuService;

    private SupplyComService supplyComService;

    public OrderInfoToOutboundConverter(SkuService skuService, SupplyComService supplyComService) {
        this.skuService = skuService;
        this.supplyComService = supplyComService;
    }

    @Override
    public OutboundReqVo convert(OrderInfo orderInfo) {
        OutboundReqVo stockReqVO = new OutboundReqVo();
        BeanUtils.copyProperties(orderInfo, stockReqVO);
        stockReqVO.setSourceOderCode(orderInfo.getOrderCode());
        //配送中心
        stockReqVO.setLogisticsCenterCode(orderInfo.getTransportCenterCode());
        stockReqVO.setLogisticsCenterName(orderInfo.getTransportCenterName());
        //预计
        stockReqVO.setPreOutboundNum(orderInfo.getPreProductNum());
        stockReqVO.setPreAmount(orderInfo.getProductChannelTotalAmount());
        stockReqVO.setPreMainUnitNum(orderInfo.getPreProductNum());
        stockReqVO.setPreTaxAmount(orderInfo.getProductChannelTotalAmount());
        stockReqVO.setPreTax(orderInfo.getProductChannelTotalAmount());
        //实际
        stockReqVO.setPraOutboundNum(orderInfo.getProductNum());
        stockReqVO.setPraMainUnitNum(orderInfo.getProductNum());
        stockReqVO.setPraTaxAmount(orderInfo.getProductChannelTotalAmount());
        stockReqVO.setPraAmount(orderInfo.getProductChannelTotalAmount());
        stockReqVO.setPraTax(orderInfo.getProductChannelTotalAmount());
        stockReqVO.setConsigneeNumber(orderInfo.getConsigneePhone());
        stockReqVO.setDetailedAddress(orderInfo.getDetailAddress());
        stockReqVO.setCreateBy(orderInfo.getCreateByName());
        stockReqVO.setUpdateBy(orderInfo.getUpdateByName());
        stockReqVO.setCreateTime(orderInfo.getCreateDate());
        stockReqVO.setUpdateTime(orderInfo.getCreateDate());
        stockReqVO.setRemark(orderInfo.getRemake());
        //状态
        stockReqVO.setOutboundStatusCode(InOutStatus.COMPLETE_INOUT.getCode());
        stockReqVO.setOutboundStatusName(InOutStatus.COMPLETE_INOUT.getName());
        //类型
        stockReqVO.setOutboundTypeCode(OutboundTypeEnum.ORDER.getCode());
        stockReqVO.setOutboundTypeName(OutboundTypeEnum.ORDER.getName());
        //出库时间
        stockReqVO.setOutboundTime(orderInfo.getCreateDate());
        stockReqVO.setPreArrivalTime(orderInfo.getReceivingTime());
        if (StringUtils.isNotBlank(orderInfo.getSupplierCode())) {
            SupplyComDetailByCodeRespVO supplyComDetailByCodeRespVO = supplyComService.detailByCode(orderInfo.getSupplierCode());
            if (Objects.isNull(supplyComDetailByCodeRespVO)) {
                throw new GroundRuntimeException("获取供货单位信息失败");
            }
            stockReqVO.setConsigneeRate(supplyComDetailByCodeRespVO.getZipCode());
        }
        List<String> skuCodes = orderInfo.getDetailList().stream().map(OrderInfoItem::getSkuCode).collect(Collectors.toList());
        Map<String, PurchaseItemRespVo> map2 = skuService.getSalesSkuList(skuCodes).stream().collect(Collectors.toMap(PurchaseItemRespVo::getSkuCode, Function.identity(), (k1, k2) -> k2));
        List<OrderInfoItem> items = orderInfo.getDetailList();
        List<OutboundProductReqVo> parts = Lists.newArrayList();
        OutboundProductReqVo outboundProduct;
        for (OrderInfoItem item : items) {
            outboundProduct = new OutboundProductReqVo();
            //sku
            outboundProduct.setSkuCode(item.getSkuCode());
            outboundProduct.setSkuName(item.getSkuName());
            //图片
            outboundProduct.setPictureUrl(map2.get(item.getSkuCode()).getPicUrl());
            //规格
            outboundProduct.setNorms(map2.get(item.getSkuCode()).getSpec());
            outboundProduct.setOutboundNorms(map2.get(item.getSkuCode()).getSpec());
            //单位
            outboundProduct.setUnitCode(item.getUnitCode());
            outboundProduct.setUnitName(item.getUnitName());
            //todo 进货规格 不知道取哪
//            outboundProduct.setOutboundNorms(item.getProductSpec());
            //预计出库数量
            outboundProduct.setPreOutboundNum(item.getNum());
            //预计出库主数量
            outboundProduct.setPreOutboundMainNum(item.getNum());
            //预计含税进价
            outboundProduct.setPreTaxPurchaseAmount(item.getChannelUnitPrice());
            //预计含税总价
            outboundProduct.setPreTaxAmount(item.getNum() * item.getChannelUnitPrice());

            //实际出库数量
            outboundProduct.setPraOutboundNum(item.getActualDeliverNum());
            //实际出库主数量
            outboundProduct.setPraOutboundMainNum(item.getActualDeliverNum());
            //实际含税进价
            outboundProduct.setPraTaxPurchaseAmount(item.getChannelUnitPrice());
            //实际含税总价
            outboundProduct.setPraTaxAmount(item.getActualDeliverNum() * item.getChannelUnitPrice());

            outboundProduct.setColorCode(item.getColorCode());
            outboundProduct.setColorName(item.getColorName());
//            outboundProduct.setCreateBy(orderInfo.getCreateBy());
            outboundProduct.setUpdateBy(orderInfo.getOperator());
            outboundProduct.setCreateTime(orderInfo.getCreateDate());
            outboundProduct.setUpdateTime(orderInfo.getCreateDate());
            //行号
            outboundProduct.setLinenum(item.getProductLineNum());
            //基商品含量固定1
            outboundProduct.setOutboundBaseContent("1");
            outboundProduct.setOutboundBaseUnit("1");
            //不计算不含税单价
            parts.add(outboundProduct);
        }
        stockReqVO.setList(parts);
        return stockReqVO;
    }
}
