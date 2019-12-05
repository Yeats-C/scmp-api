package com.aiqin.bms.scmp.api.product.service.impl;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.base.ContentTpye;
import com.aiqin.bms.scmp.api.base.EncodingRuleType;
import com.aiqin.bms.scmp.api.common.Save;
import com.aiqin.bms.scmp.api.common.Update;
import com.aiqin.bms.scmp.api.config.AuthenticationInterceptor;
import com.aiqin.bms.scmp.api.constant.Global;
import com.aiqin.bms.scmp.api.product.dao.ProductBrandTypeDao;
import com.aiqin.bms.scmp.api.product.domain.ProductBrandType;
import com.aiqin.bms.scmp.api.product.domain.request.brand.ProductBrandReqDTO;
import com.aiqin.bms.scmp.api.product.domain.request.brand.ProductBrandReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.brand.QueryProductBrandReqVO;
import com.aiqin.bms.scmp.api.product.domain.response.ProductBrandRespVO;
import com.aiqin.bms.scmp.api.product.domain.response.QueryProductBrandRespVO;
import com.aiqin.bms.scmp.api.product.mapper.ApplyProductSkuMapper;
import com.aiqin.bms.scmp.api.product.mapper.ProductSkuDraftMapper;
import com.aiqin.bms.scmp.api.product.mapper.ProductSkuInfoMapper;
import com.aiqin.bms.scmp.api.product.service.ProductBrandService;
import com.aiqin.bms.scmp.api.supplier.dao.EncodingRuleDao;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.EncodingRule;
import com.aiqin.bms.scmp.api.util.AuthToken;
import com.aiqin.bms.scmp.api.util.PageUtil;
import com.aiqin.bms.scmp.api.util.UploadFileUtil;
import com.aiqin.ground.util.exception.GroundRuntimeException;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Description:
 *
 * @author zth
 * @date 2018-12-12
 * @time: 16:42
 */
@Service
@Slf4j
public class ProductBrandServiceImpl implements ProductBrandService {

    @Autowired
    ProductBrandTypeDao productBrandTypeDao;
    @Autowired
    private UploadFileUtil uploadFileUtil;
    @Autowired
    private EncodingRuleDao encodingRuleDao;
    @Autowired
    private ProductSkuInfoMapper skuInfoMapper;
    @Autowired
    private ProductSkuDraftMapper skuDraftMapper;
    @Autowired
    private ApplyProductSkuMapper applyProductSkuMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer save(ProductBrandReqVO s) {
        ProductBrandReqDTO t = new ProductBrandReqDTO();
        BeanUtils.copyProperties(s, t);
//        if(StringUtils.isNotEmpty(t.getBrandLogo())) {
//            //保存图片  图片是非必填
//            String s1 = ((ProductBrandService) AopContext.currentProxy()).uploadImage(s.getBrandLogo());
//            t.setBrandLogo(s1);
//        }
        String companyCode = "";
        if(StringUtils.isBlank(s.getCompanyCode())){
            AuthToken authToken = AuthenticationInterceptor.getCurrentAuthToken();
            if(null != authToken){
                companyCode = authToken.getCompanyCode();
            }
        }else {
            companyCode = s.getCompanyCode();
        }
        ProductBrandType brand = productBrandTypeDao.selectByBrandName(s.getBrandName(),companyCode);
        if(Objects.nonNull(brand)){
            throw new GroundRuntimeException("品牌名称已存在，保存失败！");
        }
        //编码生成
        EncodingRule numberingType = encodingRuleDao.getNumberingType(EncodingRuleType.PRODUCT_BRAND_CODE);
        if(Objects.isNull(numberingType)){
            throw new GroundRuntimeException("无法通过编码生成规则获取编码，保存失败！");
        }
        //设置编码
        t.setBrandId(String.valueOf(numberingType.getNumberingValue()));
        //更新编码
        encodingRuleDao.updateNumberValue(numberingType.getNumberingValue(),numberingType.getId());
        return ((ProductBrandService)AopContext.currentProxy()).saveBrandData(t);
    }

    @Override
    public String uploadImage(String base64) {
        if (!base64.startsWith(ContentTpye.DATA_IMAGE) && !base64.startsWith(ContentTpye.DATA_IMG)) {
            log.error("base64内容不是图片类型", base64.substring(0, 30));
            throw new GroundRuntimeException("base64内容不是图片类型！");
        }
        try {
            return uploadFileUtil.uploadImage(base64);
        } catch (Exception e) {
            log.error("上传图片失败", e);
            throw new GroundRuntimeException("上传图片失败！");
        }
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer saveBrandData(ProductBrandReqDTO s) {
        Integer temp;
        try {
            //保存方法
            ProductBrandType t = new ProductBrandType();
            BeanUtils.copyProperties(s, t);
            //默认禁用
//            t.setBrandStatus(1);
            AuthToken authToken = AuthenticationInterceptor.getCurrentAuthToken();
            if(null != authToken){
                t.setCompanyCode(authToken.getCompanyCode());
                t.setCompanyName(authToken.getCompanyName());
            }
             temp = productBrandTypeDao.insertBrand(t);
            //TODO 保存日志
        } catch (Exception e) {
            log.error(Global.ERROR, e);
            throw new GroundRuntimeException("保存失败！");
        }
        return temp;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer edit(ProductBrandReqVO s) {
       ProductBrandType productBrandType = productBrandTypeDao.selectByPrimaryKey(s.getId());
        if(Objects.isNull(productBrandType)){
            throw new GroundRuntimeException("修改失败！");
        }
        ProductBrandReqDTO t = new ProductBrandReqDTO();
        BeanUtils.copyProperties(s, t);
        t.setOldBrandLogoUrl(productBrandType.getBrandLogo());
        if(!Objects.equals(s.getBrandName(),productBrandType.getBrandName())){
            String companyCode = "";
            if(StringUtils.isNotBlank(s.getCompanyCode())){
                companyCode= s.getCompanyCode();
            }else {
                AuthToken authToken = AuthenticationInterceptor.getCurrentAuthToken();
                if(null != authToken){
                    companyCode = authToken.getCompanyCode();
                }
            }
            ProductBrandType brand = productBrandTypeDao.selectByBrandName(s.getBrandName(),companyCode);
            if(Objects.nonNull(brand)){
                throw new GroundRuntimeException("品牌名称已存在，保存失败！");
            }
        }
        return ((ProductBrandService)AopContext.currentProxy()).editBrandData(t);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Update
    public Integer editBrandData(ProductBrandReqDTO s) {
        Integer temp;
        try {
            //保存方法
            ProductBrandType t = new ProductBrandType();
            BeanUtils.copyProperties(s, t);
            //如果传入的base64不算是空的，则进行保存图片操作
//            if(StringUtils.isNotEmpty(t.getBrandLogo())&&!t.getBrandLogo().startsWith("http")){
////                //先删除
////                boolean b = uploadFileUtil.deleteFile(s.getOldBrandLogoUrl());
////                if(!b){
////                    log.info("修改时图片删除失败");
////                }
//                String s1 = ((ProductBrandService) AopContext.currentProxy()).uploadImage(t.getBrandLogo());
//                t.setBrandLogo(s1);
//            }
            temp = productBrandTypeDao.updateByPrimaryKeySelective(t);
            //TODO 保存日志
        } catch (Exception e) {
            log.error(Global.ERROR, e);
            throw new GroundRuntimeException("修改失败！");
        }
        return temp;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer enable(Long id, Integer enable) {
        ProductBrandType productBrandType = productBrandTypeDao.selectByPrimaryKey(id);
        if(Objects.isNull(productBrandType)){
            throw new GroundRuntimeException("状态更新失败！");
        }
        if(Objects.equals(productBrandType.getBrandStatus(),enable)){
            throw new GroundRuntimeException("状态已经为"+(enable==0?"启用":"禁用")+"无需更改！");
        }
        //如果状态为禁用,需要判断是否有SKU在使用,如果有的话则不允许禁用
        if(Objects.equals(1,enable)){
            //判断正式表
            int i = skuInfoMapper.checkBrand(productBrandType.getBrandId());
            if(i>0){
                throw new GroundRuntimeException("此品牌在SKU中存在,不允许禁用");
            }
            //判断临时表
            i = skuDraftMapper.checkBrand(productBrandType.getBrandId());
            if(i>0){
                throw new GroundRuntimeException("此品牌在SKU中存在,不允许禁用");
            }
            //判断申请表
            i = applyProductSkuMapper.checkBrand(productBrandType.getBrandId());
            if(i>0){
                throw new GroundRuntimeException("此品牌在SKU中存在,不允许禁用");
            }
        }
        ProductBrandReqDTO t = new ProductBrandReqDTO();
        t.setId(productBrandType.getId());
        t.setBrandStatus(enable);
        return ((ProductBrandService)AopContext.currentProxy()).enableBrandData(t);
    }

    @Override
    @Update
    @Transactional(rollbackFor = Exception.class)
    public Integer enableBrandData(ProductBrandReqDTO s) {
        Integer temp;
        try {
            ProductBrandType t = new ProductBrandType();
            BeanUtils.copyProperties(s, t);
            temp = productBrandTypeDao.updateByPrimaryKeySelective(t);
            //TODO 保存日志
        } catch (BeansException e) {
            log.error(Global.ERROR, e);
            throw new GroundRuntimeException("状态更新失败！");
        }
        return temp;
    }

    @Override
    public ProductBrandRespVO view(Long id) {
        ProductBrandType s = productBrandTypeDao.selectByPrimaryKey(id);
        if(Objects.isNull(s)){
            throw new GroundRuntimeException("查询失败！");
        }
        ProductBrandRespVO t = new ProductBrandRespVO();
        BeanUtils.copyProperties(s,t);
        return t;
    }

    @Override
    public BasePage<QueryProductBrandRespVO> selectBrandListByQueryVO(QueryProductBrandReqVO vo) {
        try {
            //前端调用需要封装
            if(StringUtils.isBlank(vo.getCompanyCode())){
                AuthToken authToken = AuthenticationInterceptor.getCurrentAuthToken();
                if(null != authToken){
                    vo.setCompanyCode(authToken.getCompanyCode());
                }
            }
            PageHelper.startPage(vo.getPageNo(), vo.getPageSize());
            List<QueryProductBrandRespVO> userDetails = productBrandTypeDao.selectListByQueryVO(vo);
            return PageUtil.getPageList(vo.getPageNo(),userDetails);
        } catch (Exception ex) {
            log.error("商品品牌位列表查询异常！");
            ex.printStackTrace();
            throw new GroundRuntimeException(ex.getMessage());
        }
    }

    @Override
    public List<ProductBrandType> selectByBrandCodes(List<String> codes) {
        log.debug("ProductBrandServiceImpl-selectByBrandCodes,入参是：[{}]", JSONObject.toJSONString(codes));
        return productBrandTypeDao.selectByBrandCodes(codes);
    }

    @Override
    public Map<String, ProductBrandType> selectByBrandNames(Set<String> brandNameList, String companyCode) {
        return productBrandTypeDao.selectByBrandNames(brandNameList,companyCode);
    }
}
