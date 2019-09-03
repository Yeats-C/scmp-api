package com.aiqin.bms.scmp.api.purchase.service.impl;

import com.aiqin.bms.scmp.api.base.EncodingRuleType;
import com.aiqin.bms.scmp.api.base.InOutStatus;
import com.aiqin.bms.scmp.api.base.MsgStatus;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.common.*;
import com.aiqin.bms.scmp.api.constant.CommonConstant;
import com.aiqin.bms.scmp.api.constant.DictionaryEnum;
import com.aiqin.bms.scmp.api.product.dao.*;
import com.aiqin.bms.scmp.api.product.domain.ProductSku;
import com.aiqin.bms.scmp.api.product.domain.pojo.*;
import com.aiqin.bms.scmp.api.product.domain.request.StockChangeRequest;
import com.aiqin.bms.scmp.api.product.domain.request.StockVoRequest;
import com.aiqin.bms.scmp.api.product.domain.request.inbound.InboundProductReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.inbound.InboundReqSave;
import com.aiqin.bms.scmp.api.product.domain.request.outbound.OutboundProductReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.outbound.OutboundReqVo;
import com.aiqin.bms.scmp.api.product.mapper.*;
import com.aiqin.bms.scmp.api.product.service.ProductCommonService;
import com.aiqin.bms.scmp.api.product.service.SkuService;
import com.aiqin.bms.scmp.api.product.service.StockService;
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
import com.aiqin.bms.scmp.api.purchase.domain.request.callback.ProfitLossDetailRequest;
import com.aiqin.bms.scmp.api.purchase.domain.request.callback.ProfitLossRequest;
import com.aiqin.bms.scmp.api.purchase.domain.request.callback.TransfersRequest;
import com.aiqin.bms.scmp.api.purchase.domain.response.InnerValue;
import com.aiqin.bms.scmp.api.purchase.domain.response.order.OrderProductSkuResponse;
import com.aiqin.bms.scmp.api.purchase.mapper.OrderInfoItemMapper;
import com.aiqin.bms.scmp.api.purchase.mapper.OrderInfoMapper;
import com.aiqin.bms.scmp.api.purchase.mapper.ReturnOrderInfoItemMapper;
import com.aiqin.bms.scmp.api.purchase.mapper.ReturnOrderInfoMapper;
import com.aiqin.bms.scmp.api.purchase.service.GoodsRejectService;
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
    private static List<String> productTypeList = Arrays.asList("商品", "赠品", "实物返");
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
    @Resource
    private ProfitLossMapper profitLossMapper;
    @Resource
    private ProfitLossProductMapper profitLossProductMapper;
    @Resource
    private GoodsRejectService goodsRejectService;
    @Resource
    private ProductSkuPicturesDao productSkuPicturesDao;

    /**
     * 销售出库接口
     *
     * @param request
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public HttpResponse outboundOrder(OutboundRequest request) {
        try {
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
            orderInfo.setCreateTime(request.getCreateDate());
            orderInfo.setUpdateTime(request.getReceivingTime());
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
            List<String> skuCodes = request.getDetail().stream().map(OutboundDetailRequest::getSkuCode).collect(Collectors.toList());
            List<ProductSkuPictures> picturesList = productSkuPicturesDao.listBySkuCodes(skuCodes);
            Map<String,List<ProductSkuPictures>> picturesMap = picturesList.stream().collect(Collectors.groupingBy(ProductSkuPictures::getProductSkuCode));
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
                orderInfoItem.setPictureUrl(picturesMap.containsKey(outboundDetailRequest.getSkuCode())?picturesMap.get(outboundDetailRequest.getSkuCode()).get(0).getProductPicturePath():"");
                orderInfoItem.setActualDeliverNum(outboundDetailRequest.getActualDeliverNum());
                orderInfoItem.setProductLineNum(outboundDetailRequest.getProductLineNum());
                orderInfoItem.setSkuCode(outboundDetailRequest.getSkuCode());
                orderInfoItem.setChannelUnitPrice(outboundDetailRequest.getChannelUnitPrice());
                orderInfoItem.setGivePromotion(outboundDetailRequest.getGiftType());
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
                LOGGER.error("dl回调   加库存异常");
                throw new GroundRuntimeException("dl回调    减库存异常");
            }
            return HttpResponse.success();
        } catch (Exception e) {
            LOGGER.error("订单回调异常:{}",e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
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
            stockVoRequest.setOperator(orderInfo.getCreateByName());
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
        try {
            request.setReceivingTime(new DateTime(new Long(request.getReceiptTime())).toDate());
            request.setOperatorTime(request.getReceivingTime());
            //支付时间 发运时间 发货时间 等于创建时间
            request.setCreateDate(new DateTime(new Long(request.getCreateTime())).toDate());
            request.setDeliveryTime(request.getCreateDate());
            ReturnOrderInfo returnOrderInfo = new ReturnOrderInfo();
            BeanUtils.copyProperties(request, returnOrderInfo);
            returnOrderInfo.setCreateTime(request.getCreateDate());
            returnOrderInfo.setUpdateTime(request.getReceivingTime());
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
            List<String> skuCodes = request.getDetailRequestList().stream().map(ReturnDetailRequest::getSkuCode).collect(Collectors.toList());
            List<ProductSkuPictures> picturesList = productSkuPicturesDao.listBySkuCodes(skuCodes);
            Map<String,List<ProductSkuPictures>> picturesMap = picturesList.stream().collect(Collectors.groupingBy(ProductSkuPictures::getProductSkuCode));

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
                returnOrderInfoItem.setPictureUrl(picturesMap.containsKey(returnDetailRequest.getSkuCode())?picturesMap.get(returnDetailRequest.getSkuCode()).get(0).getProductPicturePath():"");
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
                    LOGGER.error("dl回调   加库存异常");
                    throw new GroundRuntimeException("dl回调   加库存异常");
                }
            }

            return HttpResponse.success();
        } catch (Exception e) {
            LOGGER.error("订单回调异常:{}", e.getCause());
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
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
            stockVoRequest.setDocumentType(1);
            stockVoRequest.setDocumentNum(inboundOderCode);
            stockVoRequest.setSourceDocumentType((int) OutboundTypeEnum.ORDER.getCode());
            stockVoRequest.setSourceDocumentNum(returnOrderInfo.getReturnOrderCode());
            stockVoRequest.setOperator(returnOrderInfo.getCreateByName());
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
        try {
            byte type;
            String typeName;
            String inboundOderCode = "";
            String outboundOderCode = "";
            Integer boundRecordType ;
            OutboundTypeEnum outboundTypeEnum;
            InboundTypeEnum inboundTypeEnum;
            if (request.getTransfersType().equals(1)) {
                //调拨
                type = AllocationTypeEnum.ALLOCATION.getType();
                typeName = AllocationTypeEnum.ALLOCATION.getTypeName();
                boundRecordType = (int)OutboundTypeEnum.ALLOCATE.getCode();
                outboundTypeEnum = OutboundTypeEnum.ALLOCATE;
                inboundTypeEnum = InboundTypeEnum.ALLOCATE;
            } else {
                //移库
                type = AllocationTypeEnum.MOVE.getType();
                typeName = AllocationTypeEnum.MOVE.getTypeName();
                boundRecordType = (int)OutboundTypeEnum.MOVEMENT.getCode();
                outboundTypeEnum = OutboundTypeEnum.MOVEMENT;
                inboundTypeEnum = InboundTypeEnum.MOVEMENT;
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
            allocation.setPrincipal(request.getUpdateByName());
            allocation.setUpdateTime(new DateTime(new Long(request.getReceiptTime())).toDate());
            List<String> skuList = allocation.getDetailList().stream().map(AllocationProduct::getSkuCode).collect(Collectors.toList());
            List<OrderProductSkuResponse> productSkuList = productSkuDao.selectStockSkuInfoList(skuList);
            Map<String, OrderProductSkuResponse> productSkuMap = productSkuList.stream().collect(Collectors.toMap(OrderProductSkuResponse::getSkuCode, Function.identity()));
            //生成出库单
            OutboundReqVo convert = handleTransferOutbound(allocation, productSkuMap,outboundTypeEnum);
            //调拨才有出库 出库单号
            outboundOderCode = outboundRecord(convert);
            //调拨直接减库存
            StockChangeRequest stockChangeRequest = new StockChangeRequest();
            //操作类型 直接减库存 4
            stockChangeRequest.setOperationType(4);
            List<StockVoRequest> list = handleAllocationStockData(request.getCreateByName(),productSkuMap,allocation.getDetailList(), allocation.getCompanyCode(), allocation.getCompanyName(), allocation.getCallOutLogisticsCenterCode()
                    , allocation.getCallOutLogisticsCenterName(), allocation.getCallOutWarehouseCode(), allocation.getCallOutWarehouseName(), allocation.getAllocationCode(), outboundOderCode,boundRecordType);
            stockChangeRequest.setStockVoRequests(list);
            HttpResponse httpResponse = stockService.changeStock(stockChangeRequest);
            if (!MsgStatus.SUCCESS.equals(httpResponse.getCode())) {
                LOGGER.error("dl回调   加库存异常");
                throw new GroundRuntimeException("dl回调    减库存异常");
            }
            //生成入库单
            InboundReqSave inboundReqSave = handleTransferInbound(allocation, productSkuMap,inboundTypeEnum);
            inboundOderCode = inboundRecord(inboundReqSave);
            //直接加库存
            StockChangeRequest stockRequest = new StockChangeRequest();
            //操作类型 直接加库存 10
            stockRequest.setOperationType(10);
            List<StockVoRequest> inboundList = handleAllocationStockData(request.getCreateByName(),productSkuMap,allocation.getDetailList(), allocation.getCompanyCode(), allocation.getCompanyName(), allocation.getCallInLogisticsCenterCode()
                    , allocation.getCallInLogisticsCenterName(), allocation.getCallInWarehouseCode(), allocation.getCallInWarehouseName(), allocation.getAllocationCode(), inboundOderCode,boundRecordType);
            stockRequest.setStockVoRequests(inboundList);
            HttpResponse stockResponse = stockService.changeStock(stockRequest);
            if (!MsgStatus.SUCCESS.equals(stockResponse.getCode())) {
                LOGGER.error("dl回调   加库存异常");
                throw new GroundRuntimeException("dl回调   加库存异常");
            }
            allocation.setOutboundOderCode(outboundOderCode);
            allocation.setInboundOderCode(inboundOderCode);
            //生成调拨单
            allocationInsert(allocation, type, typeName, productSkuMap);
            return HttpResponse.success();
        } catch (Exception e) {
            LOGGER.error("订单回调异常:{}", e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    /**
     * 调拨出库单参数处理
     *
     * @param allocation
     * @return
     */
    public OutboundReqVo handleTransferOutbound(Allocation allocation, Map<String, OrderProductSkuResponse> productSkuMap,OutboundTypeEnum outboundTypeEnum) {
        OutboundReqVo stockReqVO = new OutboundReqVo();
        BeanUtils.copyProperties(allocation, stockReqVO);
        stockReqVO.setSourceOderCode(allocation.getAllocationCode());
        //配送中心
        stockReqVO.setLogisticsCenterCode(allocation.getCallInLogisticsCenterCode());
        stockReqVO.setLogisticsCenterName(allocation.getCallInLogisticsCenterName());
        //预计
        stockReqVO.setPreOutboundNum(allocation.getQuantity());
        stockReqVO.setPreMainUnitNum(allocation.getQuantity());
        //实际
        stockReqVO.setPraOutboundNum(allocation.getQuantity());
        stockReqVO.setPraMainUnitNum(allocation.getQuantity());
        stockReqVO.setConsigneeNumber(allocation.getUpdateBy());
        stockReqVO.setCreateBy(allocation.getCreateBy());
        stockReqVO.setUpdateBy(allocation.getUpdateBy());
        stockReqVO.setCreateTime(allocation.getCreateTime());
        stockReqVO.setUpdateTime(allocation.getUpdateTime());
        //状态
        stockReqVO.setOutboundStatusCode(InOutStatus.COMPLETE_INOUT.getCode());
        stockReqVO.setOutboundStatusName(InOutStatus.COMPLETE_INOUT.getName());
        //类型
        stockReqVO.setOutboundTypeCode(outboundTypeEnum.getCode());
        stockReqVO.setOutboundTypeName(outboundTypeEnum.getName());
        //出库时间
        stockReqVO.setOutboundTime(allocation.getUpdateTime());
        stockReqVO.setPreArrivalTime(allocation.getUpdateTime());
        List<OutboundProductReqVo> parts = Lists.newArrayList();
        OutboundProductReqVo outboundProduct;
        for (AllocationProduct item : allocation.getDetailList()) {
            outboundProduct = new OutboundProductReqVo();
            //sku
            outboundProduct.setSkuCode(item.getSkuCode());
            outboundProduct.setSkuName(item.getSkuName());
            if (productSkuMap.get(item.getSkuCode()) != null) {
                //图片
                outboundProduct.setPictureUrl(productSkuMap.get(item.getSkuCode()).getPictureUrl());
                //规格
                outboundProduct.setNorms(productSkuMap.get(item.getSkuCode()).getSpec());
                outboundProduct.setUnitCode(productSkuMap.get(item.getSkuCode()).getUnitCode());
                //单位
                outboundProduct.setUnitName(productSkuMap.get(item.getSkuCode()).getUnitName());
                outboundProduct.setOutboundNorms(productSkuMap.get(item.getSkuCode()).getSpec());
                outboundProduct.setColorCode(productSkuMap.get(item.getSkuCode()).getColorCode());
                outboundProduct.setColorName(productSkuMap.get(item.getSkuCode()).getColorName());
            }
            //预计出库数量
            outboundProduct.setPreOutboundNum(item.getQuantity());
            //预计出库主数量
            outboundProduct.setPreOutboundMainNum(item.getQuantity());
            //实际出库数量
            outboundProduct.setPraOutboundNum(item.getQuantity());
            //实际出库主数量
            outboundProduct.setPraOutboundMainNum(item.getQuantity());
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

    /**
     * 调拨入库单参数处理
     *
     * @param allocation
     * @return
     */
    public InboundReqSave handleTransferInbound(Allocation allocation, Map<String, OrderProductSkuResponse> productSkuMap,InboundTypeEnum inboundTypeEnum) {
        InboundReqSave inbound = new InboundReqSave();
        InboundProductReqVo product;
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
        inbound.setInboundTypeCode(inboundTypeEnum.getCode());
        inbound.setInboundTypeName(inboundTypeEnum.getName());
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
        inbound.setUpdateBy(allocation.getUpdateBy());
        inbound.setUpdateTime(allocation.getUpdateTime());
        OrderProductSkuResponse orderProductSkuResponse;
        for (AllocationProduct allocationProduct : allocation.getDetailList()) {
            product = new InboundProductReqVo();
            product.setPreInboundMainNum(allocationProduct.getQuantity());
            product.setPreInboundNum(allocationProduct.getQuantity());
            product.setPraInboundMainNum(allocationProduct.getQuantity());
            product.setPraInboundNum(allocationProduct.getQuantity());
            orderProductSkuResponse = productSkuMap.get(allocationProduct.getSkuCode());
            if (orderProductSkuResponse != null) {
                //基商品含量固定1
                product.setInboundBaseContent(orderProductSkuResponse.getBaseProductContent());
                product.setInboundBaseUnit(String.valueOf(orderProductSkuResponse.getZeroDisassemblyCoefficient()));
                product.setNorms(orderProductSkuResponse.getSpec());
                product.setSkuName(orderProductSkuResponse.getProductName());
                product.setPictureUrl(orderProductSkuResponse.getPictureUrl());
                product.setColorCode(orderProductSkuResponse.getColorCode());
                product.setColorName(orderProductSkuResponse.getColorName());
                product.setModel(orderProductSkuResponse.getModel());
                product.setUnitCode(orderProductSkuResponse.getUnitCode());
                product.setUnitName(orderProductSkuResponse.getUnitName());
                product.setInboundNorms(orderProductSkuResponse.getSpec());
            }
            product.setCreateBy(allocation.getCreateBy());
            product.setCreateTime(allocation.getCreateTime());
            product.setSkuCode(allocationProduct.getSkuCode());
            products.add(product);
        }
        inbound.setList(products);
        return inbound;
    }

    private List<StockVoRequest> handleAllocationStockData(String getCreateByName,Map<String, OrderProductSkuResponse> productSkuMap,List<AllocationProduct> productList, String companyCode, String companyName, String logisticsCenterCode, String logisticsCenterName,
                                                           String outWarehouseCode, String outWarehouseName, String allocationCode, String outboundOderCode,Integer type) {
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
            stockVoRequest.setChangeNum(itemReqVo.getQuantity());
            stockVoRequest.setSkuCode(itemReqVo.getSkuCode());
            stockVoRequest.setSkuName(productSkuMap.get(itemReqVo.getSkuCode()).getProductName());
            stockVoRequest.setDocumentType(0);
            stockVoRequest.setDocumentNum(outboundOderCode);
            stockVoRequest.setSourceDocumentType(type);
            stockVoRequest.setSourceDocumentNum(allocationCode);
            stockVoRequest.setOperator(getCreateByName);
            list.add(stockVoRequest);
        }
        return list;
    }

    /**
     * 调拨单添加
     */
    private void allocationInsert(Allocation allocation, byte type, String typeName, Map<String, OrderProductSkuResponse> productSkuMap) {
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
                allocationProduct.setCategory(goodsRejectService.selectCategoryName(orderProductSkuResponse.getCategoryCode()));
                allocationProduct.setUnit(orderProductSkuResponse.getUnitName());
                allocationProduct.setBrand(orderProductSkuResponse.getBrandName());
                allocationProduct.setType(productTypeList.get(orderProductSkuResponse.getProductType()));
                allocationProduct.setTax(orderProductSkuResponse.getTaxRate().longValue());

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
    @Transactional(rollbackFor = Exception.class)
    public HttpResponse profitLossOrder(ProfitLossRequest request) {
        try {
            //根据详情信息 分为两个报损报溢单
            Map<String, List<ProfitLossDetailRequest>> detailMap = request.getDetailList().stream().collect(Collectors.groupingBy(ProfitLossDetailRequest::getWarehouseCode));
            ProfitLoss profitLoss;
            List<ProfitLoss> profitLossList = Lists.newArrayList();
            ProfitLossDetailRequest profitLossDetail;
            List<ProfitLossDetailRequest> profitLossProductList = Lists.newArrayList();
            List<String> skuList = request.getDetailList().stream().map(ProfitLossDetailRequest::getSkuCode).collect(Collectors.toList());
            List<OrderProductSkuResponse> productSkuList = productSkuDao.selectStockSkuInfoList(skuList);
            Map<String, OrderProductSkuResponse> productSkuResponseMap = productSkuList.stream().collect(Collectors.toMap(OrderProductSkuResponse::getSkuCode, Function.identity()));
            int order = 0;
            //报溢数量 正数值
            Long profitQuantity;
            //报损数量 负数值
            Long lossQuantity;
            OrderProductSkuResponse productSkuResponse;
            for (String warehouseCode : detailMap.keySet()) {
                profitLoss = new ProfitLoss();
                profitQuantity = 0L;
                lossQuantity = 0L;
                profitLoss.setOrderCode(String.format("%s_%d", request.getOrderCode(), ++order));
                //损溢类型:0 指定损益  1 盘点损益
                profitLoss.setOrderType(request.getOrderType());
                profitLoss.setLogisticsCenterCode(request.getLogisticsCenterCode());
                profitLoss.setLogisticsCenterName(request.getLogisticsCenterName());
                profitLoss.setRemark(request.getRemark());
                profitLoss.setCompanyName(COMPANY_NAME);
                profitLoss.setCompanyCode(COMPANY_CODE);
                profitLoss.setCreateBy(request.getCreateByName());
                profitLoss.setUpdateBy(request.getUpdateByName());
                profitLoss.setCreateTime(new DateTime(new Long(request.getCreateTime())).toDate());
                profitLoss.setUpdateTime(new DateTime(new Long(request.getApproveTime())).toDate());
                for (ProfitLossDetailRequest profitLossDetailRequest : detailMap.get(warehouseCode)) {
                    profitLossDetail = profitLossDetailRequest;
                    profitLossDetail.setCreateByName(request.getCreateByName());
                    profitLossDetail.setUpdateByName(request.getUpdateByName());
                    profitLossDetail.setUpdateTime(profitLoss.getCreateTime());
                    profitLossDetail.setCreateTime(profitLoss.getUpdateTime());
                    profitLossDetail.setQuantity(profitLossDetail.getQuantity());
                    if (profitLossDetailRequest.getQuantity() < 0) {
                        lossQuantity += profitLossDetailRequest.getQuantity();
                    } else {
                        profitQuantity += profitLossDetailRequest.getQuantity();
                    }
                    productSkuResponse = productSkuResponseMap.get(profitLossDetailRequest.getSkuCode());
                    if (productSkuResponse != null) {
                        profitLossDetail.setSkuName(productSkuResponse.getProductName());
                        profitLossDetail.setCategory(goodsRejectService.selectCategoryName(productSkuResponse.getCategoryCode()));
                        profitLossDetail.setBrand(productSkuResponse.getBrandName());
                        profitLossDetail.setColor(productSkuResponse.getColorName());
                        profitLossDetail.setSpecification(productSkuResponse.getSpec());
                        profitLossDetail.setType(productTypeList.get(productSkuResponse.getProductType()));
                        profitLossDetail.setModel(productSkuResponse.getModel());
                        profitLossDetail.setUnit(productSkuResponse.getUnitName());
                        profitLossDetail.setTax(productSkuResponse.getTaxRate().longValue());
                        profitLossDetail.setPictureUrl(productSkuResponse.getPictureUrl());
                    }
                    profitLossDetail.setOrderCode(profitLoss.getOrderCode());
                    if (StringUtils.isBlank(profitLoss.getWarehouseCode())) {
                        profitLoss.setWarehouseCode(profitLossDetailRequest.getWarehouseCode());
                        profitLoss.setWarehouseName(profitLossDetailRequest.getWarehouseName());
                    }
                    profitLossProductList.add(profitLossDetail);
                }
                profitLoss.setProfitQuantity(profitQuantity);
                profitLoss.setLossQuantity(lossQuantity);
                //dl只传回完成的
                profitLoss.setOrderStatusCode(0);
                profitLoss.setOrderStatusName("完成");
                profitLossList.add(profitLoss);
            }
            //添加损溢记录
            profitLossMapper.insertList(profitLossList);
            profitLossProductMapper.insertList(profitLossProductList);
            //库存变动操作
            Map<Integer, List<ProfitLossDetailRequest>> groupByList = profitLossProductList.stream().collect(Collectors.groupingBy(baseOrder -> {
                if (baseOrder.getQuantity() > 0) {
                    return 1;
                } else {
                    return 0;
                }
            }));
            //正值减库存
            if (groupByList.get(0) != null) {
                //操作类型 直接减库存 4
                StockChangeRequest stockChangeRequest = new StockChangeRequest();
                stockChangeRequest.setOperationType(4);
                List<StockVoRequest> list = handleProfitLossStockData(groupByList.get(0), request.getOrderCode());
                stockChangeRequest.setStockVoRequests(list);
                HttpResponse httpResponse = stockService.changeStock(stockChangeRequest);
                if (!MsgStatus.SUCCESS.equals(httpResponse.getCode())) {
                    LOGGER.error("dl回调   加库存异常");
                    throw new GroundRuntimeException("dl回调    减库存异常");
                }
            }
            if (groupByList.get(1) != null) {
                //操作类型 直接加库存 10
                StockChangeRequest stockChangeRequest = new StockChangeRequest();
                stockChangeRequest.setOperationType(10);
                List<StockVoRequest> list = handleProfitLossStockData(groupByList.get(1), request.getOrderCode());
                stockChangeRequest.setStockVoRequests(list);
                HttpResponse httpResponse = stockService.changeStock(stockChangeRequest);
                if (!MsgStatus.SUCCESS.equals(httpResponse.getCode())) {
                    LOGGER.error("dl回调   加库存异常");
                    throw new GroundRuntimeException("dl回调    减库存异常");
                }
            }
            return HttpResponse.success();
        } catch (Exception e) {
            LOGGER.error("订单回调异常:{}", e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    private List<StockVoRequest> handleProfitLossStockData(List<ProfitLossDetailRequest> profitLossProductList, String sourceOrderCode) {
        List<StockVoRequest> list = Lists.newArrayList();
        StockVoRequest stockVoRequest;
        for (ProfitLossDetailRequest itemReqVo : profitLossProductList) {
            stockVoRequest = new StockVoRequest();
            stockVoRequest.setCompanyCode(COMPANY_CODE);
            stockVoRequest.setCompanyName(COMPANY_NAME);
            stockVoRequest.setTransportCenterCode(itemReqVo.getLogisticsCenterCode());
            stockVoRequest.setTransportCenterName(itemReqVo.getLogisticsCenterName());
            stockVoRequest.setWarehouseCode(itemReqVo.getWarehouseCode());
            stockVoRequest.setWarehouseName(itemReqVo.getWarehouseName());
            stockVoRequest.setChangeNum(Math.abs(itemReqVo.getQuantity()));
            stockVoRequest.setSkuCode(itemReqVo.getSkuCode());
            stockVoRequest.setSkuName(itemReqVo.getSkuName());
            stockVoRequest.setDocumentType(11);
            stockVoRequest.setDocumentNum(itemReqVo.getOrderCode());
            stockVoRequest.setSourceDocumentType(11);
            stockVoRequest.setSourceDocumentNum(sourceOrderCode);
            stockVoRequest.setOperator(itemReqVo.getCreateByName());
            list.add(stockVoRequest);
        }
        return list;
    }

}
