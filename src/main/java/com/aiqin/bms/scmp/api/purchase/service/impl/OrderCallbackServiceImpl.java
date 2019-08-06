package com.aiqin.bms.scmp.api.purchase.service.impl;

import com.aiqin.bms.scmp.api.base.EncodingRuleType;
import com.aiqin.bms.scmp.api.base.InOutStatus;
import com.aiqin.bms.scmp.api.common.HandleTypeCoce;
import com.aiqin.bms.scmp.api.common.ObjectTypeCode;
import com.aiqin.bms.scmp.api.common.OutboundTypeEnum;
import com.aiqin.bms.scmp.api.constant.CommonConstant;
import com.aiqin.bms.scmp.api.constant.DictionaryEnum;
import com.aiqin.bms.scmp.api.product.dao.OutboundDao;
import com.aiqin.bms.scmp.api.product.dao.OutboundProductDao;
import com.aiqin.bms.scmp.api.product.dao.ProductSkuDao;
import com.aiqin.bms.scmp.api.product.domain.converter.ReturnSupply2outboundSaveConverter;
import com.aiqin.bms.scmp.api.product.domain.converter.order.OrderToOutBoundConverter;
import com.aiqin.bms.scmp.api.product.domain.pojo.Outbound;
import com.aiqin.bms.scmp.api.product.domain.pojo.OutboundProduct;
import com.aiqin.bms.scmp.api.product.domain.pojo.PriceChannel;
import com.aiqin.bms.scmp.api.product.domain.request.outbound.OutboundReqVo;
import com.aiqin.bms.scmp.api.product.mapper.PriceChannelMapper;
import com.aiqin.bms.scmp.api.product.service.ProductCommonService;
import com.aiqin.bms.scmp.api.product.service.SkuService;
import com.aiqin.bms.scmp.api.product.service.impl.OutboundServiceImpl;
import com.aiqin.bms.scmp.api.purchase.domain.converter.OrderInfoToOutboundConverter;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.order.OrderInfo;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.order.OrderInfoItem;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.returngoods.ReturnOrderInfo;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.returngoods.ReturnOrderInfoItem;
import com.aiqin.bms.scmp.api.purchase.domain.request.OutboundDetailRequest;
import com.aiqin.bms.scmp.api.purchase.domain.request.OutboundRequest;
import com.aiqin.bms.scmp.api.purchase.domain.request.ReturnDetailRequest;
import com.aiqin.bms.scmp.api.purchase.domain.request.ReturnRequest;
import com.aiqin.bms.scmp.api.purchase.domain.response.InnerValue;
import com.aiqin.bms.scmp.api.purchase.domain.response.order.OrderProductSkuResponse;
import com.aiqin.bms.scmp.api.purchase.mapper.OrderInfoItemMapper;
import com.aiqin.bms.scmp.api.purchase.mapper.OrderInfoMapper;
import com.aiqin.bms.scmp.api.purchase.mapper.ReturnOrderInfoItemMapper;
import com.aiqin.bms.scmp.api.purchase.mapper.ReturnOrderInfoMapper;
import com.aiqin.bms.scmp.api.purchase.service.OrderCallbackService;
import com.aiqin.bms.scmp.api.supplier.dao.EncodingRuleDao;
import com.aiqin.bms.scmp.api.supplier.dao.dictionary.SupplierDictionaryInfoDao;
import com.aiqin.bms.scmp.api.supplier.dao.supplier.SupplyCompanyDao;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.EncodingRule;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.SupplyCompany;
import com.aiqin.bms.scmp.api.supplier.domain.response.rule.DetailRespVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.supplier.SupplyComDetailByCodeRespVO;
import com.aiqin.bms.scmp.api.supplier.mapper.SupplierRuleMapper;
import com.aiqin.bms.scmp.api.supplier.service.SupplyComService;
import com.aiqin.bms.scmp.api.util.BeanCopyUtils;
import com.aiqin.ground.util.exception.GroundRuntimeException;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
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
    private final static String COMPANY_NAME = "宁波熙耘";
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
    @Resource
    private SupplyCompanyDao supplyCompanyDao;
    @Resource
    private PriceChannelMapper priceChannelMapper;
    @Resource
    private ReturnOrderInfoMapper returnOrderInfoMapper;
    @Resource
    private ReturnOrderInfoItemMapper returnOrderInfoItemMapper;
    @Resource
    private EncodingRuleDao encodingRuleDao;
    @Resource
    private OutboundDao outboundDao;
    @Resource
    private OutboundProductDao outboundProductDao;
    @Resource
    private ProductCommonService productCommonService;
    @Resource
    private SupplyComService supplyComService;
    @Resource
    private SkuService skuService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public HttpResponse outboundOrder(OutboundRequest request) {
        //todo 省市 code 保存
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
        Map<String, InnerValue> dictionaryInfoMap = dictionaryInfoList.stream().collect(Collectors.toMap(InnerValue::getName, Function.identity()));
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
            orderInfoItem.setActualDeliverNum(outboundDetailRequest.getActualDeliverNum());
            orderInfoItem.setProductLineNum(outboundDetailRequest.getProductLineNum());
            orderInfoItem.setSkuCode(outboundDetailRequest.getSkuCode());
            orderInfoItem.setChannelUnitPrice(outboundDetailRequest.getChannelUnitPrice());
            orderInfoItem.setTotalChannelPrice(outboundDetailRequest.getChannelUnitPrice() * outboundDetailRequest.getNum());
            orderInfoItem.setOrderCode(orderInfo.getOrderCode());
            detailList.add(orderInfoItem);
        }
        //已支付
        orderInfo.setPaymentStatus(CommonConstant.PAID);
        orderInfo.setVolume(sumBoxVolume);
        orderInfo.setWeight(sumBoxGrossWeight);
        orderInfo.setCompanyCode(COMPANY_CODE);
        orderInfo.setCompanyName(COMPANY_NAME);
        if (StringUtils.isNotBlank(request.getSupplierCode())) {
            //供应商
            SupplyCompany supplyCompany = supplyCompanyDao.selectBySupplierCode(request.getSupplierCode());
            if (supplyCompany != null) {
                orderInfo.setSupplierName(supplyCompany.getSupplierName());
            }
        }
        //渠道
        PriceChannel priceChannel = priceChannelMapper.selectByChannelName(request.getOrderOriginal());
        if (priceChannel != null) {
            orderInfo.setOrderOriginal(priceChannel.getPriceChannelCode());
        }
        orderInfo.setDetailList(detailList);
//        Integer count = orderInfoMapper.insert(orderInfo);
//        LOGGER.info("添加订单:{}", count);
//        Integer detailCount = orderInfoItemMapper.insertList(detailList);
//        LOGGER.info("添加订单详情:{}", detailCount);
        //生成出库单
        OutboundReqVo convert = new OrderInfoToOutboundConverter(skuService,supplyComService).convert(orderInfo);
        // 出库单号
        String outboundOderCode  = outboundRecord(convert);
        //直接减库存



        return HttpResponse.success();
    }

    /**
     * 生成出库单
     *
     * @param
     * @return
     */
    private String outboundRecord(OutboundReqVo stockReqVO) {
        //编码生成
        EncodingRule numberingType = encodingRuleDao.getNumberingType(EncodingRuleType.OUT_BOUND_CODE);
        Outbound outbound = new Outbound();
        BeanCopyUtils.copy(stockReqVO, outbound);
        String outboundOderCode = String.valueOf(numberingType.getNumberingValue());
        outbound.setOutboundOderCode(outboundOderCode);

        List<OutboundProduct> outboundProducts = BeanCopyUtils.copyList(stockReqVO.getList(), OutboundProduct.class);
        outboundProducts.stream().forEach(outboundProduct -> outboundProduct.setOutboundOderCode(numberingType.getNumberingValue().toString()));
        int i = outboundDao.insertSelective(outbound);
        LOGGER.info("出库主表保存结果:{}", i);
        int j = outboundProductDao.insertBatch(outboundProducts);
        LOGGER.info("出库商品保存结果:{}", j);
        //批次商品暂时没有
        //更新编码
        encodingRuleDao.updateNumberValue(numberingType.getNumberingValue(), numberingType.getId());
        // 保存日志
        productCommonService.instanceThreeParty(outbound.getOutboundOderCode(), HandleTypeCoce.ADD_OUTBOUND_ODER.getStatus(), ObjectTypeCode.OUTBOUND_ODER.getStatus(), stockReqVO, HandleTypeCoce.ADD_OUTBOUND_ODER.getName(), new Date(), stockReqVO.getCreateBy(), stockReqVO.getRemark());

        return outboundOderCode;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
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
        Map<String, InnerValue> dictionaryInfoMap = dictionaryInfoList.stream().collect(Collectors.toMap(InnerValue::getName, innerValue -> innerValue));
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
        //订单体积计算系数
        BigDecimal orderVolumeCoefficient = new BigDecimal(1);
        //订单重量计算系数
        BigDecimal orderWeightCoefficient = new BigDecimal(1);
        if (detailRespVo != null) {
            orderWeightCoefficient = detailRespVo.getOrderWeightCoefficient();
            orderVolumeCoefficient = detailRespVo.getOrderVolumeCoefficient();
        }
        //总体积
        Long sumBoxVolume = 0L;
        //总重量
        Long sumBoxGrossWeight = 0L;
        OrderProductSkuResponse productSku;
        ReturnOrderInfoItem returnOrderInfoItem;
        List<ReturnOrderInfoItem> detailList = new ArrayList<>();
        for (ReturnDetailRequest returnDetailRequest : request.getDetailRequestList()) {
            //查询商品信息
            returnOrderInfoItem = new ReturnOrderInfoItem();
            productSku = productSkuDao.selectSkuInfo(returnDetailRequest.getSkuCode());
            if (productSku != null) {
                BeanUtils.copyProperties(productSku, returnOrderInfoItem);
                //(sku体积*数量)的合计*系数
                sumBoxVolume += new BigDecimal(returnDetailRequest.getNum())
                        .multiply(productSku.getBoxVolume())
                        .multiply(orderVolumeCoefficient).longValue();
                returnOrderInfoItem.setSkuName(productSku.getProductName());
                //(sku重量*数量)的合计*系数
                sumBoxGrossWeight += new BigDecimal(returnDetailRequest.getNum())
                        .multiply(productSku.getBoxGrossWeight())
                        .multiply(orderWeightCoefficient).longValue();
            }
            returnOrderInfoItem.setNum(returnDetailRequest.getNum());
            returnOrderInfoItem.setProductLineNum(returnDetailRequest.getProductLineNum());
            returnOrderInfoItem.setSkuCode(returnDetailRequest.getSkuCode());
            returnOrderInfoItem.setPrice(returnDetailRequest.getChannelUnitPrice());
            returnOrderInfoItem.setNum(returnDetailRequest.getActualDeliverNum());
            returnOrderInfoItem.setAmount(returnDetailRequest.getChannelUnitPrice() * returnDetailRequest.getNum());
            returnOrderInfoItem.setReturnOrderCode(returnOrderInfo.getOrderCode());
            returnOrderInfoItem.setCompanyCode(COMPANY_CODE);
            returnOrderInfoItem.setCompanyName(COMPANY_NAME);
            detailList.add(returnOrderInfoItem);
        }
        returnOrderInfo.setVolume(sumBoxVolume);
        returnOrderInfo.setWeight(sumBoxGrossWeight);
        returnOrderInfo.setCompanyCode(COMPANY_CODE);
        returnOrderInfo.setCompanyName(COMPANY_NAME);
        if (StringUtils.isNotBlank(request.getSupplierCode())) {
            //供应商
            SupplyCompany supplyCompany = supplyCompanyDao.selectBySupplierCode(request.getSupplierCode());
            if (supplyCompany != null) {
                returnOrderInfo.setSupplierName(supplyCompany.getSupplierName());
            }
        }
//        渠道
//        PriceChannel priceChannel = priceChannelMapper.selectByChannelName(request.getDeptName());
//        if(priceChannel!=null){
//            returnOrderInfo.setOrderOriginal(priceChannel.getPriceChannelCode());
//        }
        Integer count = returnOrderInfoMapper.insertSelective(returnOrderInfo);
        LOGGER.info("添加退货单:{}", count);
        Integer detailCount = returnOrderInfoItemMapper.insertList(detailList);
        LOGGER.info("添加退货单详情:{}", detailCount);
        return HttpResponse.success();
    }
}
