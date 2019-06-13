package com.aiqin.bms.scmp.api.product.service.impl;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.base.WorkFlowBaseUrl;
import com.aiqin.bms.scmp.api.common.*;
import com.aiqin.bms.scmp.api.config.CommonInterceptor;
import com.aiqin.bms.scmp.api.product.dao.ProductSkuPriceDao;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductOperationLog;
import com.aiqin.bms.scmp.api.product.domain.pojo.VariablePrice;
import com.aiqin.bms.scmp.api.product.domain.pojo.VariablePriceSku;
import com.aiqin.bms.scmp.api.product.domain.request.ApplyStatus;
import com.aiqin.bms.scmp.api.product.domain.request.OperationLogVo;
import com.aiqin.bms.scmp.api.product.domain.request.variableprice.*;
import com.aiqin.bms.scmp.api.product.domain.response.LogData;
import com.aiqin.bms.scmp.api.product.domain.response.variableprice.*;
import com.aiqin.bms.scmp.api.product.mapper.*;
import com.aiqin.bms.scmp.api.product.service.*;
import com.aiqin.bms.scmp.api.supplier.dao.EncodingRuleDao;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.EncodingRule;
import com.aiqin.bms.scmp.api.util.BeanCopyUtils;
import com.aiqin.bms.scmp.api.util.JsonMapper;
import com.aiqin.bms.scmp.api.util.PageUtil;
import com.aiqin.bms.scmp.api.util.UUIDUtils;
import com.aiqin.bms.scmp.api.workflow.annotation.WorkFlow;
import com.aiqin.bms.scmp.api.workflow.vo.request.WorkFlowCallbackVO;
import com.aiqin.bms.scmp.api.workflow.vo.request.WorkFlowVO;
import com.aiqin.bms.scmp.api.workflow.vo.response.WorkFlowRespVO;
import com.aiqin.ground.util.exception.GroundRuntimeException;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.lang.StringUtils;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;


@Service
@Slf4j
public class VariablePriceServiceImplProduct extends ProductBaseServiceImpl implements VariablePriceService {
    @Autowired
    private VariablePriceMapper variablePriceMapper;
    @Autowired
    private ProductCommonService productCommonService;
    @Autowired
    private EncodingRuleDao encodingRuleDao;
    @Autowired
    private ProductSkuInfoMapper productSkuInfoMapper;
    @Autowired
    private ProductSkuInfoDao productSkuInfoDao;
    @Autowired
    private ProductOperationLogService productOperationLogService;
    @Autowired
    private ProductSkuPriceDao productSkuPriceDao;
    @Value("${Purchase.url}")
    public String PURCHASE_API_URL;
    @Autowired
    private WorkFlowBaseUrl workFlowBaseUrl;
    @Autowired
    private CommonInterceptor commonInterceptor;
    @Autowired
    private VariablePriceSkuService variablePriceSkuService;
    @Autowired
    private StockService stockService;
    @Autowired
    private VariablePriceSkuMapper variablePriceSkuMapper;
    @Autowired
    private ProductSkuPriceMapper productSkuPriceMapper;


    @Override
    @Transactional
    @Save
    public int insertSelective(VariablePrice record) {
        int k = variablePriceMapper.insertSelective(record);
        if (k > 0) {
            return k;
        } else {
            log.error(HandlingExceptionCode.VARIABLE_PRICE);
            throw new GroundRuntimeException(HandlingExceptionCode.VARIABLE_PRICE);
        }
    }

    @Override
    @Transactional
    @Update
    public int updateByPrimaryKeySelective(VariablePrice record) {
        int k = variablePriceMapper.updateByPrimaryKeySelective(record);
        if (k > 0) {
            return k;
        } else {
            log.error(HandlingExceptionCode.UPDATE_VARIABLE_PRICE);
            throw new GroundRuntimeException(HandlingExceptionCode.UPDATE_VARIABLE_PRICE);
        }
    }

    @Override
    @SaveList
    @Transactional
    public int insertList(List<VariablePrice> variablePrices) {
        int k = variablePriceMapper.insertList(variablePrices);
        if (k > 0) {
            return k;
        } else {
            log.error(HandlingExceptionCode.LIST_VARIABLE_PRICE);
            throw new GroundRuntimeException(HandlingExceptionCode.LIST_VARIABLE_PRICE);

        }
    }

    /**
     * 新建变价
     *
     * @param variablePriceReqVos
     * @return
     */
    @Override
    @Transactional
    public int saveList(VariablePriceReqVo variablePriceReqVos) {
        int flag = 0;
        String priceTypeCode = variablePriceReqVos.getPriceTypeCode();
        try {
            String varCode = variablePriceReqVos.getVariablePriceCode();
            if (StringUtils.isNotBlank(varCode)) {
                VariablePrice variablePrice1 = variablePriceMapper.selectByPrimaryCode(varCode);
                if (!variablePriceReqVos.getVariablePriceName().equals(variablePrice1.getVariablePriceName())) {
                    Integer size = getVarName(variablePriceReqVos.getVariablePriceName());
                    if (size != null && size > 0) {
                        throw new GroundRuntimeException("变价名称已经存在");
                    }
                } else {
                    Integer size = getVarName(variablePriceReqVos.getVariablePriceName());
                    if (size != null && size > 1) {
                        throw new GroundRuntimeException("变价名称已经存在");
                    }
                }
                variablePrice1.setPriceTypeCode(priceTypeCode);
                variablePrice1.setPriceTypeName(variablePriceReqVos.getPriceTypeName());
                variablePrice1.setVariablePriceName(variablePriceReqVos.getVariablePriceName());
                variablePrice1.setRemark(variablePriceReqVos.getRemark());
                variablePrice1.setStatus(variablePriceReqVos.getStatus());
                ((VariablePriceService) AopContext.currentProxy()).updateByPrimaryKeySelective(variablePrice1);
                productCommonService.getInstance(varCode, HandleTypeCoce.ADD_TO_EXAMINE_VARIABLE_PRICE.getStatus(), ObjectTypeCode.PRICE_MANAGEMENT.getStatus(), variablePrice1, HandleTypeCoce.ADD_TO_EXAMINE_VARIABLE_PRICE.getName());
            } else {
                EncodingRule encodingRule = encodingRuleDao.getNumberingType("VARIABLE_PRICE");
                long code = encodingRule.getNumberingValue();
                encodingRuleDao.updateNumberValue(code, encodingRule.getId());
                varCode = code + "";
                Integer size = getVarName(variablePriceReqVos.getVariablePriceName());
                if (size != null && size > 0) {
                    throw new GroundRuntimeException("变价名称已经存在");
                }
                VariablePrice variablePrice = new VariablePrice(code + "", priceTypeCode, variablePriceReqVos.getPriceTypeName()
                        , variablePriceReqVos.getVariablePriceName(), variablePriceReqVos.getProcurementSectionCode(), variablePriceReqVos.getProcurementSectionName(), variablePriceReqVos.getRemark(), variablePriceReqVos.getStatus(), HandlingExceptionCode.ZERO);
                ((VariablePriceService) AopContext.currentProxy()).insertSelective(variablePrice);
                productCommonService.getInstance(code + "", HandleTypeCoce.ADD_VARIABLE_PRICE.getStatus(), ObjectTypeCode.PRICE_MANAGEMENT.getStatus(), variablePrice, HandleTypeCoce.ADD_VARIABLE_PRICE.getName());
            }
            List<VariablePriceListReqVo>variablePriceListReqVos=variablePriceReqVos.getVariablePriceListReqVos();
            if(variablePriceListReqVos!=null && variablePriceListReqVos.size()>0){
                List<String>skuCodes=variablePriceListReqVos.stream().map(VariablePriceListReqVo::getSkuCode).collect(Collectors.toList());
               for (String sku:skuCodes){
                  int size=getIsDefault(variablePriceListReqVos,sku);
                  if(size>1){
                      throw new GroundRuntimeException("该skuCode 有两个默认值");
                  }
               }
            }
            List<VariablePriceSku> variablePriceSkus = conversion(variablePriceListReqVos, varCode, variablePriceReqVos.getVariablePriceName());
            List<ProductOperationLog> productOperationLogs = new LinkedList<>();
            variablePriceSkus.forEach(variablePrices -> {
                ProductOperationLog productOperationLog = new ProductOperationLog();
                if (HandlingExceptionCode.ONE.equals(variablePriceReqVos.getStatus())) {
                    productOperationLog.setHandleType(HandleTypeCoce.ADD_TO_EXAMINE_VARIABLE_PRICE_LIST.getStatus());
                    productOperationLog.setObjectId(variablePrices.getVariablePriceSkuCode());
                    String contentJson = JsonMapper.toJsonString(variablePrices);
                    productOperationLog.setContent(contentJson);
                    productOperationLog.setObjectType(ObjectTypeCode.PRICE_MANAGEMENT.getStatus());
                }
                if (HandlingExceptionCode.ZERO.equals(variablePriceReqVos.getStatus())) {
                    productOperationLog.setHandleType(HandleTypeCoce.ADD_VARIABLE_PRICE_LIST.getStatus());
                    productOperationLog.setObjectId(variablePrices.getVariablePriceSkuCode());
                    String contentJson = JsonMapper.toJsonString(variablePrices);
                    productOperationLog.setContent(contentJson);
                    productOperationLog.setObjectType(ObjectTypeCode.PRICE_MANAGEMENT.getStatus());
                }
                productOperationLogs.add(productOperationLog);
            });
            if (variablePriceSkus.size() > 0) {
                flag = variablePriceSkuService.insertList(variablePriceSkus);
                if (HandlingExceptionCode.ONE.equals(variablePriceReqVos.getStatus())) {
                    workFlow(variablePriceReqVos.getVariablePriceName(), priceTypeCode, varCode);
                }
                productCommonService.saveList(productOperationLogs);
            }
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new GroundRuntimeException(ex.getMessage());
        }
        return flag;
    }

    @Override
    public List<VariablePriceSku> conversion(List<VariablePriceListReqVo> variablePriceListReqVos, String code, String variablePriceName) {
        List<VariablePriceSku> variablePriceSkus = new LinkedList<>();
        variablePriceListReqVos.forEach(variablePriceListRe -> {
            VariablePriceSku variablePriceSku = new VariablePriceSku();
            BeanCopyUtils.copy(variablePriceListRe, variablePriceSku);
            variablePriceSku.setVariablePriceSkuCode(UUIDUtils.getUUID());
            variablePriceSku.setPriceRevoke(HandlingExceptionCode.ZERO);
            variablePriceSku.setVariablePriceCode(code);
            variablePriceSku.setVariablePriceName(variablePriceName);
            variablePriceSkus.add(variablePriceSku);
        });
        return variablePriceSkus;
    }

    /**
     * 选择商品
     *
     * @param skuDataListReqVo
     * @return
     */
    @Override
    public BasePage<SkuDataListResponse> getDataList(SkuDataListReqVo skuDataListReqVo) {
        try {
            PageHelper.startPage(skuDataListReqVo.getPageNo(), skuDataListReqVo.getPageSize());
            List<SkuDataListResponse> list = productSkuInfoDao.getDataList(skuDataListReqVo);
            List<String> skuCode = list.stream().map(a -> a.getSkuCode()).collect(Collectors.toList());
            if (list != null && list.size() > 0) {
                Map<String, Long> longMap = getLatestPurchasePriceBySkuCodes(skuCode);
                conversion(list, skuDataListReqVo.getPriceTypeCode(), longMap);
            }
            return PageUtil.getPageList(skuDataListReqVo.getPageNo(), list);
        } catch (Exception ex) {
            throw new GroundRuntimeException(ex.getMessage());
        }
    }

    @Override
    public void conversion(List<SkuDataListResponse> skuDataListResponses, String priceTypeCode, Map<String, Long> objectMap) {
        skuDataListResponses.stream().forEach(
                skuDataListResponse -> {
                    if (HandlingExceptionCode.PURCHASE_PRICE.equals(priceTypeCode)) {
                        skuDataListResponse.setNewPurchasingPrice(objectMap.get(skuDataListResponse.getSkuCode()));
                    }
                }
        );
    }

    @Override
    public int getIsDefault(List<VariablePriceListReqVo> variablePriceListReqVos, String code) {
        int k = 0;
        for (VariablePriceListReqVo vspl : variablePriceListReqVos
                ) {
            if (vspl.getSkuCode().equals(code)) {
                if (HandlingExceptionCode.ONE.equals(vspl.getIsDefault())) {
                    ++k;
                }
            }
        }
        return k;
    }

    /**
     * 批量更新变价相关数据库
     *
     * @param variablePrices
     * @param priceTypeCode
     * @return
     */
    @Override
    public Integer updateList(List<VariablePriceSku> variablePrices, String priceTypeCode) {
        try {
            Map<String, String> map = commonInterceptor.getRequest();
            String userName = null;
            if (map != null) {
                userName = map.get("userName");
            }
            List<VariblePriceUpdate> updates = new LinkedList<>();
            String finalUserName = userName;
            variablePrices.forEach(variablePrice -> {
                VariblePriceUpdate variblePriceUpdate = new VariblePriceUpdate();
                variblePriceUpdate.setSkuCode(variablePrice.getSkuCode());
                switch (priceTypeCode) {
                    case HandlingExceptionCode.PURCHASE_PRICE:
                        variblePriceUpdate.setNewPrice(variablePrice.getNewTaxedPurchasingPrice());
                        break;
                    case HandlingExceptionCode.DISTRIBUTION_PRICE:
                        variblePriceUpdate.setNewPrice(variablePrice.getNewTaxedMembershipPrice());
                        break;
                    case HandlingExceptionCode.TEMPORARY_DISTRIBUTION_PRICE:
                        variblePriceUpdate.setNewPrice(variablePrice.getNewTaxedMembershipPrice());
                        break;
                    case HandlingExceptionCode.PRICE:
                        variblePriceUpdate.setNewPrice(variablePrice.getNewTaxedMembershipPrice());
                        break;
                    case HandlingExceptionCode.TEMPORARY_SELLING_PRICE:
                        variblePriceUpdate.setNewPrice(variablePrice.getTemporaryTaxedPrice());
                        break;
                    case HandlingExceptionCode.MEMBERSHIP_PRICE:
                        variblePriceUpdate.setNewPrice(variablePrice.getNewTaxedMembershipPrice());
                        break;
                    case HandlingExceptionCode.LARGE_EFFECT_PRICE:
                        variblePriceUpdate.setNewPrice(variablePrice.getNewTaxedMembershipPrice());
                        break;
                    default:
                        break;
                }
                variblePriceUpdate.setPriceTypeCode(priceTypeCode);
                variblePriceUpdate.setUpdateBy(finalUserName);
                updates.add(variblePriceUpdate);
            });
            return productSkuPriceDao.updateList(updates);
        } catch (Exception ex) {
            throw new GroundRuntimeException(ex.getMessage());
        }
    }

    /**
     * 变价详细
     *
     * @param priceTypeCode
     * @param variablePriceCode
     * @return
     */
    @Override
    public VariableDetailList getVariableDetail(String priceTypeCode, String variablePriceCode) {
        VariableDetailList variableDetailList = new VariableDetailList();
        try {
            VariablePrice variablePrice = variablePriceMapper.variableDetail(priceTypeCode, variablePriceCode, null);
            if (variablePrice != null) {
                BeanCopyUtils.copy(variablePrice, variableDetailList);
            }
            List<VariablePriceSku> variablePriceSkus = variablePriceSkuService.getList(variablePriceCode);
            variableDetailList.setVariablePriceSkus(variablePriceSkus);
            OperationLogVo operationLogVo = new OperationLogVo();
            operationLogVo.setObjectId(variablePriceCode);
            operationLogVo.setObjectType(ObjectTypeCode.PRICE_MANAGEMENT.getStatus());
            List<LogData> logData = productOperationLogService.getLogType(operationLogVo);
            variableDetailList.setDataList(logData);
        } catch (Exception ex) {
            throw new GroundRuntimeException(ex.getMessage());
        }
        return variableDetailList;
    }

    /*
    价格管理
     */
    @Override
    public BasePage<PriceManagement> getManagement(PriceManagementReqVo priceManagementReqVo) {
        try {
            PageHelper.startPage(priceManagementReqVo.getPageNo(), priceManagementReqVo.getPageSize());
            List<PriceManagement> priceManagements = variablePriceMapper.getManagement(priceManagementReqVo);
            return PageUtil.getPageList(priceManagementReqVo.getPageNo(), priceManagements);
        } catch (Exception ex) {
            throw new GroundRuntimeException(ex.getMessage());
        }
    }

    /**
     * 变价管理
     *
     * @param variablePriceManagementReqVO
     * @return
     */
    @Override
    public BasePage<VariablePriceManagementResVO> getPriceDataList(VariablePriceManagementReqVO variablePriceManagementReqVO) {
        try {
            PageHelper.startPage(variablePriceManagementReqVO.getPageNo(), variablePriceManagementReqVO.getPageSize());
            List<VariablePriceManagementResVO> priceManagements = variablePriceMapper.getPriceDataList(variablePriceManagementReqVO);
            return PageUtil.getPageList(variablePriceManagementReqVO.getPageNo(), priceManagements);
        } catch (Exception ex) {
            throw new GroundRuntimeException(ex.getMessage());
        }
    }

    /**
     * 批量导入
     *
     * @param useList
     * @param priceTypeCode
     * @return
     */
    @Override
    public List<ErrorVariableResponse> batchImport(List<ExcelData> useList, String priceTypeCode) {
        try {
            return encapsulation(useList, priceTypeCode);
        } catch (Exception ex) {
            throw new GroundRuntimeException(ex.getMessage());
        }
    }

    /**
     * 封装变价
     *
     * @param excelData
     * @param priceTypeCode
     * @return
     */
    @Override
    public List<ErrorVariableResponse> encapsulation(List<ExcelData> excelData, String priceTypeCode) {
        List<ErrorVariableResponse> errorVariableResponses = null;
        List<String> skuCode = excelData.stream().map(a -> a.getSkuCode()).collect(Collectors.toList());
        if (skuCode.size() > 0) {
            errorVariableResponses = productSkuInfoDao.getSkuCode(skuCode, priceTypeCode);
            if (errorVariableResponses == null) {
                errorVariableResponses = new LinkedList<>();
            }
        }
        Map<String, Long> longMap = getLatestPurchasePriceBySkuCodes(skuCode);
        if (errorVariableResponses.size() > 0) {
            errorVariableResponses.stream().forEach(
                    errorVariable -> {
                        longMap.forEach((key, value) -> {
                            if (key.equals(errorVariable.getSkuCode()) && (HandlingExceptionCode.PURCHASE_PRICE.equals(errorVariable.getPriceTypeCode())
                                    || HandlingExceptionCode.PURCHASE_PRICE_NAME.equals(errorVariable.getPriceTypeName()))) {
                                errorVariable.setNewPurchasingPrice(value);
                            }
                        });
                    });
        }
        List<ErrorVariableResponse> finalErrorVariableResponses2 = new LinkedList<>();
        List<String> stringSet = errorVariableResponses.stream().map(a -> a.getSkuCode()).collect(Collectors.toList());
        List<String> names = errorVariableResponses.stream().map(a -> a.getSkuName()).collect(Collectors.toList());
        List<String> list = excelData.stream().map(a -> a.getSkuCode()).collect(Collectors.toList());
        Map<String, String> stringMap = excelData.stream().collect(
                Collectors.toMap(ExcelData::getSkuCode, ExcelData::getSkuName));
        Map<String, Long> maps = excelData.stream().collect(
                Collectors.toMap(ExcelData::getSkuCode, ExcelData::getPriceValue));
        Map<String, ErrorVariableResponse> excelDataMap = errorVariableResponses.stream().collect(Collectors.toMap(b -> b.getSkuCode(), a -> a));
        list.forEach(
                exce -> {
                    if (!stringSet.contains(exce)) {
                        ErrorVariableResponse errorVariableResponse = new ErrorVariableResponse();
                        errorVariableResponse.setErrorReason("当前skuCode,sku名称数据库不存在");
                        errorVariableResponse.setSkuCode(exce);
                        errorVariableResponse.setSkuName(stringMap.get(exce));
                        getSwitch(priceTypeCode, errorVariableResponse, maps.get(exce));
                        finalErrorVariableResponses2.add(errorVariableResponse);
                    }
                }
        );
        list.forEach(
                exce -> {
                    if (stringSet.contains(exce) && !names.contains(stringMap.get(exce))) {
                        ErrorVariableResponse errorVariableResponse = excelDataMap.get(exce);
                        errorVariableResponse.setErrorReason("sku名称数据库不存在");
                        errorVariableResponse.setSkuCode(exce);
                        errorVariableResponse.setSkuName(stringMap.get(exce));
                        getSwitch(priceTypeCode, errorVariableResponse, maps.get(exce));
                    }
                }
        );
        errorVariableResponses.addAll(finalErrorVariableResponses2);
        finalErrorVariableResponses2.clear();
        List<ErrorVariableResponse> finalErrorVariableResponses = new LinkedList<>();
        excelData.stream().forEach(errorVaria -> {
            val b = finalErrorVariableResponses.stream().filter(errorVariableResponse -> {
                if (errorVariableResponse.getSkuCode().equals(errorVaria.getSkuCode())) {
                    switch (priceTypeCode) {
                        case HandlingExceptionCode.PURCHASE_PRICE:
                            errorVariableResponse.setNewPurchasingPrice(errorVaria.getPriceValue());
                            break;
                        case HandlingExceptionCode.DISTRIBUTION_PRICE:
                            errorVariableResponse.setNewTaxedMembershipPrice(errorVaria.getPriceValue());
                            break;
                        case HandlingExceptionCode.TEMPORARY_DISTRIBUTION_PRICE:
                            errorVariableResponse.setNewTaxedMembershipPrice(errorVaria.getPriceValue());
                            break;
                        case HandlingExceptionCode.PRICE:
                            errorVariableResponse.setNewTaxedMembershipPrice(errorVaria.getPriceValue());
                            break;
                        case HandlingExceptionCode.TEMPORARY_SELLING_PRICE:
                            errorVariableResponse.setTemporaryTaxedPrice(errorVaria.getPriceValue());
                            break;
                        case HandlingExceptionCode.MEMBERSHIP_PRICE:
                            errorVariableResponse.setNewTaxedMembershipPrice(errorVaria.getPriceValue());
                            break;
                        case HandlingExceptionCode.LARGE_EFFECT_PRICE:
                            errorVariableResponse.setNewTaxedMembershipPrice(errorVaria.getPriceValue());
                            break;
                        case HandlingExceptionCode.PURCHASE_PRICE_NAME:
                            errorVariableResponse.setNewPurchasingPrice(errorVaria.getPriceValue());
                            break;
                        case HandlingExceptionCode.DISTRIBUTION_PRICE_NAME:
                            errorVariableResponse.setNewTaxedMembershipPrice(errorVaria.getPriceValue());
                            break;
                        case HandlingExceptionCode.TEMPORARY_DISTRIBUTION_PRICE_NAME:
                            errorVariableResponse.setNewTaxedMembershipPrice(errorVaria.getPriceValue());
                            break;
                        case HandlingExceptionCode.PRICE_NAME:
                            errorVariableResponse.setNewTaxedMembershipPrice(errorVaria.getPriceValue());
                            break;
                        case HandlingExceptionCode.TEMPORARY_SELLING_PRICE_NAME:
                            errorVariableResponse.setTemporaryTaxedPrice(errorVaria.getPriceValue());
                            break;
                        case HandlingExceptionCode.MEMBERSHIP_PRICE_NAME:
                            errorVariableResponse.setNewTaxedMembershipPrice(errorVaria.getPriceValue());
                            break;
                        case HandlingExceptionCode.LARGE_EFFECT_PRICE_NAME:
                            errorVariableResponse.setNewTaxedMembershipPrice(errorVaria.getPriceValue());
                            break;
                        default:
                            break;
                    }
                }
                return true;
            }).collect(Collectors.toList());
        });
        errorVariableResponses.addAll(finalErrorVariableResponses);
        finalErrorVariableResponses.clear();
        return errorVariableResponses;
    }

    /**
     * 最新采购价
     *
     * @param skuCodes
     * @return
     */
    @Override
    public Map<String, Long> getLatestPurchasePriceBySkuCodes(List<String> skuCodes) {
        return stockService.getLatestPurchasePriceBySkuCodes(skuCodes);
    }

    /**
     * 确认变价
     *
     * @param variablePriceCode
     * @param priceTypeCode
     * @return
     */
    @Override
    public ConfirmPriceList getConfirm(String variablePriceCode, String priceTypeCode) {
        try {
            ConfirmPriceList confirmPriceList = new ConfirmPriceList();
            if (StringUtils.isNotBlank(variablePriceCode)) {
                VariablePrice variablePrice = variablePriceMapper.variableDetail(priceTypeCode, variablePriceCode, HandlingExceptionCode.ZERO);
                List<VariablePriceSku> variablePriceSkuList = variablePriceSkuService.getList(variablePriceCode);
                if (variablePrice != null) {
                    BeanCopyUtils.copy(variablePrice, confirmPriceList);
                }
                confirmPriceList.setVariablePriceSkus(variablePriceSkuList);
                return confirmPriceList;
            } else {
                throw new GroundRuntimeException("variablePriceCode不能为空");
            }
        } catch (Exception ex) {
            throw new GroundRuntimeException(ex.getMessage());
        }
    }

    /**
     * 根据价格类型返回实体
     *
     * @param priceType
     * @param errorVariableResponse
     * @param priceValue
     * @return
     */
    @Override
    public ErrorVariableResponse getSwitch(String priceType, ErrorVariableResponse errorVariableResponse, Long priceValue) {
        switch (priceType) {
            case HandlingExceptionCode.PURCHASE_PRICE:
                errorVariableResponse.setNewPurchasingPrice(priceValue);
                break;
            case HandlingExceptionCode.DISTRIBUTION_PRICE:
                errorVariableResponse.setNewTaxedMembershipPrice(priceValue);
                break;
            case HandlingExceptionCode.TEMPORARY_DISTRIBUTION_PRICE:
                errorVariableResponse.setNewTaxedMembershipPrice(priceValue);
                break;
            case HandlingExceptionCode.PRICE:
                errorVariableResponse.setNewTaxedMembershipPrice(priceValue);
                break;
            case HandlingExceptionCode.TEMPORARY_SELLING_PRICE:
                errorVariableResponse.setTemporaryTaxedPrice(priceValue);
                break;
            case HandlingExceptionCode.MEMBERSHIP_PRICE:
                errorVariableResponse.setNewTaxedMembershipPrice(priceValue);
                break;
            case HandlingExceptionCode.LARGE_EFFECT_PRICE:
                errorVariableResponse.setNewTaxedMembershipPrice(priceValue);
                break;
            case HandlingExceptionCode.PURCHASE_PRICE_NAME:
                errorVariableResponse.setNewPurchasingPrice(priceValue);
                break;
            case HandlingExceptionCode.DISTRIBUTION_PRICE_NAME:
                errorVariableResponse.setNewTaxedMembershipPrice(priceValue);
                break;
            case HandlingExceptionCode.TEMPORARY_DISTRIBUTION_PRICE_NAME:
                errorVariableResponse.setNewTaxedMembershipPrice(priceValue);
                break;
            case HandlingExceptionCode.PRICE_NAME:
                errorVariableResponse.setNewTaxedMembershipPrice(priceValue);
                break;
            case HandlingExceptionCode.TEMPORARY_SELLING_PRICE_NAME:
                errorVariableResponse.setTemporaryTaxedPrice(priceValue);
                break;
            case HandlingExceptionCode.MEMBERSHIP_PRICE_NAME:
                errorVariableResponse.setNewTaxedMembershipPrice(priceValue);
                break;
            case HandlingExceptionCode.LARGE_EFFECT_PRICE_NAME:
                errorVariableResponse.setNewTaxedMembershipPrice(priceValue);
                break;
            default:
                break;
        }
        return errorVariableResponse;
    }

    /**
     * 价格详细
     *
     * @param
     * @param
     * @return
     */
    @Override
    public PriceDetailedResponse getPriceSku(Long priceSkuId) {
        PriceDetailedResponse priceDetailedResponse = null;
        try {
            priceDetailedResponse = productSkuPriceDao.getPriceSku(priceSkuId);
            if (priceDetailedResponse != null) {
                priceDetailedResponse.setPriceDetailedLogResponses(new LinkedList<>());
            } else {
                priceDetailedResponse = new PriceDetailedResponse();
                priceDetailedResponse.setPriceDetailedLogResponses(new LinkedList<>());
            }
            return priceDetailedResponse;
        } catch (Exception ex) {
            throw new GroundRuntimeException(ex.getMessage());
        }
    }


    @Override
    public void workFlow(String variablePriceName, String priceTypeCode, String variablePriceCode) {
        log.info("ApplyContractServiceImplSupplier-workFlow-传入参数是：[{}]", JSON.toJSONString(priceTypeCode + "----------------" + variablePriceCode));
        try {
            WorkFlowVO workFlowVO = new WorkFlowVO(); //variablePriceName=促销活动&priceTypeCode=1&variablePriceCode=10001
            //variablePriceName=促销活动&priceTypeCode=1&variablePriceCode=10001
            workFlowVO.setFormUrl(workFlowBaseUrl.variableUrl + "?variablePriceName=" + variablePriceName + "&priceTypeCode=" + priceTypeCode + "&variablePriceCode=" + variablePriceCode + "&" + workFlowBaseUrl.authority);
            workFlowVO.setHost(workFlowBaseUrl.supplierHost);
            workFlowVO.setFormNo(UUIDUtils.getUUID());
            workFlowVO.setUpdateUrl(workFlowBaseUrl.callBackBaseUrl + WorkFlow.VARIABLE_PRICE.getNum());
            WorkFlowRespVO workFlowRespVO = callWorkFlowApi(workFlowVO, WorkFlow.VARIABLE_PRICE);
            workFlowVO.setTitle("变价管理申请");
            if (workFlowRespVO.getSuccess()) {
                VariablePrice applyContractDTO1 = variablePriceMapper.selectByPrimaryCode(variablePriceCode);
                //状态变为审核中
                applyContractDTO1.setStatus(HandlingExceptionCode.ONE);
                //   //保存后状态是待提交，提交后状态是带审核  0:保存->待提交,1:提交->待审核，2：审核通过3:审核不通过
                // 设置流程id
                applyContractDTO1.setFormNo(workFlowVO.getFormNo());
                int i = variablePriceMapper.updateByPrimaryKeySelective(applyContractDTO1);
                if (i <= 0) {
                    throw new GroundRuntimeException("审核状态修改失败");
                }
            } else {
                throw new GroundRuntimeException("审核流读取失败");
            }
        } catch (Exception e) {
            throw new GroundRuntimeException(e.getMessage());
        }
    }


    @Override
    @Transactional
    public String workFlowCallback(WorkFlowCallbackVO vo1) {
        //通过编码查询实体
        WorkFlowCallbackVO vo = updateSupStatus(vo1);
        VariablePrice variablePrice = variablePriceMapper.selectByFormNO(vo.getFormNo());
        if (Objects.isNull(variablePrice)) {
            return HandlingExceptionCode.FLOW_CALL_BACK_FALSE;
        }
        if (ApplyStatus.APPROVAL_SUCCESS.getNumber().equals(vo.getApplyStatus())) {
            variablePrice.setStatus(ApplyStatus.APPROVAL_SUCCESS.getNumber());
            variablePrice.setAuditorBy(vo.getApprovalUserName());
            variablePrice.setAuditorTime(new Date());
            productCommonService.getInstance(variablePrice.getVariablePriceCode(), HandleTypeCoce.ADD_VARIABLE_PRICE_LIST_TO_EXAMINE_SUCCESS.getStatus()
                    , ObjectTypeCode.PRICE_MANAGEMENT.getStatus(), variablePrice, HandleTypeCoce.ADD_VARIABLE_PRICE_LIST_TO_EXAMINE_SUCCESS.getName());
            List<VariablePriceSku> variablePriceSkus = variablePriceSkuMapper.getList(variablePrice.getVariablePriceCode());
            updateList(variablePriceSkus, variablePrice.getPriceTypeCode());
            int k = updateByPrimaryKeySelective(variablePrice);
            if (k > 0) {
                return HandlingExceptionCode.FLOW_CALL_BACK_SUCCESS;
            } else {
                return HandlingExceptionCode.FLOW_CALL_BACK_FALSE;
            }
        } else if (vo.getApplyStatus().equals(ApplyStatus.APPROVAL_FAILED.getNumber())) {
            variablePrice.setStatus(ApplyStatus.APPROVAL_FAILED.getNumber());
            variablePrice.setAuditorBy(vo.getApprovalUserName());
            variablePrice.setAuditorTime(new Date());
            productCommonService.getInstance(variablePrice.getVariablePriceCode(), HandleTypeCoce.ADD_VARIABLE_PRICE_LIST_TO_EXAMINE_FAIL.getStatus()
                    , ObjectTypeCode.PRICE_MANAGEMENT.getStatus(), variablePrice, HandleTypeCoce.ADD_VARIABLE_PRICE_LIST_TO_EXAMINE_FAIL.getName());
            int k = updateByPrimaryKeySelective(variablePrice);
            if (k > 0) {
                return HandlingExceptionCode.FLOW_CALL_BACK_SUCCESS;
            } else {
                return HandlingExceptionCode.FLOW_CALL_BACK_FALSE;
            }
        } else if (vo.getApplyStatus().equals(ApplyStatus.APPROVAL.getNumber())) {
            //传入的是审批中，继续该流程
            return HandlingExceptionCode.FLOW_CALL_BACK_SUCCESS;
        } else if (vo.getApplyStatus().equals(ApplyStatus.REVOKED.getNumber())) {
            PriceRevokeReqVo priceRevokeReqVo = new PriceRevokeReqVo();
            priceRevokeReqVo.setPriceRevoke(HandlingExceptionCode.ONE);
            priceRevokeReqVo.setVariablePriceCode(variablePrice.getVariablePriceCode());
            variablePrice.setAuditorBy(vo.getApprovalUserName());
            variablePrice.setAuditorTime(new Date());
            variablePrice.setPriceRevoke(HandlingExceptionCode.ONE);
            variablePriceMapper.updateByPrimaryKeySelective(variablePrice);
            productCommonService.getInstance(variablePrice.getVariablePriceCode(), HandleTypeCoce.ADD_VARIABLE_PRICE_PRICE_REVOKE.getStatus(), ObjectTypeCode.PRICE_MANAGEMENT.getStatus(), variablePrice, HandleTypeCoce.ADD_VARIABLE_PRICE_PRICE_REVOKE.getName());
            variablePriceSkuMapper.updateAndCode(priceRevokeReqVo);
            return HandlingExceptionCode.FLOW_CALL_BACK_SUCCESS;
        } else {
            return HandlingExceptionCode.FLOW_CALL_BACK_FALSE;
        }
    }

    @Override
    @Transactional
    public Integer priceRevoke(PriceRevokeReqVo priceRevokeReqVo) {
        try {
            String variablePriceCode = priceRevokeReqVo.getVariablePriceCode();
            if (StringUtils.isNotBlank(variablePriceCode)) {
                VariablePrice variablePrice = variablePriceMapper.selectByPrimaryCode(variablePriceCode);
                if (variablePrice != null) {
                    WorkFlowVO workFlowVO = new WorkFlowVO();
                    workFlowVO.setFormNo(variablePrice.getFormNo());
                    workFlowVO.setTitle("变价管理撤销");
                    if (ApplyStatus.APPROVAL.getNumber().equals(variablePrice.getStatus())) {
                        cancelWorkFlow(workFlowVO);
                    } else {
                        throw new GroundRuntimeException("改状态不能撤销");
                    }
                    return 1;
                }
            } else {
                throw new GroundRuntimeException("variablePriceCode 不能为空");
            }
        } catch (Exception ex) {
            throw new GroundRuntimeException(ex.getMessage());
        }
        return 0;
    }

    @Override
    public Integer getVarName(String name) {
        return variablePriceMapper.getName(name);
    }
}