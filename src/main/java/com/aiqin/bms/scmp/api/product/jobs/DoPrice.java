package com.aiqin.bms.scmp.api.product.jobs;

import com.aiqin.bms.scmp.api.constant.Global;
import com.aiqin.bms.scmp.api.product.domain.pojo.Stock;
import com.aiqin.bms.scmp.api.product.service.PriceJobService;
import com.aiqin.bms.scmp.api.util.PriceThreadDo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class DoPrice implements Runnable{
    private Stock stock;
    @Autowired
    private PriceJobService service;

    public DoPrice(PriceJobService service,Stock stock){
        this.stock=stock;
        this.service=service;
    }

    @Override
    public void run() {
        try {
            service.price(stock);
        }catch (Exception e){
            log.error(Global.ERROR, e);
        }finally {
            PriceThreadDo.subThreadNum();
        }
    }
}
