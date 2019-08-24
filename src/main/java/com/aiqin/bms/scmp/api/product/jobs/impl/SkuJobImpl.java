package com.aiqin.bms.scmp.api.product.jobs.impl;

import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSku;
import com.aiqin.bms.scmp.api.product.jobs.SkuJob;
import com.aiqin.bms.scmp.api.product.service.SkuInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class SkuJobImpl implements SkuJob {

    @Autowired
    private SkuInfoService skuInfoService;

    @Override
    @Scheduled(cron = "0 1/10 * * * ?")
    public void changeSkuStatus() {
        //查未同步数据的数据
        List<ApplyProductSku> list = skuInfoService.selectUnSynData();
        skuInfoService.toBeEffective(list);
    }
}
