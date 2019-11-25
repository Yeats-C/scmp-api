package com.aiqin.bms.scmp.api.product.domain.converter;

import com.aiqin.bms.scmp.api.base.InOutStatus;
import com.aiqin.bms.scmp.api.common.BizException;
import com.aiqin.bms.scmp.api.common.OutboundTypeEnum;
import com.aiqin.bms.scmp.api.constant.Global;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuCheckout;
import com.aiqin.bms.scmp.api.product.domain.request.order.OrderInfo;
import com.aiqin.bms.scmp.api.product.domain.request.order.SupplyOrderInfo;
import com.aiqin.bms.scmp.api.product.domain.request.order.SupplyOrderProductItem;
import com.aiqin.bms.scmp.api.product.domain.request.outbound.OutboundProductReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.outbound.OutboundReqVo;
import com.aiqin.bms.scmp.api.product.service.SkuService;
import com.aiqin.bms.scmp.api.util.BeanCopyUtils;
import com.aiqin.bms.scmp.api.util.Calculate;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Description:
 *
 * @author: zth
 * @date: 2019-03-04
 * @time: 15:59
 */
@Component
@Slf4j
public class OrderVo2OutBoundConverter implements Converter<List<OrderInfo>, List<OutboundReqVo>> {
    private SkuService skuService;
    public OrderVo2OutBoundConverter(SkuService skuService) {
        this.skuService = skuService;
    }

    @Override
    public List<OutboundReqVo> convert(List<OrderInfo> source) {
        List<OutboundReqVo> list = Lists.newArrayList();
        try {
            for (OrderInfo reqMainVo : source) {
                if (CollectionUtils.isEmpty(reqMainVo.getProductItem())) {
                    throw new BizException("入库单保存请求vo的商品项集合不能为空");
                }
                SupplyOrderInfo reqVo = reqMainVo.getOrderInfo();
                List<String> skuCodes = reqMainVo.getProductItem().stream().map(SupplyOrderProductItem::getSkuCode).collect(Collectors.toList());
                List<ProductSkuCheckout> skuCheckOuts = skuService.getSkuCheckOuts(skuCodes);
                Map<String, Long> map = skuCheckOuts.stream().collect(Collectors.toMap(ProductSkuCheckout::getSkuCode, ProductSkuCheckout::getOutputTaxRate, (k1, k2) -> k2));
//              InboundSavePo po = new InboundSavePo();
                OutboundReqVo outbound = new OutboundReqVo();
                BeanUtils.copyProperties(reqVo, outbound);
                outbound.setOutboundTypeCode(OutboundTypeEnum.ORDER.getCode());
                outbound.setOutboundTypeName(OutboundTypeEnum.ORDER.getName());
                outbound.setOutboundStatusCode(InOutStatus.CREATE_INOUT.getCode());
                outbound.setOutboundStatusName(InOutStatus.CREATE_INOUT.getName());
                outbound.setCompanyCode(reqVo.getCompanyCode());
                outbound.setCompanyName(reqVo.getCompanyName());
                outbound.setOutboundTime(new Date());
                outbound.setSourceOderCode(reqVo.getOrderCode());
                outbound.setLogisticsCenterCode(reqVo.getTransportCenterCode());
                outbound.setLogisticsCenterName(reqVo.getTransportCenterName());
                outbound.setSupplierName(reqVo.getSupplierName());
                outbound.setSupplierCode(reqVo.getSupplierCode());
                outbound.setPreArrivalTime(reqVo.getReceivingTime());
                outbound.setPreOutboundNum(reqVo.getProductNum());
                outbound.setPreMainUnitNum(reqVo.getProductNum());
                outbound.setPreTaxAmount(reqVo.getOrderAmount());
                outbound.setPreAmount(reqVo.getOrderAmount());
                outbound.setPreTax(BigDecimal.valueOf(0));
//                order.setPraTax(order.getPreTax());
//                order.setPraAmount(order.getPreAmount());
//                order.setPraTaxAmount(order.getPreTaxAmount());
//                order.setPraOutboundNum(order.getPraOutboundNum());
//                order.setPraMainUnitNum(order.getPreMainUnitNum());
                outbound.setCreateTime(new Date());
                outbound.setCreateBy(reqVo.getOperator());
                // 收货人
                outbound.setConsigneeRate(reqVo.getConsignee());
                outbound.setConsigneeRate(reqVo.getZipCode());
                outbound.setConsigneeNumber(reqVo.getConsigneePhone());
                //省市区详细地址
                outbound.setProvinceCode(reqVo.getProvinceCode());
                outbound.setProvinceName(reqVo.getProvinceName());
                outbound.setCityCode(reqVo.getCityCode());
                outbound.setCityName(reqVo.getCityName());
                outbound.setCountyCode(reqVo.getDistrictCode());
                outbound.setCountyName(reqVo.getDistrictName());
                outbound.setDetailedAddress(reqVo.getDetailAddress());
//            if(StringUtils.isNotBlank(inbound.getUpdateBy())){
//                inbound.setUpdateTime(new Date());
//            }
                BigDecimal noTaxTotalAmount = BigDecimal.valueOf(0);
                List<OutboundProductReqVo> products = Lists.newArrayList();
                for (SupplyOrderProductItem vo : reqMainVo.getProductItem()) {
                    OutboundProductReqVo product = BeanCopyUtils.copy(vo, OutboundProductReqVo.class);
//                product.setInboundOderCode(inbound.getInboundOderCode());
                    product.setPreOutboundNum(vo.getNum());
                    try {
                        //计算不含税单价
                        Long aLong = map.get(vo.getSkuCode());
                        BigDecimal noTaxPrice = Calculate.computeNoTaxPrice(vo.getPrice(), BigDecimal.valueOf(aLong));

                        //计算不含税总价 (现在是主单位数量 * 单价）
//                long noTaxTotalPrice = noTaxPrice * o.getNum();
                        BigDecimal noTaxTotalPrice = noTaxPrice.multiply(BigDecimal.valueOf(vo.getNum())).setScale(4, BigDecimal.ROUND_HALF_UP);
                        noTaxTotalAmount = noTaxTotalPrice.add(noTaxTotalAmount);
//                        product.setPreInboundMainNum(vo.getNum() * map.get(vo.getSkuCode()));
                    } catch (Exception e) {
                        log.error(Global.ERROR, e);
                        throw new BizException("sku编码:" + vo.getSkuCode() + ",对应的转换单位系数不存在");
                    }
                    product.setCreateTime(new Date());
                    product.setPreOutboundMainNum(vo.getNum());
                    product.setPreOutboundNum(vo.getNum());
                    product.setPreTaxAmount(vo.getAmount());
                    product.setPreTaxPurchaseAmount(vo.getPrice());
                    product.setLinenum(vo.getProductLineNum());
                    //默认取基本数据的基商品含量
                    product.setOutboundBaseContent("1");
//                    product.setPreTaxPurchaseAmount(vo.getPrice());
//                    product.setPraTaxPurchaseAmount(vo.getPrice());
//                    product.setPraTaxAmount(vo.getAmount());
                    product.setOutboundNorms(vo.getSpec());
                    products.add(product);
                }
                outbound.setPreAmount(noTaxTotalAmount);
                outbound.setPreTax(reqVo.getOrderAmount().subtract(noTaxTotalAmount));
//            po.setInbound(inbound);
                outbound.setList(products);
                list.add(outbound);
            }
        } catch (Exception e) {
            log.error(Global.ERROR, e);
            if (e instanceof BizException) {
                throw new BizException(e.getMessage());
            } else {
                throw new BizException("出库单保存请求reqVo转换Po异常");
            }
        }
        return list;
    }
}
