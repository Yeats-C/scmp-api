package com.aiqin.bms.scmp.api.abutment.jobs.impl;

import com.aiqin.bms.scmp.api.abutment.domain.request.dl.MonthStockRequest;
import com.aiqin.bms.scmp.api.abutment.jobs.DlMonthStockService;
import com.aiqin.bms.scmp.api.abutment.service.ParameterAssemblyService;
import com.aiqin.bms.scmp.api.product.dao.StockDayBatchDao;
import com.aiqin.bms.scmp.api.util.CollectionUtils;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DlMonthStockServiceImpl implements DlMonthStockService {

    private static Logger LOGGER = LoggerFactory.getLogger(DlMonthStockServiceImpl.class);

    public static final Integer DEBANG = 1;
    public static final Integer JINGDONG = 2;

    @Resource
    private StockDayBatchDao stockDayBatchDao;
    @Value("${dl.url}")
    private String DL_URL;
    @Resource
    @Lazy
    private ParameterAssemblyService parameterAssemblyService;

    @Override
    @Scheduled(cron = "0 0/20 * * * ?")
    public void monthStockDl(){

        List<MonthStockRequest> list = Lists.newArrayList();

        // 查询德邦所有的库存信息
        List<MonthStockRequest> stockDayBatches = stockDayBatchDao.stockDayByDl(DEBANG);
        if(CollectionUtils.isNotEmptyCollection(stockDayBatches)){
            list.addAll(stockDayBatches);
            LOGGER.info("同步DL日期库存数据，德邦数量：{}", stockDayBatches.size());
        }

        // 查询京东所有的库存信息
        List<MonthStockRequest> stockDayBatchJds = stockDayBatchDao.stockDayByDl(JINGDONG);
        if(CollectionUtils.isNotEmptyCollection(stockDayBatchJds)){
            list.addAll(stockDayBatchJds);
            LOGGER.info("同步DL日期库存数据，京东数量：{}", stockDayBatchJds.size());
        }
        LOGGER.info("推送DL月份批次数据条数：{}", list.size());
        parameterAssemblyService.monthStockDlParameter(list);
    }
}
