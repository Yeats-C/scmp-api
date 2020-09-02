package com.aiqin.bms.scmp.api.abutment.service.impl;

import com.aiqin.bms.scmp.api.abutment.dao.DlOrderBillDao;
import com.aiqin.bms.scmp.api.abutment.dao.DlOtherInfoDao;
import com.aiqin.bms.scmp.api.abutment.domain.DlOrderBill;
import com.aiqin.bms.scmp.api.abutment.domain.DlOtherInfo;
import com.aiqin.bms.scmp.api.abutment.domain.manual.ManualOrderRequest;
import com.aiqin.bms.scmp.api.abutment.domain.request.dl.EchoOrderRequest;
import com.aiqin.bms.scmp.api.abutment.domain.request.dl.OrderInfoRequest;
import com.aiqin.bms.scmp.api.abutment.domain.request.dl.ReturnOrderInfoRequest;
import com.aiqin.bms.scmp.api.abutment.domain.request.dl.StockChangeRequest;
import com.aiqin.bms.scmp.api.abutment.service.DlAbutmentService;
import com.aiqin.bms.scmp.api.abutment.service.ManualOrderService;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.constant.Global;
import com.aiqin.bms.scmp.api.util.CollectionUtils;
import com.aiqin.ground.util.json.JsonUtil;
import com.aiqin.ground.util.protocol.MessageId;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ManualOrderServiceImpl implements ManualOrderService {

    private static Logger LOGGER = LoggerFactory.getLogger(ManualOrderServiceImpl.class);

    @Resource
    private DlOrderBillDao dlOrderBillDao;
    @Resource
    private DlOtherInfoDao dlOtherInfoDao;
    @Resource
    private DlAbutmentService dlService;


    @Override
    public HttpResponse<List<String>> dlOrder(ManualOrderRequest request){
        LOGGER.info("手动推送DL单据到耘链信息：{}", JsonUtil.toJson(request));
        if(request == null || CollectionUtils.isEmptyCollection(request.getOrderCodes()) || request.getType() == null){
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        DlOrderBill dlOrderBill;
        OrderInfoRequest orderInfoRequest;
        ReturnOrderInfoRequest returnOrderInfoRequest;
        List<String> logInfo = Lists.newArrayList();
        HttpResponse response;
        for (String code : request.getOrderCodes()){
            // 查询对应单据的报文信息
            dlOrderBill = new DlOrderBill();
            dlOrderBill.setDocumentCode(code);
            dlOrderBill.setDocumentType(request.getType());
            dlOrderBill.setBusinessType(Global.PUSH_TYPE);
            DlOrderBill orderBill = dlOrderBillDao.selectByCode(dlOrderBill);
            if(orderBill.getReturnStatus().equals(0)){
                LOGGER.info("此单据已推送成功，不允许重复推送DL：{}", code);
                logInfo.add(code + ": 此单据已推送成功，重复推送失败");
                continue;
            }

            if(request.getType().equals(Global.ORDER_TYPE)){
                // 推送销售单
                orderInfoRequest = JSONArray.parseObject(orderBill.getDocumentContent(), OrderInfoRequest.class);
                response = dlService.orderInfo(orderInfoRequest);
                logInfo.add(code + ":" + response.getMessage());
            }else if(request.getType().equals(Global.RETURN_INFO_TYPE)){
                // 推送退货单
                returnOrderInfoRequest = JSONArray.parseObject(orderBill.getDocumentContent(), ReturnOrderInfoRequest.class);
                response = dlService.returnInfo(returnOrderInfoRequest);
                logInfo.add(code + ":" + response.getMessage());
            }
        }
        return HttpResponse.successGenerics(logInfo);
    }

    @Override
    public HttpResponse<List<String>> orderDl(ManualOrderRequest request){
        LOGGER.info("手动推送耘链单据到DL信息：{}", JsonUtil.toJson(request));
        if(request == null || CollectionUtils.isEmptyCollection(request.getOrderCodes()) || request.getType() == null){
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        List<String> logInfo = Lists.newArrayList();
        if(request.getType().equals(1) || request.getType().equals(2)) {
            // 销售退货单
            DlOrderBill dlOrderBill;
            EchoOrderRequest orderRequest;
            for (String code : request.getOrderCodes()) {
                // 查询对应单据的报文信息
                dlOrderBill = new DlOrderBill();
                dlOrderBill.setDocumentCode(code);
                dlOrderBill.setDocumentType(request.getType());
                dlOrderBill.setBusinessType(Global.ECHO_TYPE);
                DlOrderBill orderBill = dlOrderBillDao.selectByCode(dlOrderBill);
                if(orderBill.getReturnStatus().equals(0)){
                    LOGGER.info("此单据已回传成功，不允许重复推送DL：{}", code);
                    continue;
                }
                orderRequest = JSONArray.parseObject(orderBill.getDocumentContent(), EchoOrderRequest.class);
                dlService.echoOrderInfo(orderRequest);
            }
            //  查询对应单据的状态
            List<DlOrderBill> dlOrderBills = dlOrderBillDao.selectByCodes(request.getOrderCodes(), request.getType(), Global.ECHO_TYPE);
            if(CollectionUtils.isNotEmptyCollection(dlOrderBills)){
                for (DlOrderBill orderBill : dlOrderBills){
                    logInfo.add(orderBill.getDocumentCode() + ":" + orderBill.getResponseDesc());
                }
            }
        }else {
            // 采购退供单
            DlOtherInfo dlOtherInfo;
            StockChangeRequest stockChangeRequest;
            for (String code : request.getOrderCodes()) {
                // 查询对应单据的报文信息
                dlOtherInfo = new DlOtherInfo();
                dlOtherInfo.setDocumentCode(code);
                dlOtherInfo.setDocumentType(Global.STOCK_TYPE);
                dlOtherInfo.setBusinessType(Global.ECHO_TYPE);
                if(request.getType().equals(3)){
                    dlOtherInfo.setStockType(1);
                }else if(request.getType().equals(4)){
                    dlOtherInfo.setStockType(2);
                }else {
                    continue;
                }
                DlOtherInfo otherInfo = dlOtherInfoDao.selectOtherInfo(dlOtherInfo);
                if(otherInfo.getReturnStatus().equals(0)){
                    LOGGER.info("此单据已回传成功，不允许重复推送DL：{}", code);
                    continue;
                }
                stockChangeRequest = JSONArray.parseObject(otherInfo.getDocumentContent(), StockChangeRequest.class);
                dlService.stockChange(stockChangeRequest);
            }
            //  查询对应单据的状态
            Integer stockType = 0;
            if(request.getType().equals(3)){
                stockType  = 1;
            }else if(request.getType().equals(4)){
                stockType = 2;
            }
            List<String> codes = dlOtherInfoDao.selectByCodes(request.getOrderCodes(), request.getType(), stockType);
            logInfo.addAll(codes);
        }
        return HttpResponse.successGenerics(logInfo);
    }


}
