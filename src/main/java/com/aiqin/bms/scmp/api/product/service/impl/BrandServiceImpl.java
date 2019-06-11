package com.aiqin.bms.scmp.api.product.service.impl;

import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.aiqin.bms.scmp.api.product.dao.ProductBrandDistributionDao;
import com.aiqin.bms.scmp.api.product.dao.ProductBrandTypeDao;
import com.aiqin.bms.scmp.api.product.service.BrandService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Createed by sunx on 2018/11/22.<br/>
 */
@Service
public class BrandServiceImpl implements BrandService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BrandServiceImpl.class);

    @Resource
    private ProductBrandTypeDao productBrandTypeDao;
    @Resource
    private ProductBrandDistributionDao productBrandDistributionDao;

    @Override
    public HttpResponse selectAllBrand() {
        return HttpResponse.success(productBrandTypeDao.selectAllBrand());
    }

    @Override
    public HttpResponse selectAllTopBrand() {
        return HttpResponse.success(productBrandTypeDao.selectAllTopBrand());
    }
}
