package com.aiqin.bms.scmp.api.purchase.service.impl;

import com.aiqin.bms.scmp.api.base.ApplyStatus;
import com.aiqin.bms.scmp.api.base.WorkFlowBaseUrl;
import com.aiqin.bms.scmp.api.base.service.impl.BaseServiceImpl;
import com.aiqin.bms.scmp.api.common.BizException;
import com.aiqin.bms.scmp.api.common.WorkFlowReturn;
import com.aiqin.bms.scmp.api.config.AuthenticationInterceptor;
import com.aiqin.bms.scmp.api.constant.Global;
import com.aiqin.bms.scmp.api.purchase.dao.*;
import com.aiqin.bms.scmp.api.purchase.domain.PurchaseApply;
import com.aiqin.bms.scmp.api.purchase.service.PurchaseApplyService;
import com.aiqin.bms.scmp.api.purchase.service.PurchaseApprovalService;
import com.aiqin.bms.scmp.api.util.AuthToken;
import com.aiqin.bms.scmp.api.workflow.annotation.WorkFlowAnnotation;
import com.aiqin.bms.scmp.api.workflow.enumerate.WorkFlow;
import com.aiqin.bms.scmp.api.workflow.helper.WorkFlowHelper;
import com.aiqin.bms.scmp.api.workflow.vo.request.WorkFlowCallbackVO;
import com.aiqin.bms.scmp.api.workflow.vo.request.WorkFlowVO;
import com.aiqin.bms.scmp.api.workflow.vo.response.WorkFlowRespVO;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Objects;

@Service
@WorkFlowAnnotation(WorkFlow.APPLY_PURCHASE)
public class PurchaseApprovalServiceImpl extends BaseServiceImpl implements PurchaseApprovalService, WorkFlowHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(PurchaseApprovalServiceImpl.class);

    private static final BigDecimal big = BigDecimal.valueOf(0);

    @Resource
    private WorkFlowBaseUrl workFlowBaseUrl;
    @Resource
    private PurchaseApplyDao purchaseApplyDao;
    @Resource
    private PurchaseApplyService purchaseApplyService;

    /**
     * 审核回调接口
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String workFlowCallback(WorkFlowCallbackVO vo1) {
        try {
            WorkFlowCallbackVO vo = updateSupStatus(vo1);
            PurchaseApply purchaseApply = new PurchaseApply();
            purchaseApply.setPurchaseApplyCode(vo1.getFormNo());
            PurchaseApply order = purchaseApplyDao.purchaseApply(purchaseApply);
            if(order == null){
                LOGGER.info("采购申请单为空");
                return WorkFlowReturn.FALSE;
            }else if(!order.getApplyStatus().equals(Global.PURCHASE_APPLY_2) &&
                    !order.getApplyStatus().equals(Global.PURCHASE_APPLY_3)){
                // 采购单不是待审核状态
                LOGGER.info("采购单不是待审核状态");
                return WorkFlowReturn.SUCCESS;
            }
            order.setUpdateById(vo1.getApprovalUserCode());
            order.setUpdateByName(vo1.getApprovalUserName());
            if (Objects.equals(vo.getApplyStatus(), ApplyStatus.APPROVAL_FAILED.getNumber())) {
                //审批失败
                order.setApplyStatus(Global.PURCHASE_APPLY_5);
                Integer count = purchaseApplyDao.update(order);
                LOGGER.info("影响条数:{}",count);
                // 添加审批不通过操作日志
//                log(order.getPurchaseApplyId(), vo1.getApprovalUserCode(), vo1.getApprovalUserName(),
//                        PurchaseOrderLogEnum.PURCHASE_APPLY_CHECKOUT_ADOPT_NOT.getCode(),
//                        PurchaseOrderLogEnum.PURCHASE_APPLY_CHECKOUT_ADOPT_NOT.getName(), order.getRemark());
            } else if (Objects.equals(vo.getApplyStatus(), ApplyStatus.APPROVAL.getNumber())) {
                // 审批中
                order.setApplyStatus(Global.PURCHASE_APPLY_3);
                Integer count = purchaseApplyDao.update(order);
                LOGGER.info("影响条数:{}",count);
                // 添加审批中操作日志
//                log(order.getPurchaseApplyId(), vo1.getApprovalUserCode(), vo1.getApprovalUserName(),
//                        PurchaseOrderLogEnum.PURCHASE_APPLY_CHECKOUT.getCode(),
//                        PurchaseOrderLogEnum.PURCHASE_APPLY_CHECKOUT.getName(), order.getRemark());
            } else if (Objects.equals(vo.getApplyStatus(), ApplyStatus.APPROVAL_SUCCESS.getNumber())) {
                //审批成功
                order.setApplyStatus(Global.PURCHASE_APPLY_4);
                Integer count = purchaseApplyDao.update(order);
                LOGGER.info("影响条数:{}",count);
                // 审批通过，创建采购单
                LOGGER.info("开始调用审批成功，采购申请单："+ order.getPurchaseApplyCode());
                purchaseApplyService.insertPurchaseOrder(order.getPurchaseApplyId());
                // 添加审批通过操作日志
//                log(order.getPurchaseApplyId(), vo1.getApprovalUserCode(), vo1.getApprovalUserName(),
//                        PurchaseOrderLogEnum.PURCHASE_APPLY_CHECKOUT_ADOPT.getCode(),
//                        PurchaseOrderLogEnum.PURCHASE_APPLY_CHECKOUT_ADOPT.getName(), order.getRemark());

            } else if(Objects.equals(vo.getApplyStatus(), ApplyStatus.REVOKED.getNumber())){
                // 审批撤销
                order.setApplyStatus(Global.PURCHASE_APPLY_6);
                Integer count = purchaseApplyDao.update(order);
                LOGGER.info("影响条数:{}",count);
                // 添加审批不通过操作日志
//                log(order.getPurchaseApplyId(), vo1.getApprovalUserCode(), vo1.getApprovalUserName(),
//                        PurchaseOrderLogEnum.PURCHASE_APPLY_REVOKE.getCode(),
//                        PurchaseOrderLogEnum.PURCHASE_APPLY_REVOKE.getName(), order.getRemark());
            }
            return WorkFlowReturn.SUCCESS;
        }catch  (Exception e) {
            LOGGER.error("审批回调异常:{}", e.getMessage());
            return WorkFlowReturn.SUCCESS;
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void workFlow(String formNo, String userName, String directSupervisorCode, String positionCode){
        WorkFlowVO workFlowVO = new WorkFlowVO();
        //在审批中看到的页面
        workFlowVO.setFormUrl(workFlowBaseUrl.applyPurchase + "?purchase_apply_code=" + formNo + "&" + workFlowBaseUrl.authority);
        workFlowVO.setHost(workFlowBaseUrl.supplierHost);
        workFlowVO.setUpdateUrl(workFlowBaseUrl.callBackBaseUrl + WorkFlow.APPLY_PURCHASE.getNum());
        workFlowVO.setFormNo(formNo);
        workFlowVO.setTitle(userName);
        workFlowVO.setPositionCode(positionCode);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("auditPersonId", directSupervisorCode);
        // 查询采购价格
        PurchaseApply apply = new PurchaseApply();
        apply.setPurchaseApplyCode(formNo);
        PurchaseApply purchaseApply = purchaseApplyDao.purchaseApply(apply);
        if(purchaseApply != null){
            BigDecimal productAmount = purchaseApply.getProductTaxAmount() == null ? big : purchaseApply.getProductTaxAmount();
            BigDecimal giftAmount = purchaseApply.getGiftTaxAmount() == null ? big : purchaseApply.getGiftTaxAmount();
            jsonObject.addProperty("purchaseAmount", productAmount.add(giftAmount));
        }
        workFlowVO.setVariables(jsonObject.toString());
        WorkFlowRespVO workFlowRespVO = callWorkFlowApi(workFlowVO, WorkFlow.APPLY_PURCHASE);
        //判断是否成功
        if (workFlowRespVO.getSuccess()) {
            LOGGER.info("创建采购单审批成功:{}",workFlowRespVO);

            AuthToken currentAuthToken = AuthenticationInterceptor.getCurrentAuthToken();
            if (currentAuthToken == null) {
                LOGGER.info("获取当前登录信息失败");
                throw new BizException("审批获取当前登录信息失败");
            }
            purchaseApply.setApplyStatus(Global.PURCHASE_APPLY_2);
            purchaseApply.setUpdateById(currentAuthToken.getPersonId());
            purchaseApply.setUpdateByName(currentAuthToken.getPersonName());
            Integer count = purchaseApplyDao.update(purchaseApply);
            LOGGER.info("更改采购申请单为待审核：" + count);
        } else {
            LOGGER.info("审批流调用失败");
            throw new BizException(workFlowRespVO.getMsg());
        }
    }

//    private void log(String purchaseOrderId, String createById, String createByName, Integer code, String name, String remark){
//        OperationLog log = new OperationLog();
//        log.setOperationId(purchaseOrderId);
//        log.setCreateById(createById);
//        log.setCreateByName(createByName);
//        log.setOperationType(code);
//        log.setOperationContent(name);
//        log.setRemark(remark);
//        Integer count = operationLogDao.insert(log);
//        LOGGER.info("操作日志{}", count);
//    }


}
