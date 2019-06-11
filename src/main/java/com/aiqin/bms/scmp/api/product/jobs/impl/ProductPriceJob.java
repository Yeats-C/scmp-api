package com.aiqin.bms.scmp.api.product.jobs.impl;

import com.aiqin.bms.scmp.api.product.domain.pojo.Stock;
import com.aiqin.bms.scmp.api.product.jobs.DoPrice;
import com.aiqin.bms.scmp.api.product.service.PriceJobService;
import com.aiqin.bms.scmp.api.product.service.StockService;
import com.aiqin.bms.scmp.api.util.PriceThreadDo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductPriceJob {

    @Autowired
    private PriceJobService priceJobService;
    @Autowired
    private StockService stockService;

    /**
     * 每天晚上零点统计产品成本价
     */
    @Scheduled(cron = "0 0 0 * * ?")
//    @Scheduled(cron = "*/30 * * * * ?")
    public void productPrice() {
        //查询仓库数
        List<Stock> warehouse = stockService.selectGroup();
        PriceThreadDo.setPool(warehouse.size());
        for (Stock stock : warehouse) {
            DoPrice doPrice=null;
            try {
            doPrice=new DoPrice(priceJobService,stock);
            PriceThreadDo.doExecutor(doPrice);
            Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
