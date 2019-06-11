package com.aiqin.bms.scmp.api.product.service.impl;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.common.*;
import com.aiqin.bms.scmp.api.config.AuthenticationInterceptor;
import com.aiqin.bms.scmp.api.product.dao.ProductDictionaryDao;
import com.aiqin.bms.scmp.api.product.dao.ProductDictionaryInfoDao;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductDictionary;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductDictionaryInfo;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductOperationLog;
import com.aiqin.bms.scmp.api.product.domain.request.dictionary.DictionaryInfoReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.dictionary.DictionarySaveReqDTO;
import com.aiqin.bms.scmp.api.product.domain.request.dictionary.DictionaryUpdateReqDTO;
import com.aiqin.bms.scmp.api.product.domain.request.dictionary.QueryDictionaryReqVO;
import com.aiqin.bms.scmp.api.product.domain.response.dictionary.DictionaryCodeResVo;
import com.aiqin.bms.scmp.api.product.domain.response.dictionary.DictionaryDetailResVO;
import com.aiqin.bms.scmp.api.product.domain.response.dictionary.DictionaryInfoResponseVO;
import com.aiqin.bms.scmp.api.product.domain.response.dictionary.DictionaryListResVO;
import com.aiqin.bms.scmp.api.product.service.ProductCommonService;
import com.aiqin.bms.scmp.api.product.service.ProductDictionaryService;
import com.aiqin.bms.scmp.api.product.service.ProductOperationLogService;
import com.aiqin.bms.scmp.api.supplier.dao.EncodingRuleDao;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.EncodingRule;
import com.aiqin.bms.scmp.api.util.AuthToken;
import com.aiqin.bms.scmp.api.util.JsonMapper;
import com.aiqin.bms.scmp.api.util.PageUtil;
import com.aiqin.ground.util.exception.GroundRuntimeException;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

@Service
@Slf4j
public class ProductDictionaryServiceImpl implements ProductDictionaryService {
    @Autowired
    private ProductDictionaryDao productDictionaryDao;
    @Autowired
    private ProductDictionaryInfoDao productDictionaryInfoDao;
    @Autowired
    private ProductOperationLogService productOperationLogService;
    @Autowired
    private ProductCommonService productCommonService;
    @Autowired
    private EncodingRuleDao encodingRuleDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public  int batchInsert(DictionarySaveReqDTO productDictionarySaveReqVO) {
        int flg = 0;
        try {
            String codNa = productDictionarySaveReqVO.getDictionaryName();
            AuthToken authToken = AuthenticationInterceptor.getCurrentAuthToken();
            String companyCode = "";
            if(null != authToken){
                companyCode = authToken.getCompanyCode();
            }
            Integer integer = productDictionaryDao.checkName(codNa, null,companyCode);
            if (integer > 0) {
                log.error("字典名称不能重复");
                throw new GroundRuntimeException("字典名称不能重复");
            }
            EncodingRule encodingRule = encodingRuleDao.getNumberingType("PRODUCT_DICTIONARY_CODE");
            long code = encodingRule.getNumberingValue();
            encodingRuleDao.updateNumberValue(code,encodingRule.getId());
            List<ProductDictionaryInfo> save = new LinkedList<>();
            ProductDictionary productDictionary = new ProductDictionary();
            productDictionary.setProductCode(code + "");
            productDictionary.setProductName(codNa);
            productDictionary.setProductType(productDictionarySaveReqVO.getDictionaryType());
            productDictionary.setDelFlag(HandlingExceptionCode.ZERO);
            productDictionary.setEnabled(HandlingExceptionCode.ZERO);
            ((ProductDictionaryService) AopContext.currentProxy()).insertSelective(productDictionary);
            productCommonService.getInstance(code + "", HandleTypeCoce.ADD_PRODUCT_DICTIONARY.getStatus(), ObjectTypeCode.PRODUCT_DICTIONARY.getStatus(), productDictionary, HandleTypeCoce.ADD_PRODUCT_DICTIONARY.getName());
            if (productDictionarySaveReqVO.getListInfo() != null && productDictionarySaveReqVO.getListInfo().size() > 0) {
                productDictionarySaveReqVO.getListInfo().forEach(infoReqVO -> {
                    EncodingRule encodingRule1 = encodingRuleDao.getNumberingType("PRODUCT_DICTIONARY_INFO_CODE");
                    long coif = encodingRule1.getNumberingValue();
                    encodingRuleDao.updateNumberValue(coif, encodingRule1.getId());
                    ProductDictionaryInfo productDictionaryInfo = new ProductDictionaryInfo();
                    productDictionaryInfo.setProductContent(infoReqVO.getDictionaryContent());
                    productDictionaryInfo.setProductDictionaryCode(code + "");
                    productDictionaryInfo.setProductDictionaryName(codNa);
                    productDictionaryInfo.setProductDictionaryInfoCode(coif+"");
                    productDictionaryInfo.setProductDictionaryValue(infoReqVO.getDictionaryValue());
                    productDictionaryInfo.setProductWeight(infoReqVO.getDictionaryWeight());
                    productDictionaryInfo.setDelFlag(HandlingExceptionCode.ZERO);
                    productDictionaryInfo.setEnabled(HandlingExceptionCode.ZERO);
                    save.add(productDictionaryInfo);
                });
            }
            if (save.size() > 0) {
                List<ProductOperationLog> supplierOperationLogList = conversion(save, "save");
                productCommonService.saveList(supplierOperationLogList);
                flg = ((ProductDictionaryService) AopContext.currentProxy()).insertList(save);

            }
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new GroundRuntimeException(ex.getMessage());
        }
        return flg;
    }

    @Override
    public DictionaryDetailResVO getListDictionary(Long id) {
        DictionaryDetailResVO productDictionaryDetailRespVO = new DictionaryDetailResVO();
        if (id != null) {
            ProductDictionary productDictionary = productDictionaryDao.selectByPrimaryKey(id);
            if (productDictionary != null) {
                productDictionaryDetailRespVO.setId(id);
                productDictionaryDetailRespVO.setDictionaryName(productDictionary.getProductName());
                productDictionaryDetailRespVO.setDictionaryType(productDictionary.getProductType());
                productDictionaryDetailRespVO.setDelFlag(productDictionary.getDelFlag());
                productDictionaryDetailRespVO.setEnabled(productDictionary.getEnabled());
                String code = productDictionary.getProductCode();
                if (StringUtils.isNotBlank(code)) {
                    List<DictionaryInfoResponseVO> productDictionaryInfoRespVOS = productDictionaryInfoDao.getCode(code);
                    if (productDictionaryInfoRespVOS != null && productDictionaryInfoRespVOS.size() > 0) {
                        productDictionaryDetailRespVO.setListInfo(productDictionaryInfoRespVOS);
                    } else {
                        productDictionaryDetailRespVO.setListInfo(new ArrayList<>());
                    }
                } else {
                    log.error("关联code 不能为空");
                    throw new GroundRuntimeException("关联code 不能为空");
                }
            } else {
                log.error("当前对象不存在");
                throw new GroundRuntimeException("当前对象不存在");
            }
        }
        return productDictionaryDetailRespVO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int batchUpdate(DictionaryUpdateReqDTO listDto) {
        int flg = 0;
        Long dId = listDto.getId();
        String codNa = listDto.getDictionaryName();
        AuthToken authToken = AuthenticationInterceptor.getCurrentAuthToken();
        String companyCode = "";
        if(null != authToken){
            companyCode = authToken.getCompanyCode();
        }
        Integer integer = productDictionaryDao.checkName(codNa, dId,companyCode);
        if (integer > 0) {
            throw new GroundRuntimeException("字典名称不能重复");
        }
        String code = null;
        List<ProductDictionaryInfo> save = new LinkedList<>();
        List<ProductDictionaryInfo> update = new LinkedList<>();
        if (dId != null && listDto.getListInfo() != null && listDto.getListInfo().size() > 0) {
            ProductDictionary supplierDictionary = productDictionaryDao.selectByPrimaryKey(dId);
            ProductDictionary supplierDictionary1 = new ProductDictionary();
            code = supplierDictionary.getProductCode();
            supplierDictionary1.setProductName(codNa);
            supplierDictionary1.setProductType(listDto.getDictionaryType());
            supplierDictionary1.setDelFlag(listDto.getDelFlag());
            supplierDictionary1.setId(dId);
            supplierDictionary1.setProductCode(code);
            supplierDictionary1.setCreateBy(supplierDictionary.getCreateBy());
            supplierDictionary1.setCreateTime(supplierDictionary.getCreateTime());
            supplierDictionary1.setUpdateBy(supplierDictionary.getUpdateBy());
            supplierDictionary1.setUpdateTime(supplierDictionary.getUpdateTime());
            supplierDictionary1.setEnabled(supplierDictionary.getEnabled());
            ((ProductDictionaryService) AopContext.currentProxy()).updateDictionary(supplierDictionary1);
            productCommonService.getInstance(code, HandleTypeCoce.UPDATE_PRODUCT_DICTIONARY.getStatus(), ObjectTypeCode.PRODUCT_DICTIONARY.getStatus(), supplierDictionary1, HandleTypeCoce.UPDATE_PRODUCT_DICTIONARY.getName());
            String finalCode = code;
            listDto.getListInfo().forEach(infoReqVO -> {
                if (infoReqVO.getId() != null && infoReqVO.getId() != 0) {
                    ProductDictionaryInfo supplierDic = productDictionaryInfoDao.selectByPrimaryKey(infoReqVO.getId());
                    supplierDic.setId(infoReqVO.getId());
                    supplierDic.setEnabled(infoReqVO.getEnabled());
                    supplierDic.setProductContent(infoReqVO.getDictionaryContent());
                    supplierDic.setProductDictionaryCode(finalCode);
                    supplierDic.setProductDictionaryName(codNa);
                    supplierDic.setProductDictionaryValue(infoReqVO.getDictionaryDictionaryValue());
                    supplierDic.setProductWeight(infoReqVO.getDictionaryWeight());
                    update.add(supplierDic);
                } else {
                    EncodingRule encodingRule1 = encodingRuleDao.getNumberingType("PRODUCT_DICTIONARY_INFO_CODE");
                    long coif = encodingRule1.getNumberingValue();
                    encodingRuleDao.updateNumberValue(coif, encodingRule1.getId());
                    ProductDictionaryInfo supplierDic1 = new ProductDictionaryInfo();
                    supplierDic1.setProductContent(infoReqVO.getDictionaryContent());
                    supplierDic1.setProductDictionaryCode(finalCode);
                    supplierDic1.setProductDictionaryName(codNa);
                    supplierDic1.setProductDictionaryValue(infoReqVO.getDictionaryDictionaryValue());
                    supplierDic1.setProductWeight(infoReqVO.getDictionaryWeight());
                    supplierDic1.setEnabled(HandlingExceptionCode.ZERO);
                    supplierDic1.setDelFlag(HandlingExceptionCode.ZERO);
                    supplierDic1.setProductDictionaryInfoCode(coif+"");
                    save.add(supplierDic1);
                }
            });
        }
        if (save.size() > 0) {
            flg = ((ProductDictionaryService) AopContext.currentProxy()).insertList(save);
            List<ProductOperationLog> supplierOperationLogList = conversion(save, "save");
            productCommonService.saveList(supplierOperationLogList);
        }
        if (update.size() > 0) {
            List<ProductOperationLog> supplierOperationLogList = conversion(update, "update");
            productCommonService.saveList(supplierOperationLogList);
            flg = ((ProductDictionaryService) AopContext.currentProxy()).updateList(update);
        }
        return flg;
    }

    @Override
    public BasePage<DictionaryListResVO> listDictionary(QueryDictionaryReqVO querDictionaryReqVO) {
        try {
            AuthToken authToken = AuthenticationInterceptor.getCurrentAuthToken();
            if(null != authToken){
                querDictionaryReqVO.setCompanyCode(authToken.getCompanyCode());
            }
            PageHelper.startPage(querDictionaryReqVO.getPageNo(), querDictionaryReqVO.getPageSize());
            List<DictionaryListResVO> querySupplierDictionaryResVOS = productDictionaryDao.listDictionary(querDictionaryReqVO);
            return PageUtil.getPageList(querDictionaryReqVO.getPageNo(), querySupplierDictionaryResVOS);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new GroundRuntimeException(e.getMessage());
        }
    }


    @Override
    public List<ProductDictionaryInfo> getList(String code) {
        return productDictionaryInfoDao.dictionaryCode(code);
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @Override
    @Transactional
    public int offOrOn(Long id) {
        int resultNum = 0;
        try {
            if (id != null) {
                ProductDictionary productDictionary = productDictionaryDao.selectByPrimaryKey(id);
                if (productDictionary != null) {
                    String code = productDictionary.getProductCode();
                    productDictionary.setDelFlag((byte) 1);
                    if (StringUtils.isNotBlank(code)) {
                        List<ProductDictionaryInfo> supplierDictionaries = getList(code);
                        List<ProductOperationLog> supplierOperationLogList = conversion(supplierDictionaries, "delete");
                        productDictionaryInfoDao.deleteList(code);
                        productCommonService.saveList(supplierOperationLogList);
                        productCommonService.getInstance(code, HandleTypeCoce.DELETE_PRODUCT_DICTIONARY.getStatus(), ObjectTypeCode.PRODUCT_DICTIONARY.getStatus(), productDictionary, HandleTypeCoce.DELETE_PRODUCT_DICTIONARY.getName());
                        resultNum = productDictionaryDao.offOrOn(id);
                    } else {
                        log.error("code=>关联不存在");
                        throw new GroundRuntimeException("code 关联不存在");
                    }
                } else {
                    log.error("不存在该对象");
                    throw new GroundRuntimeException("不存在该对象");
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new GroundRuntimeException(e.getMessage());
        }
        return resultNum;
    }

    @Override
    @Transactional
    @Save
    public int insertSelective(ProductDictionary record) {
        AuthToken authToken = AuthenticationInterceptor.getCurrentAuthToken();
        if(null != authToken){
            record.setCompanyCode(authToken.getCompanyCode());
            record.setCompanyName(authToken.getCompanyName());
        }
        int k = productDictionaryDao.insertSelective(record);
        if (k > 0) {
            return k;
        } else {
            log.error("插入商品字典失败");
            throw new GroundRuntimeException("插入商品字典失败");
        }
    }

    @Override
    @Update
    @Transactional
    public int updateDictionary(ProductDictionary supplierDictionary) {
        int k = productDictionaryDao.updateByPrimaryKeySelective(supplierDictionary);
        if (k > 0) {
            return k;
        } else {
            log.error("更新商品字典失败");
            throw new GroundRuntimeException("更新商品字典失败");
        }
    }

    @Override
    @Transactional
    public int enabled(Long id, Byte status) {
        if (id != null) {
            ProductDictionary productDictionary = productDictionaryDao.selectByPrimaryKey(id);
            if (productDictionary != null) {
                String code = productDictionary.getProductCode();
                productDictionary.setEnabled(status);
                if (status == 0) {
                    productCommonService.getInstance(code, HandleTypeCoce.ENABLED_PRODUCT_DICTIONARY.getStatus(), ObjectTypeCode.PRODUCT_DICTIONARY.getStatus(), productDictionary, HandleTypeCoce.ENABLED_PRODUCT_DICTIONARY.getName());
                } else {
                    productCommonService.getInstance(code, HandleTypeCoce.NO_ENABLED_PRODUCT_DICTIONARY.getStatus(), ObjectTypeCode.PRODUCT_DICTIONARY.getStatus(), productDictionary, HandleTypeCoce.NO_ENABLED_PRODUCT_DICTIONARY.getName());
                }
                 productDictionaryInfoDao.infoEnabled(status,code);
                return productDictionaryDao.updateByPrimaryKeySelective(productDictionary);
            } else {
                log.error("字典不能为空");
                throw new GroundRuntimeException("字典不能为空");
            }
        } else {
            log.error("id不能为空");
            throw new GroundRuntimeException("id不能为空");
        }
    }

    @Override
    @SaveList
    @Transactional
    public int insertList(Collection<ProductDictionaryInfo> list) {
        int k = productDictionaryInfoDao.insertList(list);
        if (k > 0) {
            return k;
        } else {
            throw new GroundRuntimeException("批量新增字典详情失败");
        }
    }

    @Override
    @Transactional
    @UpdateList
    public int updateList(Collection<ProductDictionaryInfo> list) {
        int k = productDictionaryInfoDao.updateList(list);
        if (k > 0) {
            return k;
        } else {
            log.error("批量修改字典详情失败");
            throw new GroundRuntimeException("批量修改字典详情失败");
        }
    }

    @Override
    public List<ProductOperationLog> conversion(List<ProductDictionaryInfo> productDictionaryInfos, String status) {
        List<ProductOperationLog> supplierlist = new LinkedList<>();
        if (productDictionaryInfos != null && productDictionaryInfos.size() > 0) {
            productDictionaryInfos.forEach(dictionaryInfo -> {
                ProductOperationLog supplierOperationLog = new ProductOperationLog();
                supplierOperationLog.setContent(JsonMapper.toJsonString(dictionaryInfo));
                supplierOperationLog.setObjectType(ObjectTypeCode.PRODUCT_DICTIONARY.getStatus());
                if ("delete".equals(status)) {
                    dictionaryInfo.setDelFlag((byte) 1);
                    supplierOperationLog.setHandleType(HandleTypeCoce.DELETE_PRODUCT_DICTIONARY_INFO.getStatus());
                }
                if ("save".equals(status)) {
                    supplierOperationLog.setHandleType(HandleTypeCoce.ADD_PRODUCT_DICTIONARY_INFO.getStatus());
                }
                if ("update".equals(status)) {
                    supplierOperationLog.setHandleType(HandleTypeCoce.UPDATE_PRODUCT_DICTIONARY_INFO.getStatus());
                }
                supplierOperationLog.setObjectId(dictionaryInfo.getProductDictionaryInfoCode());
                supplierlist.add(supplierOperationLog);
            });
        } else {
            log.error("字典详细不存在");
            throw new GroundRuntimeException("字典详细不存在");
        }
        return supplierlist;
    }

    @Override
    public List<DictionaryCodeResVo> getCode(DictionaryInfoReqVO dictionaryInfoReqVO) {
        AuthToken authToken = AuthenticationInterceptor.getCurrentAuthToken();
        if(null != authToken){
            dictionaryInfoReqVO.setCompanyCode(authToken.getCompanyCode());
        }
        return productDictionaryDao.getCode(dictionaryInfoReqVO);
    }


}
