package com.aiqin.bms.scmp.api.purchase.service.impl;

import com.aiqin.bms.scmp.api.base.EncodingRuleType;
import com.aiqin.bms.scmp.api.base.InOutStatus;
import com.aiqin.bms.scmp.api.base.MsgStatus;
import com.aiqin.bms.scmp.api.common.*;
import com.aiqin.bms.scmp.api.constant.CommonConstant;
import com.aiqin.bms.scmp.api.constant.DictionaryEnum;
import com.aiqin.bms.scmp.api.product.dao.*;
import com.aiqin.bms.scmp.api.product.domain.pojo.*;
import com.aiqin.bms.scmp.api.product.domain.request.StockChangeRequest;
import com.aiqin.bms.scmp.api.product.domain.request.StockVoRequest;
import com.aiqin.bms.scmp.api.product.domain.request.inbound.InboundProductReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.inbound.InboundReqSave;
import com.aiqin.bms.scmp.api.product.domain.request.outbound.OutboundReqVo;
import com.aiqin.bms.scmp.api.product.domain.response.sku.purchase.PurchaseItemRespVo;
import com.aiqin.bms.scmp.api.product.mapper.AllocationMapper;
import com.aiqin.bms.scmp.api.product.mapper.AllocationProductMapper;
import com.aiqin.bms.scmp.api.product.mapper.PriceChannelMapper;
import com.aiqin.bms.scmp.api.product.service.ProductCommonService;
import com.aiqin.bms.scmp.api.product.service.SkuService;
import com.aiqin.bms.scmp.api.product.service.StockService;
import com.aiqin.bms.scmp.api.purchase.domain.converter.AllocationToOutboundConverter;
import com.aiqin.bms.scmp.api.purchase.domain.converter.OrderInfoToOutboundConverter;
import com.aiqin.bms.scmp.api.purchase.domain.converter.ReturnInfoToInboundConverter;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.order.OrderInfo;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.order.OrderInfoItem;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.returngoods.ReturnOrderInfo;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.returngoods.ReturnOrderInfoItem;
import com.aiqin.bms.scmp.api.purchase.domain.request.OutboundDetailRequest;
import com.aiqin.bms.scmp.api.purchase.domain.request.OutboundRequest;
import com.aiqin.bms.scmp.api.purchase.domain.request.ReturnDetailRequest;
import com.aiqin.bms.scmp.api.purchase.domain.request.ReturnRequest;
import com.aiqin.bms.scmp.api.purchase.domain.request.callback.ProfitLossRequest;
import com.aiqin.bms.scmp.api.purchase.domain.request.callback.TransfersRequest;
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
import com.aiqin.bms.scmp.api.supplier.mapper.SupplierRuleMapper;
import com.aiqin.bms.scmp.api.supplier.service.SupplierCommonService;
import com.aiqin.bms.scmp.api.supplier.service.SupplyComService;
import com.aiqin.bms.scmp.api.util.BeanCopyUtils;
import com.aiqin.ground.util.exception.GroundRuntimeException;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
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
    @Resource
    private StockService stockService;
    @Resource
    private InboundDao inboundDao;
    @Resource
    private InboundProductDao inboundProductDao;
    @Resource
    private AllocationMapper allocationMapper;
    @Resource
    private SupplierCommonService supplierCommonService;
    @Resource
    private AllocationProductMapper allocationProductMapper;

    /**
     * 销售出库接口
     *
     * @param request
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public HttpResponse outboundOrder(OutboundRequest request) {
        //todo 省市 code 保存
        //操作时间 签收时间 等于回单时间
        request.setReceivingTime(new DateTime(new Long(request.getReceiptTime())).toDate());
        request.setOperatorTime(request.getReceivingTime());
        //支付时间 发运时间 发货时间 等于创建时间
        request.setCreateDate(new DateTime(new Long(request.getCreateTime())).toDate());
        request.setCreateTime(new DateTime(request.getCreateDate()).toString());
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
            orderInfo.setOrderTypeCode(Integer.valueOf(dictionaryInfoMap.get(DictionaryEnum.ORDER_TYPE.getCode() + request.getOrderType()).getValue()));
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
            orderInfoItem.setActualChannelUnitPrice(outboundDetailRequest.getChannelUnitPrice());
            orderInfoItem.setActualTotalChannelPrice(outboundDetailRequest.getChannelUnitPrice() * outboundDetailRequest.getActualDeliverNum());
            detailList.add(orderInfoItem);
        }
        //已支付
        orderInfo.setPaymentStatus(CommonConstant.PAID);
        orderInfo.setVolume(sumBoxVolume);
        orderInfo.setWeight(sumBoxGrossWeight);
        orderInfo.setActualVolume(sumBoxVolume);
        orderInfo.setActualWeight(sumBoxGrossWeight);
        orderInfo.setCompanyCode(COMPANY_CODE);
        orderInfo.setCompanyName(COMPANY_NAME);
        orderInfo.setOperator(request.getOperatorName());
        orderInfo.setUpdateById(request.getOperatorCode());
        orderInfo.setUpdateByName(request.getOperatorName());
        orderInfo.setActualProductChannelTotalAmount(request.getProductChannelTotalAmount());
        orderInfo.setActualOrderAmount(request.getProductChannelTotalAmount());
        orderInfo.setActualProductNum(request.getProductNum());
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
            orderInfo.setOrderOriginalName(priceChannel.getPriceChannelName());
        }
        orderInfo.setDetailList(detailList);
        Integer count = orderInfoMapper.insert(orderInfo);
        LOGGER.info("添加订单:{}", count);
        Integer detailCount = orderInfoItemMapper.insertList(detailList);
        LOGGER.info("添加订单详情:{}", detailCount);
        //生成出库单
        OutboundReqVo convert = new OrderInfoToOutboundConverter(skuService, supplyComService).convert(orderInfo);
        // 出库单号
        String outboundOderCode = outboundRecord(convert);
        //直接减库存
        StockChangeRequest stockChangeRequest = new StockChangeRequest();
        //操作类型 直接减库存 4
        stockChangeRequest.setOperationType(4);
        List<StockVoRequest> list = handleOutStockData(orderInfo, outboundOderCode);
        stockChangeRequest.setStockVoRequests(list);
        HttpResponse httpResponse = stockService.changeStock(stockChangeRequest);
        if (!MsgStatus.SUCCESS.equals(httpResponse.getCode())) {
            throw new GroundRuntimeException("dl回调    减库存异常");
        }
        return HttpResponse.success();
    }

    /**
     * 销售出库处理库存参数
     *
     * @param orderInfo
     * @param outboundOderCode
     * @return
     */
    private List<StockVoRequest> handleOutStockData(OrderInfo orderInfo, String outboundOderCode) {
        List<StockVoRequest> list = Lists.newArrayList();
        StockVoRequest stockVoRequest;
        for (OrderInfoItem itemReqVo : orderInfo.getDetailList()) {
            stockVoRequest = new StockVoRequest();
            BeanUtils.copyProperties(itemReqVo, stockVoRequest);
            stockVoRequest.setTransportCenterCode(orderInfo.getTransportCenterCode());
            stockVoRequest.setTransportCenterName(orderInfo.getTransportCenterName());
            stockVoRequest.setWarehouseCode(orderInfo.getWarehouseCode());
            stockVoRequest.setWarehouseName(orderInfo.getWarehouseName());
            //没有采购组
//            stockVoRequest.setPurchaseGroupCode(purchaseGroupCode);
            //库存是主单位数量 退供基商品含量默认是1
            stockVoRequest.setChangeNum(itemReqVo.getActualDeliverNum());
            stockVoRequest.setSkuCode(itemReqVo.getSkuCode());
            stockVoRequest.setSkuName(itemReqVo.getSkuName());
            stockVoRequest.setDocumentType(0);
            stockVoRequest.setDocumentNum(outboundOderCode);
            stockVoRequest.setSourceDocumentType((int) OutboundTypeEnum.ORDER.getCode());
            stockVoRequest.setSourceDocumentNum(itemReqVo.getOrderCode());
            stockVoRequest.setOperator(orderInfo.getCreateById());
            list.add(stockVoRequest);
        }
        return list;
    }

    /**
     * 销售出库生成出库单
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

    /**
     * 销售退货接口
     *
     * @param request
     * @return
     */
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
        }
        //订单类型
        if (dictionaryInfoMap.containsKey(DictionaryEnum.ORDER_TYPE.getCode() + request.getOrderType())) {
            returnOrderInfo.setOrderTypeCode(Integer.valueOf(dictionaryInfoMap.get(DictionaryEnum.ORDER_TYPE.getCode() + request.getOrderType()).getValue()));
        }
        //退货类型
        if (dictionaryInfoMap.containsKey(DictionaryEnum.RETURN_TYPE.getCode() + request.getReturnOrderType())) {
            returnOrderInfo.setReturnOrderTypeCode(Integer.valueOf(dictionaryInfoMap.get(DictionaryEnum.RETURN_TYPE.getCode() + request.getReturnOrderType()).getValue()));
        }
        //渠道
        PriceChannel priceChannel = priceChannelMapper.selectByChannelName(request.getDeptName());
        if (priceChannel != null) {
            returnOrderInfo.setOrderOriginal(priceChannel.getPriceChannelCode());
            returnOrderInfo.setOrderOriginalName(priceChannel.getPriceChannelName());
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
            returnOrderInfoItem.setReturnOrderCode(request.getReturnOrderCode());
            returnOrderInfoItem.setNum(returnDetailRequest.getNum());
            returnOrderInfoItem.setProductLineNum(returnDetailRequest.getProductLineNum());
            returnOrderInfoItem.setSkuCode(returnDetailRequest.getSkuCode());
            returnOrderInfoItem.setPrice(returnDetailRequest.getChannelUnitPrice());
            //渠道价格
            returnOrderInfoItem.setChannelUnitPrice(returnDetailRequest.getChannelUnitPrice());
            returnOrderInfoItem.setTotalChannelPrice(returnDetailRequest.getChannelUnitPrice() * returnDetailRequest.getActualDeliverNum());
            //实际渠道价格
            returnOrderInfoItem.setActualChannelUnitPrice(returnDetailRequest.getChannelUnitPrice());
            returnOrderInfoItem.setActualTotalChannelPrice(returnDetailRequest.getChannelUnitPrice() * returnDetailRequest.getActualDeliverNum());
            returnOrderInfoItem.setActualInboundNum(returnDetailRequest.getActualDeliverNum().intValue());
            returnOrderInfoItem.setAmount(returnDetailRequest.getChannelUnitPrice() * returnDetailRequest.getActualDeliverNum());
            //实际单价
            returnOrderInfoItem.setActualAmount(returnDetailRequest.getChannelUnitPrice());
            //实际总价
            returnOrderInfoItem.setActualPrice(returnDetailRequest.getChannelUnitPrice() * returnDetailRequest.getActualDeliverNum());
            returnOrderInfoItem.setCompanyCode(COMPANY_CODE);
            returnOrderInfoItem.setCompanyName(COMPANY_NAME);
            returnOrderInfoItem.setWarehouseCode(returnDetailRequest.getWarehouseCode());
            returnOrderInfoItem.setWarehouseName(returnDetailRequest.getWarehouseName());
            detailList.add(returnOrderInfoItem);
        }
        //实际金额 数量
        returnOrderInfo.setActualProductNum(request.getProductNum());
        returnOrderInfo.setActualReturnOrderAmount(request.getReturnOrderAmount());
        returnOrderInfo.setProductChannelTotalAmount(request.getReturnOrderAmount());
        returnOrderInfo.setActualProductChannelTotalAmount(request.getReturnOrderAmount());
        returnOrderInfo.setActualProductTotalAmount(request.getReturnOrderAmount());
        returnOrderInfo.setActualVolume(sumBoxVolume);
        returnOrderInfo.setVolume(sumBoxVolume);
        returnOrderInfo.setActualWeight(sumBoxGrossWeight);
        returnOrderInfo.setWeight(sumBoxGrossWeight);
        returnOrderInfo.setCompanyCode(COMPANY_CODE);
        returnOrderInfo.setCompanyName(COMPANY_NAME);
        returnOrderInfo.setUpdateByName(request.getOperator());
        returnOrderInfo.setUpdateById(request.getOperatorCode());
        if (StringUtils.isNotBlank(request.getSupplierCode())) {
            //供应商
            SupplyCompany supplyCompany = supplyCompanyDao.selectBySupplierCode(request.getSupplierCode());
            if (supplyCompany != null) {
                returnOrderInfo.setSupplierName(supplyCompany.getSupplierName());
            }
        }
        Integer count = returnOrderInfoMapper.insertSelective(returnOrderInfo);
        LOGGER.info("添加退货单:{}", count);
        Integer detailCount = returnOrderInfoItemMapper.insertList(detailList);
        LOGGER.info("添加退货单详情:{}", detailCount);
        returnOrderInfo.setDetailList(detailList);
        //入库单生成
        String inboundOderCode;
        List<InboundReqSave> convert = new ReturnInfoToInboundConverter(skuService).convert(returnOrderInfo);
        for (InboundReqSave inboundReqSave : convert) {
            inboundOderCode = inboundRecord(inboundReqSave);
            //直接加库存
            StockChangeRequest stockChangeRequest = new StockChangeRequest();
            //操作类型 直接加库存 10
            stockChangeRequest.setOperationType(10);
            List<StockVoRequest> list = handleInboundStockData(returnOrderInfo, inboundOderCode, inboundReqSave);
            stockChangeRequest.setStockVoRequests(list);
            HttpResponse httpResponse = stockService.changeStock(stockChangeRequest);
            if (!MsgStatus.SUCCESS.equals(httpResponse.getCode())) {
                throw new GroundRuntimeException("dl回调   加库存异常");
            }
        }

        return HttpResponse.success();
    }


    /**
     * 销售入库处理库存参数
     *
     * @param returnOrderInfo
     * @param inboundOderCode
     * @param inboundReqSave
     * @return
     */
    private List<StockVoRequest> handleInboundStockData(ReturnOrderInfo returnOrderInfo, String inboundOderCode, InboundReqSave inboundReqSave) {
        List<StockVoRequest> list = Lists.newArrayList();
        StockVoRequest stockVoRequest;
        for (InboundProductReqVo itemReqVo : inboundReqSave.getList()) {
            stockVoRequest = new StockVoRequest();
            BeanUtils.copyProperties(returnOrderInfo, stockVoRequest);
            stockVoRequest.setWarehouseCode(inboundReqSave.getWarehouseCode());
            stockVoRequest.setWarehouseName(inboundReqSave.getWarehouseName());
            //库存是主单位数量 退供基商品含量默认是1
            stockVoRequest.setChangeNum(itemReqVo.getPraInboundNum());
            stockVoRequest.setSkuCode(itemReqVo.getSkuCode());
            stockVoRequest.setSkuName(itemReqVo.getSkuName());
            stockVoRequest.setDocumentType(0);
            stockVoRequest.setDocumentNum(inboundOderCode);
            stockVoRequest.setSourceDocumentType((int) OutboundTypeEnum.ORDER.getCode());
            stockVoRequest.setSourceDocumentNum(returnOrderInfo.getReturnOrderCode());
            stockVoRequest.setOperator(returnOrderInfo.getCreateById());
            list.add(stockVoRequest);
        }
        return list;
    }

    /**
     * 销售入库生成入库单
     *
     * @param
     * @return
     */
    private String inboundRecord(InboundReqSave reqVo) {
        // 入库单转化主体保存实体
        Inbound inbound = new Inbound();
        BeanCopyUtils.copy(reqVo, inbound);
        // 获取编码 尺度
        EncodingRule rule = encodingRuleDao.getNumberingType(EncodingRuleType.IN_BOUND_CODE);
        inbound.setInboundOderCode(rule.getNumberingValue().toString());
        //插入入库单主表
        int insert = inboundDao.insert(inbound);
        LOGGER.info("插入入库单主表返回结果:{}", insert);
        //  转化入库单sku实体
        List<InboundProduct> list = BeanCopyUtils.copyList(reqVo.getList(), InboundProduct.class);
        list.stream().forEach(inboundItemReqVo -> inboundItemReqVo.setInboundOderCode(rule.getNumberingValue().toString()));
        //插入入库单商品表
        int insertProducts = inboundProductDao.insertBatch(list);
        LOGGER.info("插入入库单商品表返回结果:{}", insertProducts);
        //更新编码表
        encodingRuleDao.updateNumberValue(rule.getNumberingValue(), rule.getId());
        // 保存日志
        productCommonService.instanceThreeParty(inbound.getInboundOderCode(), HandleTypeCoce.ADD_INBOUND_ODER.getStatus(), ObjectTypeCode.INBOUND_ODER.getStatus(), reqVo, HandleTypeCoce.ADD_INBOUND_ODER.getName(), new Date(), reqVo.getCreateBy(), reqVo.getRemark());

        return inbound.getInboundOderCode();
    }

    /**
     * 调拨回调
     *
     * @param
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public HttpResponse transfersOrder(TransfersRequest request) {
        byte type;
        String typeName;
        String inboundOderCode = "";
        String outboundOderCode = "";
        if (request.getTransfersType().equals(1)) {
            //调拨
            type = AllocationTypeEnum.ALLOCATION.getType();
            typeName = AllocationTypeEnum.ALLOCATION.getTypeName();
        } else {
            //移库
            type = AllocationTypeEnum.MOVE.getType();
            typeName = AllocationTypeEnum.MOVE.getTypeName();
        }
        //添加调拨单
        //添加调拨单详情
        Allocation allocation = new Allocation();
        BeanUtils.copyProperties(request, allocation);
        allocation.setCompanyCode(COMPANY_CODE);
        allocation.setCompanyName(COMPANY_NAME);
        allocation.setCreateBy(request.getCreateByName());
        allocation.setCreateTime(new DateTime(new Long(request.getCreateTime())).toDate());
        allocation.setUpdateBy(request.getUpdateByName());
        allocation.setUpdateTime(new DateTime(new Long(request.getReceiptTime())).toDate());
        allocationInsert(allocation, type, typeName);
        //调拨生成出库单
        OutboundReqVo convert = new AllocationToOutboundConverter(skuService).convert(allocation);
        if (request.getTransfersType().equals(1)) {
            //调拨才有出库 出库单号
            outboundOderCode = outboundRecord(convert);
        }
        //调拨直接减库存
        StockChangeRequest stockChangeRequest = new StockChangeRequest();
        //操作类型 直接减库存 4
        stockChangeRequest.setOperationType(4);
        List<StockVoRequest> list = handleAllocationStockData(allocation.getDetailList(), allocation.getCompanyCode(), allocation.getCompanyName(), allocation.getCallOutLogisticsCenterCode()
                , allocation.getCallOutLogisticsCenterName(), allocation.getCallOutWarehouseCode(), allocation.getCallOutWarehouseName(), allocation.getAllocationCode(), outboundOderCode);
        stockChangeRequest.setStockVoRequests(list);
        HttpResponse httpResponse = stockService.changeStock(stockChangeRequest);
        if (!MsgStatus.SUCCESS.equals(httpResponse.getCode())) {
            throw new GroundRuntimeException("dl回调    减库存异常");
        }
        //调拨生成入库单
        InboundReqSave inboundReqSave = handleTransferInbound(allocation);
        if (request.getTransfersType().equals(1)) {
            inboundOderCode = inboundRecord(inboundReqSave);
        }
        //直接加库存
        StockChangeRequest stockRequest = new StockChangeRequest();
        //操作类型 直接加库存 10
        stockRequest.setOperationType(10);
        List<StockVoRequest> inboundList = handleAllocationStockData(allocation.getDetailList(), allocation.getCompanyCode(), allocation.getCompanyName(), allocation.getCallInLogisticsCenterCode()
                , allocation.getCallInLogisticsCenterName(), allocation.getCallInWarehouseCode(), allocation.getCallInWarehouseName(), allocation.getAllocationCode(), inboundOderCode);
        stockRequest.setStockVoRequests(inboundList);
        HttpResponse stockResponse = stockService.changeStock(stockRequest);
        if (!MsgStatus.SUCCESS.equals(stockResponse.getCode())) {
            throw new GroundRuntimeException("dl回调   加库存异常");
        }
        return HttpResponse.success();
    }

    /**
     * 调拨入库单参数处理
     *
     * @param allocation
     * @return
     */
    public InboundReqSave handleTransferInbound(Allocation allocation) {
        List<String> skuCodes = allocation.getDetailList().stream().map(AllocationProduct::getSkuCode).collect(Collectors.toList());
        Map<String, PurchaseItemRespVo> map = skuService.getSalesSkuList(skuCodes).stream().collect(Collectors.toMap(PurchaseItemRespVo::getSkuCode, Function.identity(), (k1, k2) -> k2));
        InboundProductReqVo product;
        InboundReqSave inbound = new InboundReqSave();
        List<InboundProductReqVo> products = Lists.newArrayList();
        //实际入库数量
        inbound.setPraInboundNum(allocation.getQuantity());
        //实际入库主数量
        inbound.setPraMainUnitNum(allocation.getQuantity());
        //预计入库数量
        inbound.setPreInboundNum(allocation.getQuantity());
        //预计入库主数量
        inbound.setPreMainUnitNum(allocation.getQuantity());
        //入库类型
        inbound.setInboundTypeCode(InboundTypeEnum.ORDER.getCode());
        inbound.setInboundTypeName(InboundTypeEnum.ORDER.getName());
        //入库状态
        inbound.setInboundStatusCode(InOutStatus.COMPLETE_INOUT.getCode());
        inbound.setInboundStatusName(InOutStatus.COMPLETE_INOUT.getName());
        //公司编码
        inbound.setCompanyCode(allocation.getCompanyCode());
        inbound.setCompanyName(allocation.getCompanyName());
        inbound.setInboundTime(allocation.getUpdateTime());
        inbound.setShipper(allocation.getUpdateBy());
        //来源单据号
        inbound.setSourceOderCode(allocation.getAllocationCode());
        //物流中心编码
        inbound.setLogisticsCenterCode(allocation.getCallInLogisticsCenterCode());
        inbound.setLogisticsCenterName(allocation.getCallInLogisticsCenterName());
        //预计到货时间
        inbound.setPreArrivalTime(allocation.getUpdateTime());
        inbound.setCreateTime(allocation.getCreateTime());
        inbound.setWarehouseCode(allocation.getCallInWarehouseCode());
        inbound.setWarehouseName(allocation.getCallInWarehouseName());
        //创建人
        inbound.setCreateBy(allocation.getCreateBy());
        for (AllocationProduct allocationProduct : allocation.getDetailList()) {
            product = new InboundProductReqVo();
            product.setPreInboundMainNum(allocationProduct.getQuantity());
            product.setPreInboundNum(allocationProduct.getQuantity());
            product.setPraInboundMainNum(allocationProduct.getQuantity());
            product.setPraInboundNum(allocationProduct.getQuantity());
            //规格.
            product.setNorms(map.get(allocationProduct.getSkuCode()).getSpec());
            product.setInboundNorms(allocationProduct.getSpecification());
            product.setCreateBy(allocation.getCreateBy());
            product.setCreateTime(allocation.getCreateTime());
            products.add(product);
        }
        inbound.setList(products);
        return inbound;
    }

    private List<StockVoRequest> handleAllocationStockData(List<AllocationProduct> productList, String companyCode, String companyName, String logisticsCenterCode, String logisticsCenterName,
                                                           String outWarehouseCode, String outWarehouseName, String allocationCode, String outboundOderCode) {
        List<StockVoRequest> list = Lists.newArrayList();
        StockVoRequest stockVoRequest;
        for (AllocationProduct itemReqVo : productList) {
            stockVoRequest = new StockVoRequest();
            stockVoRequest.setCompanyCode(companyCode);
            stockVoRequest.setCompanyName(companyName);
            stockVoRequest.setTransportCenterCode(logisticsCenterCode);
            stockVoRequest.setTransportCenterName(logisticsCenterName);
            stockVoRequest.setWarehouseCode(outWarehouseCode);
            stockVoRequest.setWarehouseName(outWarehouseName);
            //库存是主单位数量 退供基商品含量默认是1
            stockVoRequest.setChangeNum(itemReqVo.getLineNum());
            stockVoRequest.setSkuCode(itemReqVo.getSkuCode());
            stockVoRequest.setSkuName(itemReqVo.getSkuName());
            stockVoRequest.setDocumentType(0);
            stockVoRequest.setDocumentNum(outboundOderCode);
            stockVoRequest.setSourceDocumentType((int) OutboundTypeEnum.ORDER.getCode());
            stockVoRequest.setSourceDocumentNum(allocationCode);
            stockVoRequest.setOperator(itemReqVo.getCreateBy());
            list.add(stockVoRequest);
        }
        return list;
    }

    /**
     * 调拨单添加
     */
    private void allocationInsert(Allocation allocation, byte type, String typeName) {
        // 获取编码
        String content = HandleTypeCoce.ADD_ALLOCATION.getName();
        //保存日志
        supplierCommonService.getInstance(allocation.getAllocationCode() + "", HandleTypeCoce.ADD.getStatus(), ObjectTypeCode.ALLOCATION.getStatus(), content, null, HandleTypeCoce.ADD.getName());
        //设置状态(已完成)
        allocation.setAllocationStatusCode(AllocationEnum.ALLOCATION_TYPE_FINISHED.getStatus());
        allocation.setAllocationStatusName(AllocationEnum.ALLOCATION_TYPE_FINISHED.getName());
        allocation.setAllocationType(type);
        allocation.setAllocationTypeName(typeName);
        allocationMapper.insertSelective(allocation);
        List<String> skuList = allocation.getDetailList().stream().map(AllocationProduct::getSkuCode).collect(Collectors.toList());
        List<OrderProductSkuResponse> productSkuList = productSkuDao.selectSkuInfoList(skuList);
        Map<String, OrderProductSkuResponse> productSkuMap = productSkuList.stream().collect(Collectors.toMap(OrderProductSkuResponse::getSkuCode, Function.identity()));
        OrderProductSkuResponse orderProductSkuResponse;
        List<AllocationProduct> allocationProductList = Lists.newArrayList();
        for (AllocationProduct allocationProduct : allocation.getDetailList()) {
            orderProductSkuResponse = productSkuMap.get(allocationProduct.getSkuCode());
            if (orderProductSkuResponse != null) {
                allocationProduct.setSkuName(orderProductSkuResponse.getProductName());
                allocationProduct.setPictureUrl(orderProductSkuResponse.getPictureUrl());
                allocationProduct.setSpecification(orderProductSkuResponse.getSpec());
                allocationProduct.setColor(orderProductSkuResponse.getColorName());
                allocationProduct.setModel(orderProductSkuResponse.getModel());
                allocationProduct.setUnit(orderProductSkuResponse.getUnitName());
            }
            allocationProduct.setAllocationCode(allocation.getAllocationCode());
            allocationProduct.setCreateBy(allocation.getCreateBy());
            allocationProduct.setCreateTime(allocation.getCreateTime());
            allocationProduct.setUpdateTime(allocation.getUpdateTime());
            allocationProduct.setUpdateBy(allocation.getUpdateBy());
            allocationProductList.add(allocationProduct);
        }
        allocationProductMapper.saveList(allocationProductList);
    }


    /**
     * 报损报溢回调
     *
     * @param
     * @return
     */
    @Override
    public HttpResponse profitLossOrder(ProfitLossRequest request) {

        //添加损溢记录

        //库存变动操作

        return null;
    }
}
