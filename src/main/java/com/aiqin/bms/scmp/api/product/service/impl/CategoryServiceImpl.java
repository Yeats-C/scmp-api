package com.aiqin.bms.scmp.api.product.service.impl;

import com.aiqin.bms.scmp.api.constant.Global;
import com.aiqin.bms.scmp.api.product.dao.ProductCategoryDao;
import com.aiqin.bms.scmp.api.product.dao.ProductCategoryDistributionDao;
import com.aiqin.bms.scmp.api.product.domain.ProductCategoryDistribution;
import com.aiqin.bms.scmp.api.product.domain.response.ProductCategoryResponse;
import com.aiqin.bms.scmp.api.product.service.CategoryService;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Createed by sunx on 2018/11/22.<br/>
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    @Resource
    private ProductCategoryDao productCategoryDao;
    @Resource
    private ProductCategoryDistributionDao productCategoryDistributionDao;

    @Override
    public HttpResponse selectProductCategoryByParentId(String parentId) {
        return HttpResponse.success(productCategoryDao.selectCategoryByParentId(parentId,null));
    }

    @Override
    public HttpResponse selectCategoryDisInfoByDisId(String distributorId) {
        List<ProductCategoryDistribution> productCategoryDistributionList = productCategoryDistributionDao.selectCategoryDisInfoByDisId(distributorId);
        List<ProductCategoryResponse> firstList = new ArrayList<>();
        List<ProductCategoryResponse> secondList = new ArrayList<>();
        List<ProductCategoryResponse> thirdList = new ArrayList<>();
        List<ProductCategoryResponse> fouthList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(productCategoryDistributionList)) {
            for (ProductCategoryDistribution productCategoryDistribution : productCategoryDistributionList) {
                if (productCategoryDistribution.getCategoryId() != null) {
                    ProductCategoryResponse productCategory = productCategoryDao.selectCategoryLevelByCategoryId(productCategoryDistribution.getCategoryId());
                    if (productCategory != null) {
                        if (productCategory.getCategoryLevel() == Global.PRODUCT_CATEGORY_FIRST_LEVEL) {
                            firstList.add(productCategory);
                        } else if (productCategory.getCategoryLevel() == Global.PRODUCT_CATEGORY_SECOND_LEVEL) {
                            secondList = new ArrayList<>();
                            String parentId = productCategory.getParentId();
                            ProductCategoryResponse firstCategory = productCategoryDao.selectCategoryLevelByCategoryId(parentId);
                            secondList.add(productCategory);
                            if (firstCategory != null) {
                                firstCategory.setChildrenList(secondList);
                                firstList.add(firstCategory);
                            }
                        } else if (productCategory.getCategoryLevel() == Global.PRODUCT_CATEGORY_THIRD_LEVEL) {
                            secondList = new ArrayList<>();
                            thirdList = new ArrayList<>();
                            String parentId = productCategory.getParentId();
                            ProductCategoryResponse secondCategory = productCategoryDao.selectCategoryLevelByCategoryId(parentId);
                            thirdList.add(productCategory);
                            if (secondCategory != null) {
                                secondCategory.setChildrenList(thirdList);
                                secondList.add(secondCategory);
                                ProductCategoryResponse firstCategory = productCategoryDao.selectCategoryLevelByCategoryId(secondCategory.getParentId());
                                firstCategory.setChildrenList(secondList);
                                firstList.add(firstCategory);
                            }
                        } else if (productCategory.getCategoryLevel() == Global.PRODUCT_CATEGORY_FOUTH_LEVEL) {
                            secondList = new ArrayList<>();
                            thirdList = new ArrayList<>();
                            fouthList = new ArrayList<>();
                            String parentId = productCategory.getParentId();
                            ProductCategoryResponse thirdCategory = productCategoryDao.selectCategoryLevelByCategoryId(parentId);
                            fouthList.add(productCategory);
                            if (thirdCategory != null) {
                                thirdList.add(thirdCategory);
                                thirdCategory.setChildrenList(fouthList);
                                if (thirdCategory != null) {
                                    ProductCategoryResponse secondCategory = productCategoryDao.selectCategoryLevelByCategoryId(thirdCategory.getParentId());
                                    if (secondCategory != null) {
                                        secondCategory.setChildrenList(thirdList);
                                        secondList.add(secondCategory);
                                        ProductCategoryResponse firstCategory = productCategoryDao.selectCategoryLevelByCategoryId(secondCategory.getParentId());
                                        if (firstCategory != null) {
                                            firstCategory.setChildrenList(secondList);
                                            firstList.add(firstCategory);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return HttpResponse.success(firstList);
    }

}
