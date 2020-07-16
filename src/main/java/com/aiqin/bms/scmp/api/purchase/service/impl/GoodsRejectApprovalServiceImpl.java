package com.aiqin.bms.scmp.api.purchase.service.impl;

import com.aiqin.bms.scmp.api.base.ApplyStatus;
import com.aiqin.bms.scmp.api.base.WorkFlowBaseUrl;
import com.aiqin.bms.scmp.api.base.service.impl.BaseServiceImpl;
import com.aiqin.bms.scmp.api.common.WorkFlowReturn;
import com.aiqin.bms.scmp.api.constant.RejectRecordStatus;
import com.aiqin.bms.scmp.api.purchase.dao.RejectApplyRecordDao;
import com.aiqin.bms.scmp.api.purchase.domain.RejectApplyRecord;
import com.aiqin.bms.scmp.api.purchase.service.GoodsRejectApprovalService;
import com.aiqin.bms.scmp.api.workflow.annotation.WorkFlowAnnotation;
import com.aiqin.bms.scmp.api.workflow.enumerate.WorkFlow;
import com.aiqin.bms.scmp.api.workflow.helper.WorkFlowHelper;
import com.aiqin.bms.scmp.api.workflow.vo.request.WorkFlowCallbackVO;
import com.aiqin.bms.scmp.api.workflow.vo.request.WorkFlowVO;
import com.aiqin.bms.scmp.api.workflow.vo.response.WorkFlowRespVO;
import com.aiqin.ground.util.exception.GroundRuntimeException;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Objects;

@Service
@WorkFlowAnnotation(WorkFlow.APPLY_REFUND)
public class GoodsRejectApprovalServiceImpl extends BaseServiceImpl implements GoodsRejectApprovalService, WorkFlowHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsRejectApprovalServiceImpl.class);

    @Resource
    private WorkFlowBaseUrl workFlowBaseUrl;
    @Resource
    private GoodsRejectServiceImpl goodsRejectService;
    @Resource
    private RejectApplyRecordDao rejectApplyRecordDao;

    /**
     * 审核回调接口
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String workFlowCallback(WorkFlowCallbackVO vo1) {
        try {
            WorkFlowCallbackVO vo = updateSupStatus(vo1);
            //审批驳回
            RejectApplyRecord rejectApplyRecord = new RejectApplyRecord();
            rejectApplyRecord.setRejectApplyRecordCode(vo.getFormNo());
            RejectApplyRecord record = rejectApplyRecordDao.selectByRejectCode(vo.getFormNo());
            if (record == null) {
                //未查询到退供单信息
                return WorkFlowReturn.FALSE;
            } else if (!record.getApplyRecordStatus().equals(RejectRecordStatus.REJECT_APPLY_TO_REVIEW)) {
                //退供单是取消状态,不进行操作
                LOGGER.info("退供申请单非待审核状态,无法进行审批:", vo.getFormNo());
                return WorkFlowReturn.SUCCESS;
            }
            if (Objects.equals(vo.getApplyStatus(), ApplyStatus.APPROVAL.getNumber())) {
                //审批中
                rejectApplyRecord.setApplyRecordStatus(RejectRecordStatus.REJECT_APPLY_UNDER_REVIEW);
                Integer count = rejectApplyRecordDao.update(rejectApplyRecord);
                LOGGER.info("审批中影响条数:{}", count);
            } else if (Objects.equals(vo.getApplyStatus(), ApplyStatus.APPROVAL_FAILED.getNumber())) {
                //审批失败
                rejectApplyRecord.setApplyRecordStatus(RejectRecordStatus.REJECT_APPLY_NO);
                Integer count = rejectApplyRecordDao.update(rejectApplyRecord);
                LOGGER.info("审核不通过影响条数:{}", count);
            } else if (Objects.equals(vo.getApplyStatus(), ApplyStatus.REVOKED.getNumber())) {
                //审批撤销
                rejectApplyRecord.setApplyRecordStatus(RejectRecordStatus.REJECT_APPLY_REVOKE);
                Integer count = rejectApplyRecordDao.update(rejectApplyRecord);
                LOGGER.info("审批撤销影响条数:{}", count);
            } else if (Objects.equals(vo.getApplyStatus(), ApplyStatus.APPROVAL_SUCCESS.getNumber())) {
                // 调用生成退供单
                HttpResponse response = goodsRejectService.generateRejectRecord(vo.getFormNo());
                //审批通过
                if(response.getCode().equals("0")){
                    rejectApplyRecord.setApplyRecordStatus(RejectRecordStatus.REJECT_APPLY_YES);
                    Integer count = rejectApplyRecordDao.update(rejectApplyRecord);
                    LOGGER.info("审批通过影响条数:{}", count);
                    return WorkFlowReturn.SUCCESS;
                }else {
                    LOGGER.info("退供单未生成，所以申请单审批未通过:{}", response.getMessage());
                    return WorkFlowReturn.FALSE;
                }
            }
            return WorkFlowReturn.SUCCESS;
        } catch (Exception e) {
            LOGGER.error("审批回调异常:{}", e.getStackTrace());
            return WorkFlowReturn.SUCCESS;
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void workFlow(String formNo, String userName, String directSupervisorCode, String positionCode) {
        WorkFlowVO workFlowVO = new WorkFlowVO();
        //在审批中看到的页面
        workFlowVO.setFormUrl(workFlowBaseUrl.applyRefund + "?code=" + formNo + "&" + workFlowBaseUrl.authority);
        workFlowVO.setHost(workFlowBaseUrl.supplierHost);
        workFlowVO.setUpdateUrl(workFlowBaseUrl.callBackBaseUrl + WorkFlow.APPLY_REFUND.getNum());
        workFlowVO.setFormNo(formNo);
        workFlowVO.setPositionCode(positionCode);
        workFlowVO.setTitle(userName);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("auditPersonId", directSupervisorCode);
        workFlowVO.setVariables(jsonObject.toString());
        WorkFlowRespVO workFlowRespVO = callWorkFlowApi(workFlowVO, WorkFlow.APPLY_REFUND);
        //判断是否成功
        if (workFlowRespVO.getSuccess()) {
            LOGGER.info("创建退供申请单审批成功:{}", workFlowRespVO.toString());
        } else {
            throw new GroundRuntimeException(workFlowRespVO.getMsg());
        }
    }

}
