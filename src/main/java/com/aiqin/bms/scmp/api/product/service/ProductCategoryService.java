package com.aiqin.bms.scmp.api.product.service;

import com.aiqin.bms.scmp.api.product.domain.ProductCategory;
import com.aiqin.bms.scmp.api.product.domain.request.ProductCategoryAddReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.ProductCategoryReqDTO;
import com.aiqin.bms.scmp.api.product.domain.request.ProductCategoryReqVO;
import com.aiqin.bms.scmp.api.product.domain.response.ProductCategoryRespVO;

import java.util.List;
import java.util.Set;

/**
 * @功能说明:品类service
 * @author wangxu
 * @date 2018/12/12 0012 17:25
 */
public interface ProductCategoryService {
    /**
     * 设置新增需要的对象属性等
     * @param productCategoryAddReqVO
     * @return
     */
    int saveProductCategory(ProductCategoryAddReqVO productCategoryAddReqVO);

    /**
     * 调用dao层批量新增
     * @param productCategoryReqDTOS
     * @return
     */
    int insertList(List<ProductCategoryReqDTO> productCategoryReqDTOS);

    /**
     * 复制对象等
     * @param productCategoryReqVO
     * @return
     */
    int updateProductCategory(ProductCategoryReqVO productCategoryReqVO);

    /**
     * 调用dao层修改品类属性
     * @param productCategory
     * @return
     */
    int update(ProductCategory productCategory);

    /**
     * 复制对象等
     * @param id
     * @return
     */
    int deleteProductCategory(Long id);

    /**
     * 获取返回列表
     * @param categoryStatus
     * @return
     */
    List<ProductCategoryRespVO> getList(Byte categoryStatus, String companyCode);


    /**
     * 获取返回列表
     * @param categoryStatus
     * @return
     */
    List<ProductCategoryRespVO> getTree(Byte categoryStatus, String parentCode, String companyCode);

    /**
     * 根据品类id查询父类集合
     * @param categoryId
     * @return
     */
    List<ProductCategory> getParentCategoryList(String categoryId);


    /**
     * 根据品类id查询子类集合
     * @param categoryId
     * @param companyCode
     * @return
     */
    List<ProductCategory> getChildCategoryList(String categoryId, String companyCode);

    /**
     *
     * 功能描述: 验证是否能禁用
     *
     * @param categoryCode
     * @return
     * @auther knight.xie
     * @date 2019/7/19 20:03
     */
    boolean verifyDisable(String categoryCode);
    /**
     * 名字查品类品牌
     * @author NullPointException
     * @date 2019/7/21
     * @param brandNameList
     * @param companyCode
     * @return java.util.Map<java.lang.String,com.aiqin.bms.scmp.api.product.domain.ProductCategory>
     */
    List<ProductCategory> selectByCategoryNames(Set<String> brandNameList, String companyCode);
    List<ProductCategory> selectByCategoryCodes(Set<String> brandNameList, String companyCode);



}
