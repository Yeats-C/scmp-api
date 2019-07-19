package com.aiqin.bms.scmp.api.product.service.impl;

import com.aiqin.bms.scmp.api.common.SaveList;
import com.aiqin.bms.scmp.api.common.Update;
import com.aiqin.bms.scmp.api.config.AuthenticationInterceptor;
import com.aiqin.bms.scmp.api.product.dao.ProductCategoryDao;
import com.aiqin.bms.scmp.api.product.domain.ProductCategory;
import com.aiqin.bms.scmp.api.product.domain.request.ProductCategoryAddReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.ProductCategoryReqDTO;
import com.aiqin.bms.scmp.api.product.domain.request.ProductCategoryReqVO;
import com.aiqin.bms.scmp.api.product.domain.response.ProductCategoryRespVO;
import com.aiqin.bms.scmp.api.product.mapper.ApplyProductSkuMapper;
import com.aiqin.bms.scmp.api.product.mapper.ProductSkuDraftMapper;
import com.aiqin.bms.scmp.api.product.mapper.ProductSkuInfoMapper;
import com.aiqin.bms.scmp.api.product.service.ProductCategoryService;
import com.aiqin.bms.scmp.api.util.AuthToken;
import com.aiqin.bms.scmp.api.util.BeanCopyUtils;
import com.aiqin.ground.util.exception.GroundRuntimeException;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @功能说明:品类service实现
 * @author wangxu
 * @date 2018/12/13 0013 11:26
 */
@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {
    @Autowired
    private ProductCategoryDao productCategoryDao;
    @Autowired
    private ProductSkuInfoMapper skuInfoMapper;
    @Autowired
    private ProductSkuDraftMapper skuDraftMapper;
    @Autowired
    private ApplyProductSkuMapper applyProductSkuMapper;
    @Override
    @Transactional(rollbackFor = GroundRuntimeException.class)
    public int saveProductCategory(ProductCategoryAddReqVO productCategoryAddReqVO) {
        int num = 1;
        try {
            String companyCode = null;
            String companyName = null;
            AuthToken authToken = AuthenticationInterceptor.getCurrentAuthToken();
            if(null != authToken){
                companyCode = authToken.getCompanyCode();
                companyName = authToken.getCompanyName();
            }
            //传入sql的集合
            List<ProductCategoryReqDTO> productCategoryReqDTOS = new ArrayList();
            //同级分类新增
            if (productCategoryAddReqVO.getSameLevelList().size()>0){
                //同级分类集合
                List<ProductCategoryReqVO> sameList = productCategoryAddReqVO.getSameLevelList();
                //分类Id
                Long categoryId;
                //获取当前同类最大分类id
                String maxCategoryId = productCategoryDao.getMaxCategoryIdByParentId(String.valueOf(productCategoryAddReqVO.getParentId()));
                if (null == maxCategoryId){
                    categoryId = (long)100;
                } else {
                    categoryId = Long.valueOf(maxCategoryId);
                }
                String finalCompanyCode = companyCode;
                String finalCompanyName = companyName;
                Long categoryId1 = categoryId;
                for (ProductCategoryReqVO item : sameList) {
                    ProductCategoryReqDTO productCategoryReqDTO = new ProductCategoryReqDTO();
                    categoryId1 = categoryId1 + 1;
                    item.setCategoryId(String.valueOf(categoryId1));
                    //复制对象属性值
                    BeanCopyUtils.copy(item, productCategoryReqDTO);
                    productCategoryReqDTO.setCompanyCode(finalCompanyCode);
                    productCategoryReqDTO.setCompanyName(finalCompanyName);
                    productCategoryReqDTOS.add(productCategoryReqDTO);
                }
            }
            if (productCategoryAddReqVO.getLowerLevelList().size() > 0){
                //同级分类集合
                List<ProductCategoryReqVO> lowerLevelList = productCategoryAddReqVO.getLowerLevelList();
                String maxChildCategoryId = productCategoryDao.getMaxChildNodeByCategoryId(String.valueOf(productCategoryAddReqVO.getCurrentCategoryId()));
                String childCategoryId;
                if (null == maxChildCategoryId){
                    childCategoryId = String.valueOf(productCategoryAddReqVO.getCurrentCategoryId())+"000";
                } else {
                    childCategoryId = maxChildCategoryId;
                }
                String finalCompanyCode = companyCode;
                String finalCompanyName = companyName;
                Long childCaIdLong = Long.valueOf(childCategoryId);
                for (ProductCategoryReqVO item : lowerLevelList) {
                    ProductCategoryReqDTO productCategoryReqDTO = new ProductCategoryReqDTO();
                    childCaIdLong = childCaIdLong + 1;
                    item.setCategoryId(String.valueOf(childCaIdLong));
                    //复制对象属性值
                    BeanCopyUtils.copy(item, productCategoryReqDTO);
                    productCategoryReqDTO.setCompanyCode(finalCompanyCode);
                    productCategoryReqDTO.setCompanyName(finalCompanyName);
                    productCategoryReqDTOS.add(productCategoryReqDTO);
                }
            }
            //调用批量新增
            num = ((ProductCategoryService) AopContext.currentProxy()).insertList(productCategoryReqDTOS);
        } catch (GroundRuntimeException e){
            e.printStackTrace();
            throw new GroundRuntimeException("新增品类失败");
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new GroundRuntimeException("新增品类失败");
        }
        return num;
    }

    @Override
    @Transactional(rollbackFor = GroundRuntimeException.class)
    @SaveList
    public int insertList(List<ProductCategoryReqDTO> productCategoryReqDTOS) {
        int num;
        try {
            num = productCategoryDao.insertList(productCategoryReqDTOS);
            if (num > 0){
                return num;
            } else {
                throw new GroundRuntimeException("新增品类失败");
            }
        } catch (GroundRuntimeException e){
            throw new GroundRuntimeException(e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = GroundRuntimeException.class)
    public int updateProductCategory(ProductCategoryReqVO productCategoryReqVO) {
        int num;
        try {
            ProductCategory productCategory = new ProductCategory();
            BeanCopyUtils.copy(productCategoryReqVO,productCategory);
            verifyDisable(productCategory.getCategoryId());
            num = ((ProductCategoryService)AopContext.currentProxy()).update(productCategory);
        } catch (GroundRuntimeException e){
            throw new GroundRuntimeException(e.getMessage());
        }
        return num;
    }

    @Override
    @Transactional(rollbackFor = GroundRuntimeException.class)
    @Update
    public int update(ProductCategory productCategory) {
        int num;
        try {
            num = productCategoryDao.updateByPrimaryKeySelective(productCategory);
            if (num > 0){
                return num;
            } else {
                throw new GroundRuntimeException("修改品类失败");
            }
        } catch (GroundRuntimeException e){
            throw new GroundRuntimeException(e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = GroundRuntimeException.class)
    public int deleteProductCategory(Long id) {
        int num;
        try {
            ProductCategory productCategory = productCategoryDao.selectByPrimaryKey(id);
            //设置需要更新的状态
            Integer status = 0;
            if(Objects.equals(0,productCategory.getCategoryStatus())){
                status = 1;
                verifyDisable(productCategory.getCategoryId());
            }
            productCategory.setCategoryStatus(status);
            //调用修改,修改删除标志
            num = ((ProductCategoryService)AopContext.currentProxy()).update(productCategory);
        } catch (GroundRuntimeException e){
            throw new GroundRuntimeException("删除品类失败");
        }
        return num;
    }

    @Override
    public List<ProductCategoryRespVO> getList(Byte categoryStatus) {
        try {
            String companyCode = "";
            AuthToken authToken = AuthenticationInterceptor.getCurrentAuthToken();
            if(null != authToken){
                companyCode = authToken.getCompanyCode();
            }
            //所有数据集合
            List<ProductCategoryRespVO> productCategoryRespVOS =productCategoryDao.getProductCategoryList(categoryStatus,companyCode);
            //所有根节点
            List<ProductCategoryRespVO> parentList = new ArrayList<>();
            //所有子节点
            List<ProductCategoryRespVO> childList = new ArrayList<>();
            productCategoryRespVOS.forEach(item->{
                if (item.getParentId().equals("0")){
                    parentList.add(item);
                } else {
                    childList.add(item);
                }
            });

            parentList.forEach(item->{
                List<ProductCategoryRespVO> resultList = getChildren(String.valueOf(item.getCategoryId()),childList);
                item.setProductCategoryRespVOS(resultList);
            });
            return parentList;
        } catch (GroundRuntimeException e){
            throw new GroundRuntimeException("查询品类失败");
        }
    }

    @Override
    public List<ProductCategory> getParentCategoryList(String categoryId) {
        try {
            List<ProductCategory> productCategories = new ArrayList<>();
            ProductCategory productCategory = productCategoryDao.getProductCategoryById(categoryId);
            if (null != productCategory && productCategory.getCategoryLevel() > 0){
                productCategories = getParentCategory(productCategories,productCategory.getParentId());
            }
            return productCategories;
        } catch (Exception e){
            throw new GroundRuntimeException(e.getMessage());
        }
    }

    public List<ProductCategory> getParentCategory(List<ProductCategory> productCategories,String categoryId){
        ProductCategory productCategory = productCategoryDao.getProductCategoryById(categoryId);
        if (null != productCategory){
            productCategories.add(productCategory);
            if (productCategory.getCategoryLevel() > 0){
                getParentCategory(productCategories,productCategory.getParentId());
            }
        }
        return productCategories;
    }

    /**
     * 根据父节点和所有子节点集合获取父节点下得子节点集合
     * @param parentId
     * @param children
     * @return
     */
    public List<ProductCategoryRespVO> getChildren(String parentId,List<ProductCategoryRespVO> children){
        List<ProductCategoryRespVO> list = new ArrayList<>();
        children.forEach(item->{
            if (parentId.equals(item.getParentId())){
                item.setProductCategoryRespVOS(getChildren(String.valueOf(item.getCategoryId()),children));
                list.add(item);
            }
        });
        return list;
    }

    /**
     * 根据品类id查询子类集合
     *
     * @param categoryId
     * @return
     */
    @Override
    public List<ProductCategory> getChildCategoryList(String categoryId,String companyCode) {
        List<ProductCategory> productCategories = new ArrayList<>();
        //包含自身
        ProductCategory productCategory = productCategoryDao.getProductCategoryById(categoryId);
        productCategories.add(productCategory);
        List<ProductCategory> productCategoryList = productCategoryDao.selectCategoryByParentId(categoryId,companyCode);
        productCategoryList.forEach(item->{
            productCategories.add(item);
            getChildCategory(productCategories,item.getCategoryId(),companyCode);
        });
        return productCategories;
    }

    /**
     * 功能描述: 验证是否能禁用
     *
     * @param categoryCode
     * @return
     * @auther knight.xie
     * @date 2019/7/19 20:03
     */
    @Override
    public boolean verifyDisable(String categoryCode) {
        //验证子集是否全部被禁用
        int i = productCategoryDao.selectSubCategoryEnableCount(categoryCode);
        if(i > 0){
            throw new GroundRuntimeException("此品类存在下级启用的数据,不允许禁用");
        }
        //判断正式表
        i = skuInfoMapper.checkCategory(categoryCode);
        if(i>0){
            throw new GroundRuntimeException("此品类在SKU中存在,不允许禁用");
        }
        //判断临时表
        i = skuDraftMapper.checkCategory(categoryCode);
        if(i>0){
            throw new GroundRuntimeException("此品类在SKU中存在,不允许禁用");
        }
        //判断申请表
        i = applyProductSkuMapper.checkCategory(categoryCode);
        if(i>0){
            throw new GroundRuntimeException("此品类在SKU中存在,不允许禁用");
        }
        return true;
    }

    private List<ProductCategory> getChildCategory(List<ProductCategory> productCategories, String categoryId, String companyCode){
        List<ProductCategory> productCategoryList = productCategoryDao.selectCategoryByParentId(categoryId,companyCode);
        if(CollectionUtils.isNotEmpty(productCategoryList)){
            productCategoryList.forEach(item->{
                productCategories.add(item);
                getChildCategory(productCategories,item.getCategoryId(),companyCode);
            });
        }
        return productCategories;
    }
}
