package com.aiqin.bms.scmp.api.purchase.domain.converter;

import com.aiqin.bms.scmp.api.base.InOutStatus;
import com.aiqin.bms.scmp.api.common.OutboundTypeEnum;
import com.aiqin.bms.scmp.api.product.dao.ProductSkuDao;
import com.aiqin.bms.scmp.api.product.domain.pojo.Allocation;
import com.aiqin.bms.scmp.api.product.domain.pojo.AllocationProduct;
import com.aiqin.bms.scmp.api.product.domain.request.outbound.OutboundProductReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.outbound.OutboundReqVo;
import com.aiqin.bms.scmp.api.product.domain.response.sku.purchase.PurchaseItemRespVo;
import com.aiqin.bms.scmp.api.product.service.SkuService;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.order.OrderInfo;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.order.OrderInfoItem;
import com.aiqin.bms.scmp.api.purchase.domain.response.order.OrderProductSkuResponse;
import com.aiqin.bms.scmp.api.supplier.domain.response.supplier.SupplyComDetailByCodeRespVO;
import com.aiqin.bms.scmp.api.supplier.service.SupplyComService;
import com.aiqin.ground.util.exception.GroundRuntimeException;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

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
public class AllocationToOutboundConverter implements Converter<Allocation, OutboundReqVo> {

    private ProductSkuDao productSkuDao;

    public AllocationToOutboundConverter(ProductSkuDao productSkuDao) {
        this.productSkuDao = productSkuDao;
    }

    @Override
    public OutboundReqVo convert(Allocation allocation) {
        OutboundReqVo stockReqVO = new OutboundReqVo();
        BeanUtils.copyProperties(allocation, stockReqVO);
        stockReqVO.setSourceOderCode(allocation.getAllocationCode());
        //配送中心
        stockReqVO.setLogisticsCenterCode(allocation.getCallInLogisticsCenterCode());
        stockReqVO.setLogisticsCenterName(allocation.getCallInLogisticsCenterName());
        //预计
        stockReqVO.setPreOutboundNum(allocation.getQuantity());
//        stockReqVO.setPreAmount(allocation.getProductChannelTotalAmount());
        stockReqVO.setPreMainUnitNum(allocation.getQuantity());
//        stockReqVO.setPreTaxAmount(allocation.getProductChannelTotalAmount());
//        stockReqVO.setPreTax(allocation.getProductChannelTotalAmount());
        //实际
        stockReqVO.setPraOutboundNum(allocation.getQuantity());
        stockReqVO.setPraMainUnitNum(allocation.getQuantity());
//        stockReqVO.setPraTaxAmount(allocation.getProductChannelTotalAmount());
//        stockReqVO.setPraAmount(allocation.getProductChannelTotalAmount());
//        stockReqVO.setPraTax(allocation.getProductChannelTotalAmount());
        stockReqVO.setConsigneeNumber(allocation.getUpdateBy());
//        stockReqVO.setDetailedAddress(allocation.getDetailAddress());
        stockReqVO.setCreateBy(allocation.getCreateBy());
        stockReqVO.setCreateTime(allocation.getCreateTime());
        stockReqVO.setUpdateTime(allocation.getUpdateTime());
        //状态
        stockReqVO.setOutboundStatusCode(InOutStatus.COMPLETE_INOUT.getCode());
        stockReqVO.setOutboundStatusName(InOutStatus.COMPLETE_INOUT.getName());
        //类型
        stockReqVO.setOutboundTypeCode(OutboundTypeEnum.ALLOCATE.getCode());
        stockReqVO.setOutboundTypeName(OutboundTypeEnum.ALLOCATE.getName());
        //出库时间
        stockReqVO.setOutboundTime(allocation.getUpdateTime());
        stockReqVO.setPreArrivalTime(allocation.getUpdateTime());
        List<String> skuCodes = allocation.getDetailList().stream().map(AllocationProduct::getSkuCode).collect(Collectors.toList());
        Map<String, OrderProductSkuResponse> map2 = productSkuDao.selectStockSkuInfoList(skuCodes).stream().collect(Collectors.toMap(OrderProductSkuResponse::getSkuCode, Function.identity(), (k1, k2) -> k2));
        List<OutboundProductReqVo> parts = Lists.newArrayList();
        OutboundProductReqVo outboundProduct;
        for (AllocationProduct item : allocation.getDetailList()) {
            outboundProduct = new OutboundProductReqVo();
            //sku
            outboundProduct.setSkuCode(item.getSkuCode());
            outboundProduct.setSkuName(item.getSkuName());
            //图片
            outboundProduct.setPictureUrl(map2.get(item.getSkuCode()).getPictureUrl());
            //规格
            outboundProduct.setNorms(map2.get(item.getSkuCode()).getSpec());
            outboundProduct.setOutboundNorms(map2.get(item.getSkuCode()).getSpec());
            //单位
            outboundProduct.setUnitCode(map2.get(item.getSkuCode()).getUnitCode());
            outboundProduct.setUnitName(map2.get(item.getSkuCode()).getUnitName());
            //todo 进货规格 不知道取哪
//            outboundProduct.setOutboundNorms(item.getProductSpec());
            //预计出库数量
            outboundProduct.setPreOutboundNum(item.getQuantity());
            //预计出库主数量
            outboundProduct.setPreOutboundMainNum(item.getQuantity());
            //预计含税进价
//            outboundProduct.setPreTaxPurchaseAmount(item.getChannelUnitPrice());
//            预计含税总价
//            outboundProduct.setPreTaxAmount(item.getNum() * item.getChannelUnitPrice());

            //实际出库数量
            outboundProduct.setPraOutboundNum(item.getQuantity());
            //实际出库主数量
            outboundProduct.setPraOutboundMainNum(item.getQuantity());
            //实际含税进价
//            outboundProduct.setPraTaxPurchaseAmount(item.getChannelUnitPrice());
            //实际含税总价
//            outboundProduct.setPraTaxAmount(item.getActualDeliverNum() * item.getChannelUnitPrice());

            outboundProduct.setColorCode(map2.get(item.getSkuCode()).getColorCode());
            outboundProduct.setColorName(map2.get(item.getSkuCode()).getColorName());
            outboundProduct.setCreateBy(allocation.getCreateBy());
            outboundProduct.setUpdateBy(allocation.getUpdateBy());
            outboundProduct.setCreateTime(allocation.getCreateTime());
            outboundProduct.setUpdateTime(allocation.getUpdateTime());
            //行号
            outboundProduct.setLinenum(item.getLineNum());
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
