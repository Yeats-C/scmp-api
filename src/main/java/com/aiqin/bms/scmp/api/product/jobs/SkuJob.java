package com.aiqin.bms.scmp.api.product.jobs;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

public interface SkuJob {
    /**
     * 更改sku生效状态
     */
    void changeSkuStatus();
    /**
     *
     */
    void changeSkuConfigStatus();

    @Transactional(rollbackFor = Exception.class)
    @Scheduled(cron = "0 1/10 * * * ?")
    void changeSupplyCompanyStatus();
}
