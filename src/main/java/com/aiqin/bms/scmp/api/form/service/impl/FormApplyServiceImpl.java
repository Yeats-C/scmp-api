package com.aiqin.bms.scmp.api.form.service.impl;

import com.aiqin.bms.scmp.api.form.domain.FormApplyRequest;
import com.aiqin.bms.scmp.api.form.service.FormApplyService;
import com.aiqin.ground.util.id.IdUtil;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.aiqin.platform.flows.client.constant.FormUpdateUrlType;
import com.aiqin.platform.flows.client.constant.Indicator;
import com.aiqin.platform.flows.client.constant.IndicatorStr;
import com.aiqin.platform.flows.client.constant.TpmBpmUtils;
import com.aiqin.platform.flows.client.domain.vo.ActBaseProcessEntity;
import com.aiqin.platform.flows.client.domain.vo.FormCallBackVo;
import com.aiqin.platform.flows.client.domain.vo.StartProcessParamVO;
import com.aiqin.platform.flows.client.service.FormApplyCommonService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class FormApplyServiceImpl implements FormApplyService {

    @Resource
    private FormApplyCommonService formApplyCommonService;

    /**
     * 提交表单
     *
     * @param request
     * @return
     */
    @Override
    public HttpResponse submitActBaseProcess(FormApplyRequest request) {
        StartProcessParamVO paramVO = new StartProcessParamVO();
        paramVO.setCostType(request.getProcessKey());
        paramVO.setFormType(request.getProcessKey());
        paramVO.setCurrentUser(request.getPersonId());
        paramVO.setFormNo(request.getFormNo());
        paramVO.setTitle(request.getTitle());
        paramVO.setRemark(request.getRemark());
        paramVO.setFormUrl(request.getFormUrl());
        paramVO.setFormUpdateUrl(request.getFormUpdateUrl());
        paramVO.setFormUpdateUrlType(FormUpdateUrlType.HTTP);
        paramVO.setReceiptType(2); // 2代表供应链
        paramVO.setSignTicket(IdUtil.uuid());
        if (StringUtils.isNotBlank(request.getAuditPersonId())) {
            Map<String, Object> map = new HashMap<>();
            map.put("auditPersonId", request.getAuditPersonId());
            paramVO.setVariables(map);
        }
        log.info("调用审批流发起申请,request={}", paramVO);
        HttpResponse response = formApplyCommonService.submitActBaseProcessScmp(paramVO);
        log.info("调用审批流发起申请返回结果,result={}", response);
        return response;
    }

    @Override
    public String callback(FormCallBackVo formCallBackVo) {
        log.info("进入回调,request={}", formCallBackVo);
        if (formCallBackVo.getUpdateFormStatus().equals(Indicator.COST_FORM_STATUS_APPROVED.getCode())) {
            //审核通过
        } else if (TpmBpmUtils.isPass(formCallBackVo.getUpdateFormStatus(), formCallBackVo.getOptBtn())) {
            //审核中
        } else {
            if (formCallBackVo.getOptBtn().equals(IndicatorStr.PROCESS_BTN_REJECT_FIRST.getCode())) {
                //驳回发起人
            } else if (formCallBackVo.getOptBtn().equals(IndicatorStr.PROCESS_BTN_REJECT_END.getCode())) {
                //驳回并结束
            } else if (formCallBackVo.getOptBtn().equals(IndicatorStr.PROCESS_BTN_CANCEL.getCode())) {
                //撤销
            } else if (formCallBackVo.getOptBtn().equals(IndicatorStr.PROCESS_BTN_KILL.getCode())) {
                //终止
            } else {
                //终止
            }
        }
        return "success";
    }
}
