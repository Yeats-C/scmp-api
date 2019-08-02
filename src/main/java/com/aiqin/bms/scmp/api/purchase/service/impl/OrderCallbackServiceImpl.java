package com.aiqin.bms.scmp.api.purchase.service.impl;

import com.aiqin.bms.scmp.api.constant.CommonConstant;
import com.aiqin.bms.scmp.api.constant.DictionaryEnum;
import com.aiqin.bms.scmp.api.product.dao.ProductSkuDao;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.order.OrderInfo;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.order.OrderInfoItem;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.returngoods.ReturnOrderInfo;
import com.aiqin.bms.scmp.api.purchase.domain.request.OutboundDetailRequest;
import com.aiqin.bms.scmp.api.purchase.domain.request.OutboundRequest;
import com.aiqin.bms.scmp.api.purchase.domain.request.ReturnRequest;
import com.aiqin.bms.scmp.api.purchase.domain.response.InnerValue;
import com.aiqin.bms.scmp.api.purchase.domain.response.order.OrderProductSkuResponse;
import com.aiqin.bms.scmp.api.purchase.mapper.OrderInfoItemMapper;
import com.aiqin.bms.scmp.api.purchase.mapper.OrderInfoMapper;
import com.aiqin.bms.scmp.api.purchase.service.OrderCallbackService;
import com.aiqin.bms.scmp.api.supplier.dao.dictionary.SupplierDictionaryInfoDao;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.SupplierDictionaryInfo;
import com.aiqin.bms.scmp.api.supplier.domain.response.rule.DetailRespVo;
import com.aiqin.bms.scmp.api.supplier.mapper.SupplierRuleMapper;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
@Service
public class OrderCallbackServiceImpl implements OrderCallbackService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderCallbackServiceImpl.class);
    /**
     * 宁波熙耘
     */
    private final static String COMPANY_CODE = "09";
    @Resource
    private OrderInfoMapper orderInfoMapper;
    @Resource
    private OrderInfoItemMapper orderInfoItemMapper;
    @Resource
    private SupplierRuleMapper supplierRuleMapper;
    @Resource
    private ProductSkuDao productSkuDao;
    @Resource
    private SupplierDictionaryInfoDao supplierDictionaryInfoDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public HttpResponse outboundOrder(OutboundRequest request) {
        //操作时间 签收时间 等于回单时间
        request.setReceivingTime(new DateTime(new Long(request.getReceiptTime())).toDate());
        request.setOperatorTime(request.getReceivingTime());
        //支付时间 发运时间 发货时间 等于创建时间
        request.setCreateDate(new DateTime(new Long(request.getCreateTime())).toDate());
        request.setPaymentTime(request.getCreateDate());
        request.setTransportTime(request.getCreateDate());
        request.setDeliveryTime(request.getCreateDate());
        OrderInfo orderInfo = new OrderInfo();
        BeanUtils.copyProperties(request, orderInfo);
        DetailRespVo detailRespVo = supplierRuleMapper.findByCompanyCode(COMPANY_CODE);
        //取字典表数据
        List<InnerValue> dictionaryInfoList = supplierDictionaryInfoDao.allList();
        Map<String, InnerValue> dictionaryInfoMap = dictionaryInfoList.stream().collect(Collectors.toMap(InnerValue::getName,innerValue -> innerValue));
        //支付方式
        if (dictionaryInfoMap.containsKey(DictionaryEnum.PAY_TYPE.getCode() + request.getPaymentType())) {
            orderInfo.setPaymentTypeCode(dictionaryInfoMap.get(DictionaryEnum.PAY_TYPE.getCode() + request.getPaymentType()).getValue());
            orderInfo.setPaymentType(request.getPaymentType());
        }
        //订单类别
        if (dictionaryInfoMap.containsKey(DictionaryEnum.ORDER_CATEGORY.getCode() + request.getOrderCategory())) {
            orderInfo.setOrderCategoryCode(dictionaryInfoMap.get(DictionaryEnum.ORDER_CATEGORY.getCode() + request.getOrderCategory()).getValue());
            orderInfo.setOrderCategory(request.getOrderCategory());
        }
        //订单类型
        if (dictionaryInfoMap.containsKey(DictionaryEnum.ORDER_TYPE.getCode() + request.getOrderType())) {
            orderInfo.setOrderType(dictionaryInfoMap.get(DictionaryEnum.ORDER_TYPE.getCode() + request.getOrderType()).getValue());
            orderInfo.setOrderType(request.getOrderType());
        }
        //订单体积计算系数
        BigDecimal orderVolumeCoefficient = new BigDecimal(1);
        //订单重量计算系数
        BigDecimal orderWeightCoefficient = new BigDecimal(1);
        if (detailRespVo != null) {
            orderWeightCoefficient = detailRespVo.getOrderWeightCoefficient();
            orderVolumeCoefficient = detailRespVo.getOrderVolumeCoefficient();
        }
        OrderProductSkuResponse productSku;
        List<OrderInfoItem> detailList = new ArrayList<>();
        OrderInfoItem orderInfoItem;
        //总体积
        Long sumBoxVolume = 0L;
        //总重量
        Long sumBoxGrossWeight = 0L;
        for (OutboundDetailRequest outboundDetailRequest : request.getDetail()) {
            //查询商品信息
            orderInfoItem = new OrderInfoItem();
            productSku = productSkuDao.selectSkuInfo(outboundDetailRequest.getSkuCode());
            if (productSku != null) {
                BeanUtils.copyProperties(productSku, orderInfoItem);
                orderInfoItem.setSkuName(productSku.getProductName());
                //(sku体积*数量)的合计*系数
                sumBoxVolume += new BigDecimal(outboundDetailRequest.getNum())
                        .multiply(productSku.getBoxVolume())
                        .multiply(orderVolumeCoefficient).longValue();
                //(sku重量*数量)的合计*系数
                sumBoxGrossWeight += new BigDecimal(outboundDetailRequest.getNum())
                        .multiply(productSku.getBoxGrossWeight())
                        .multiply(orderWeightCoefficient).longValue();
            }
            orderInfoItem.setNum(outboundDetailRequest.getNum());
            orderInfoItem.setProductLineNum(outboundDetailRequest.getProductLineNum());
            orderInfoItem.setSkuCode(outboundDetailRequest.getSkuCode());
            orderInfoItem.setChannelUnitPrice(outboundDetailRequest.getChannelUnitPrice());
            orderInfoItem.setActualDeliverNum(outboundDetailRequest.getActualDeliverNum());
            orderInfoItem.setTotalChannelPrice(outboundDetailRequest.getChannelUnitPrice() * outboundDetailRequest.getNum());
            orderInfoItem.setOrderCode(orderInfo.getOrderCode());
            detailList.add(orderInfoItem);
        }
        //已支付
        orderInfo.setPaymentStatus(CommonConstant.PAID);
        orderInfo.setVolume(sumBoxVolume);
        orderInfo.setWeight(sumBoxGrossWeight);
        Integer count = orderInfoMapper.insert(orderInfo);
        LOGGER.info("添加订单:{}", count);
        Integer detailCount = orderInfoItemMapper.insertList(detailList);
        LOGGER.info("添加订单详情:{}", detailCount);
        return HttpResponse.success();
    }

    @Override
    public HttpResponse returnOrder(ReturnRequest request) {
        //操作时间 签收时间 等于回单时间
        request.setReceivingTime(new DateTime(new Long(request.getReceiptTime())).toDate());
        request.setOperatorTime(request.getReceivingTime());
        //支付时间 发运时间 发货时间 等于创建时间
        request.setCreateDate(new DateTime(new Long(request.getCreateTime())).toDate());
        request.setDeliveryTime(request.getCreateDate());
        ReturnOrderInfo returnOrderInfo = new ReturnOrderInfo();
        BeanUtils.copyProperties(request, returnOrderInfo);
        DetailRespVo detailRespVo = supplierRuleMapper.findByCompanyCode(COMPANY_CODE);
        //取字典表数据
        List<InnerValue> dictionaryInfoList = supplierDictionaryInfoDao.allList();
        Map<String, InnerValue> dictionaryInfoMap = dictionaryInfoList.stream().collect(Collectors.toMap(InnerValue::getName,innerValue -> innerValue));
        //支付方式
        if (dictionaryInfoMap.containsKey(DictionaryEnum.PAY_TYPE.getCode() + request.getPaymentType())) {
            returnOrderInfo.setPaymentTypeCode(dictionaryInfoMap.get(DictionaryEnum.PAY_TYPE.getCode() + request.getPaymentType()).getValue());
            returnOrderInfo.setPaymentType(request.getPaymentType());
        }
        //订单类型
        if (dictionaryInfoMap.containsKey(DictionaryEnum.ORDER_TYPE.getCode() + request.getOrderType())) {
            returnOrderInfo.setOrderType(dictionaryInfoMap.get(DictionaryEnum.ORDER_TYPE.getCode() + request.getOrderType()).getValue());
            returnOrderInfo.setOrderType(request.getOrderType());
        }

        return HttpResponse.success();
    }
}
