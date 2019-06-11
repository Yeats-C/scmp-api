package com.aiqin.bms.scmp.api.product.service;

import com.aiqin.bms.scmp.api.base.PageResData;
import com.aiqin.bms.scmp.api.product.domain.ProductBrandDistribution;
import com.aiqin.bms.scmp.api.product.domain.request.brand.ModifyBrandDistributionStatusRequest;
import com.aiqin.bms.scmp.api.product.domain.request.brand.ProductBrandDistributionPageRequest;
import com.aiqin.bms.scmp.api.product.domain.request.brand.QueryBrandDistributionRequest;

import java.util.List;

/**
 * Createed by sunx on 2018/12/5.<br/>
 * 分销机构品牌管理
 */
public interface BrandDistributionService {

    /**
     * 分页
     * @param request
     * @return
     */
    PageResData<ProductBrandDistribution> brandDistributionPage(ProductBrandDistributionPageRequest request);

    /**
     * 分销机构品牌详情
     * @param brandId
     * @param distributorId
     * @return
     */
    ProductBrandDistribution brandDistributionDetail(String brandId, String distributorId);

    /**
     * 编辑机构品牌信息
     * @param productBrandDistribution
     */
    void updateBrandDistribution(ProductBrandDistribution productBrandDistribution);

    /**
     * 编辑机构品牌状态信息 入删除  展示 优选状态
     * @param request
     */
    void modifyInfoStatus(ModifyBrandDistributionStatusRequest request);

    /**
     * 查询分销机构品牌
     * @param request
     * @return
     */
    List<ProductBrandDistribution> queryBrandDistributions(QueryBrandDistributionRequest request);
}
