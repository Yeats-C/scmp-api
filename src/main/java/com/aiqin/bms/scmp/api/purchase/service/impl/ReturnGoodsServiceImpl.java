package com.aiqin.bms.scmp.api.purchase.service.impl;

import com.aiqin.bms.scmp.api.base.*;
import com.aiqin.bms.scmp.api.base.service.impl.BaseServiceImpl;
import com.aiqin.bms.scmp.api.common.BizException;
import com.aiqin.bms.scmp.api.common.InboundTypeEnum;
import com.aiqin.bms.scmp.api.constant.Global;
import com.aiqin.bms.scmp.api.product.dao.InboundBatchDao;
import com.aiqin.bms.scmp.api.product.dao.InboundDao;
import com.aiqin.bms.scmp.api.product.dao.InboundProductDao;
import com.aiqin.bms.scmp.api.product.domain.converter.returnorder.ReturnOrderToInboundConverter;
import com.aiqin.bms.scmp.api.product.domain.dto.returnorder.ReturnOrderInfoDTO;
import com.aiqin.bms.scmp.api.product.domain.pojo.Inbound;
import com.aiqin.bms.scmp.api.product.domain.pojo.InboundBatch;
import com.aiqin.bms.scmp.api.product.domain.pojo.InboundProduct;
import com.aiqin.bms.scmp.api.product.domain.pojo.StockBatch;
import com.aiqin.bms.scmp.api.product.domain.request.*;
import com.aiqin.bms.scmp.api.product.domain.request.inbound.InboundBatchReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.inbound.InboundProductReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.inbound.InboundReqSave;
import com.aiqin.bms.scmp.api.product.service.InboundService;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.order.OrderInfoItemProductBatch;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.returngoods.ReturnOrderInfo;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.returngoods.ReturnOrderInfoInspectionItem;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.returngoods.ReturnOrderInfoItem;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.returngoods.ReturnOrderInfoLog;
import com.aiqin.bms.scmp.api.purchase.domain.request.returngoods.*;
import com.aiqin.bms.scmp.api.purchase.domain.response.returngoods.*;
import com.aiqin.bms.scmp.api.purchase.mapper.*;
import com.aiqin.bms.scmp.api.purchase.service.ReturnGoodsService;
import com.aiqin.bms.scmp.api.util.BeanCopyUtils;
import com.aiqin.bms.scmp.api.util.Calculate;
import com.aiqin.bms.scmp.api.util.CollectionUtils;
import com.aiqin.ground.util.exception.GroundRuntimeException;
import com.aiqin.ground.util.http.HttpClient;
import com.aiqin.ground.util.json.JsonUtil;
import com.aiqin.ground.util.protocol.MessageId;
import com.aiqin.ground.util.protocol.Project;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Description:
 * @author: NullPointException
 * @date: 2019-06-13
 * @time: 17:35
 */
@Service
@Slf4j
public class ReturnGoodsServiceImpl extends BaseServiceImpl implements ReturnGoodsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReturnGoodsServiceImpl.class);

    @Autowired
    private ReturnOrderInfoMapper returnOrderInfoMapper;
    @Autowired
    private ReturnOrderInfoItemMapper returnOrderInfoItemMapper;
    @Autowired
    private UrlConfig urlConfig;
    @Autowired
    private InboundService inboundService;
    @Autowired
    private ReturnOrderInfoInspectionItemMapper returnOrderInfoInspectionItemMapper;
    @Autowired
    private ReturnOrderInfoLogMapper returnOrderInfoLogMapper;
    @Autowired
    private InboundDao inboundDao;
    @Autowired
    private InboundProductDao inboundProductDao;
    @Autowired
    private InboundBatchDao inboundBatchDao;
    @Autowired
    private OrderInfoItemProductBatchMapper orderInfoItemProductBatchMapper;

    @Override
    public HttpResponse<ReturnOrderDetailResponse> returnOrderDetail(String returnOrderCode) {
        LOGGER.info("查询退货单详情：", returnOrderCode);
        if(StringUtils.isBlank(returnOrderCode)){
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        ReturnOrderInfo returnOrderInfo = returnOrderInfoMapper.selectByCode(returnOrderCode);
        ReturnOrderDetailResponse response = BeanCopyUtils.copy(returnOrderInfo, ReturnOrderDetailResponse.class);
        // 查询退货单的日志信息
        List<ReturnOrderInfoLog> logs = returnOrderInfoLogMapper.returnOrderLog(returnOrderCode);
        response.setLogList(logs);
        // 查询入库单的基本信息
        List<Inbound> inbounds = inboundDao.inboundBySource(returnOrderCode, String.valueOf(InboundTypeEnum.ORDER.getCode()));
        response.setInboundList(inbounds);
        return HttpResponse.successGenerics(response);
    }

    @Override
    public HttpResponse inboundBatch(InboundBatchReqVo request){
        List<ReturnOrderInboundBatchResponse> list = returnOrderInfoInspectionItemMapper.inboundBatchByReturnOrderList(request);
        Integer count = returnOrderInfoInspectionItemMapper.inboundBatchByReturnOrderCount(request);
        return HttpResponse.successGenerics(new PageResData<>(count, list));
    }

    @Override
    public HttpResponse<PageResData<ReturnOrderInfo>> returnOrderList(ReturnGoodsRequest request) {
        List<ReturnOrderInfo> list = returnOrderInfoMapper.list(request);
        Integer count = returnOrderInfoMapper.listCount(request);
        return HttpResponse.successGenerics(new PageResData<>(count, list));
    }

    @Override
    public HttpResponse<PageResData<ReturnOrderInfoItem>> returnOrderProductList(ReturnGoodsRequest request){
        List<ReturnOrderInfoItem> list = returnOrderInfoItemMapper.list(request);
        Integer count = returnOrderInfoItemMapper.listCount(request);
        return HttpResponse.successGenerics(new PageResData<>(count, list));
    }

    @Override
    public HttpResponse<PageResData<ReturnOrderInfoInspectionItem>> returnOrderBatchList(ReturnGoodsRequest request){
        List<ReturnOrderInfoInspectionItem> list = returnOrderInfoInspectionItemMapper.list(request);
        Integer count = returnOrderInfoInspectionItemMapper.listCount(request);
        return HttpResponse.successGenerics(new PageResData<>(count, list));
    }

    @Override
    public HttpResponse returnOrderCancel(String returnOrderCode){
       if(StringUtils.isBlank(returnOrderCode)){
           return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
       }
       ReturnOrderInfo returnOrderInfo = new ReturnOrderInfo();
       returnOrderInfo.setUpdateByName(getUser().getPersonName());
       returnOrderInfo.setUpdateById(getUser().getPersonId());
       returnOrderInfo.setOrderStatus(ReturnOrderStatus.cancelled.getStatusCode());
       Integer count = returnOrderInfoMapper.update(returnOrderInfo);
       LOGGER.info("更改退货单异常终止：{}", count);

        // 添加日志
        ReturnOrderInfoLog log = new ReturnOrderInfoLog();
        log.setCompanyCode(Global.COMPANY_09);
        log.setCompanyName(Global.COMPANY_09_NAME);
        log.setOperator(getUser().getPersonName());
        log.setOrderCode(returnOrderCode);
        log.setRemark(ReturnOrderStatus.cancelled.getStandardDescription());
        log.setStatus(ReturnOrderStatus.cancelled.getStatusCode());
        log.setStatusDesc(ReturnOrderStatus.cancelled.getExplain());
        log.setContent(ReturnOrderStatus.cancelled.getBackgroundOrderStatus());
        Integer logCount = returnOrderInfoLogMapper.insert(log);
        LOGGER.info("添加退货单异常终止日志：{}", logCount);
       return HttpResponse.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public HttpResponse saveReturnInspection(ReturnInspectionRequest request) {
        if(request == null){
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        // 保存退货验货
        ReturnOrderInfo returnOrder = new ReturnOrderInfo();
        returnOrder.setReturnOrderCode(request.getReturnOrderCode());
        returnOrder.setInspectionRemark(request.getInspectionRemark());
        returnOrder.setUpdateById(getUser().getPersonId());
        returnOrder.setUpdateByName(getUser().getPersonName());
        Integer orderCount = returnOrderInfoMapper.update(returnOrder);
        LOGGER.info("更新退货单保存验货备注信息：", orderCount);

        if(CollectionUtils.isEmptyCollection(request.getItemList())){
            LOGGER.info("退货验货单的商品信息为空：{}", JsonUtil.toJson(request.getItemList()));
            return HttpResponse.failure(MessageId.create(Project.SCMP_API, 500, "退货验货单的商品信息为空"));
        }

        // 查询商品信息
        Map<String, ReturnOrderInfoItem> returnMap = new HashMap<>();
        String key;
        for(ReturnOrderInfoInspectionItem item : request.getItemList()) {
            key = String.format("%s,%s", item.getSkuCode(), item.getLineCode());
            if (returnMap.get(key) == null) {
                returnMap.put(key, returnOrderInfoItemMapper.returnOrderOne(request.getReturnOrderCode(), item.getSkuCode(), item.getLineCode()));
            }
        }

        // 查询最后商品的行号
        Long lineCode = returnOrderInfoItemMapper.returnOrderByLastLineCode(request.getReturnOrderCode());

        List<ReturnOrderInfoItem> itemList = Lists.newArrayList();
        ReturnOrderInfoItem returnOrderInfoItem;
        // 处理退货单的多库房数据问题
        for(ReturnOrderInfoInspectionItem item : request.getItemList()){
            // 根据sku和行号去重查询出多库房的数据
            List<ReturnOrderInfoInspectionItem> items = request.getItemList().stream().collect(Collectors.collectingAndThen(
                    Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(o -> o.getLineCode() + ";" + o.getSkuCode()))), ArrayList::new));
            if(CollectionUtils.isNotEmptyCollection(items) && items.size() > 1){
                for(int i=0;i<items.size();i++){
                    if(i==0){
                        continue;
                    }
                    ++lineCode;
                    if(item.getSkuCode().equals(items.get(i).getSkuCode()) && item.getWarehouseCode().equals(items.get(i).getWarehouseCode())){
                        item.setLineCode(lineCode);
                    }
                    // 获取商品信息
                    key = String.format("%s,%s", item.getSkuCode(), item.getLineCode());
                    returnOrderInfoItem = BeanCopyUtils.copy(returnMap.get(key), ReturnOrderInfoItem.class);
                    returnOrderInfoItem.setProductLineNum(lineCode);
                    itemList.add(returnOrderInfoItem);
                }
            }
        }

        // 添加验货之后根据库房新增的商品
        Integer detailCount = returnOrderInfoItemMapper.insertList(itemList);
        LOGGER.info("验货之后根据库房新增的商品条数：", detailCount);

        Integer batchCount = returnOrderInfoInspectionItemMapper.insertBatch(request.getItemList());
        LOGGER.info("保存退货单验货商品信息：", batchCount);

        // 调用生成入库单 并传送wms
        List<InboundReqSave> inbounds = getInboundReqSave(request.getReturnOrderCode());
        if(CollectionUtils.isNotEmptyCollection(inbounds)){
            for(InboundReqSave inbound : inbounds){
                inboundService.saveInbound(inbound);
            }
        }

        // 添加日志
        ReturnOrderInfoLog log = new ReturnOrderInfoLog();
        log.setCompanyCode(Global.COMPANY_09);
        log.setCompanyName(Global.COMPANY_09_NAME);
        log.setOperator(getUser().getPersonName());
        log.setOrderCode(request.getReturnOrderCode());
        log.setRemark(ReturnOrderStatus.WAITING_FOR_RETURN_TO_THE_WAREHOUSE.getStandardDescription());
        log.setStatus(ReturnOrderStatus.WAITING_FOR_RETURN_TO_THE_WAREHOUSE.getStatusCode());
        log.setStatusDesc(ReturnOrderStatus.WAITING_FOR_RETURN_TO_THE_WAREHOUSE.getExplain());
        log.setContent(ReturnOrderStatus.WAITING_FOR_RETURN_TO_THE_WAREHOUSE.getBackgroundOrderStatus());
        Integer logCount = returnOrderInfoLogMapper.insert(log);
        LOGGER.info("添加退货验货日志：{}", logCount);
        return HttpResponse.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public HttpResponse returnReceipt(List<ReturnOrderInfoItem> itemList) {
        if(CollectionUtils.isEmptyCollection(itemList)){
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        ReturnOrderInfoItem returnOrderInfoItem;
        Long actualProductCount = 0L;
        BigDecimal actualTotalProductAmount = BigDecimal.ZERO, actualTotalChannelAmount = BigDecimal.ZERO;
        for(ReturnOrderInfoItem item : itemList){
            returnOrderInfoItem = new ReturnOrderInfoItem();
            returnOrderInfoItem.setId(item.getId());
            returnOrderInfoItem.setActualPrice(item.getPrice());
            returnOrderInfoItem.setActualChannelUnitPrice(item.getChannelUnitPrice());
            Integer count = returnOrderInfoItemMapper.update(returnOrderInfoItem);
            LOGGER.info("直送退货单实退的商品数量：{}", count);

            actualProductCount += item.getActualInboundNum();
            actualTotalProductAmount = actualTotalProductAmount.add(item.getActualAmount());
            actualTotalChannelAmount = actualTotalChannelAmount.add(item.getActualTotalChannelPrice());
        }

        ReturnOrderInfo returnOrderInfo = new ReturnOrderInfo();
        returnOrderInfo.setActualVolume(0L);
        returnOrderInfo.setActualWeight(0L);
        // 计算实际体积/重量
        if(returnOrderInfo.getVolume() != null && returnOrderInfo.getVolume() > 0 && actualProductCount > 0){
            returnOrderInfo.setActualVolume(actualProductCount / returnOrderInfo.getVolume());
        }
        if(returnOrderInfo.getWeight() != null && returnOrderInfo.getWeight() > 0 && actualProductCount > 0){
            returnOrderInfo.setActualWeight(actualProductCount / returnOrderInfo.getWeight());
        }
        returnOrderInfo.setActualProductCount(actualProductCount);
        returnOrderInfo.setActualProductTotalAmount(actualTotalProductAmount);
        returnOrderInfo.setActualProductChannelTotalAmount(actualTotalChannelAmount);
        returnOrderInfo.setDeliveryTime(Calendar.getInstance().getTime());
        returnOrderInfo.setReturnOrderCode(itemList.get(0).getReturnOrderCode());
        returnOrderInfo.setUpdateById(getUser().getPersonId());
        returnOrderInfo.setUpdateByName(getUser().getPersonName());
        returnOrderInfo.setOrderStatus(ReturnOrderStatus.RETURN_COMPLETED.getStatusCode());
        Integer returnCount = returnOrderInfoMapper.update(returnOrderInfo);
        LOGGER.info("退货单退货收货完成变更退货单状态：{}", returnCount);

        // 添加日志
        ReturnOrderInfoLog log = new ReturnOrderInfoLog();
        log.setCompanyCode(Global.COMPANY_09);
        log.setCompanyName(Global.COMPANY_09_NAME);
        log.setOperator(getUser().getPersonName());
        log.setOrderCode(itemList.get(0).getReturnOrderCode());
        log.setRemark(ReturnOrderStatus.RETURN_COMPLETED.getStandardDescription());
        log.setStatus(ReturnOrderStatus.RETURN_COMPLETED.getStatusCode());
        log.setStatusDesc(ReturnOrderStatus.RETURN_COMPLETED.getExplain());
        log.setContent(ReturnOrderStatus.RETURN_COMPLETED.getBackgroundOrderStatus());
        Integer count = returnOrderInfoLogMapper.insert(log);
        LOGGER.info("添加退货单退货收货的日志：{}", count);

        // 退货收货完成- 直送订单 回传运营中台
        changeParameter(itemList.get(0).getReturnOrderCode());
        return HttpResponse.success();
    }

    @Override
    public HttpResponse<PageResData<ReturnOrderInspectionResponse>> inspectionBatch(ReturnGoodsRequest request){
        List<ReturnOrderInfoItem> list = returnOrderInfoItemMapper.list(request);
        List<ReturnOrderInspectionResponse> responses = BeanCopyUtils.copyList(list, ReturnOrderInspectionResponse.class);
        if(CollectionUtils.isNotEmptyCollection(responses)){
            for(ReturnOrderInspectionResponse response:responses){
                if(StringUtils.isBlank(response.getSkuCode()) || response.getProductLineNum() == null){
                    return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
                }
                List<OrderInfoItemProductBatch> batches = orderInfoItemProductBatchMapper.orderBatchList(
                        response.getSkuCode(), response.getReturnOrderCode(), response.getProductLineNum().intValue());
                response.setBatchList(batches);
            }
        }
        Integer count = returnOrderInfoItemMapper.listCount(request);
        return HttpResponse.successGenerics(new PageResData<>(count, responses));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public HttpResponse record(ReturnReq request) {
        LOGGER.info("运营中台调用耘链，开始生成退货单：{}", JsonUtil.toJson(request));
        // 进行主表添加
        if (request == null || request.getReturnOrderInfo() == null ||
                CollectionUtils.isEmptyCollection(request.getReturnOrderDetailReqList())) {
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        ReturnOrderInfoReq returnOrderInfo = request.getReturnOrderInfo();
        ReturnOrderInfo returnOrder = BeanCopyUtils.copy(request.getReturnOrderInfo(), ReturnOrderInfo.class);
        returnOrder.setOrderCode(returnOrderInfo.getOrderStoreCode());
        returnOrder.setCreateDate(returnOrderInfo.getCreateTime());
        returnOrder.setBeLock(returnOrderInfo.getReturnLock());
        returnOrder.setLockReason(returnOrderInfo.getReturnReason());
        returnOrder.setDetailAddress(returnOrderInfo.getReceiveAddress());
        returnOrder.setConsignee(returnOrderInfo.getReceivePerson());
        returnOrder.setConsigneePhone(returnOrderInfo.getReceivePerson());
        returnOrder.setDistributionMode(returnOrderInfo.getDistributionModeName());
        // 退货单状态
        if (returnOrderInfo.getOrderType().equals(Global.ORDER_TYPE_0)) {
            returnOrder.setOrderStatus(ReturnOrderStatus.WAITING_FOR_RETURN_TO_THE_WAREHOUSE.getStatusCode());
        } else if (returnOrderInfo.getOrderType().equals(Global.ORDER_TYPE_1)) {
            returnOrder.setOrderStatus(ReturnOrderStatus.WAITING_FOR_RETURN_TO_INSPECTION.getStatusCode());
        }
        returnOrder.setPaymentTypeCode(returnOrderInfo.getPaymentCode());
        returnOrder.setPaymentType(returnOrderInfo.getPaymentName());
        returnOrder.setProductCount(returnOrderInfo.getProductCount());
        returnOrder.setProductTotalAmount(returnOrderInfo.getReturnOrderAmount());
        returnOrder.setWeight(returnOrderInfo.getTotalWeight());
        returnOrder.setVolume(returnOrderInfo.getTotalVolume());
        returnOrder.setCompanyCode(Global.COMPANY_09);
        returnOrder.setCompanyName(Global.COMPANY_09_NAME);
        Integer count = returnOrderInfoMapper.insert(returnOrder);
        LOGGER.info("添加退货单条数：", count);

        List<ReturnOrderDetailReq> detailList = request.getReturnOrderDetailReqList();
        List<ReturnOrderInfoItem> details = Lists.newArrayList();
        ReturnOrderInfoItem returnOrderInfoItem;
        for (ReturnOrderDetailReq returnOrderDetail : detailList) {
            returnOrderInfoItem = BeanCopyUtils.copy(returnOrderDetail, ReturnOrderInfoItem.class);
            returnOrderInfoItem.setSpec(returnOrderDetail.getProductSpec());
            returnOrderInfoItem.setModel(returnOrderDetail.getModelCode());
            returnOrderInfoItem.setBaseProductContent(returnOrderDetail.getBaseProductSpec().intValue());
            returnOrderInfoItem.setGivePromotion(returnOrderDetail.getProductType());
            returnOrderInfoItem.setPrice(returnOrderDetail.getProductAmount());
            returnOrderInfoItem.setNum(returnOrderDetail.getReturnProductCount());
            returnOrderInfoItem.setAmount(returnOrderDetail.getTotalProductAmount());
            returnOrderInfoItem.setProductLineNum(returnOrderDetail.getLineCode());
            returnOrderInfoItem.setProductStatus(returnOrderDetail.getProductStatus());
            returnOrderInfoItem.setCompanyCode(Global.COMPANY_09);
            returnOrderInfoItem.setCompanyName(Global.COMPANY_09_NAME);
            returnOrderInfoItem.setChannelUnitPrice(returnOrderDetail.getProductAmount());
            returnOrderInfoItem.setTax(returnOrderDetail.getTaxRate());
            details.add(returnOrderInfoItem);
        }
        Integer detailCount = returnOrderInfoItemMapper.insertList(details);
        LOGGER.info("添加退货单详情条数：", detailCount);

        // 添加退货单日志
        ReturnOrderInfoLog log = new ReturnOrderInfoLog();
        log.setOrderCode(returnOrder.getReturnOrderCode());
        log.setStatus(Integer.valueOf(InOutStatus.CREATE_INOUT.getCode()));
        log.setStatusDesc(InOutStatus.CREATE_INOUT.getName());
        log.setRemark(returnOrder.getRemake());
        log.setOperator(returnOrder.getCreateByName());
        log.setOperatorTime(returnOrder.getCreateTime());
        log.setCompanyCode(Global.COMPANY_09);
        log.setCompanyName(Global.COMPANY_09_NAME);
        Integer logCount = returnOrderInfoLogMapper.insert(log);
        LOGGER.info("添加退货单日志条数：", logCount);
        return HttpResponse.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public HttpResponse changeParameter(String returnOrderCode) {
        if(StringUtils.isBlank(returnOrderCode)){
          return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        LOGGER.info("开始转换退货单参数传送运营中台：{}", returnOrderCode);
        // 查询退货单信息
        ReturnOrderInfo returnOrderInfo = returnOrderInfoMapper.selectByCode(returnOrderCode);
        if(returnOrderInfo == null){
            return HttpResponse.failure(MessageId.create(Project.SCMP_API, 500, "退货单的信息为空"));
        }
        // 查询退货单商品信息
        List<ReturnOrderInfoItem> infoItems = returnOrderInfoItemMapper.selectByReturnOrderCode(returnOrderCode);
        if(CollectionUtils.isEmptyCollection(infoItems)){
            return HttpResponse.failure(MessageId.create(Project.SCMP_API, 500, "退货单的商品信息为空"));
        }

        // 赋值传送运营中台的参数
        ReturnDLReq response = new ReturnDLReq();
        ReturnOrderInfoDLReq orderInfo = new ReturnOrderInfoDLReq();
        orderInfo.setReturnOrderCode(returnOrderCode);
        orderInfo.setActualProductCount(returnOrderInfo.getActualProductCount());
        orderInfo.setReturnById(returnOrderInfo.getUpdateById());
        orderInfo.setReturnTime(returnOrderInfo.getUpdateTime());
        response.setReturnOrderInfoDLReq(orderInfo);

        List<ReturnOrderDetailDLReq> orderItems = Lists.newArrayList();
        ReturnOrderDetailDLReq returnOrderItem;
        for(ReturnOrderInfoItem item : infoItems){
            returnOrderItem = new ReturnOrderDetailDLReq();
            returnOrderItem.setActualReturnProductCount(item.getActualInboundNum().longValue());
            returnOrderItem.setLineCode(item.getProductLineNum());
            returnOrderItem.setSkuCode(item.getSkuCode());
            returnOrderItem.setSkuName(item.getSkuName());
            orderItems.add(returnOrderItem);
        }
        response.setReturnOrderDetailDLReqList(orderItems);

        // 查询退货单的批次信息
        List<ReturnOrderInfoInspectionItem> inspectionItems = returnOrderInfoInspectionItemMapper.returnOrderBatchList(returnOrderCode);
        if(CollectionUtils.isNotEmptyCollection(inspectionItems) && inspectionItems.size() > 0){
            List<ReturnBatchDetailDLReq> batchList = Lists.newArrayList();
            ReturnBatchDetailDLReq batchInfo;
            for(ReturnOrderInfoInspectionItem batch : inspectionItems){
                batchInfo = new ReturnBatchDetailDLReq();
                batchInfo.setSkuCode(batch.getSkuCode());
                batchInfo.setSkuName(batch.getSkuName());
                batchInfo.setLineCode(batch.getLineCode());
                batchInfo.setBatchNum(batch.getProductCount().intValue());
                batchInfo.setActualReturnProductCount(batch.getActualProductCount());
                batchList.add(batchInfo);
            }
            response.setReturnBatchDetailDLReqList(batchList);
        }
        LOGGER.info("退货单调用运营中台参数：{}", JsonUtil.toJson(response));

        StringBuilder sb = new StringBuilder();
        sb.append(urlConfig.Order_URL).append("/reject/info");
        HttpClient httpClient = HttpClient.post(String.valueOf(sb)).json(response).timeout(10000);
        HttpResponse<Boolean> httpResponse = httpClient.action().result(new TypeReference<HttpResponse<Boolean>>() {
        });
        if(httpResponse.getCode().equals(MessageId.SUCCESS_CODE)){
            LOGGER.info("退货单回传运营中台成功");
            return HttpResponse.success();
        }else {
            LOGGER.info("退货单回传运营中台调用失败", httpResponse.getMessage());
            return HttpResponse.failure(MessageId.create(Project.SCMP_API, 500, "退货单回传运营中台调用失败"));
        }
    }

    @Override
    public HttpResponse recordWMS(String inboundOderCode) {
        // 查询入库单的信息
        Inbound inbound = inboundDao.selectByCode(inboundOderCode);
        if(inbound == null){
            LOGGER.info("退货单wms回传入库单的信息为空：{}", JsonUtil.toJson(inbound));
            return HttpResponse.failure(ResultCode.INBOUND_INFO_NULL);
        }
        ReturnOrderInfo returnOrder = new ReturnOrderInfo();
        returnOrder.setActualProductCount(inbound.getPraMainUnitNum());
        returnOrder.setOrderStatus(ReturnOrderStatus.RETURN_COMPLETED.getStatusCode());
        returnOrder.setUpdateByName(inbound.getUpdateBy());

        // 查询入库单的商品信息
        BigDecimal actualChannelAmount = BigDecimal.ZERO, actualProductAmount = BigDecimal.ZERO;
        ReturnOrderInfoItem returnOrderInfoItem;
        List<InboundProduct> inboundProducts = inboundProductDao.selectByInboundOderCode(inboundOderCode);

        for (InboundProduct product : inboundProducts) {
            // 查询对应的退货单商品信息
            returnOrderInfoItem = returnOrderInfoItemMapper.returnOrderOne(inbound.getSourceOderCode(), product.getSkuCode(), product.getLinenum());
            returnOrderInfoItem.setActualInboundNum(product.getPraInboundMainNum().intValue());
            returnOrderInfoItem.setActualChannelUnitPrice(returnOrderInfoItem.getChannelUnitPrice());
            BigDecimal count = BigDecimal.valueOf(product.getPraInboundMainNum());
            returnOrderInfoItem.setActualTotalChannelPrice(count.multiply(returnOrderInfoItem.getChannelUnitPrice()).setScale(4, BigDecimal.ROUND_HALF_UP));
            returnOrderInfoItem.setActualAmount(returnOrderInfoItem.getPrice());
            returnOrderInfoItem.setActualPrice(count.multiply(returnOrderInfoItem.getPrice()).setScale(4, BigDecimal.ROUND_HALF_UP));
            Integer returnInfoProduct = returnOrderInfoItemMapper.update(returnOrderInfoItem);
            log.info("更新退货单商品:", returnInfoProduct);

            actualChannelAmount.add(returnOrderInfoItem.getActualTotalChannelPrice());
            actualProductAmount.add(returnOrderInfoItem.getActualPrice());
        }

        returnOrder.setActualProductChannelTotalAmount(actualChannelAmount);
        returnOrder.setActualProductTotalAmount(actualProductAmount);
        returnOrder.setActualVolume(0L);
        returnOrder.setActualWeight(0L);
        // 计算实际体积/重量
        if(returnOrder.getVolume() > 0 && returnOrder.getActualProductCount() > 0){
            returnOrder.setActualVolume(returnOrder.getActualProductCount() / returnOrder.getVolume());
        }
        if(returnOrder.getWeight() > 0 && returnOrder.getActualProductCount() > 0){
            returnOrder.setActualWeight(returnOrder.getActualProductCount() / returnOrder.getWeight());
        }
        // 查询入库单的批次信息
        List<InboundBatch> inboundBatches = inboundBatchDao.selectInboundBatchList(inboundOderCode);
        List<ReturnOrderInfoInspectionItem> batchList = Lists.newArrayList();
        ReturnOrderInfoInspectionItem returnBatchItem;
        ReturnOrderInfoInspectionItem returnBatch;
        for (InboundBatch batch : inboundBatches) {
            // 根据批次号、sku、行号查询对应的批次
            returnBatchItem = returnOrderInfoInspectionItemMapper.returnOrderInfo(batch.getBatchInfoCode(),
                    inbound.getSourceOderCode(), batch.getLineCode());
            returnBatchItem.setActualProductCount(batch.getActualTotalCount());

            if(returnBatchItem == null){
                // 根据sku  行号查询对应的批次
                returnBatchItem = returnOrderInfoInspectionItemMapper.returnOrderInfo(batch.getBatchInfoCode(),
                        inbound.getSourceOderCode(), null);
                returnBatchItem.setActualProductCount(batch.getActualTotalCount() + returnBatchItem.getActualProductCount());
                if(returnBatchItem == null){
                    returnBatch = new ReturnOrderInfoInspectionItem();
                    returnBatch.setReturnOrderCode(inbound.getSourceOderCode());
                    returnBatch.setSkuCode(batch.getSkuCode());
                    returnBatch.setSkuName(batch.getSkuName());
                    returnBatch.setLineCode(batch.getLineCode().longValue());
                    returnBatch.setReturnProductCount(batch.getTotalCount());
                    returnBatch.setActualProductCount(batch.getActualTotalCount());
                    returnBatch.setBatchInfoCode(batch.getBatchInfoCode());
                    returnBatch.setBatchCode(batch.getBatchCode());
                    returnBatch.setBeOverdueDate(batch.getBeOverdueDate());
                    returnBatch.setProductDate(batch.getProductDate());
                    returnBatch.setBatchRemark(batch.getBatchRemark());
                    batchList.add(returnBatch);
                }
            }

           Integer i = returnOrderInfoInspectionItemMapper.update(returnBatchItem);
            LOGGER.info("更新退货单批次：", i);
        }
        Integer count = returnOrderInfoInspectionItemMapper.insertBatch(batchList);
        LOGGER.info("添加退货单批次：", count);

        Integer returnInfo = returnOrderInfoMapper.update(returnOrder);
        log.info("更新退货单主信息：", returnInfo);
        return HttpResponse.success();
    }

    private List<InboundReqSave> getInboundReqSave(String returnOrderCode) {
        LOGGER.info("根据运营中台退货单，开始生成耘链入库单：{}", returnOrderCode);
        List<InboundReqSave> inbounds = Lists.newArrayList();
        // 查询退货单的信息
        ReturnOrderInfo returnOrderInfo = returnOrderInfoMapper.selectByCode(returnOrderCode);
        InboundReqSave inbound = BeanCopyUtils.copy(returnOrderInfo, InboundReqSave.class);
        inbound.setCompanyCode(Global.COMPANY_09);
        inbound.setCompanyName(Global.COMPANY_09_NAME);
        inbound.setInboundStatusCode(InOutStatus.CREATE_INOUT.getCode());
        inbound.setInboundStatusName(InOutStatus.CREATE_INOUT.getName());
        inbound.setInboundTypeCode(InboundTypeEnum.ORDER.getCode());
        inbound.setInboundTypeName(InboundTypeEnum.ORDER.getName());
        inbound.setSourceOderCode(returnOrderInfo.getReturnOrderCode());
        inbound.setLogisticsCenterCode(returnOrderInfo.getTransportCenterCode());
        inbound.setLogisticsCenterName(returnOrderInfo.getTransportCenterName());
        inbound.setPreInboundNum(returnOrderInfo.getProductCount());
        inbound.setPreMainUnitNum(returnOrderInfo.getProductCount());
        inbound.setPreTaxAmount(returnOrderInfo.getReturnOrderAmount());
        inbound.setCountyCode(returnOrderInfo.getDistrictCode());
        inbound.setCountyName(returnOrderInfo.getDistrictName());
        inbound.setCreateBy(getUser().getPersonName());
        inbound.setUpdateBy(getUser().getPersonName());

        // 查询应有的库房信息
        List<ReturnOrderInfoInspectionItem> warehouses = returnOrderInfoInspectionItemMapper.returnOrderByWarehouse(returnOrderCode);
        if(CollectionUtils.isEmptyCollection(warehouses)){
            LOGGER.info("退货单查询商品库房的信息为空：{}", JsonUtil.toJson(warehouses));
            throw new GroundRuntimeException(String.format("退货单查询商品库房的信息为空"));
        }
        for(ReturnOrderInfoInspectionItem warehouse : warehouses){
            inbound.setWarehouseCode(warehouse.getWarehouseCode());
            inbound.setWarehouseName(warehouse.getWarehouseName());

            // 查询退货单批次信息
            List<ReturnOrderInfoInspectionItem> items =
                    returnOrderInfoInspectionItemMapper.returnOrderBatchListByWarehouse(returnOrderCode, warehouse.getWarehouseCode());
            Map<String, ReturnOrderInfoItem> map = new HashMap<>();
            if(CollectionUtils.isNotEmptyCollection(items)){
                InboundBatch inboundBatch;
                List<InboundBatch> batchList = Lists.newArrayList();

                // 查询对应库房商品信息
                for (ReturnOrderInfoInspectionItem item : items){
                    String key = String.format("%s,%s,%s", item.getSkuCode(), item.getLineCode(),item.getReturnOrderCode());
                    if(map.get(key) == null){
                        map.put(key, returnOrderInfoItemMapper.returnOrderOne(item.getReturnOrderCode(), item.getSkuCode(), item.getLineCode()));
                    }
                }

                for (ReturnOrderInfoInspectionItem item : items){
                    inboundBatch = BeanCopyUtils.copy(item, InboundBatch.class);
                    inboundBatch.setInboundOderCode(inbound.getInboundOderCode());
                    inboundBatch.setTotalCount(item.getProductCount());
                    inboundBatch.setActualTotalCount(item.getActualProductCount());
                    inboundBatch.setCreateById(returnOrderInfo.getUpdateById());
                    inboundBatch.setCreateByName(returnOrderInfo.getUpdateByName());
                    inboundBatch.setUpdateById(returnOrderInfo.getUpdateById());
                    inboundBatch.setUpdateByName(returnOrderInfo.getUpdateByName());
                    batchList.add(inboundBatch);
                }
                inbound.setInboundBatchList(batchList);
            }

            List<ReturnOrderInfoItem> itemList = map.values().stream().collect(Collectors.toList());
            List<InboundProductReqVo> list = Lists.newArrayList();
            InboundProductReqVo inboundProductReqVo;
            BigDecimal preAmount = BigDecimal.ZERO;
            Long productCount =0L;
            // 查询退货单商品信息
            for (ReturnOrderInfoItem detail : itemList) {
                inboundProductReqVo = BeanCopyUtils.copy(detail, InboundProductReqVo.class);
                inboundProductReqVo.setInboundOderCode(inbound.getInboundOderCode());
                inboundProductReqVo.setNorms(detail.getSpec());
                inboundProductReqVo.setModel(detail.getModelCode());
                inboundProductReqVo.setInboundNorms(detail.getSpec());
                inboundProductReqVo.setInboundBaseUnit(String.valueOf(detail.getZeroDisassemblyCoefficient()));
                inboundProductReqVo.setPreInboundNum(detail.getNum());
                inboundProductReqVo.setPreInboundMainNum(detail.getNum());
                inboundProductReqVo.setPreTaxPurchaseAmount(detail.getPrice());
                inboundProductReqVo.setPreTaxAmount(detail.getAmount());
                inboundProductReqVo.setCreateBy(getUser().getPersonName());
                inboundProductReqVo.setUpdateBy(getUser().getPersonName());
                list.add(inboundProductReqVo);
                // 计算预计无税金额、税额
                BigDecimal noTax = Calculate.computeNoTaxPrice(detail.getAmount(), detail.getTax());
                preAmount = preAmount.add(noTax);
                productCount += detail.getNum();
            }
            inbound.setList(list);
            inbound.setPreMainUnitNum(productCount);
            inbound.setPraAmount(preAmount);
            inbound.setPreTax(inbound.getPreTaxAmount().subtract(preAmount));
            LOGGER.info("根据运营中台退货单，转换生成耘链入库单参数：{}", JsonUtil.toJson(inbound));
            inbounds.add(inbound);
        }
        LOGGER.info("退货单根据库房生成多个入库单：{}", JsonUtil.toJson(inbounds));
        return inbounds;
    }
}
