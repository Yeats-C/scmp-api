package com.aiqin.bms.scmp.api.abutment.jobs.impl;

import com.aiqin.bms.scmp.api.abutment.domain.request.SapOrderRequest;
import com.aiqin.bms.scmp.api.abutment.jobs.SapScheduledService;
import com.aiqin.bms.scmp.api.abutment.service.SapBaseDataService;
import com.aiqin.bms.scmp.api.abutment.service.impl.SapBaseDataServiceImpl;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

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
public class SapScheduledServiceImpl implements SapScheduledService {
    private static Logger LOGGER = LoggerFactory.getLogger(SapScheduledServiceImpl.class);

    private static final String STANDARD_FORMAT = "yyyy-MM-dd HH:mm:ss";

    @Resource
    private SapBaseDataService sapBaseDataService;

    @Scheduled(cron = "0 0 * * * ?")
    public void orderInfoAbutment(){
        DateTime now = new DateTime();
        LOGGER.info("进入sap同步orderInfoAbutment信息:{}",now.toString(STANDARD_FORMAT));
        orderInfoAbutmentSchedule(now.minusHours(1).toString(STANDARD_FORMAT),now.toString(STANDARD_FORMAT));
    }

    @Scheduled(cron = "0 0 * * * ?")
    public void stockAbutment(){
        DateTime now = new DateTime();
        LOGGER.info("进入sap同步stockAbutment信息:{}",now.toString(STANDARD_FORMAT));
        stockSynchronizationSchedule(now.minusHours(1).toString(STANDARD_FORMAT),now.toString(STANDARD_FORMAT));
    }

    @Scheduled(cron = "0 0 * * * ?")
    public void purchaseAbutment(){
        DateTime now = new DateTime();
        LOGGER.info("进入sap同步purchaseAbutment信息:{}",now.toString(STANDARD_FORMAT));
        purchaseSynchronizationSchedule(now.minusHours(1).toString(STANDARD_FORMAT),now.toString(STANDARD_FORMAT));
    }

    @Scheduled(cron = "0 0 * * * ?")
    public void productAbutment(){
        DateTime now = new DateTime();
        LOGGER.info("进入sap同步productAbutment信息:{}",now.toString(STANDARD_FORMAT));
        productSynchronizationSchedule(now.minusHours(1).toString(STANDARD_FORMAT),now.toString(STANDARD_FORMAT));
    }

    @Scheduled(cron = "0 0 * * * ?")
    public void supplyAbutment(){
        DateTime now = new DateTime();
        LOGGER.info("进入sap同步supplyAbutment信息:{}",now.toString(STANDARD_FORMAT));
        supplySynchronizationSchedule(now.minusHours(1).toString(STANDARD_FORMAT),now.toString(STANDARD_FORMAT));
    }


    /**
     *  同步订单数据
     * @param beginTime
     * @param finishTime
     */

    public void orderInfoAbutmentSchedule(String beginTime,String finishTime){
        LOGGER.info("同步销售/退货的订单数据,beginTime:{},finishTime:{}",beginTime,finishTime);
        SapOrderRequest sapOrderRequest = new SapOrderRequest();
        sapOrderRequest.setBeginTime(beginTime);
        sapOrderRequest.setFinishTime(finishTime);
        sapBaseDataService.saleSynchronization(sapOrderRequest);
    }
  /**
     *  同步出入库数据
     * @param beginTime
     * @param finishTime
     */

    public void stockSynchronizationSchedule(String beginTime,String finishTime){
        LOGGER.info("同步销售/退货的订单数据,beginTime:{},finishTime:{}",beginTime,finishTime);
        SapOrderRequest sapOrderRequest = new SapOrderRequest();
        sapOrderRequest.setBeginTime(beginTime);
        sapOrderRequest.setFinishTime(finishTime);
        sapBaseDataService.stockSynchronization(sapOrderRequest);
    }

    /**
     *  同步采购数据
     * @param beginTime
     * @param finishTime
     */

    public void purchaseSynchronizationSchedule(String beginTime,String finishTime){
        LOGGER.info("同步销售/退货的订单数据,beginTime:{},finishTime:{}",beginTime,finishTime);
        SapOrderRequest sapOrderRequest = new SapOrderRequest();
        sapOrderRequest.setBeginTime(beginTime);
        sapOrderRequest.setFinishTime(finishTime);
        sapBaseDataService.purchaseSynchronization(sapOrderRequest);
    }
    /**
     *  同步商品数据
     * @param beginTime
     * @param finishTime
     */

    public void productSynchronizationSchedule(String beginTime,String finishTime){
        LOGGER.info("同步销售/退货的订单数据,beginTime:{},finishTime:{}",beginTime,finishTime);
        SapOrderRequest sapOrderRequest = new SapOrderRequest();
        sapOrderRequest.setBeginTime(beginTime);
        sapOrderRequest.setFinishTime(finishTime);
        sapBaseDataService.productSynchronization(sapOrderRequest);
    }
    /**
     *  同步供应商数据
     * @param beginTime
     * @param finishTime
     */

    public void supplySynchronizationSchedule(String beginTime,String finishTime){
        LOGGER.info("同步销售/退货的订单数据,beginTime:{},finishTime:{}",beginTime,finishTime);
        SapOrderRequest sapOrderRequest = new SapOrderRequest();
        sapOrderRequest.setBeginTime(beginTime);
        sapOrderRequest.setFinishTime(finishTime);
        sapBaseDataService.supplySynchronization(sapOrderRequest);
    }


}
