package com.aiqin.bms.scmp.api.product.service.impl;

import com.aiqin.bms.scmp.api.base.ApplyStatus;
import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.base.WorkFlowBaseUrl;
import com.aiqin.bms.scmp.api.base.service.impl.BaseServiceImpl;
import com.aiqin.bms.scmp.api.common.*;
import com.aiqin.bms.scmp.api.config.AuthenticationInterceptor;
import com.aiqin.bms.scmp.api.constant.Global;
import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProduct;
import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSku;
import com.aiqin.bms.scmp.api.product.domain.pojo.NewProduct;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductOperationLog;
import com.aiqin.bms.scmp.api.product.domain.request.newproduct.ApplyDraftReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.newproduct.DraftSaveListReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.newproduct.NewProductSaveReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.newproduct.QueryApplyProductReqVO;
import com.aiqin.bms.scmp.api.product.domain.response.newproduct.ApplyProductDetailsResponseVO;
import com.aiqin.bms.scmp.api.product.domain.response.newproduct.ApplyProductResponseVO;
import com.aiqin.bms.scmp.api.product.mapper.ApplyProductMapper;
import com.aiqin.bms.scmp.api.product.mapper.ApplyProductSkuMapper;
import com.aiqin.bms.scmp.api.product.service.*;
import com.aiqin.bms.scmp.api.supplier.dao.EncodingRuleDao;
import com.aiqin.bms.scmp.api.util.BeanCopyUtils;
import com.aiqin.bms.scmp.api.util.CodeUtils;
import com.aiqin.bms.scmp.api.util.IdSequenceUtils;
import com.aiqin.bms.scmp.api.util.PageUtil;
import com.aiqin.bms.scmp.api.workflow.annotation.WorkFlowAnnotation;
import com.aiqin.bms.scmp.api.workflow.enumerate.WorkFlow;
import com.aiqin.bms.scmp.api.workflow.helper.WorkFlowHelper;
import com.aiqin.bms.scmp.api.workflow.vo.request.WorkFlowCallbackVO;
import com.aiqin.bms.scmp.api.workflow.vo.request.WorkFlowVO;
import com.aiqin.bms.scmp.api.workflow.vo.response.WorkFlowRespVO;
import com.aiqin.ground.util.exception.GroundRuntimeException;
import com.aiqin.ground.util.json.JsonUtil;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
@WorkFlowAnnotation(WorkFlow.APPLY_GOODS)
public class ApplyProductServiceImpl extends BaseServiceImpl implements ApplyProductService, WorkFlowHelper {
    @Autowired
    private ApplyProductMapper applyProductMapper;
    @Autowired
    private ApplyProductSkuMapper applyProductSkuMapper;
    @Autowired
    private ProductCommonService productCommonService;
    @Autowired
    private EncodingRuleDao encodingRuleDao;
    @Autowired
    private ApplyProductDraftService applyProductDraftService;
    @Autowired
    private WorkFlowBaseUrl workFlowBaseUrl;
    @Autowired
    private NewProductService newProductService;

    @Autowired
    private SkuInfoService skuInfoService;

    @Resource
    private CodeUtils codeUtils;

    @Override
    @Transactional
    @Save
    public int insertSelective(ApplyProduct record) {
        int k = applyProductMapper.insertSelective(record);
        if (k > 0) {
            return k;
        } else {
            log.error(HandlingExceptionCode.APPLY_PRODUCT_ADD);
            throw new GroundRuntimeException(HandlingExceptionCode.APPLY_PRODUCT_ADD);
        }
    }

    @Override
    public ApplyProduct selectByPrimaryKey(Long id) {
        return applyProductMapper.selectByPrimaryKey(id);
    }

    @Override
    @Transactional
    public String saveList(ApplyDraftReqVO applyDraftReqVO, Long code) {
        String fromNo = "SP" + IdSequenceUtils.getInstance().nextId();
        try {
            List<ApplyProduct> applyProducts = new LinkedList<>();
            List<DraftSaveListReqVO> draftSaveListReqVOS = applyDraftReqVO.getDraftSaveListReqVO();
            if (draftSaveListReqVOS != null && draftSaveListReqVOS.size() > 0) {
                draftSaveListReqVOS.forEach(
                        draftSaveLi -> {
                            ApplyProduct applyProduct = new ApplyProduct();
                            applyProduct.setBarCode(draftSaveLi.getBarCode());
                            applyProduct.setProductName(draftSaveLi.getProductName());
                            applyProduct.setProductCode(draftSaveLi.getProductCode());
                            applyProduct.setCompanyCode(draftSaveLi.getCompanyCode());
                            applyProduct.setCompanyName(draftSaveLi.getCompanyName());
                            applyProduct.setApplyCode(Long.toString(code));
                            applyProduct.setApplyStatus(HandlingExceptionCode.ZERO);
                            applyProduct.setPriceRevoke(HandlingExceptionCode.ZERO);
                            applyProduct.setSelectionEffectiveTime(applyDraftReqVO.getSelectionEffectiveTime());
                            applyProduct.setSelectionEffectiveEndTime(applyDraftReqVO.getSelectionEffectiveEndTime());
                            applyProduct.setSelectionEffectiveStartTime(applyDraftReqVO.getSelectionEffectiveStartTime());
                            applyProduct.setFormNo(fromNo);
                            applyProducts.add(applyProduct);
                        }
                );
            } else {
                throw new GroundRuntimeException("商品不能为空");
            }
            List<ProductOperationLog> productOperationLogs = conversion(applyProducts);
            List<String> proCodes = applyProducts.stream().map(ApplyProduct::getProductCode).collect(Collectors.toList());
            productCommonService.saveList(productOperationLogs);
            applyProductDraftService.deleteCode(proCodes);
            ((ApplyProductService) AopContext.currentProxy()).insertList(applyProducts);
            //return workFlow(Long.toString(code));
            return fromNo;
        } catch (Exception ex) {
            throw new GroundRuntimeException(ex.getMessage());
        }
    }

    @Override
    @Transactional
    @Update
    public int updateByPrimaryKeySelective(ApplyProduct record) {
        int k = applyProductMapper.updateByPrimaryKeySelective(record);
        if (k > 0) {
            return k;
        } else {
            log.error(HandlingExceptionCode.APPLY_PRODUCT_UPDATE);
            throw new GroundRuntimeException(HandlingExceptionCode.APPLY_PRODUCT_UPDATE);
        }
    }

    @Override
    @Transactional
    public int insertProduct(NewProductSaveReqVO newProductSaveReqVO) {
        int flg = 0;
        try {
//            EncodingRule encodingRule = encodingRuleDao.getNumberingType("APPLY_PRODUCT_CODE");
//            long code = encodingRule.getNumberingValue();
//            encodingRuleDao.updateNumberValue(code, encodingRule.getId());
            String code = codeUtils.getRedisCode("APPLY_PRODUCT_CODE");
            ApplyProduct applyProduct = new ApplyProduct();
            applyProduct.setProductCode(code);
            BeanCopyUtils.copy(newProductSaveReqVO, applyProduct);
            applyProduct.setApplyStatus(HandlingExceptionCode.ZERO);
            flg = ((ApplyProductService) AopContext.currentProxy()).insertSelective(applyProduct);
            productCommonService.getInstance(code, HandleTypeCoce.APPLY_ADD_PRODUCT.getStatus(), ObjectTypeCode.PRODUCT_MANAGEMENT.getStatus(), applyProduct, HandleTypeCoce.APPLY_ADD_PRODUCT.getName());
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new GroundRuntimeException(ex.getMessage());
        }
        return flg;
    }

    @Override
    public void ExceptionId(String productCode) {
        if (StringUtils.isNotBlank(productCode)) {
            log.error(HandlingExceptionCode.PRODUCT_PRODUCTCODE);
            throw new GroundRuntimeException(HandlingExceptionCode.PRODUCT_PRODUCTCODE);
        }
    }

    @Override
    public BasePage<ApplyProductResponseVO> getList(QueryApplyProductReqVO queryApplyProductReqVO) {
        try {
            PageHelper.startPage(queryApplyProductReqVO.getPageNo(), queryApplyProductReqVO.getPageSize());
            List<ApplyProductResponseVO> list = applyProductMapper.getList(queryApplyProductReqVO);
            return PageUtil.getPageList(queryApplyProductReqVO.getPageNo(), list);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new GroundRuntimeException(ex.getMessage());
        }
    }

    @Override
    public List<ApplyProductDetailsResponseVO> getApplyProduct(String applyCode) {
        if (StringUtils.isNotBlank(applyCode)) {
            return applyProductMapper.getApplyProduct(applyCode);
        } else {
            throw new GroundRuntimeException("applyCode 不能为空");
        }
    }

    @Override
    public Integer getName(String proName) {
        return applyProductMapper.getName(proName);
    }

    @Override
    @Transactional
    @SaveList
    public int insertList(List<ApplyProduct> applyProducts) {
        int k = applyProductMapper.insertList(applyProducts);
        if (k > 0) {
            return k;
        } else {
            log.error(HandlingExceptionCode.APPLY_PRODUCT_ADD);
            throw new GroundRuntimeException(HandlingExceptionCode.APPLY_PRODUCT_ADD);
        }
    }

    @Override
    public List<ProductOperationLog> conversion(List<ApplyProduct> applyProducts) {
        List<ProductOperationLog> productOperationLogs = new LinkedList<>();
        applyProducts.forEach(applyProduct -> {
            ProductOperationLog productOperationLog = new ProductOperationLog();
            productOperationLog.setHandleType(HandleTypeCoce.APPLY_ADD_PRODUCT.getStatus());
            productOperationLog.setObjectId(applyProduct.getProductCode());
            String contentJson = JsonUtil.toJson(applyProduct);
            productOperationLog.setContent(contentJson);
            productOperationLog.setObjectType(ObjectTypeCode.PRODUCT_MANAGEMENT.getStatus());
            productOperationLog.setHandleName(HandleTypeCoce.APPLY_ADD_PRODUCT.getName());
            productOperationLogs.add(productOperationLog);
        });
        return productOperationLogs;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public String workFlow(String applyCode) {
        String fromNo = "SP" + IdSequenceUtils.getInstance().nextId();
        log.info("ApplyProductServiceImplProduct-workFlow-传入参数是：[{}]", JSON.toJSONString(applyCode));
        try {
            WorkFlowVO workFlowVO = new WorkFlowVO();
            workFlowVO.setFormUrl(workFlowBaseUrl.applySku + "?applyCode=" + applyCode + "&" + workFlowBaseUrl.authority);
            workFlowVO.setHost(workFlowBaseUrl.supplierHost);
            workFlowVO.setFormNo(fromNo);
            workFlowVO.setUpdateUrl(workFlowBaseUrl.callBackBaseUrl + WorkFlow.APPLY_GOODS.getNum());
            WorkFlowRespVO workFlowRespVO = callWorkFlowApi(workFlowVO, WorkFlow.APPLY_GOODS);
            workFlowVO.setTitle(AuthenticationInterceptor.getCurrentAuthToken().getPersonName() + ",进行商品申请");
            if (workFlowRespVO.getSuccess()) {
                List<ApplyProduct> applyProducts = applyProductMapper.getApplyCode(applyCode);
                applyProducts.forEach(applyProduct ->
                        {
                            applyProduct.setApplyStatus(HandlingExceptionCode.ONE);
                            applyProduct.setFormNo(workFlowVO.getFormNo());
                        }
                );
                int i = applyProductMapper.updateList(applyProducts);
                if (i <= 0) {
                    throw new GroundRuntimeException("审核状态修改失败");
                }
            } else {
                throw new GroundRuntimeException(workFlowRespVO.getMsg());
            }
        } catch (Exception e) {
            throw new GroundRuntimeException(e.getMessage());
        }
        return fromNo;

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String workFlowCallback(WorkFlowCallbackVO vo) {

        return nativeWorkFlowCallback(vo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String nativeWorkFlowCallback(WorkFlowCallbackVO vo) {
        WorkFlowCallbackVO workFlowCallbackVO = updateSupStatus(vo);
        //判断审核通过还是撤销，或者审核不通过
        List<ApplyProductSku> applyProductSkus = applyProductSkuMapper.selectByFormNO(vo.getFormNo());
        Date currentDate = new Date();
        if (vo.getApplyStatus().equals(ApplyStatus.APPROVAL_SUCCESS.getNumber())) {
            //审批通过
            if (CollectionUtils.isNotEmpty(applyProductSkus)) {
                skuInfoService.skuWorkFlowCallback(workFlowCallbackVO);
            }
            return HandlingExceptionCode.FLOW_CALL_BACK_SUCCESS;
        } else if (vo.getApplyStatus().equals(ApplyStatus.APPROVAL_FAILED.getNumber())) {
            //审批不通过
            log.info("ApplyProductServiceImplProduct-workFlow-参数是：[{}]", JSON.toJSONString(workFlowCallbackVO));
            if (CollectionUtils.isNotEmpty(applyProductSkus)) {
                //批量更新审核不通过
                try {
                    int k = applyProductSkuMapper.updateStatusByFormNo((byte) 3, vo.getFormNo(), vo.getApprovalUserName(), currentDate);
                } catch (Exception e) {
                    // 修改审批中的商品失败
                    return "false";
                }
            }
            return "success";
        } else if (vo.getApplyStatus().intValue() == ApplyStatus.REVOKED.getNumber()) {
            //撤销
            log.info("ApplyProductServiceImplProduct-workFlow-参数是：[{}]", JSON.toJSONString(workFlowCallbackVO));
            if (CollectionUtils.isNotEmpty(applyProductSkus)) {
                //批量更新审核不通过
                try {
                    String auditorBy = Objects.nonNull(vo.getApprovalUserName()) ? vo.getAuditorBy() : applyProductSkus.get(0).getCreateBy();
                    int k = applyProductSkuMapper.updateStatusByFormNo((byte) 4, vo.getFormNo(), auditorBy, currentDate);
                } catch (Exception e) {
                    log.error(Global.ERROR, e);
                    // 修改审批中的sku失败
                    return "false";
                }
            }
            return "success";
        } else if (vo.getApplyStatus().equals(ApplyStatus.APPROVAL.getNumber())) {
            //审批中节点
            return "success";
        } else {
            return "false";
        }
    }

    @Override
    public String productFlow(List<ApplyProduct> applyProducts, WorkFlowCallbackVO vo) {
        try {
            List<ProductOperationLog> productOperationLogs = new LinkedList<>();
            List<ProductOperationLog> newlog = new LinkedList<>();
            if (applyProducts != null && applyProducts.size() > 0) {
                if (ApplyStatus.APPROVAL_SUCCESS.getNumber().equals(vo.getApplyStatus())) {
                    applyProducts.forEach(applyProduct -> {
                        applyProduct.setApplyStatus(ApplyStatus.APPROVAL_SUCCESS.getNumber());
                        applyProduct.setAuditorBy(vo.getApprovalUserName());
                        applyProduct.setAuditorTime(new Date());
                        ProductOperationLog productOperationLog = new ProductOperationLog();
                        productOperationLog.setObjectId(applyProduct.getProductCode());
                        productOperationLog.setContent(JsonUtil.toJson(applyProduct));
                        productOperationLog.setObjectType(ObjectTypeCode.APPLY_PRODUCT.getStatus());
                        productOperationLog.setHandleType(HandleTypeCoce.APPLY_UPDATE_PRODUCT_TO_EXAMINE.getStatus());
                        productOperationLog.setHandleName(HandleTypeCoce.APPLY_UPDATE_PRODUCT_TO_EXAMINE.getName());
                        productOperationLogs.add(productOperationLog);
                    });
                    productCommonService.saveList(productOperationLogs);
                    applyProductMapper.updateList(applyProducts);
                    List<NewProduct> newProducts = BeanCopyUtils.copyList(applyProducts, NewProduct.class);
                    newProducts.forEach(newProduct -> {
                        ProductOperationLog productOperationLog = malloc(newProduct);
                        newlog.add(productOperationLog);
                    });
                    int k = newProductService.insertList(newProducts);
                    productCommonService.saveList(newlog);
                    if (k > 0) {
                        return HandlingExceptionCode.FLOW_CALL_BACK_SUCCESS;
                    } else {
                        return HandlingExceptionCode.FLOW_CALL_BACK_FALSE;
                    }
                } else if (ApplyStatus.APPROVAL_FAILED.getNumber().equals(vo.getApplyStatus())) {
                    applyProducts.forEach(applyProduct -> {
                        applyProduct.setApplyStatus(ApplyStatus.APPROVAL_FAILED.getNumber());
                        applyProduct.setAuditorBy(vo.getApprovalUserName());
                        applyProduct.setAuditorTime(new Date());
                        ProductOperationLog productOperationLog = new ProductOperationLog();
                        productOperationLog.setObjectId(applyProduct.getProductCode());
                        productOperationLog.setContent(JsonUtil.toJson(applyProduct));
                        productOperationLog.setObjectType(ObjectTypeCode.APPLY_PRODUCT.getStatus());
                        productOperationLog.setHandleType(HandleTypeCoce.APPLY_UPDATE_PRODUCT_TO_EXAMINE_FAIL.getStatus());
                        productOperationLog.setHandleName(HandleTypeCoce.APPLY_UPDATE_PRODUCT_TO_EXAMINE_FAIL.getName());
                        productOperationLogs.add(productOperationLog);
                    });
                    productCommonService.saveList(productOperationLogs);
                    applyProductMapper.updateList(applyProducts);
                    List<NewProduct> newProducts = BeanCopyUtils.copyList(applyProducts, NewProduct.class);
                    newProducts.forEach(newProduct -> {
                        ProductOperationLog productOperationLog = malloc(newProduct);
                        newlog.add(productOperationLog);
                    });
                    int k = newProductService.insertList(newProducts);
                    productCommonService.saveList(newlog);
                    if (k > 0) {
                        return HandlingExceptionCode.FLOW_CALL_BACK_SUCCESS;
                    } else {
                        return HandlingExceptionCode.FLOW_CALL_BACK_FALSE;
                    }
                } else if (vo.getApplyStatus().equals(ApplyStatus.APPROVAL.getNumber())) {
                    //审批中
                    return HandlingExceptionCode.FLOW_CALL_BACK_SUCCESS;
                } else {
                    return HandlingExceptionCode.FLOW_CALL_BACK_FALSE;
                }
            }
        } catch (Exception ex) {
            throw new GroundRuntimeException(ex.getMessage());
        }
        return HandlingExceptionCode.FLOW_CALL_BACK_SUCCESS;
    }


    @Override
    public ProductOperationLog malloc(NewProduct newProduct) {
        ProductOperationLog productOperationLog = new ProductOperationLog();
        productOperationLog.setObjectId(newProduct.getProductCode());
        productOperationLog.setContent(JsonUtil.toJson(newProduct));
        productOperationLog.setObjectType(ObjectTypeCode.PRODUCT_MANAGEMENT.getStatus());
        productOperationLog.setHandleType(HandleTypeCoce.ADD_PRODUCT.getStatus());
        productOperationLog.setHandleName(HandleTypeCoce.ADD_PRODUCT.getName());
        return productOperationLog;
    }

    @Override
    @Transactional
    public Integer revoke(String formNo) {
        try {
            //根据formNo调用审批接口，申请撤销
            WorkFlowVO workFlowVO = new WorkFlowVO();
            workFlowVO.setFormNo(formNo);
            // 调用审批流的撤销接口
            WorkFlowRespVO workFlowRespVO = cancelWorkFlow(workFlowVO);
            if (workFlowRespVO.getSuccess().equals(true)) {
                return 1;
            } else {
                log.error("商品" + formNo + "撤销失败");
                throw new GroundRuntimeException("撤销失败");
            }
        } catch (Exception ex) {
            throw new GroundRuntimeException(ex.getMessage());
        }
    }

    @Override
    public List<ApplyProduct> getProductApplyList(List<String> productCodes, Byte number) {
        return applyProductMapper.getProductApplyList(productCodes, number);
    }


}
