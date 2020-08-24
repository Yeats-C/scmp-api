package com.aiqin.bms.scmp.api.abutment.jobs.impl;

import com.aiqin.bms.scmp.api.abutment.dao.DlOrderBillDao;
import com.aiqin.bms.scmp.api.abutment.domain.DlOrderBill;
import com.aiqin.bms.scmp.api.abutment.domain.request.dl.EchoOrderRequest;
import com.aiqin.bms.scmp.api.abutment.jobs.DlScheduledService;
import com.aiqin.bms.scmp.api.abutment.service.DlAbutmentService;
import com.aiqin.bms.scmp.api.purchase.service.asyn.AsynSaveDocuments;
import com.aiqin.bms.scmp.api.purchase.service.impl.OrderCallbackServiceImpl;
import com.aiqin.bms.scmp.api.util.DateUtils;
import com.aiqin.bms.scmp.api.util.RedisTool;
import com.aiqin.ground.util.json.JsonUtil;
import com.aiqin.ground.util.protocol.MessageId;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class DlScheduledServiceImpl implements DlScheduledService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderCallbackServiceImpl.class);

    @Resource
    @Lazy(true)
    private DlAbutmentService dlService;
    @Resource
    private DlOrderBillDao dlOrderBillDao;
    @Resource
    private RedisTool redisTool;
    @Resource
    private ApplicationContext applicationContext;



    @Override
    @Scheduled(cron = "0 0/30 * * * ?")
    public HttpResponse pullOrderDl() {
        // 查询出超时的推送dl
        String day = DateUtils.sameDay();
        List<DlOrderBill> dlOrderBills = dlOrderBillDao.pullOrderDl(day);
        if(CollectionUtils.isNotEmpty(dlOrderBills)){
            for (DlOrderBill dlOrderBill : dlOrderBills) {
                String documentContent = dlOrderBill.getDocumentContent();
                String documentCode = dlOrderBill.getDocumentCode();
                String lockKey = "lock:pullOrderDl:" + documentCode;
                String requestId = UUID.randomUUID().toString();
                try {
                    if (!redisTool.tryGetDistributedLock(lockKey, requestId)) {
                        LOGGER.info("定时推送dl销售单重复请求单号={}", documentCode);
                    }else {
                        LOGGER.info("定时处理推送dl超时订单信息,参数：{}", JsonUtil.toJson(documentContent));
                        EchoOrderRequest echo = JSONObject.parseObject(documentContent, EchoOrderRequest.class);
                        LOGGER.info("获取定时处理推送转换对象信息,参数：{}", JsonUtil.toJson(echo));
                        HttpResponse response = dlService.echoOrderInfo(echo);
                        if (!response.getCode().equals(MessageId.SUCCESS_CODE)) {
                            LOGGER.info("定时推送dl超时订单失败信息,参数：{}", JsonUtil.toJson(response.getMessage()));
                        }
                    }
                } finally {
                    redisTool.releaseDistributedLock(lockKey, requestId);
                }
            }
        }
        return HttpResponse.success();
    }
}
