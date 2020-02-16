package com.aiqin.bms.scmp.api.purchase.service.impl;

import com.aiqin.bms.scmp.api.base.*;
import com.aiqin.bms.scmp.api.common.*;
import com.aiqin.bms.scmp.api.constant.CommonConstant;
import com.aiqin.bms.scmp.api.constant.DictionaryEnum;
import com.aiqin.bms.scmp.api.constant.Global;
import com.aiqin.bms.scmp.api.product.dao.*;
import com.aiqin.bms.scmp.api.product.domain.pojo.*;
import com.aiqin.bms.scmp.api.product.domain.request.StockChangeRequest;
import com.aiqin.bms.scmp.api.product.domain.request.StockVoRequest;
import com.aiqin.bms.scmp.api.product.domain.request.UpdateOutboundProductReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.inbound.InboundBatchReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.inbound.InboundProductReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.inbound.InboundReqSave;
import com.aiqin.bms.scmp.api.product.domain.request.outbound.*;
import com.aiqin.bms.scmp.api.product.mapper.*;
import com.aiqin.bms.scmp.api.product.service.ProductCommonService;
import com.aiqin.bms.scmp.api.product.service.SkuService;
import com.aiqin.bms.scmp.api.product.service.StockService;
import com.aiqin.bms.scmp.api.purchase.domain.converter.OrderInfoToOutboundConverter;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.order.OrderInfo;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.order.OrderInfoItem;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.order.OrderInfoItemProductBatch;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.order.OrderInfoLog;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.returngoods.ReturnOrderInfo;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.returngoods.ReturnOrderInfoItem;
import com.aiqin.bms.scmp.api.purchase.domain.request.*;
import com.aiqin.bms.scmp.api.purchase.domain.request.callback.ProfitLossDetailRequest;
import com.aiqin.bms.scmp.api.purchase.domain.request.callback.ProfitLossRequest;
import com.aiqin.bms.scmp.api.purchase.domain.request.callback.TransfersRequest;
import com.aiqin.bms.scmp.api.purchase.domain.request.callback.TransfersSupplyDetailRequest;
import com.aiqin.bms.scmp.api.purchase.domain.request.order.*;
import com.aiqin.bms.scmp.api.purchase.domain.response.InnerValue;
import com.aiqin.bms.scmp.api.purchase.domain.response.order.OrderProductSkuResponse;
import com.aiqin.bms.scmp.api.purchase.domain.response.order.QueryOrderInfoItemBatchRespVO;
import com.aiqin.bms.scmp.api.purchase.domain.response.order.QueryOrderInfoItemRespVO;
import com.aiqin.bms.scmp.api.purchase.domain.response.order.QueryOrderInfoRespVO;
import com.aiqin.bms.scmp.api.purchase.mapper.*;
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
import com.aiqin.bms.scmp.api.util.Calculate;
import com.aiqin.ground.util.exception.GroundRuntimeException;
import com.aiqin.ground.util.http.HttpClient;
import com.aiqin.ground.util.json.JsonUtil;
import com.aiqin.ground.util.protocol.MessageId;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springfox.documentation.spring.web.json.Json;

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

    private static final BigDecimal big = BigDecimal.valueOf(0);
    /**
     * 宁波熙耘
     */
    private final static String COMPANY_CODE = "09";
    private final static String COMPANY_NAME = "宁波熙耘科技有限公司";
    private static List<String> productTypeList = Arrays.asList("商品", "赠品", "实物返");
    @Value("${order.info.url}")
    public String orderUrl;
    @Resource
    private OrderInfoMapper orderInfoMapper;
    @Resource
    private OrderInfoItemMapper orderInfoItemMapper;
    @Resource
    private OrderInfoItemProductBatchMapper orderInfoItemProductBatchMapper;
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
    private OutboundBatchDao outboundBatchDao;
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
    private InboundBatchDao inboundBatchDao;
    @Resource
    private AllocationMapper allocationMapper;
    @Resource
    private AllocationProductMapper allocationProductMapper;
    @Resource
    private AllocationProductBatchMapper allocationProductBatchMapper;
    @Resource
    private SupplierCommonService supplierCommonService;
    @Resource
    private ProfitLossMapper profitLossMapper;
    @Resource
    private ProfitLossProductMapper profitLossProductMapper;
    @Resource
    private ProfitLossProductBatchMapper profitLossProductBatchMapper;
    @Resource
    private GoodsRejectService goodsRejectService;
    @Resource
    private ProductSkuPicturesDao productSkuPicturesDao;
    @Resource
    private OrderInfoItemProductBatchMapper orderInfoItemProductBatchDao;
    @Resource
    private OrderInfoLogMapper orderInfoLogMapper;

    /**
     * 销售出库接口
     *
     * @param request
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public HttpResponse outboundOrder(OutboundRequest request) {
        // 验证销售订单是否存在
        OrderInfo response = orderInfoMapper.selectByOrderCode2(request.getOrderCode());
        if (response != null) {
            return HttpResponse.failure(ResultCode.ORDER_INFO_IS_HAVE);
        }
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
        Map<String, List<ProductSkuPictures>> picturesMap = picturesList.stream().collect(Collectors.groupingBy(ProductSkuPictures::getProductSkuCode));
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
            } else {
                throw new GroundRuntimeException(String.format("未查询到商品信息,skuCode:%s", outboundDetailRequest.getSkuCode()));
            }
            orderInfoItem.setTax(productSku.getTax());
            orderInfoItem.setNum(outboundDetailRequest.getNum());
            orderInfoItem.setPictureUrl(picturesMap.containsKey(outboundDetailRequest.getSkuCode()) ? picturesMap.get(outboundDetailRequest.getSkuCode()).get(0).getProductPicturePath() : "");
            orderInfoItem.setActualDeliverNum(outboundDetailRequest.getActualDeliverNum());
            orderInfoItem.setProductLineNum(outboundDetailRequest.getProductLineNum());
            orderInfoItem.setSkuCode(outboundDetailRequest.getSkuCode());
            orderInfoItem.setChannelUnitPrice(outboundDetailRequest.getChannelUnitPrice());
            orderInfoItem.setGivePromotion(outboundDetailRequest.getGiftType());
            orderInfoItem.setTotalChannelPrice(outboundDetailRequest.getChannelUnitPrice().multiply(BigDecimal.valueOf(outboundDetailRequest.getNum())).setScale(4 , BigDecimal.ROUND_HALF_UP));
            orderInfoItem.setOrderCode(orderInfo.getOrderCode());
            orderInfoItem.setActualChannelUnitPrice(outboundDetailRequest.getChannelUnitPrice());
            orderInfoItem.setActualTotalChannelPrice(outboundDetailRequest.getChannelUnitPrice().multiply(BigDecimal.valueOf(outboundDetailRequest.getActualDeliverNum())).setScale(4, BigDecimal.ROUND_HALF_UP));
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
        //渠道
        PriceChannel priceChannel = priceChannelMapper.selectByChannelName(request.getOrderOriginal());
        if (priceChannel != null) {
            orderInfo.setOrderOriginal(priceChannel.getPriceChannelCode());
            orderInfo.setOrderOriginalName(priceChannel.getPriceChannelName());
        } else {
            throw new GroundRuntimeException(String.format("未查询到渠道信息,name:%s", request.getOrderOriginal()));
        }
        //供应商与商品记录存入批次表中
        SupplyCompany supplyCompany;
        if(CollectionUtils.isNotEmpty(request.getSupplyDetail())){
            List<String> supplyIds = request.getSupplyDetail().stream().map(OutboundSupplyDetailRequest::getSupplyCode).collect(Collectors.toList());
            Map<String, SupplyCompany> supplyCompanyMap = supplyCompanyDao.selectByCompanyCodeList(supplyIds, "09");
            List<OrderInfoItemProductBatch> supplyDetailList = Lists.newArrayList();
            OrderInfoItemProductBatch orderInfoItemProductBatch;
            Map<String, OrderInfoItem> orderInfoItemMap = detailList.stream().collect(Collectors.toMap(orderInfoItems->{return orderInfoItems.getSkuCode()+orderInfoItems.getGivePromotion();}, Function.identity()));
            OrderInfoItem infoItem;
            for (OutboundSupplyDetailRequest supplyDetailRequest : request.getSupplyDetail()) {
                orderInfoItemProductBatch = new OrderInfoItemProductBatch();
                supplyCompany = supplyCompanyMap.get(supplyDetailRequest.getSupplyCode());
                if (supplyCompany == null) {
                    throw new GroundRuntimeException(String.format("未查询到供应商信息!,code:%s", supplyDetailRequest.getSupplyCode()));
                }
                infoItem = orderInfoItemMap.get(supplyDetailRequest.getSkuCode()+supplyDetailRequest.getGiftType());
                if (infoItem == null) {
                    throw new GroundRuntimeException(String.format("未查询到商品信息!,code:%s", supplyDetailRequest.getSkuCode()));
                }
                orderInfoItemProductBatch.setNum(supplyDetailRequest.getActualDeliverNum());
                orderInfoItemProductBatch.setActualDeliverNum(supplyDetailRequest.getActualDeliverNum());
                orderInfoItemProductBatch.setOrderCode(request.getOrderCode());
                orderInfoItemProductBatch.setSupplierCode(supplyDetailRequest.getSupplyCode());
                orderInfoItemProductBatch.setSupplierName(supplyCompany.getSupplyName());
                orderInfoItemProductBatch.setSkuCode(supplyDetailRequest.getSkuCode());
                orderInfoItemProductBatch.setSkuName(infoItem.getSkuName());
                supplyDetailList.add(orderInfoItemProductBatch);
            }
            orderInfo.setDetailBatchList(supplyDetailList);
            Integer supplyDetailCount = orderInfoItemProductBatchMapper.insertList(supplyDetailList);
            LOGGER.info("添加订单供应商详情:{}", supplyDetailCount);
        }

        orderInfo.setDetailList(detailList);
        Integer count = orderInfoMapper.insertSelective(orderInfo);
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
            LOGGER.error("dl回调:减库存异常");
            throw new GroundRuntimeException("dl回调:减库存异常");
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
            stockVoRequest.setCompanyName(orderInfo.getCompanyName());
            stockVoRequest.setCompanyCode(orderInfo.getCompanyCode());
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
        outboundProducts.stream().forEach(outboundProduct -> outboundProduct.setOutboundOderCode(outboundOderCode));
        int i = outboundDao.insertSelective(outbound);
        LOGGER.info("出库主表保存结果:{}", i);
        int j = outboundProductDao.insertBatch(outboundProducts);
        LOGGER.info("出库商品保存结果:{}", j);
        List<OutboundBatch> batchList = stockReqVO.getOutboundBatches();
        if(CollectionUtils.isNotEmpty(batchList)){
            batchList.stream().forEach(outBoundBatch -> outBoundBatch.setOutboundOderCode(outboundOderCode));
            //添加供应商对应的商品信息
            Integer count = outboundBatchDao.insertList(batchList);
            LOGGER.info("插入出库单供应商对应的商品信息返回结果:{}", count);
        }
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
        //操作时间 签收时间 等于回单时间 退货的商品对应供应商信息取入库单据相关
        //查询退货单是否存在
        ReturnOrderInfo response = returnOrderInfoMapper.selectByCode1(request.getReturnOrderCode());
        if (response != null) {
            return HttpResponse.failure(ResultCode.ORDER_INFO_IS_HAVE);
        }
        try {
            request.setReceivingTime(new DateTime(new Long(request.getReceiptTime())).toDate());
            request.setOperatorTime(request.getReceivingTime());
            //支付时间 发运时间 发货时间 等于创建时间
            request.setCreateDate(new DateTime(new Long(request.getCreateTime())).toDate());
            request.setDeliveryTime(request.getCreateDate());

            DetailRespVo detailRespVo = supplierRuleMapper.findByCompanyCode(COMPANY_CODE);
            //取字典表数据
            List<InnerValue> dictionaryInfoList = supplierDictionaryInfoDao.allList();
            Map<String, InnerValue> dictionaryInfoMap = dictionaryInfoList.stream().collect(Collectors.toMap(InnerValue::getName, innerValue -> innerValue));

            //渠道
            PriceChannel priceChannel = priceChannelMapper.selectByChannelName(request.getDeptName());

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
            Map<String, List<ProductSkuPictures>> picturesMap = picturesList.stream().collect(Collectors.groupingBy(ProductSkuPictures::getProductSkuCode));
            List<String> supplyIds = request.getDetailRequestList().stream().map(ReturnDetailRequest::getSupplyCode).collect(Collectors.toList());
            Map<String, SupplyCompany> supplyCompanyMap = supplyCompanyDao.selectByCompanyCodeList(supplyIds, "09");
            SupplyCompany supplyCompany;
            //根据详情信息 分为两个报损报溢单
            ReturnOrderInfo returnOrderInfo;
            List<ReturnOrderInfo> returnOrderInfoList = Lists.newArrayList();
            Map<String, List<ReturnDetailRequest>> detailMap = request.getDetailRequestList().stream().collect(Collectors.groupingBy(ReturnDetailRequest::getWarehouseCode));
            for (String warehouseCode : detailMap.keySet()) {
                returnOrderInfo = new ReturnOrderInfo();
                BeanUtils.copyProperties(request, returnOrderInfo);
                returnOrderInfo.setReturnOrderCode(String.format("%s-%s", request.getReturnOrderCode(), warehouseCode));
                returnOrderInfo.setCreateTime(request.getCreateDate());
                returnOrderInfo.setUpdateTime(request.getReceivingTime());
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
                if (priceChannel != null) {
                    returnOrderInfo.setOrderOriginal(priceChannel.getPriceChannelCode());
                    returnOrderInfo.setOrderOriginalName(priceChannel.getPriceChannelName());
                }
                for (ReturnDetailRequest returnDetailRequest : detailMap.get(warehouseCode)) {
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
                    } else {
                        throw new GroundRuntimeException(String.format("未查询到商品信息,skuCode:%s", returnOrderInfoItem.getSkuCode()));
                    }
                    returnOrderInfoItem.setTax(productSku.getTax());
                    returnOrderInfoItem.setPictureUrl(picturesMap.containsKey(returnDetailRequest.getSkuCode()) ? picturesMap.get(returnDetailRequest.getSkuCode()).get(0).getProductPicturePath() : "");
                    returnOrderInfoItem.setReturnOrderCode(returnOrderInfo.getReturnOrderCode());
                    returnOrderInfoItem.setNum(returnDetailRequest.getNum());
                    returnOrderInfoItem.setProductLineNum(returnDetailRequest.getProductLineNum());
                    returnOrderInfoItem.setSkuCode(returnDetailRequest.getSkuCode());
                    returnOrderInfoItem.setPrice(returnDetailRequest.getChannelUnitPrice());
                    //渠道价格
                    returnOrderInfoItem.setChannelUnitPrice(returnDetailRequest.getChannelUnitPrice());
                    returnOrderInfoItem.setTotalChannelPrice(returnDetailRequest.getChannelUnitPrice().multiply(BigDecimal.valueOf(returnDetailRequest.getActualDeliverNum())).setScale(4, BigDecimal.ROUND_HALF_UP));
                    //实际渠道价格
                    returnOrderInfoItem.setActualChannelUnitPrice(returnDetailRequest.getChannelUnitPrice());
                    returnOrderInfoItem.setActualTotalChannelPrice(returnDetailRequest.getChannelUnitPrice().multiply(BigDecimal.valueOf(returnDetailRequest.getActualDeliverNum())).setScale(4, BigDecimal.ROUND_HALF_UP));
                    returnOrderInfoItem.setActualInboundNum(returnDetailRequest.getActualDeliverNum().intValue());
                    returnOrderInfoItem.setAmount(returnDetailRequest.getChannelUnitPrice().multiply(BigDecimal.valueOf(returnDetailRequest.getActualDeliverNum())).setScale(4, BigDecimal.ROUND_HALF_UP));
                    //实际单价
                    returnOrderInfoItem.setActualAmount(returnDetailRequest.getChannelUnitPrice());
                    //实际总价
                    returnOrderInfoItem.setActualPrice(returnDetailRequest.getChannelUnitPrice().multiply(BigDecimal.valueOf(returnDetailRequest.getActualDeliverNum())).setScale(4, BigDecimal.ROUND_HALF_UP));
                    returnOrderInfoItem.setCompanyCode(COMPANY_CODE);
                    returnOrderInfoItem.setCompanyName(COMPANY_NAME);
                    returnOrderInfoItem.setWarehouseCode(returnDetailRequest.getWarehouseCode());
                    returnOrderInfoItem.setWarehouseName(returnDetailRequest.getWarehouseName());
                    supplyCompany = supplyCompanyMap.get(returnDetailRequest.getSupplyCode());
                    if (supplyCompany != null) {
                        returnOrderInfoItem.setSupplyCode(returnDetailRequest.getSupplyCode());
                        returnOrderInfoItem.setSupplyName(supplyCompany.getSupplyName());
                    }
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
                returnOrderInfoList.add(returnOrderInfo);
            }
            Integer count = returnOrderInfoMapper.insertList(returnOrderInfoList);
            LOGGER.info("添加退货单:{}", count);
            Integer detailCount = returnOrderInfoItemMapper.insertList(detailList);
            LOGGER.info("添加退货单详情:{}", detailCount);
            //入库单生成
            String inboundOderCode;
            List<InboundReqSave> convert = returnInfoConvert(returnOrderInfoList, detailList);
            for (InboundReqSave inboundReqSave : convert) {
                inboundOderCode = inboundRecord(inboundReqSave);
                //直接加库存
                StockChangeRequest stockChangeRequest = new StockChangeRequest();
                //操作类型 直接加库存 10
                stockChangeRequest.setOperationType(10);
                List<StockVoRequest> list = handleInboundStockData( inboundOderCode, inboundReqSave);
                stockChangeRequest.setStockVoRequests(list);
                HttpResponse httpResponse = stockService.changeStock(stockChangeRequest);
                if (!MsgStatus.SUCCESS.equals(httpResponse.getCode())) {
                    LOGGER.error("dl回调   加库存异常");
                    throw new GroundRuntimeException("dl回调   加库存异常");
                }
            }

            return HttpResponse.success();
        }catch (DataAccessException e){
            LOGGER.error("订单回调异常:{}", e);
            if(e.getCause().toString().contains("return_order_info_return_order_code_uindex")){
                throw new GroundRuntimeException("单据已存在");
            }
            throw new GroundRuntimeException("订单回调异常");
        }catch (Exception e) {
            LOGGER.error("订单回调异常:{}", e);
            throw new GroundRuntimeException("订单回调异常");
        }
    }

    public List<InboundReqSave> returnInfoConvert(List<ReturnOrderInfo> returnOrderInfoList, List<ReturnOrderInfoItem> detailList) {
        List<InboundReqSave> list = Lists.newArrayList();
        InboundReqSave inbound;
        InboundProductReqVo product;
        List<InboundProductReqVo> products;
        List<InboundBatchReqVo> batchList;
        InboundBatchReqVo inboundBatchReqVo;
        Map<String, List<ReturnOrderInfoItem>> detailMap = detailList.stream().collect(Collectors.groupingBy(ReturnOrderInfoItem::getReturnOrderCode));
        for (ReturnOrderInfo reqVo : returnOrderInfoList) {
            //实际含税金额
            BigDecimal praTaxAmount = big;
            //实际入库数量
            Long praInboundNum = 0L;
            //实际入库主数量
            Long praMainUnitNum = 0L;
            //预计含税金额
            BigDecimal preTaxAmount = big;
            //预计入库数量
            Long preInboundNum = 0L;
            //预计入库主数量
            Long preMainUnitNum = 0L;
            products = Lists.newArrayList();
            batchList = Lists.newArrayList();
            inbound = new InboundReqSave();
            BeanUtils.copyProperties(reqVo, inbound);
            //入库类型
            inbound.setInboundTypeCode(InboundTypeEnum.ORDER.getCode());
            inbound.setInboundTypeName(InboundTypeEnum.ORDER.getName());
            //入库状态
            inbound.setInboundStatusCode(InOutStatus.COMPLETE_INOUT.getCode());
            inbound.setInboundStatusName(InOutStatus.COMPLETE_INOUT.getName());
            //公司编码
            inbound.setCompanyCode(reqVo.getCompanyCode());
            inbound.setCompanyName(reqVo.getCompanyName());
            inbound.setInboundTime(reqVo.getDeliveryTime());
            inbound.setProvinceCode(reqVo.getProvinceCode());
            inbound.setProvinceName(reqVo.getProvinceName());
            inbound.setCityCode(reqVo.getCityCode());
            inbound.setCityName(reqVo.getCityName());
            inbound.setShipper(reqVo.getConsignee());
            inbound.setShipperNumber(reqVo.getConsigneePhone());
            inbound.setDetailedAddress(reqVo.getDetailAddress());
            //来源单据号
            inbound.setSourceOderCode(reqVo.getReturnOrderCode());
            //物流中心编码
            inbound.setLogisticsCenterCode(reqVo.getTransportCenterCode());
            inbound.setLogisticsCenterName(reqVo.getTransportCenterName());
            //供应商名称
            inbound.setSupplierName(reqVo.getSupplierName());
            inbound.setSupplierCode(reqVo.getSupplierCode());
            //预计到货时间
            inbound.setPreArrivalTime(reqVo.getReceivingTime());
            inbound.setCreateTime(reqVo.getCreateDate());
            //创建人
            inbound.setCreateBy(reqVo.getCreateByName());
            inbound.setUpdateBy(reqVo.getUpdateByName());
            for (ReturnOrderInfoItem returnOrderInfoItem : detailMap.get(reqVo.getReturnOrderCode())) {
                product = new InboundProductReqVo();
                inboundBatchReqVo = new InboundBatchReqVo();
                if(StringUtils.isNotBlank(returnOrderInfoItem.getSupplyCode())){
                    inboundBatchReqVo.setSkuName(returnOrderInfoItem.getSkuName());
                    inboundBatchReqVo.setSkuCode(returnOrderInfoItem.getSkuCode());
                    inboundBatchReqVo.setSupplierCode(returnOrderInfoItem.getSupplyCode());
                    inboundBatchReqVo.setSupplierName(returnOrderInfoItem.getSupplyName());
                    inboundBatchReqVo.setPraQty(returnOrderInfoItem.getNum());
                    inboundBatchReqVo.setCreateBy(reqVo.getCreateByName());
                    inboundBatchReqVo.setUpdateBy(reqVo.getUpdateByName());
                    batchList.add(inboundBatchReqVo);
                    inbound.setInboundBatchReqVos(batchList);
                    product.setSupplyCode(returnOrderInfoItem.getSupplyCode());
                    product.setSupplyName(returnOrderInfoItem.getSupplyName());
                }
                BeanUtils.copyProperties(returnOrderInfoItem, product);
                product.setPreInboundMainNum(returnOrderInfoItem.getNum());
                product.setPreInboundNum(returnOrderInfoItem.getNum());
                product.setPreTaxPurchaseAmount(returnOrderInfoItem.getPrice());
                product.setPreTaxAmount(returnOrderInfoItem.getPrice().multiply(BigDecimal.valueOf(product.getPreInboundNum())).setScale(4, BigDecimal.ROUND_HALF_UP));
                product.setPraInboundMainNum(returnOrderInfoItem.getActualInboundNum().longValue());
                product.setPraInboundNum(returnOrderInfoItem.getActualInboundNum().longValue());
                product.setPraTaxPurchaseAmount(returnOrderInfoItem.getPrice());
                product.setPraTaxAmount(returnOrderInfoItem.getPrice().multiply(BigDecimal.valueOf(product.getPraInboundNum())).setScale(4, BigDecimal.ROUND_HALF_UP));
                praInboundNum += returnOrderInfoItem.getActualInboundNum();
                praMainUnitNum += returnOrderInfoItem.getActualInboundNum();
                praTaxAmount = returnOrderInfoItem.getPrice().multiply(BigDecimal.valueOf(praInboundNum)).setScale(4, BigDecimal.ROUND_HALF_UP).add(praTaxAmount);
                preInboundNum += returnOrderInfoItem.getNum();
                preMainUnitNum += returnOrderInfoItem.getNum();
                preTaxAmount = returnOrderInfoItem.getPrice().multiply(BigDecimal.valueOf(preInboundNum)).setScale(4, BigDecimal.ROUND_HALF_UP).add(preTaxAmount);;
                inbound.setWarehouseCode(returnOrderInfoItem.getWarehouseCode());
                inbound.setWarehouseName(returnOrderInfoItem.getWarehouseName());
                //规格.
                product.setInboundNorms(returnOrderInfoItem.getSpec());
                product.setNorms(returnOrderInfoItem.getSpec());
                product.setCreateBy(reqVo.getCreateByName());
                product.setCreateTime(reqVo.getCreateDate());
                product.setInboundBaseContent("1");
                product.setInboundBaseUnit("1");
                //税率
                product.setTax(returnOrderInfoItem.getTax());
                products.add(product);
                inbound.setList(products);
            }
            //实际含税总金额
            inbound.setPraTaxAmount(praTaxAmount);
            //实际入库数量
            inbound.setPraInboundNum(praInboundNum);
            //实际入库主数量
            inbound.setPraMainUnitNum(praMainUnitNum);
            //预计入库数量
            inbound.setPreInboundNum(preInboundNum);
            //预计入库主数量
            inbound.setPreMainUnitNum(preMainUnitNum);
            //预计含税总金额
            inbound.setPreTaxAmount(preTaxAmount);
            list.add(inbound);
        }
        return list;
    }

    /**
     * 销售入库处理库存参数
     *
     * @param inboundOderCode
     * @param inboundReqSave
     * @return
     */
    private List<StockVoRequest> handleInboundStockData( String inboundOderCode, InboundReqSave inboundReqSave) {
        List<StockVoRequest> list = Lists.newArrayList();
        StockVoRequest stockVoRequest;
        for (InboundProductReqVo itemReqVo : inboundReqSave.getList()) {
            stockVoRequest = new StockVoRequest();
//            BeanUtils.copyProperties(returnOrderInfo, stockVoRequest);
            stockVoRequest.setCompanyName(inboundReqSave.getCompanyName());
            stockVoRequest.setCompanyCode(inboundReqSave.getCompanyCode());
            stockVoRequest.setTransportCenterName(inboundReqSave.getLogisticsCenterName());
            stockVoRequest.setTransportCenterCode(inboundReqSave.getLogisticsCenterCode());
            stockVoRequest.setWarehouseCode(inboundReqSave.getWarehouseCode());
            stockVoRequest.setWarehouseName(inboundReqSave.getWarehouseName());
            //库存是主单位数量 退供基商品含量默认是1
            stockVoRequest.setChangeNum(itemReqVo.getPraInboundNum());
            stockVoRequest.setSkuCode(itemReqVo.getSkuCode());
            stockVoRequest.setSkuName(itemReqVo.getSkuName());
            stockVoRequest.setDocumentType(1);
            stockVoRequest.setDocumentNum(inboundOderCode);
            stockVoRequest.setSourceDocumentType((int) OutboundTypeEnum.ORDER.getCode());
            stockVoRequest.setSourceDocumentNum(inboundReqSave.getSourceOderCode());
            stockVoRequest.setOperator(inboundReqSave.getCreateBy());
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
        //添加供应商对应的商品信息
        List<InboundBatchReqVo> batchList = reqVo.getInboundBatchReqVos();
        if(CollectionUtils.isNotEmpty(batchList)){
            batchList.stream().forEach(inboundBatchReqVo -> inboundBatchReqVo.setInboundOderCode(rule.getNumberingValue().toString()));
            Integer count = inboundBatchDao.insertList(batchList);
            LOGGER.info("插入入库单供应商对应的商品信息返回结果:{}", count);
        }
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
            Integer boundRecordType;
            OutboundTypeEnum outboundTypeEnum;
            InboundTypeEnum inboundTypeEnum;
            //查看单据是否重复
            Allocation response = allocationMapper.selectByCode(request.getAllocationCode());
            if (response != null) {
                return HttpResponse.failure(ResultCode.ORDER_INFO_IS_HAVE);
            }
            if (request.getTransfersType().equals(1)) {
                //调拨
                type = AllocationTypeEnum.ALLOCATION.getType();
                typeName = AllocationTypeEnum.ALLOCATION.getTypeName();
                boundRecordType = (int) OutboundTypeEnum.ALLOCATE.getCode();
                outboundTypeEnum = OutboundTypeEnum.ALLOCATE;
                inboundTypeEnum = InboundTypeEnum.ALLOCATE;
            } else {
                //移库
                type = AllocationTypeEnum.MOVE.getType();
                typeName = AllocationTypeEnum.MOVE.getTypeName();
                boundRecordType = (int) OutboundTypeEnum.MOVEMENT.getCode();
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
            allocationHandler(request, productSkuMap, allocation);
            //生成出库单
            OutboundReqVo convert = handleTransferOutbound(allocation, productSkuMap, outboundTypeEnum);
            //调拨才有出库 出库单号
            outboundOderCode = outboundRecord(convert);
            //调拨直接减库存
            StockChangeRequest stockChangeRequest = new StockChangeRequest();
            //操作类型 直接减库存 4
            stockChangeRequest.setOperationType(4);
            List<StockVoRequest> list = handleAllocationStockData(request.getCreateByName(), productSkuMap, allocation.getDetailList(), allocation.getCompanyCode(), allocation.getCompanyName(), allocation.getCallOutLogisticsCenterCode()
                    , allocation.getCallOutLogisticsCenterName(), allocation.getCallOutWarehouseCode(), allocation.getCallOutWarehouseName(), allocation.getAllocationCode(), outboundOderCode, boundRecordType);
            stockChangeRequest.setStockVoRequests(list);
            HttpResponse httpResponse = stockService.changeStock(stockChangeRequest);
            if (!MsgStatus.SUCCESS.equals(httpResponse.getCode())) {
                LOGGER.error("dl回调:调拨减库存异常");
                throw new GroundRuntimeException("dl回:调减库存异常");
            }
            //生成入库单
            InboundReqSave inboundReqSave = handleTransferInbound(allocation, productSkuMap, inboundTypeEnum);
            inboundOderCode = inboundRecord(inboundReqSave);
            //直接加库存
            StockChangeRequest stockRequest = new StockChangeRequest();
            //操作类型 直接加库存 10
            stockRequest.setOperationType(10);
            List<StockVoRequest> inboundList = handleAllocationStockData(request.getCreateByName(), productSkuMap, allocation.getDetailList(), allocation.getCompanyCode(), allocation.getCompanyName(), allocation.getCallInLogisticsCenterCode()
                    , allocation.getCallInLogisticsCenterName(), allocation.getCallInWarehouseCode(), allocation.getCallInWarehouseName(), allocation.getAllocationCode(), inboundOderCode, boundRecordType);
            stockRequest.setStockVoRequests(inboundList);
            HttpResponse stockResponse = stockService.changeStock(stockRequest);
            if (!MsgStatus.SUCCESS.equals(stockResponse.getCode())) {
                LOGGER.error("dl回调:调拨加库存异常");
                throw new GroundRuntimeException("dl回调:加库存异常");
            }
            allocation.setOutboundOderCode(outboundOderCode);
            allocation.setInboundOderCode(inboundOderCode);
            //生成调拨单
            allocationInsert(allocation, type, typeName);
            return HttpResponse.success();
        } catch (GroundRuntimeException e) {
            LOGGER.error("订单回调异常:{}", e);
            throw new GroundRuntimeException("订单回调异常");
        }
    }

    /**
     * 供应商与商品关系
     */
    private void allocationHandler(TransfersRequest requests, Map<String, OrderProductSkuResponse> productSkuMap, Allocation allocation) {
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
                allocationProduct.setTax(orderProductSkuResponse.getTax());

            }
            allocationProduct.setAllocationCode(allocation.getAllocationCode());
            allocationProduct.setCreateBy(allocation.getCreateBy());
            allocationProduct.setCreateTime(allocation.getCreateTime());
            allocationProduct.setUpdateTime(allocation.getUpdateTime());
            allocationProduct.setUpdateBy(allocation.getUpdateBy());
            allocationProductList.add(allocationProduct);
        }
        //添加
        allocation.setDetailList(allocationProductList);
        List<AllocationProductBatch> allocationProductBatchList = Lists.newArrayList();
        AllocationProductBatch allocationProductBatch;
        List<String> supplyIds = requests.getSupplyDetailList().stream().map(TransfersSupplyDetailRequest::getSupplyCode).collect(Collectors.toList());
        Map<String, SupplyCompany> supplyCompanyMap = supplyCompanyDao.selectByCompanyCodeList(supplyIds, "09");
        SupplyCompany supplyCompany;
        for (TransfersSupplyDetailRequest request : requests.getSupplyDetailList()) {
            allocationProductBatch = new AllocationProductBatch();
            orderProductSkuResponse = productSkuMap.get(request.getSkuCode());
            if (orderProductSkuResponse != null) {
                allocationProductBatch.setSkuName(orderProductSkuResponse.getProductName());
            }
            allocationProductBatch.setSkuCode(request.getSkuCode());
            allocationProductBatch.setAllocationCode(allocation.getAllocationCode());
            allocationProductBatch.setQuantity(request.getQuantity());
            allocationProductBatch.setCreateBy(allocation.getCreateBy());
            allocationProductBatch.setCreateTime(allocation.getCreateTime());
            allocationProductBatch.setUpdateTime(allocation.getUpdateTime());
            allocationProductBatch.setUpdateBy(allocation.getUpdateBy());
            allocationProductBatch.setSupplierCode(request.getSupplyCode());
            supplyCompany = supplyCompanyMap.get(request.getSupplyCode());
            if (supplyCompany == null) {
                throw new GroundRuntimeException(String.format("未查询到供应商信息!,code:%s", request.getSupplyCode()));
            }
            allocationProductBatch.setSupplierName(supplyCompany.getSupplyName());
            allocationProductBatchList.add(allocationProductBatch);
        }
        allocation.setDetailBatchList(allocationProductBatchList);
    }

    /**
     * 调拨出库单参数处理
     *
     * @param allocation
     * @return
     */
    public OutboundReqVo handleTransferOutbound(Allocation allocation, Map<String, OrderProductSkuResponse> productSkuMap, OutboundTypeEnum outboundTypeEnum) {
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
        List<OutboundBatch> batchList = Lists.newArrayList();
        OutboundBatch outboundBatch;
        for (AllocationProductBatch batch : allocation.getDetailBatchList()) {
            outboundBatch = new OutboundBatch();
            outboundBatch.setSkuName(batch.getSkuName());
            outboundBatch.setSkuCode(batch.getSkuCode());
            outboundBatch.setSupplierCode(batch.getSupplierCode());
            outboundBatch.setSupplierName(batch.getSupplierName());
            outboundBatch.setPraQty(batch.getQuantity());
            outboundBatch.setCreateBy(allocation.getCreateBy());
            outboundBatch.setUpdateBy(allocation.getUpdateBy());
            batchList.add(outboundBatch);
        }
        OrderProductSkuResponse orderProductSkuResponse;
        for (AllocationProduct item : allocation.getDetailList()) {
            outboundProduct = new OutboundProductReqVo();
            //sku
            outboundProduct.setSkuCode(item.getSkuCode());
            outboundProduct.setSkuName(item.getSkuName());
            orderProductSkuResponse = productSkuMap.get(item.getSkuCode());
            if (orderProductSkuResponse != null) {
                //图片
                outboundProduct.setPictureUrl(orderProductSkuResponse.getPictureUrl());
                //规格
                outboundProduct.setNorms(orderProductSkuResponse.getSpec());
                outboundProduct.setUnitCode(orderProductSkuResponse.getUnitCode());
                //单位
                outboundProduct.setUnitName(orderProductSkuResponse.getUnitName());
                outboundProduct.setOutboundNorms(orderProductSkuResponse.getSpec());
                outboundProduct.setColorCode(orderProductSkuResponse.getColorCode());
                outboundProduct.setColorName(orderProductSkuResponse.getColorName());
                outboundProduct.setTax(orderProductSkuResponse.getTax());
            } else {
                throw new GroundRuntimeException(String.format("未查询到商品信息,skuCode:%s", item.getSkuCode()));
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
        stockReqVO.setOutboundBatches(batchList);
        return stockReqVO;
    }

    /**
     * 调拨入库单参数处理
     *
     * @param allocation
     * @return
     */
    public InboundReqSave handleTransferInbound(Allocation allocation, Map<String, OrderProductSkuResponse> productSkuMap, InboundTypeEnum inboundTypeEnum) {
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
        List<InboundBatchReqVo> batchList = Lists.newArrayList();
        InboundBatchReqVo inboundBatchReqVo;
        for (AllocationProductBatch batch : allocation.getDetailBatchList()) {
            inboundBatchReqVo = new InboundBatchReqVo();
            inboundBatchReqVo.setSkuName(batch.getSkuName());
            inboundBatchReqVo.setSkuCode(batch.getSkuCode());
            inboundBatchReqVo.setSupplierCode(batch.getSupplierCode());
            inboundBatchReqVo.setSupplierName(batch.getSupplierName());
            inboundBatchReqVo.setPraQty(batch.getQuantity());
            inboundBatchReqVo.setCreateBy(allocation.getCreateBy());
            inboundBatchReqVo.setUpdateBy(allocation.getUpdateBy());
            batchList.add(inboundBatchReqVo);
        }
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
                product.setTax(orderProductSkuResponse.getTax());
            } else {
                throw new GroundRuntimeException(String.format("未查询到商品信息,skuCode:%s", allocationProduct.getSkuCode()));
            }
            product.setCreateBy(allocation.getCreateBy());
            product.setCreateTime(allocation.getCreateTime());
            product.setSkuCode(allocationProduct.getSkuCode());
            products.add(product);
        }
        inbound.setList(products);
        inbound.setInboundBatchReqVos(batchList);
        return inbound;
    }

    private List<StockVoRequest> handleAllocationStockData(String getCreateByName, Map<String, OrderProductSkuResponse> productSkuMap, List<AllocationProduct> productList, String companyCode, String companyName, String logisticsCenterCode, String logisticsCenterName,
                                                           String outWarehouseCode, String outWarehouseName, String allocationCode, String outboundOderCode, Integer type) {
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
    private void allocationInsert(Allocation allocation, byte type, String typeName) {
        // 获取编码
        String content = HandleTypeCoce.ADD_ALLOCATION.getName();
        //保存日志
        supplierCommonService.getInstance(allocation.getAllocationCode() + "", HandleTypeCoce.ADD.getStatus(), ObjectTypeCode.ALLOCATION.getStatus(), content, null, HandleTypeCoce.ADD.getName(),"DL同步");
        //设置状态(已完成)
        allocation.setAllocationStatusCode(AllocationEnum.ALLOCATION_TYPE_FINISHED.getStatus());
        allocation.setAllocationStatusName(AllocationEnum.ALLOCATION_TYPE_FINISHED.getName());
        allocation.setAllocationType(type);
        allocation.setAllocationTypeName(typeName);
        allocationMapper.insertSelective(allocation);
        //添加详情
        allocationProductMapper.saveList(allocation.getDetailList());
        //添加供应商和商品关系
        allocationProductBatchMapper.saveList(allocation.getDetailBatchList());
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
            //报溢数量 正数值
            Long profitQuantity;
            //报损数量 负数值
            Long lossQuantity;
            OrderProductSkuResponse productSkuResponse;
            List<String> supplyIds = request.getDetailList().stream().map(ProfitLossDetailRequest::getSupplyCode).collect(Collectors.toList());
            Map<String, SupplyCompany> supplyCompanyMap = supplyCompanyDao.selectByCompanyCodeList(supplyIds, "09");
            SupplyCompany supplyCompany;
            List<ProfitLossProductBatch> batchList = Lists.newArrayList();
            ProfitLossProductBatch profitLossProductBatch;
            ProfitLoss result;
            for (String warehouseCode : detailMap.keySet()) {
                profitLoss = new ProfitLoss();
                profitQuantity = 0L;
                lossQuantity = 0L;
                profitLoss.setOrderCode(String.format("%s_%s", request.getOrderCode(), warehouseCode));
                result = profitLossMapper.selectByOrderCode(profitLoss.getOrderCode());
                if(result!=null){
                    throw new GroundRuntimeException("单据已存在");
                }
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
                    if (productSkuResponse == null) {
                        throw new GroundRuntimeException("未查询到商品信息");
                    }
                    profitLossDetail.setSkuName(productSkuResponse.getProductName());
                    profitLossDetail.setCategory(goodsRejectService.selectCategoryName(productSkuResponse.getCategoryCode()));
                    profitLossDetail.setBrand(productSkuResponse.getBrandName());
                    profitLossDetail.setColor(productSkuResponse.getColorName());
                    profitLossDetail.setSpecification(productSkuResponse.getSpec());
                    profitLossDetail.setType(productTypeList.get(productSkuResponse.getProductType()));
                    profitLossDetail.setModel(productSkuResponse.getModel());
                    profitLossDetail.setUnit(productSkuResponse.getUnitName());
                    profitLossDetail.setTax(productSkuResponse.getTax());
                    profitLossDetail.setPictureUrl(productSkuResponse.getPictureUrl());
                    profitLossDetail.setOrderCode(profitLoss.getOrderCode());
                    if (StringUtils.isBlank(profitLoss.getWarehouseCode())) {
                        profitLoss.setWarehouseCode(profitLossDetailRequest.getWarehouseCode());
                        profitLoss.setWarehouseName(profitLossDetailRequest.getWarehouseName());
                    }
                    //增加批次供应商的信息
                    profitLossProductBatch = new ProfitLossProductBatch();
                    profitLossProductBatch.setQuantity(profitLossDetailRequest.getQuantity());
                    profitLossProductBatch.setSkuCode(profitLossDetailRequest.getSkuCode());
                    profitLossProductBatch.setSkuName(productSkuResponse.getProductName());
                    profitLossProductBatch.setSupplierCode(profitLossDetailRequest.getSupplyCode());
                    supplyCompany = supplyCompanyMap.get(profitLossDetailRequest.getSupplyCode());
                    if (supplyCompany == null) {
                        throw new GroundRuntimeException(String.format("未查询到供应商信息!,code:%s", profitLossDetailRequest.getSupplyCode()));
                    }
                    profitLossProductBatch.setSupplierName(supplyCompany.getSupplyName());
                    profitLossProductBatch.setOrderCode(profitLoss.getOrderCode());
                    batchList.add(profitLossProductBatch);
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
            profitLossProductBatchMapper.insertList(batchList);
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
                    LOGGER.error("dl回调:减库存异常");
                    throw new GroundRuntimeException("dl回调:减库存异常");
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
                    LOGGER.error("dl回调:加库存异常");
                    throw new GroundRuntimeException("dl回调:加库存异常");
                }
            }
            return HttpResponse.success();
        } catch (GroundRuntimeException e) {
            e.printStackTrace();
            //LOGGER.error("报损报溢订单回调异常:{}", e);
            throw new GroundRuntimeException("报损报溢订单回调异常");
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public HttpResponse outboundOrderCallBack(OutboundCallBackRequest request) {
        // 验证销售订单是否存在
        OrderInfo response = orderInfoMapper.selectByOrderCode2(request.getOderCode());
        if (response == null) {
            return HttpResponse.failure(ResultCode.ORDER_INFO_NOT_HAVE);
        }
        // 操作时间 签收时间
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrderCode(request.getOderCode());
        orderInfo.setDeliveryTime(request.getDeliveryTime());
        orderInfo.setOperatorTime(orderInfo.getReceivingTime());
        orderInfo.setOperator(request.getDeliveryPerson());
        orderInfo.setUpdateById(request.getPersonId());
        orderInfo.setUpdateByName(request.getPersonName());
        orderInfo.setReceivingTime(request.getReceiveTime());
        orderInfo.setActualProductNum(request.getActualTotalCount());
        List<OutboundCallBackDetailRequest> detailList = request.getDetailList();
        if (CollectionUtils.isEmpty(detailList)) {
            LOGGER.info("销售单回传的详情信息缺失");
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        // 根据回传信息，更新销售单的实际发货信息
        OrderInfoItem orderInfoItem;
        List<OrderInfoItem> itemList = Lists.newArrayList();
        Map<String, OrderInfoItem> product = new HashMap<>();
        String key;
        for (OutboundCallBackDetailRequest detail : detailList) {
            key = String.format("%s,%s", response.getOrderCode(), detail.getLineCode());
            if (product.get(key) == null) {
                product.put(key, orderInfoItemMapper.selectOrderByLine(response.getOrderCode(), detail.getLineCode()));
            }
        }
        BigDecimal actualTotalChannelAmount = BigDecimal.ZERO, actualTotalProductAmount = BigDecimal.ZERO;
        for (OutboundCallBackDetailRequest detail : detailList) {
            orderInfoItem = new OrderInfoItem();
            orderInfoItem.setActualDeliverNum(detail.getActualProductCount());
            // 根据单价计算总价
            key = String.format("%s,%s", response.getOrderCode(), detail.getLineCode());
            OrderInfoItem item = product.get(key);
            // 计算实际分销总价和实际渠道总计
            orderInfoItem.setActualPrice(item.getPrice());
            orderInfoItem.setActualAmount(item.getPrice().multiply(BigDecimal.valueOf(detail.getActualProductCount())).
                    setScale(4, BigDecimal.ROUND_HALF_UP));
            orderInfoItem.setActualChannelUnitPrice(item.getChannelUnitPrice());
            orderInfoItem.setActualTotalChannelPrice(item.getChannelUnitPrice().multiply(BigDecimal.valueOf(detail.getActualProductCount())).
                    setScale(4, BigDecimal.ROUND_HALF_UP));
            orderInfoItem.setProductLineNum(detail.getLineCode());
            orderInfoItem.setOrderCode(request.getOderCode());
            itemList.add(orderInfoItem);

            actualTotalChannelAmount = actualTotalChannelAmount.add(item.getChannelUnitPrice());
            actualTotalProductAmount = actualTotalProductAmount.add(item.getPrice());
        }
        // 更新订单信息
        orderInfo.setActualProductChannelTotalAmount(actualTotalChannelAmount);
        orderInfo.setActualProductTotalAmount(actualTotalProductAmount);
        // 计算实际的体积,重量
        if(response.getVolume() != null && response.getVolume() > 0){
            Long volume = response.getVolume() / response.getProductNum() * orderInfo.getActualProductNum();
            orderInfo.setActualVolume(volume);
        }
        if(response.getWeight() != null && response.getWeight() > 0){
            Long weight = response.getWeight() / response.getProductNum() * orderInfo.getActualProductNum();
            orderInfo.setActualWeight(weight);
        }
        Integer count = orderInfoMapper.updateByOrderCode(orderInfo);
        if(count <= 0){
            LOGGER.error("发货回传失败 ！！" + orderInfo);
            //throw new GroundRuntimeException(String.format("发货回传失败"));
        }
        orderInfoItemMapper.updateBatch(itemList);
        // 添加订单日志
        OrderInfoLog orderInfoLog = new OrderInfoLog(null, request.getOderCode(), OrderStatus.ALL_SHIPPED.getStatusCode(),
                OrderStatus.ALL_SHIPPED.getBackgroundOrderStatus(),
                OrderStatus.ALL_SHIPPED.getExplain(),
                OrderStatus.ALL_SHIPPED.getStandardDescription(),
                request.getPersonName(), new Date(), Global.COMPANY_09, Global.COMPANY_09_NAME);
        orderInfoLogMapper.insert(orderInfoLog);
        // 根据回传信息，更新销售单的实际发货批次信息
        if(CollectionUtils.isNotEmpty(request.getBatchList())){
            List<OrderInfoItemProductBatch> batchList = Lists.newArrayList();
            OrderInfoItemProductBatch productBatch;
            for (OutboundCallBackBatchRequest batch : request.getBatchList()){
                productBatch = new OrderInfoItemProductBatch();
                productBatch.setOrderCode(request.getOderCode());
                productBatch.setSkuCode(batch.getSkuCode());
                productBatch.setSkuName(batch.getSkuName());
                productBatch.setNum(batch.getProductCount());
                productBatch.setActualDeliverNum(batch.getActualTotalCount());
                productBatch.setProductTime(batch.getProductDate());
                productBatch.setBatchNumber(batch.getBatchCode());
                productBatch.setBatchRemark(batch.getBatchRemark());
                productBatch.setTransportCenterCode(response.getTransportCenterCode());
                productBatch.setTransportCenterName(response.getTransportCenterName());
                productBatch.setWarehouseCode(response.getWarehouseCode());
                productBatch.setWarehouseName(response.getWarehouseName());
                productBatch.setCompanyCode(response.getCompanyCode());
                productBatch.setCompanyName(response.getCompanyName());
                productBatch.setSupplierCode(response.getSupplierCode());
                productBatch.setSupplierName(response.getSupplierName());
                productBatch.setProductLineNum(batch.getLineCode());
                batchList.add(productBatch);
            }
            orderInfoItemProductBatchDao.insertBatch(batchList);
        }
        // 更新出库单
        this.updateOutbound(request);
        // 调用爱亲供应链的接口 回传销售单的发货等信息
        this.updateAiqinOrder(request);
        return HttpResponse.success();
    }

    private void updateOutbound(OutboundCallBackRequest request){
        // 根据来源单号查询销售单
        Outbound outbound = outboundDao.selectBySourceCode(request.getOderCode());
        // 设置状态
        outbound.setOutboundStatusCode(InOutStatus.COMPLETE_INOUT.getCode());
        outbound.setOutboundStatusName(InOutStatus.COMPLETE_INOUT.getName());
        // 设置出库时间
        outbound.setOutboundTime(request.getDeliveryTime());
        // 设置出库的实际数量，实际金额
        outbound.setPraOutboundNum(request.getActualTotalCount());
        outbound.setPraMainUnitNum(request.getActualTotalCount());
        outbound.setUpdateBy(request.getDeliveryPerson());
        List<OutboundCallBackDetailRequest> detailList = request.getDetailList();
        String key;
        Map<String, OutboundProduct> product = new HashMap<>();
        for (OutboundCallBackDetailRequest detail : detailList) {
            key = String.format("%s,%s", outbound.getOutboundOderCode(), detail.getLineCode());
            if (product.get(key) == null) {
                product.put(key, outboundProductDao.selectByProductAmount(outbound.getOutboundOderCode(), detail.getLineCode()));
            }
        }

        List<UpdateOutboundProductReqVO> productList = Lists.newArrayList();
        UpdateOutboundProductReqVO outboundProduct;
        // 便利更新出库单的实际数量
        BigDecimal totalTaxAmount = BigDecimal.ZERO, totalAmount = BigDecimal.ZERO;
        for(OutboundCallBackDetailRequest detail : detailList){
            outboundProduct = new UpdateOutboundProductReqVO();
            // 根据出库单与行号找到对应的预计采购价
            key = String.format("%s,%s", outbound.getOutboundOderCode(), detail.getLineCode());
            outboundProduct.setOutboundOderCode(outbound.getOutboundOderCode());
            outboundProduct.setLineCode(detail.getLineCode());
            outboundProduct.setPraOutboundNum(detail.getActualProductCount());
            outboundProduct.setPraOutboundMainNum(detail.getActualProductCount());
            OutboundProduct outProduct = product.get(key);
            outboundProduct.setPraTaxPurchaseAmount(outProduct.getPreTaxPurchaseAmount());
            BigDecimal taxAmount = outProduct.getPreTaxPurchaseAmount().multiply(BigDecimal.valueOf(detail.getActualProductCount())).
                    setScale(4, BigDecimal.ROUND_HALF_UP);
            outboundProduct.setPraTaxAmount(taxAmount);
            outboundProduct.setOperator(request.getDeliveryPerson());
            productList.add(outboundProduct);
            totalTaxAmount = totalTaxAmount.add(taxAmount);
            BigDecimal praAmount = Calculate.computeNoTaxPrice(taxAmount, product.get(key).getTax());
            totalAmount = totalAmount.add(praAmount);
        }
        outbound.setPraTaxAmount(totalTaxAmount);
        outbound.setPraAmount(totalAmount);
        outbound.setPraTax(totalTaxAmount.subtract(totalAmount));

        // 便利更新出库的批次信息
        if(CollectionUtils.isNotEmpty(request.getBatchList())){
            List<OutboundBatch> outboundBatchList = Lists.newArrayList();
            OutboundBatch outboundBatch;
            for(OutboundCallBackBatchRequest batch : request.getBatchList()){
                outboundBatch = new OutboundBatch();
                outboundBatch.setOutboundOderCode(outbound.getOutboundOderCode());
                outboundBatch.setSkuCode(batch.getSkuCode());
                outboundBatch.setSkuName(batch.getSkuName());
                outboundBatch.setOutboundBatchCode(batch.getBatchCode());
                outboundBatch.setManufactureTime(batch.getProductDate());
                outboundBatch.setBatchRemark(batch.getBatchRemark());
                outboundBatch.setPraQty(batch.getActualTotalCount());
                outboundBatch.setCreateBy(request.getDeliveryPerson());
                outboundBatch.setUpdateBy(request.getDeliveryPerson());
                outboundBatch.setLineNum(batch.getLineCode());
                outboundBatchList.add(outboundBatch);
            }
            outboundBatchDao.insertList(outboundBatchList);
        }
        Integer count = outboundDao.updateByPrimaryKey(outbound);
        if(count <= 0){
            LOGGER.error("发货回传失败, 出库单更新失败");
            throw new GroundRuntimeException(String.format("发货回传失败, 出库单更新失败"));
        }
            outboundProductDao.updateAll(productList);
    }

    @Async
    public void updateAiqinOrder(OutboundCallBackRequest request){
        LOGGER.info("调用审批流发起申请,request={}", request);
        String url = orderUrl + "/purchase/sale/info";
        OrderIogisticsVo info = new OrderIogisticsVo();
        // 复制订单主信息
        BeanUtils.copyProperties(request, info);
        info.setOrderStoreCode(request.getOderCode());
        // 复制订单商品明细
        List<OrderStoreDetail> infoList = BeanCopyUtils.copyList(request.getDetailList(), OrderStoreDetail.class);
        info.setOrderStoreDetail(infoList);
        // 复制批次信息
        List<OrderBatchStoreDetail> batchList = BeanCopyUtils.copyList(request.getBatchList(), OrderBatchStoreDetail.class);
        info.setOrderBatchStoreDetail(batchList);
        HttpClient httpClient = HttpClient.post(url).json(info).timeout(20000);
        HttpResponse response = httpClient.action().result(HttpResponse.class);
        if(response.getCode().equals(MessageId.SUCCESS_CODE)){
            LOGGER.info("回传爱亲供应链的发货单成功");
        }else {
            LOGGER.error("回传爱亲供应链的发货单失败:{}", response.getMessage());
            throw new GroundRuntimeException(String.format("回传爱亲供应链的发货单失败:%s", response.getMessage()));
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public HttpResponse deliveryCallBack(DeliveryCallBackRequest request){
        if(request == null){
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        List<OrderInfo> list = Lists.newArrayList();
        OrderInfo orderInfo;
        List<DeliveryDetailRequest> detailList = request.getDetailList();
        if(CollectionUtils.isEmpty(detailList)){
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        // 便利更新采购单的发运信息
        for(DeliveryDetailRequest detail : detailList){
            orderInfo = new OrderInfo();
            orderInfo.setOrderCode(detail.getOrderCode());
            orderInfo.setDeliverAmount(detail.getTransportAmount());
            orderInfo.setTransportTime(request.getTransportDate());
            orderInfo.setTransportCompanyCode(request.getTransportCompanyCode());
            orderInfo.setTransportCompany(request.getTransportCompanyName());
            orderInfo.setTransportNumber(request.getTransportCode());
            orderInfo.setCustomerCode(request.getCustomerCode());
            orderInfo.setCustomerName(request.getCustomerName());
            orderInfo.setTransportStatus(0);
            orderInfo.setUpdateByName(request.getTransportPerson());
            list.add(orderInfo);
        }
        // 更新订单的发运信息
        Integer count = orderInfoMapper.updateBatch(list);
        if (count <= 0){
            LOGGER.error("更新耘链的订单的发运信息失败！！！");
            throw new GroundRuntimeException(String.format("更新耘链的订单的发运信息失败:%s", count));
        }
        // 回传爱亲的销售单的发运信息
        DeliveryInfoVo info =  new DeliveryInfoVo();
        BeanUtils.copyProperties(request, info);
        info.setTransportStatus(0);
        List<DeliveryDetailInfo> infoList = BeanCopyUtils.copyList(request.getDetailList(), DeliveryDetailInfo.class);
        info.setDeliveryDetail(infoList);
        String url = orderUrl + "/purchase/delivery/info";
        LOGGER.info("打印发运单回传爱亲数据："  + JsonUtil.toJson(info));
        HttpClient httpClient = HttpClient.post(url).json(info).timeout(20000);
        HttpResponse response = httpClient.action().result(HttpResponse.class);
        if(response.getCode().equals(MessageId.SUCCESS_CODE)){
            LOGGER.info("回传爱亲供应链的发运单成功");
        }else {
            LOGGER.error("回传爱亲供应链的发运单失败:{}", response.getMessage());
            throw new GroundRuntimeException(String.format("回传爱亲供应链的发运单失败:%s",response.getMessage()));
        }
        return HttpResponse.success();
    }

}
