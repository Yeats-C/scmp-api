package com.aiqin.bms.scmp.api.purchase.service.impl;


import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.common.BizException;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.order.OrderInfo;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.transport.Transport;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.transport.TransportLog;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.transport.TransportOrders;
import com.aiqin.bms.scmp.api.purchase.domain.request.transport.TransportAddRequest;
import com.aiqin.bms.scmp.api.purchase.domain.request.transport.TransportLogsRequest;
import com.aiqin.bms.scmp.api.purchase.domain.request.transport.TransportOrdersResquest;
import com.aiqin.bms.scmp.api.purchase.domain.request.transport.TransportRequest;
import com.aiqin.bms.scmp.api.purchase.mapper.TransportLogMapper;
import com.aiqin.bms.scmp.api.purchase.mapper.TransportMapper;
import com.aiqin.bms.scmp.api.purchase.mapper.TransportOrdersMapper;
import com.aiqin.bms.scmp.api.purchase.service.OrderService;
import com.aiqin.bms.scmp.api.purchase.service.TransportService;
import com.aiqin.bms.scmp.api.util.BeanCopyUtils;
import com.aiqin.bms.scmp.api.util.CollectionUtils;
import com.aiqin.bms.scmp.api.util.IdSequenceUtils;
import com.aiqin.bms.scmp.api.util.PageUtil;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        Transport transport=new Transport();
        //获取一个运输单号
        String code = new IdSequenceUtils().nextId()+"";
        BeanCopyUtils.copy(transportAddRequest,transport);
        transport.setLogisticsFee(transport.getAdditionalLogisticsFee()+transport.getStandardLogisticsFee());
        transport.setTransportCode(code);
        List<TransportOrders> transportOrders = BeanCopyUtils.copyList(transportAddRequest.getOrdersList(), TransportOrders.class);
        Long transportAmount=0L;
        Long orderCommodityNum=0L;
        for (TransportOrders  transportOrder: transportOrders) {
            transportOrder.setTransportCode(code);
            transportAmount+=transportOrder.getOrderAmount();
            orderCommodityNum+=transportOrder.getProductNum();
        }
        //查询一次收货信息设置值
        OrderInfo orderInfo= orderService.selectByOrderCode(transportOrders.get(0).getOrderCode());
        transport.setConsigneeName(orderInfo.getConsignee());
        transport.setCustomerCode(orderInfo.getCustomerCode());
        transport.setCustomerName(orderInfo.getCustomerName());
        transport.setConsigneePhone(orderInfo.getConsigneePhone());
        transport.setDetailedAddress(orderInfo.getDetailAddress());
        transport.setStatus(1);//设置未发运状态
        transport.setTransportAmount(transportAmount+transport.getLogisticsFee());
        transport.setOrderCommodityNum(orderCommodityNum);
        transportMapper.insertOne(transport);
        transportOrdersMapper.insertBatch(transportOrders);
        //写入发运单创建日志
        TransportLog transportLog=new TransportLog();
        transportLog.setTransportCode(code);
        transportLog.setType("新增");
        transportLog.setContent("新增发运单");
        transportLog.setRemark(transport.getRemark());
        transportLogMapper.insertOne(transportLog);
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
