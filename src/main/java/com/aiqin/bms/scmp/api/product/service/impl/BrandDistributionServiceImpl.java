package com.aiqin.bms.scmp.api.product.service.impl;

import com.aiqin.bms.scmp.api.base.PageResData;
import com.aiqin.bms.scmp.api.product.dao.ProductBrandDistributionDao;
import com.aiqin.bms.scmp.api.product.domain.ProductBrandDistribution;
import com.aiqin.bms.scmp.api.product.domain.request.brand.ModifyBrandDistributionStatusRequest;
import com.aiqin.bms.scmp.api.product.domain.request.brand.ProductBrandDistributionPageRequest;
import com.aiqin.bms.scmp.api.product.domain.request.brand.QueryBrandDistributionRequest;
import com.aiqin.bms.scmp.api.product.service.BrandDistributionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Createed by sunx on 2018/12/5.<br/>
 */
@Service
@Slf4j
public class BrandDistributionServiceImpl implements BrandDistributionService {

    @Resource
    private ProductBrandDistributionDao productBrandDistributionDao;

    @Override
    public PageResData<ProductBrandDistribution> brandDistributionPage(ProductBrandDistributionPageRequest request) {
        PageResData<ProductBrandDistribution> re = new PageResData<>();
        ProductBrandDistribution productBrandDistribution = new ProductBrandDistribution();
        BeanUtils.copyProperties(request, productBrandDistribution);
        List<ProductBrandDistribution> list = productBrandDistributionDao.selectBrandInfoByBrandName(productBrandDistribution);
        int total = productBrandDistributionDao.countBrandInfoByBrandName(productBrandDistribution);
        if (CollectionUtils.isNotEmpty(list)) {
            re.setDataList(list);
        }
        re.setTotalCount(total);
        return re;
    }


    @Override
    public ProductBrandDistribution brandDistributionDetail(String brandId, String distributorId) {
        return productBrandDistributionDao.selectByBrandIdAndDistributorId(brandId, distributorId);
    }

    @Override
    public void updateBrandDistribution(ProductBrandDistribution productBrandDistribution) {
        productBrandDistributionDao.updateBrandDisByBrandIdAndDisId(productBrandDistribution);
    }

    @Override
    public void modifyInfoStatus(ModifyBrandDistributionStatusRequest request) {
        ProductBrandDistribution productBrandDistribution = new ProductBrandDistribution();
        productBrandDistribution.setBrandId(request.getBrandId());
        productBrandDistribution.setDistributorId(request.getDistributorId());
        productBrandDistribution.setUpdateBy(request.getUpdateBy());
        Integer type = request.getOptType();
        switch (type) {
            case 0:
                //删除
                productBrandDistribution.setDelFlag(request.getOptValue());
                break;
            case 1:
                //是否优选
                productBrandDistribution.setBrandTop(request.getOptValue());
                break;
            case 2:
                //是否展示
                productBrandDistribution.setIsShow(request.getOptValue());
                break;
            default:
                return;
        }
        productBrandDistributionDao.updateStatusByParam(productBrandDistribution);
    }

    @Override
    public List<ProductBrandDistribution> queryBrandDistributions(QueryBrandDistributionRequest request) {
        return productBrandDistributionDao.queryByParamOrderByBrandInitialsAsc(request);
    }
}
