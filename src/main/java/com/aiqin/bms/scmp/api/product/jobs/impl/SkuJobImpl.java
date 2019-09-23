package com.aiqin.bms.scmp.api.product.jobs.impl;

import com.aiqin.bms.scmp.api.product.dao.ProductSkuSupplyUnitDao;
import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSku;
import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSkuConfig;
import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSkuSupplyUnit;
import com.aiqin.bms.scmp.api.product.jobs.SkuJob;
import com.aiqin.bms.scmp.api.product.service.ProductSkuConfigService;
import com.aiqin.bms.scmp.api.product.service.ProductSkuSupplyUnitService;
import com.aiqin.bms.scmp.api.product.service.SkuInfoService;
import com.aiqin.bms.scmp.api.util.CollectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class SkuJobImpl implements SkuJob {

    @Autowired
    private SkuInfoService skuInfoService;

    @Autowired
    private ProductSkuConfigService productSkuConfigService;

    @Autowired
    private ProductSkuSupplyUnitDao productSkuSupplyUnitDao;

    @Autowired
    private ProductSkuSupplyUnitService productSkuSupplyUnitService;


    @Override
    @Scheduled(cron = "0 1/10 * * * ?")
    @Transactional(rollbackFor = Exception.class)
    public void changeSkuStatus() {
        //查未同步数据的数据
        List<ApplyProductSku> list = skuInfoService.selectUnSynData();
        if (CollectionUtils.isEmptyCollection(list)) {
            return;
        }
        skuInfoService.toBeEffective(list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Scheduled(cron = "0 1/10 * * * ?")
    public void changeSkuConfigStatus() {
        //查未同步的数据
        List<ApplyProductSkuConfig> list = productSkuConfigService.selectUnSynData();
        if (CollectionUtils.isEmptyCollection(list)) {
            return;
        }
        productSkuConfigService.tobeEffective(list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Scheduled(cron = "0 1/10 * * * ?")
    public void changeSupplyCompanyStatus() {
        //查未同步的数据
        List<ApplyProductSkuSupplyUnit> list = productSkuSupplyUnitDao.selectUnSynData();
        if (CollectionUtils.isEmptyCollection(list)) {
            return;
        }
        productSkuSupplyUnitService.tobeEffective(list);
    }
}
