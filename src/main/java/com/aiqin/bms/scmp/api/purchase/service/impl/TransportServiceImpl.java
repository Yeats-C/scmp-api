package com.aiqin.bms.scmp.api.purchase.service.impl;


import com.aiqin.bms.scmp.api.base.ResultCode;
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
import com.aiqin.bms.scmp.api.util.IdSequenceUtils;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
    public PageInfo<Transport> selectPage(TransportRequest transportRequest) {
        PageHelper.startPage(transportRequest.getPageNo(), transportRequest.getPageSize());
        List<Transport> page = transportMapper.selectList(transportRequest);
        return new PageInfo<Transport>(page);
    }

    @Override
    public PageInfo<TransportOrders> selectTransportOrdersPage(TransportOrdersResquest transportOrdersResquest) {
        PageHelper.startPage(transportOrdersResquest.getPageNo(), transportOrdersResquest.getPageSize());
        List<TransportOrders> page = transportMapper.selectTransportOrdersWithOutCodeList(transportOrdersResquest);
        return new PageInfo<TransportOrders>(page);
    }

    @Override
    public PageInfo<TransportOrders> selectDeliverOrdersPage(TransportOrdersResquest transportOrdersResquest) {
        PageHelper.startPage(transportOrdersResquest.getPageNo(), transportOrdersResquest.getPageSize());
        List<TransportOrders> page = transportMapper.selectDeliverOrdersWithOutCodeList(transportOrdersResquest);
        return new PageInfo<TransportOrders>(page);
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
        transport.setStatus(0);//设置未发运状态
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
    public HttpResponse deliver(String transportCode) {
//        查询发运单
        Transport transport=transportMapper.selectByTransportCode(transportCode);
        if (null==transport||transport.getStatus()!=0){
            return HttpResponse.failure(ResultCode.TRANSPORT_DELIVERY_ERROR);
        }else {
            transportMapper.updateStatusByTransportCode(transportCode,1);
            TransportLog transportLog=new TransportLog();
            //写入发运单创建日志
            transportLog.setTransportCode(transport.getTransportCode());
            transportLog.setType("发运");
            transportLog.setContent("发运单发运操作");
            transportLog.setRemark(transport.getRemark());
            transportLogMapper.insertOne(transportLog);
            return HttpResponse.success();
        }
    }
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public HttpResponse sign(String transportCode) {
//        查询发运单
        Transport transport=transportMapper.selectByTransportCode(transportCode);
        if (null==transport||transport.getStatus()!=1){
            return HttpResponse.failure(ResultCode.TRANSPORT_SIGN_ERROR);
        }else {
            transportMapper.updateStatusByTransportCode(transportCode,2);
            TransportLog transportLog=new TransportLog();
            //写入发运单创建日志
            transportLog.setTransportCode(transport.getTransportCode());
            transportLog.setType("签收");
            transportLog.setContent("发运单签收操作");
            transportLog.setRemark(transport.getRemark());
            transportLogMapper.insertOne(transportLog);
            return HttpResponse.success();
        }
    }

    @Override
    public PageInfo<TransportLog> transportLogs(TransportLogsRequest transportLogsRequest) {
        PageHelper.startPage(transportLogsRequest.getPageNo(), transportLogsRequest.getPageSize());
        List<TransportLog> page=transportLogMapper.selectList(transportLogsRequest);
        return new PageInfo<TransportLog>(page);
    }
}
