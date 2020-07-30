package com.aiqin.bms.scmp.api.purchase.service.impl;


import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.base.UrlConfig;
import com.aiqin.bms.scmp.api.common.BizException;
import com.aiqin.bms.scmp.api.config.AuthenticationInterceptor;
import com.aiqin.bms.scmp.api.constant.Global;
import com.aiqin.bms.scmp.api.product.domain.E8OrderCreate;
import com.aiqin.bms.scmp.api.product.domain.JdB2cDespatchTarget;
import com.aiqin.bms.scmp.api.product.domain.request.outbound.DeliveryCallBackRequest;
import com.aiqin.bms.scmp.api.product.domain.request.outbound.DeliveryDetailRequest;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.order.OrderInfo;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.transport.Transport;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.transport.TransportLog;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.transport.TransportOrders;
import com.aiqin.bms.scmp.api.purchase.domain.request.transport.TransportAddRequest;
import com.aiqin.bms.scmp.api.purchase.domain.request.transport.TransportLogsRequest;
import com.aiqin.bms.scmp.api.purchase.domain.request.transport.TransportOrdersResquest;
import com.aiqin.bms.scmp.api.purchase.domain.request.transport.TransportRequest;
import com.aiqin.bms.scmp.api.purchase.domain.response.order.QueryOrderInfoRespVO;
import com.aiqin.bms.scmp.api.purchase.mapper.OrderInfoMapper;
import com.aiqin.bms.scmp.api.purchase.mapper.TransportLogMapper;
import com.aiqin.bms.scmp.api.purchase.mapper.TransportMapper;
import com.aiqin.bms.scmp.api.purchase.mapper.TransportOrdersMapper;
import com.aiqin.bms.scmp.api.purchase.service.OrderCallbackService;
import com.aiqin.bms.scmp.api.purchase.service.OrderService;
import com.aiqin.bms.scmp.api.purchase.service.TransportService;
import com.aiqin.bms.scmp.api.supplier.dao.warehouse.WarehouseDao;
import com.aiqin.bms.scmp.api.supplier.domain.request.warehouse.dto.WarehouseDTO;
import com.aiqin.bms.scmp.api.util.*;
import com.aiqin.ground.util.json.JsonUtil;
import com.aiqin.ground.util.protocol.MessageId;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.aiqin.ground.util.http.HttpClient;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TransportServiceImpl implements TransportService {
    @Autowired
    private TransportMapper transportMapper;
    @Autowired
    private TransportOrdersMapper transportOrdersMapper;
    @Autowired
    private TransportLogMapper transportLogMapper;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderCallbackService orderCallbackService;
    @Autowired
    private WarehouseDao warehouseDao;
    @Autowired
    private OrderInfoMapper orderInfoMapper;
    @Autowired
    private UrlConfig urlConfig;

    @Override
    public BasePage<Transport> selectPage(TransportRequest transportRequest) {
        PageHelper.startPage(transportRequest.getPageNo(), transportRequest.getPageSize());
        List<Transport> page = transportMapper.selectList(transportRequest);
        return PageUtil.getPageList(transportRequest.getPageNo(),page);
    }

    @Override
    public BasePage<TransportOrders> selectTransportOrdersPage(TransportOrdersResquest transportOrdersResquest) {
        PageHelper.startPage(transportOrdersResquest.getPageNo(), transportOrdersResquest.getPageSize());
        List<TransportOrders> page = transportMapper.selectTransportOrdersWithOutCodeList(transportOrdersResquest);
        return PageUtil.getPageList(transportOrdersResquest.getPageNo(),page);
    }

    @Override
    public BasePage<TransportOrders> selectDeliverOrdersPage(TransportOrdersResquest transportOrdersResquest) {
        PageHelper.startPage(transportOrdersResquest.getPageNo(), transportOrdersResquest.getPageSize());
        List<TransportOrders> page = transportMapper.selectDeliverOrdersWithOutCodeList(transportOrdersResquest);
        return PageUtil.getPageList(transportOrdersResquest.getPageNo(),page);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public HttpResponse saveTransport(TransportAddRequest transportAddRequest) {
        AuthToken currentAuthToken = AuthenticationInterceptor.getCurrentAuthToken();
        Transport transport=new Transport();
        //获取一个运输单号
        String code = IdSequenceUtils.getInstance().nextId()+"";
        BeanCopyUtils.copy(transportAddRequest,transport);
        transport.setAdditionalLogisticsFee(transport.getAdditionalLogisticsFee() == null ? BigDecimal.ZERO : transport.getAdditionalLogisticsFee());
        transport.setStandardLogisticsFee(transport.getStandardLogisticsFee() == null ? BigDecimal.ZERO : transport.getStandardLogisticsFee());
        transport.setLogisticsFee(transport.getAdditionalLogisticsFee().add(transport.getStandardLogisticsFee()));
        transport.setTransportCode(code);
        if(currentAuthToken != null){
            transport.setCreateBy(currentAuthToken.getPersonName());
            transport.setUpdateBy(currentAuthToken.getPersonName());
        }
        List<TransportOrders> transportOrders = BeanCopyUtils.copyList(transportAddRequest.getOrdersList(), TransportOrders.class);
        Long transportAmount = 0L;
        Long orderCommodityNum = 0L;
        for (TransportOrders  transportOrder: transportOrders) {
            transportOrder.setTransportCode(code);
            if(currentAuthToken != null){
                transportOrder.setCreateBy(currentAuthToken.getPersonName());
                transportOrder.setUpdateBy(currentAuthToken.getPersonName());
            }
            transportAmount += transportOrder.getOrderAmount().longValue();
            orderCommodityNum += transportOrder.getProductNum();
            transport.setTransportCenterCode(transportOrder.getTransportCenterCode());
//            orderCommodityNum+=transportOrder.getProductNum();
        }
        //查询一次收货信息设置值
        OrderInfo orderInfo= orderService.selectByOrderCode(transportOrders.get(0).getOrderCode());
        transport.setConsigneeName(orderInfo.getConsignee());
        transport.setCustomerCode(orderInfo.getCustomerCode());
        transport.setCustomerName(orderInfo.getCustomerName());
        transport.setConsigneePhone(orderInfo.getConsigneePhone());
        transport.setDetailedAddress(orderInfo.getDetailAddress());
        transport.setStatus(1);//设置未发运状态
        transport.setTransportAmount(new BigDecimal(transportAmount).add(transport.getLogisticsFee()));
        transport.setOrderCommodityNum(orderCommodityNum);
        transport.setZip(orderInfo.getZipCode());
        transportMapper.insertOne(transport);
        transportOrdersMapper.insertBatch(transportOrders);
        //写入发运单创建日志
        TransportLog transportLog=new TransportLog();
        transportLog.setTransportCode(code);
        transportLog.setType("新增");
        transportLog.setContent("新增发运单");
        transportLog.setRemark(transport.getRemark());
        if(currentAuthToken != null){
            transportLog.setCreateBy(currentAuthToken.getPersonName());
            transportLog.setUpdateBy(currentAuthToken.getPersonName());
        }
        transportLogMapper.insertOne(transportLog);

        // 传发运参数
        DeliveryCallBackRequest request = new DeliveryCallBackRequest();
        request.setDeliveryCode(transport.getTransportCode());
        request.setCustomerCode(transport.getCustomerCode());
        request.setCustomerName(transport.getCustomerName());
        request.setTransportDate(transport.getTransportTime());
//            request.setTransportPerson(); // 发运人
        request.setTransportAmount(transport.getTransportAmount());
        request.setStandardLogisticsFee(transport.getStandardLogisticsFee());
        request.setAdditionalLogisticsFee(transport.getAdditionalLogisticsFee());
        request.setTransportCode(transport.getLogisticsNumber());
        request.setTransportCompanyCode(transport.getLogisticsCompany());
        request.setTransportCompanyName(transport.getLogisticsCompanyName());
        request.setTransportCenterCode(transport.getTransportCenterCode());
        request.setTransportCenterName(transport.getTransportCenterName());
        request.setDeliverTo(transport.getDeliverTo());
        request.setPackingNum(transport.getPackingNum());
        request.setOrderCommodityNum(transport.getOrderCommodityNum());
        request.setTotalVolume(transport.getTotalVolume());
        request.setTotalWeight(transport.getTotalWeight());
        List<DeliveryDetailRequest> detailList = new ArrayList<>();
        List<TransportOrders> transportOrderLists = transportOrdersMapper.selectOrderCodeByTransportCode(transport.getTransportCode());
        for (TransportOrders t : transportOrderLists) {
            DeliveryDetailRequest detail = new DeliveryDetailRequest();
            detail.setOrderCode(t.getOrderCode());
            detail.setTransportAmount(t.getOrderAmount());
            detailList.add(detail);
        }
        request.setDetailList(detailList);
        // 推送wms 发运信息
        log.info("耘链发运单推送wms发运信息，参数信息：[{}]", JsonUtil.toJson(request));
        if(transportAddRequest.getFlag() != null && transportAddRequest.getFlag().equals(1)){
            transportWmsPushJD(request);
        }else {
            transportWmsPush(request);
        }
        return HttpResponse.success();
    }

    public HttpResponse transportWmsPushJD(DeliveryCallBackRequest request) {
        // 查询 订单库房
        TransportOrders transportOrders = transportOrdersMapper.selectTransportOrdersByTransportCode(request.getDeliveryCode());
        // 通过库房编码获取对应信息
        WarehouseDTO warehouse = warehouseDao.getWarehouseByCode(transportOrders.getWarehouseCode());
        // 查询订单信息
        QueryOrderInfoRespVO queryOrderInfoRespVO = orderInfoMapper.selectByOrderCode(transportOrders.getOrderCode());
        // 参数赋值
        JdB2cDespatchTarget jdB2cDespatchTarget = new JdB2cDespatchTarget();
        jdB2cDespatchTarget.setSoType(queryOrderInfoRespVO.getOrderProductType());
        jdB2cDespatchTarget.setWarehouseCode(warehouse.getWarehouseCode());
        jdB2cDespatchTarget.setOrderId(queryOrderInfoRespVO.getOrderCode());
        jdB2cDespatchTarget.setSenderName(warehouse.getContact());
        jdB2cDespatchTarget.setSenderAddress(warehouse.getDetailedAddress());
        jdB2cDespatchTarget.setSenderMobile(warehouse.getPhone());
        jdB2cDespatchTarget.setReceiveName(queryOrderInfoRespVO.getConsignee());
        jdB2cDespatchTarget.setReceiveAddress(queryOrderInfoRespVO.getDetailAddress());
        jdB2cDespatchTarget.setProvince(queryOrderInfoRespVO.getProvinceName());
        jdB2cDespatchTarget.setCity(queryOrderInfoRespVO.getCityName());
        jdB2cDespatchTarget.setCounty(queryOrderInfoRespVO.getDistrictName());
        jdB2cDespatchTarget.setReceiveMobile(queryOrderInfoRespVO.getConsigneePhone());
        jdB2cDespatchTarget.setPostcode(queryOrderInfoRespVO.getZipCode());
        jdB2cDespatchTarget.setPackageCount(String.valueOf(request.getPackingNum()));
        jdB2cDespatchTarget.setWeight(String.valueOf(request.getTotalWeight()));
        log.info("耘链发运单推送wms调用京东系统发运信息，参数信息：[{}]", JsonUtil.toJson(jdB2cDespatchTarget));
        String url = urlConfig.WMS_API_URL2 + "/sale/source/jDongShipping";
        HttpClient httpClient = HttpClient.post(url).json(jdB2cDespatchTarget).timeout(20000);
        HttpResponse orderDto = httpClient.action().result(HttpResponse.class);
        if (!orderDto.getCode().equals(MessageId.SUCCESS_CODE)) {
            return HttpResponse.failure(null, "调用wms调用京东系统失败,原因：" + orderDto.getMessage());
        }
        return HttpResponse.success();
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public HttpResponse deliver(List<String> transportCode) {
//        查询发运单
        List<Transport> transport = transportMapper.selectByTransportCodes(transportCode);
        if (CollectionUtils.isEmptyCollection(transport)) {
            return HttpResponse.failure(ResultCode.TRANSPORT_SIGN_ERROR);
        }
        List<TransportLog> logs = Lists.newArrayList();
        for (Transport transport1 : transport) {
            if(StringUtils.isBlank(transport1.getLogisticsCompany()) || StringUtils.isBlank(transport1.getLogisticsCompanyName()) || StringUtils.isBlank(transport1.getLogisticsNumber())){
                return HttpResponse.failure(ResultCode.NOT_HAVE_PARAM);
            }
            // 传发运参数
            DeliveryCallBackRequest request = new DeliveryCallBackRequest();
            request.setDeliveryCode(transport1.getTransportCode());
            request.setCustomerCode(transport1.getCustomerCode());
            request.setCustomerName(transport1.getCustomerName());
            request.setTransportDate(transport1.getTransportTime());
//            request.setTransportPerson(); // 发运人
            request.setTransportAmount(transport1.getTransportAmount());
            request.setStandardLogisticsFee(transport1.getStandardLogisticsFee());
            request.setAdditionalLogisticsFee(transport1.getAdditionalLogisticsFee());
            request.setTransportCode(transport1.getLogisticsNumber());
            request.setTransportCompanyCode(transport1.getLogisticsCompany());
            request.setTransportCompanyName(transport1.getLogisticsCompanyName());
            request.setTransportCenterCode(transport1.getTransportCenterCode());
            request.setTransportCenterName(transport1.getTransportCenterName());
            request.setDeliverTo(transport1.getDeliverTo());
            request.setPackingNum(transport1.getPackingNum());
            request.setOrderCommodityNum(transport1.getOrderCommodityNum());
            request.setTotalVolume(transport1.getTotalVolume());
            request.setTotalWeight(transport1.getTotalWeight());

//            调用发运接口
            orderCallbackService.deliveryCallBack(request);
            // 推送wms 发运信息
//            transportWmsPush(request);
            //写入发运单创建日志
            if (transport1.getStatus() != 1) {
                transportCode.remove(transport1.getTransportCode());
                continue;
            }
            TransportLog transportLog = new TransportLog();
            transportLog.setTransportCode(transport1.getTransportCode());
            transportLog.setType("发运");
            transportLog.setContent("发运单发运操作");
            transportLog.setRemark(transport1.getRemark());
            logs.add(transportLog);
        }
        updateStatus(transportCode, 2);
        saveLog(logs);
        return HttpResponse.success();
    }

    private HttpResponse transportWmsPush(DeliveryCallBackRequest request) {
        // 查询 订单库房
        TransportOrders transportOrders = transportOrdersMapper.selectTransportOrdersByTransportCode(request.getDeliveryCode());
        // 通过库房编码获取对应信息
        WarehouseDTO warehouse = warehouseDao.getWarehouseByCode(transportOrders.getWarehouseCode());
        // 查询订单信息
        QueryOrderInfoRespVO queryOrderInfoRespVO = orderInfoMapper.selectByOrderCode(transportOrders.getOrderCode());
        // 参数赋值
        E8OrderCreate e8OrderCreate = new E8OrderCreate();
        e8OrderCreate.setWarehouseCode(warehouse.getWarehouseCode());
        e8OrderCreate.setCcode(request.getDeliveryCode());
        // 寄件人信息
        e8OrderCreate.setSendMan(warehouse.getContact());
        e8OrderCreate.setSendPhone(warehouse.getPhone());
        e8OrderCreate.setSendProvince(warehouse.getProvinceName());
        e8OrderCreate.setSendCity(warehouse.getCityName());
        e8OrderCreate.setSendDistrict(warehouse.getCountyName());
//        e8OrderCreate.setSendTown();  镇没有
        e8OrderCreate.setSendStreetNo(warehouse.getDetailedAddress());
        // 收件人信息
        e8OrderCreate.setReceiveMan(queryOrderInfoRespVO.getConsignee());
        e8OrderCreate.setReceivePhone(queryOrderInfoRespVO.getConsigneePhone());
        e8OrderCreate.setReceiveProvince(queryOrderInfoRespVO.getProvinceName());
        e8OrderCreate.setReceiveCity(queryOrderInfoRespVO.getCityName());
        e8OrderCreate.setReceiveDistrict(queryOrderInfoRespVO.getDistrictName());
//        e8OrderCreate.setReceiveTown();  镇没有
        e8OrderCreate.setReceiveStreetNo(queryOrderInfoRespVO.getDetailAddress());
        if(request.getPackingNum() != null){
            e8OrderCreate.setAmount(request.getPackingNum().intValue());
        }
        if(request.getTotalVolume() != null){
            e8OrderCreate.setVolume(request.getTotalVolume().doubleValue());
        }
        if(request.getTotalWeight() != null){
            e8OrderCreate.setWeight(request.getTotalWeight().doubleValue());
        }
        if (request.getDeliverTo() != null && request.getDeliverTo().equals(Global.DELIVERTO_1)) {
            e8OrderCreate.setServiceMode(Global.SERVICE_MODE_1);   // 服务方式
            e8OrderCreate.setIfVisit(false);       // 上门提货
        } else {
            e8OrderCreate.setServiceMode(Global.SERVICE_MODE_4);   // 服务方式
            e8OrderCreate.setIfVisit(true);       // 上门提货
        }
        e8OrderCreate.setSettleType(Global.SETTLE_TYPE_1);    // 支付方式
        log.info("耘链发运单推送wms调用e8系统发运信息，参数信息：[{}]", JsonUtil.toJson(e8OrderCreate));
        String url = urlConfig.WMS_API_URL2 + "/sale/source/depponShipping";
        HttpClient httpClient = HttpClient.post(url).json(e8OrderCreate).timeout(20000);
        HttpResponse orderDto = httpClient.action().result(HttpResponse.class);
        if (!orderDto.getCode().equals(MessageId.SUCCESS_CODE)) {
            return HttpResponse.failure(null, "调用wms失败,原因：" + orderDto.getMessage());
        }
        return HttpResponse.success();
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public HttpResponse sign(List<String> transportCode) {
//        查询发运单
        List<Transport> transport=transportMapper.selectByTransportCodes(transportCode);
        if (CollectionUtils.isEmptyCollection(transport)){
            return HttpResponse.failure(ResultCode.TRANSPORT_SIGN_ERROR);
        }
        List<TransportLog> logs = Lists.newArrayList();
        for (Transport transport1 : transport) {
            //写入发运单创建日志
            if(transport1.getStatus()!=2){
                transportCode.remove(transport1.getTransportCode());
                continue;
            }
            TransportLog transportLog=new TransportLog();
            transportLog.setTransportCode(transport1.getTransportCode());
            transportLog.setType("签收");
            transportLog.setContent("发运单签收操作");
            transportLog.setRemark(transport1.getRemark());
            logs.add(transportLog);
        }
        updateStatus(transportCode,3);
        saveLog(logs);
        return HttpResponse.success();
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(List<String> transportCode, Integer status) {
        if(CollectionUtils.isEmptyCollection(transportCode)){
            return;
        }
        int i = transportMapper.updateStatusByTransportCodes(transportCode,status);
        if(i!=transportCode.size()){
            throw new BizException(ResultCode.UPDATE_TRANSPORT_STATUS_FAILED);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveLog(List<TransportLog> logs) {
        if(CollectionUtils.isEmptyCollection(logs)){
            return;
        }
        int i1 = transportLogMapper.insertBatch(logs);
        if(i1!=logs.size()){
            throw new BizException(ResultCode.SAVE_TRANSPORT_LOG_FAILED);
        }
    }

    @Override
    public BasePage<TransportLog> transportLogs(TransportLogsRequest transportLogsRequest) {
        PageHelper.startPage(transportLogsRequest.getPageNo(), transportLogsRequest.getPageSize());
        List<TransportLog> page=transportLogMapper.selectList(transportLogsRequest);
        return PageUtil.getPageList(transportLogsRequest.getPageNo(),page);
    }

    @Override
    public HttpResponse<Transport> detail(String transportCode) {
        Transport transport = transportMapper.selectByTransportCode(transportCode);
        return HttpResponse.success(transport);
    }
    @Override
    public BasePage<TransportOrders> orders(TransportRequest transportRequest) {
        PageHelper.startPage(transportRequest.getPageNo(), transportRequest.getPageSize());
        List<TransportOrders> orders = transportOrdersMapper.selectListByTransportCode(transportRequest);
        return PageUtil.getPageList(transportRequest.getPageNo(),orders);
    }

//    @Override
//    public TransportR
}
