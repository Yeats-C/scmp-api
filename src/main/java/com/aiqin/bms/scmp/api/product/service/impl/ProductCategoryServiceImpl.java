package com.aiqin.bms.scmp.api.product.service.impl;

import com.aiqin.bms.scmp.api.common.BizException;
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
import com.aiqin.bms.scmp.api.purchase.manager.DataManageService;
import com.aiqin.bms.scmp.api.util.AuthToken;
import com.aiqin.bms.scmp.api.util.BeanCopyUtils;
import com.aiqin.ground.util.exception.GroundRuntimeException;
import com.aiqin.ground.util.protocol.MessageId;
import com.aiqin.ground.util.protocol.Project;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author wangxu
 * @功能说明:品类service实现
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
    @Autowired
    private DataManageService dataManageService;

    @Override
    @Transactional(rollbackFor = GroundRuntimeException.class)
    public int saveProductCategory(ProductCategoryAddReqVO productCategoryAddReqVO) {
        int num = 1;
        String companyCode = null;
        String companyName = null;
        if(StringUtils.isBlank(productCategoryAddReqVO.getCompanyCode())){
            AuthToken authToken = AuthenticationInterceptor.getCurrentAuthToken();
            if (null != authToken) {
                companyCode = authToken.getCompanyCode();
                companyName = authToken.getCompanyName();
            }
        }else {
            companyCode = productCategoryAddReqVO.getCompanyCode();
            companyName = productCategoryAddReqVO.getCompanyName();
        }

        //验证提交的数据中是否存在五级分类
        List<ProductCategoryReqVO> productCategoryTmps = Lists.newArrayList();
        productCategoryTmps.addAll(productCategoryAddReqVO.getSameLevelList());
        productCategoryTmps.addAll(productCategoryAddReqVO.getLowerLevelList());
        List<ProductCategoryReqVO> productCategoryTmps2 = productCategoryTmps.stream().filter(item ->item.getCategoryLevel().compareTo(Byte.parseByte("5"))>=0).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(productCategoryTmps2)) {
            throw new BizException(MessageId.create(Project.SCMP_API, 99, "存在5级分类,不允许保存"));
        }
        //判断传入的集合名称有没有重复
        List<String> sameLevelNameList = productCategoryAddReqVO.getSameLevelList().stream().map(pcrvo -> pcrvo.getCategoryName()).distinct().collect(Collectors.toList());
        List<String> lowerLevelNameList = productCategoryAddReqVO.getLowerLevelList().stream().map(pcrvo -> pcrvo.getCategoryName()).distinct().collect(Collectors.toList());
        List<String> nameList = Lists.newArrayList();
        nameList.addAll(sameLevelNameList);
        nameList.addAll(lowerLevelNameList);
        List<String> tempList = nameList.stream().distinct().collect(Collectors.toList());
        if (productCategoryAddReqVO.getSameLevelList().size() != sameLevelNameList.size()) {
            throw new BizException(MessageId.create(Project.SCMP_API, 99, "新增同级分类,存在相同名称,请检查"));
        }
        if (productCategoryAddReqVO.getLowerLevelList().size() != lowerLevelNameList.size()) {
            throw new BizException(MessageId.create(Project.SCMP_API, 99, "新增下级分类,存在相同名称,请检查"));
        }
        if (nameList.size() != tempList.size()) {
            throw new BizException(MessageId.create(Project.SCMP_API, 99, "新增分类,同级分类和下级分类存在相同名称,请检查"));
        }
        //判断数据库中是否存在相同的名字
        List<String> repeatName = productCategoryDao.checkName(tempList, companyCode);
        if (CollectionUtils.isNotEmpty(repeatName)) {
            throw new BizException(MessageId.create(Project.SCMP_API, 99, "新增分类," + StringUtils.join(repeatName, ",") + "在数据库中存在相同名称"));
        }
        //传入sql的集合
        List<ProductCategoryReqDTO> productCategoryReqDTOS = new ArrayList();
        //同级分类新增
        if (productCategoryAddReqVO.getSameLevelList().size() > 0) {
            //同级分类集合
            List<ProductCategoryReqVO> sameList = productCategoryAddReqVO.getSameLevelList();
            //分类Id
            Long categoryId;
            //获取当前同类最大分类id
            String maxCategoryId = productCategoryDao.getMaxCategoryIdByParentId(String.valueOf(productCategoryAddReqVO.getParentId()));
            if (null == maxCategoryId) {
                categoryId = (long) 0;
            } else {
                categoryId = Long.valueOf(maxCategoryId);
            }
            String finalCompanyCode = companyCode;
            String finalCompanyName = companyName;
            Long categoryId1 = categoryId;
            for (ProductCategoryReqVO item : sameList) {
                ProductCategoryReqDTO productCategoryReqDTO = new ProductCategoryReqDTO();
                String code = getCode(categoryId1, item.getCategoryLevel());
                categoryId1 = Long.parseLong(code);
                item.setCategoryId(code);
                //复制对象属性值
                BeanCopyUtils.copy(item, productCategoryReqDTO);
                productCategoryReqDTO.setCompanyCode(finalCompanyCode);
                productCategoryReqDTO.setCompanyName(finalCompanyName);
                productCategoryReqDTOS.add(productCategoryReqDTO);
            }
        }
        if (productCategoryAddReqVO.getLowerLevelList().size() > 0) {
            //同级分类集合
            List<ProductCategoryReqVO> lowerLevelList = productCategoryAddReqVO.getLowerLevelList();
            String maxChildCategoryId = productCategoryDao.getMaxChildNodeByCategoryId(productCategoryAddReqVO.getCurrentCategoryId());
            String childCategoryId;
            if (null == maxChildCategoryId) {
                childCategoryId = String.valueOf(productCategoryAddReqVO.getCurrentCategoryId()) + "00";
            } else {
                childCategoryId = maxChildCategoryId;
            }
            String finalCompanyCode = companyCode;
            String finalCompanyName = companyName;
            Long childCaIdLong = Long.valueOf(childCategoryId);
            for (ProductCategoryReqVO item : lowerLevelList) {
                ProductCategoryReqDTO productCategoryReqDTO = new ProductCategoryReqDTO();
                String code = getCode(childCaIdLong, item.getCategoryLevel());
                childCaIdLong = Long.parseLong(code);
                item.setCategoryId(code);
                //复制对象属性值
                BeanCopyUtils.copy(item, productCategoryReqDTO);
                productCategoryReqDTO.setCompanyCode(finalCompanyCode);
                productCategoryReqDTO.setCompanyName(finalCompanyName);
                productCategoryReqDTOS.add(productCategoryReqDTO);
            }
        }
        //调用批量新增
        num = ((ProductCategoryService) AopContext.currentProxy()).insertList(productCategoryReqDTOS);
        //删除品类缓存信息
        dataManageService.deleteCategoryName();
        return num;
    }

    @Override
    @Transactional(rollbackFor = GroundRuntimeException.class)
    @SaveList
    public int insertList(List<ProductCategoryReqDTO> productCategoryReqDTOS) {
        int num;
        try {
            num = productCategoryDao.insertList(productCategoryReqDTOS);
            if (num > 0) {
                return num;
            } else {
                throw new GroundRuntimeException("新增品类失败");
            }
        } catch (GroundRuntimeException e) {
            throw new GroundRuntimeException(e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = GroundRuntimeException.class)
    public int updateProductCategory(ProductCategoryReqVO productCategoryReqVO) {
        int num;
        String companyCode = this.company(productCategoryReqVO.getCompanyCode());
        int count = productCategoryDao.checkNameByNameAndCode(productCategoryReqVO.getCategoryName(), productCategoryReqVO.getCategoryId(), companyCode);
        if (count > 0) {
            throw new BizException(MessageId.create(Project.SCMP_API, 99, "在数据库中存在相同名称"));
        }
        ProductCategory productCategory = new ProductCategory();
        BeanCopyUtils.copy(productCategoryReqVO, productCategory);
        if (productCategoryReqVO.getCategoryStatus().equals(Byte.parseByte("1"))) {
            verifyDisable(productCategory.getCategoryId());
        }
        num = ((ProductCategoryService) AopContext.currentProxy()).update(productCategory);
        //删除品类缓存信息
        dataManageService.deleteCategoryName();
        return num;
    }

    private String company(String companyCode){
        String code = null;
        if(StringUtils.isNotBlank(companyCode)){
            code = companyCode;
        }else {
            AuthToken authToken = AuthenticationInterceptor.getCurrentAuthToken();
            if (null != authToken) {
                code = authToken.getCompanyCode();
            }
        }
        return code;
    }

    @Override
    @Transactional(rollbackFor = GroundRuntimeException.class)
    @Update
    public int update(ProductCategory productCategory) {
        int num;
        try {
            num = productCategoryDao.updateByPrimaryKeySelective(productCategory);
            if (num > 0) {
                return num;
            } else {
                throw new GroundRuntimeException("修改品类失败");
            }
        } catch (GroundRuntimeException e) {
            throw new GroundRuntimeException(e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = GroundRuntimeException.class)
    public int deleteProductCategory(Long id) {
        int num;

        ProductCategory productCategory = productCategoryDao.selectByPrimaryKey(id);
        //设置需要更新的状态
        Integer status = 0;
        if (Objects.equals(0, productCategory.getCategoryStatus())) {
            status = 1;
            verifyDisable(productCategory.getCategoryId());
        }
        productCategory.setCategoryStatus(status);
        //调用修改,修改删除标志
        num = ((ProductCategoryService) AopContext.currentProxy()).update(productCategory);
        //删除品类缓存信息
        dataManageService.deleteCategoryName();
        return num;
    }

    @Override
    public List<ProductCategoryRespVO> getList(Byte categoryStatus, String companyCode) {
        try {
            if(StringUtils.isBlank(companyCode)){
                AuthToken authToken = AuthenticationInterceptor.getCurrentAuthToken();
                if (null != authToken) {
                    companyCode = authToken.getCompanyCode();
                }
            }
            //所有数据集合
            List<ProductCategoryRespVO> productCategoryRespVOS = productCategoryDao.getProductCategoryList(categoryStatus, companyCode, null);
            //所有根节点
            List<ProductCategoryRespVO> parentList = new ArrayList<>();
            //所有子节点
            List<ProductCategoryRespVO> childList = new ArrayList<>();
            productCategoryRespVOS.forEach(item -> {
                if (item.getParentId().equals("0")) {
                    parentList.add(item);
                } else {
                    childList.add(item);
                }
            });

            parentList.forEach(item -> {
                List<ProductCategoryRespVO> resultList = getChildren(String.valueOf(item.getCategoryId()), childList);
                item.setProductCategoryRespVOS(resultList);
            });
            return parentList;
        } catch (GroundRuntimeException e) {
            throw new GroundRuntimeException("查询品类失败");
        }
    }

    @Override
    public List<ProductCategoryRespVO> getTree(Byte categoryStatus, String parentCode, String companyCode) {
        try {
           if(StringUtils.isBlank(companyCode)){
               AuthToken authToken = AuthenticationInterceptor.getCurrentAuthToken();
               if (null != authToken) {
                   companyCode = authToken.getCompanyCode();
               }
           }
            List<ProductCategoryRespVO> productCategoryRespVOS = productCategoryDao.getProductCategoryList(categoryStatus, companyCode, parentCode);
            final String finalCompanyCode = companyCode;
            productCategoryRespVOS.forEach(item -> {
                List<ProductCategoryRespVO> productCategoryList = productCategoryDao.getProductCategoryList(categoryStatus, finalCompanyCode, item.getCategoryId());
                if (CollectionUtils.isNotEmpty(productCategoryList)) {
                    item.setChildNode(true);
                } else {
                    item.setChildNode(false);
                }
            });
//            //所有根节点
//            List<ProductCategoryRespVO> parentList = new ArrayList<>();
//            //所有子节点
//            List<ProductCategoryRespVO> childList = new ArrayList<>();
//            productCategoryRespVOS.forEach(item->{
//                if (item.getParentId().equals("0")){
//                    parentList.add(item);
//                } else {
//                    childList.add(item);
//                }
//            });
//
//            parentList.forEach(item->{
//                List<ProductCategoryRespVO> resultList = getChildren(String.valueOf(item.getCategoryId()),childList);
//                item.setProductCategoryRespVOS(resultList);
//            });
            return productCategoryRespVOS;
        } catch (GroundRuntimeException e) {
            throw new GroundRuntimeException("查询品类失败");
        }
    }

    @Override
    public List<ProductCategory> getParentCategoryList(String categoryId) {
        try {
            List<ProductCategory> productCategories = new ArrayList<>();
            ProductCategory productCategory = productCategoryDao.getProductCategoryById(categoryId);
            if (null != productCategory && productCategory.getCategoryLevel() > 0) {
                productCategories = getParentCategory(productCategories, productCategory.getParentId());
            }
            return productCategories;
        } catch (Exception e) {
            throw new GroundRuntimeException(e.getMessage());
        }
    }

    public List<ProductCategory> getParentCategory(List<ProductCategory> productCategories, String categoryId) {
        ProductCategory productCategory = productCategoryDao.getProductCategoryById(categoryId);
        if (null != productCategory) {
            productCategories.add(productCategory);
            if (productCategory.getCategoryLevel() > 0) {
                getParentCategory(productCategories, productCategory.getParentId());
            }
        }
        return productCategories;
    }

    /**
     * 根据父节点和所有子节点集合获取父节点下得子节点集合
     *
     * @param parentId
     * @param children
     * @return
     */
    public List<ProductCategoryRespVO> getChildren(String parentId, List<ProductCategoryRespVO> children) {
        List<ProductCategoryRespVO> list = new ArrayList<>();
        children.forEach(item -> {
            if (parentId.equals(item.getParentId())) {
                item.setProductCategoryRespVOS(getChildren(String.valueOf(item.getCategoryId()), children));
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
    public List<ProductCategory> getChildCategoryList(String categoryId, String companyCode) {
        List<ProductCategory> productCategories = new ArrayList<>();
        //包含自身
        ProductCategory productCategory = productCategoryDao.getProductCategoryById(categoryId);
        productCategories.add(productCategory);
        List<ProductCategory> productCategoryList = productCategoryDao.selectCategoryByParentId(categoryId, companyCode);
        productCategoryList.forEach(item -> {
            productCategories.add(item);
            getChildCategory(productCategories, item.getCategoryId(), companyCode);
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
        if (i > 0) {
            throw new GroundRuntimeException("此品类存在下级启用的数据,不允许禁用");
        }
        //判断正式表
        i = skuInfoMapper.checkCategory(categoryCode);
        if (i > 0) {
            throw new GroundRuntimeException("此品类在SKU中存在,不允许禁用");
        }
        //判断临时表
        i = skuDraftMapper.checkCategory(categoryCode);
        if (i > 0) {
            throw new GroundRuntimeException("此品类在SKU中存在,不允许禁用");
        }
        //判断申请表
        i = applyProductSkuMapper.checkCategory(categoryCode);
        if (i > 0) {
            throw new GroundRuntimeException("此品类在SKU中存在,不允许禁用");
        }
        return true;
    }

    @Override
    public List<ProductCategory> selectByCategoryNames(Set<String> brandNameList, String companyCode) {
        return productCategoryDao.selectByCategoryNames(brandNameList, companyCode);
    }

    @Override
    public List<ProductCategory> selectByCategoryCodes(Set<String> brandCodeList, String companyCode) {
        return productCategoryDao.selectByCategoryCodes(brandCodeList, companyCode);
    }

    private List<ProductCategory> getChildCategory(List<ProductCategory> productCategories, String categoryId, String companyCode) {
        List<ProductCategory> productCategoryList = productCategoryDao.selectCategoryByParentId(categoryId, companyCode);
        if (CollectionUtils.isNotEmpty(productCategoryList)) {
            productCategoryList.forEach(item -> {
                productCategories.add(item);
                getChildCategory(productCategories, item.getCategoryId(), companyCode);
            });
        }
        return productCategories;
    }

    private String getCode(Long categoryId, Byte currentLevel) {
        categoryId = categoryId + 1;
        String code = categoryId.toString();
        if (Objects.equals(Byte.valueOf("1"), currentLevel)) {
            if (categoryId < 10L) {
                code = "0" + categoryId;
            }
        } else if (Objects.equals(Byte.valueOf("2"), currentLevel)) {
            if (categoryId < 1000L) {
                code = "0" + categoryId;
            }
        } else if (Objects.equals(Byte.valueOf("3"), currentLevel)) {
            if (categoryId < 100000L) {
                code = "0" + categoryId;
            }
        } else if (Objects.equals(Byte.valueOf("4"), currentLevel)) {
            if (categoryId < 10000000L) {
                code = "0" + categoryId;
            }
        }
        return code;
    }
}
