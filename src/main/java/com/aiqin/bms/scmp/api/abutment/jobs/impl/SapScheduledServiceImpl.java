package com.aiqin.bms.scmp.api.abutment.jobs.impl;

import com.aiqin.bms.scmp.api.abutment.domain.request.SapOrderRequest;
import com.aiqin.bms.scmp.api.abutment.jobs.SapScheduledService;
import com.aiqin.bms.scmp.api.abutment.service.SapBaseDataService;
import com.aiqin.bms.scmp.api.abutment.service.impl.SapBaseDataServiceImpl;
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

    @Resource
    private SapBaseDataService sapBaseDataService;

    public void orderInfoAbutment(){
        orderInfoAbutmentSchedule("","");
    }

    /**
     *  同步订单数据
     * @param beginTime
     * @param finishTime
     */
//    @Scheduled(cron = "0 0 * * * ?")
    public void orderInfoAbutmentSchedule(String beginTime,String finishTime){
        LOGGER.info("同步销售/退货的订单数据,beginTime:{},finishTime:{}",beginTime,finishTime);
        SapOrderRequest sapOrderRequest = new SapOrderRequest();
        sapOrderRequest.setBeginTime(beginTime);
        sapOrderRequest.setFinishTime(finishTime);
        sapBaseDataService.saleSynchronization(sapOrderRequest);
    }


}