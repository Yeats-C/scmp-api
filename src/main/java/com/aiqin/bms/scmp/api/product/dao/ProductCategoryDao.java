package com.aiqin.bms.scmp.api.product.dao;

import com.aiqin.bms.scmp.api.product.domain.ProductCategory;
import com.aiqin.bms.scmp.api.product.domain.request.ProductCategoryReqDTO;
import com.aiqin.bms.scmp.api.product.domain.response.ProductCategoryRespVO;
import com.aiqin.bms.scmp.api.product.domain.response.ProductCategoryResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface ProductCategoryDao {

    List<ProductCategoryResponse> selectFirstCategoryList();

    List<ProductCategoryResponse> selecSecondCategoryList();

    List<ProductCategoryResponse> selecThirdCategoryList();

    List<ProductCategoryResponse> selecFouthCategoryList();

    List<ProductCategoryResponse> selectSencondCategoryListByCategoryId(String categoryId);

    List<ProductCategoryResponse> selectThirdCategoryListByCategoryId(String categoryId);

    List<ProductCategoryResponse> selectFouthCategoryListByCategoryId(String categoryId);

    ProductCategoryResponse selectCategoryLevelByCategoryId(@Param("categoryId") String categoryId);

    Integer countAllCategory(@Param(value = "categoryIdList") List<String> categoryIdList);

    List<ProductCategory> selectCategoryByParentId(@Param("parentId") String parentId, @Param("companyCode") String companyCode);


    /**
     * 批量新增
     * @param productCategoryReqDTOS
     * @return
     */
    int insertList(List<ProductCategoryReqDTO> productCategoryReqDTOS);

    /**
     * 查询品类列表
     * @param categoryStatus
     * @return
     */
    List<ProductCategoryRespVO> getProductCategoryList(@Param("categoryStatus") Byte categoryStatus, @Param("companyCode") String companyCode, @Param("parentCode") String parentCode);

    /**
     * 修改品类记录
     * @param productCategory
     * @return
     */
    int updateByPrimaryKeySelective(ProductCategory productCategory);

    /**
     * 根据父节点id查询当前节点最大分类id值
     * @param parentId
     * @return
     */
    String getMaxCategoryIdByParentId(@Param("parentId") String parentId);

    /**
     * 根据当前分类id获取子节点最大分类id值
     * @param categoryId
     * @return
     */
    String getMaxChildNodeByCategoryId(@Param("categoryId") String categoryId);

    /**
     * 根据分类id查询品类
     * @param categoryId
     * @return
     */
    ProductCategory getProductCategoryById(@Param("categoryId") String categoryId);

    ProductCategory selectByPrimaryKey(Long id);
    /**
     *
     * 功能描述: 查询下级启用数量
     *
     * @param categoryId
     * @return
     * @auther knight.xie
     * @date 2019/7/19 20:06
     */
    int selectSubCategoryEnableCount(String categoryId);
    List<ProductCategory> selectByCategoryNames(@Param("list") Set<String> brandNameList, @Param("companyCode") String companyCode);

    List<String> checkName(@Param("list") List<String> sameLevelNameList, @Param("companyCode") String companyCode);

    int checkNameByNameAndCode(@Param("categoryName") String categoryName, @Param("categoryId") String categoryId, @Param("companyCode") String companyCode);
}
