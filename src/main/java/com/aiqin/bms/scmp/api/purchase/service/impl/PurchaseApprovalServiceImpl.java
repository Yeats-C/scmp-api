package com.aiqin.bms.scmp.api.purchase.service.impl;

import com.aiqin.bms.scmp.api.base.ApplyStatus;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.base.UrlConfig;
import com.aiqin.bms.scmp.api.base.WorkFlowBaseUrl;
import com.aiqin.bms.scmp.api.common.BizException;
import com.aiqin.bms.scmp.api.common.WorkFlowReturn;
import com.aiqin.bms.scmp.api.config.AuthenticationInterceptor;
import com.aiqin.bms.scmp.api.purchase.domain.RejectRecord;
import com.aiqin.bms.scmp.api.purchase.service.PurchaseApprovalService;
import com.aiqin.bms.scmp.api.util.AuthToken;
import com.aiqin.bms.scmp.api.util.HttpClientHelper;
import com.aiqin.bms.scmp.api.util.MD5Utils;
import com.aiqin.bms.scmp.api.util.MapUtil;
import com.aiqin.bms.scmp.api.workflow.annotation.WorkFlowAnnotation;
import com.aiqin.bms.scmp.api.workflow.enumerate.WorkFlow;
import com.aiqin.bms.scmp.api.workflow.helper.WorkFlowHelper;
import com.aiqin.bms.scmp.api.workflow.vo.request.WorkFlowCallbackVO;
import com.aiqin.bms.scmp.api.workflow.vo.request.WorkFlowVO;
import com.aiqin.bms.scmp.api.workflow.vo.response.WorkFlowRespVO;
import com.aiqin.ground.util.http.HttpClient;
import com.alibaba.fastjson.JSON;
import com.google.gson.JsonObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * <p>
 * ━━━━━━神兽出没━━━━━━
 * 　　┏┓　　　┏┓+ +
 * 　┏┛┻━━━┛┻┓ + +
 * 　┃　　　　　　　┃
 * 　┃　　　━　　　┃ ++ + + +
 * ████━████ ┃+
 * 　┃　　　　　　　┃ +
 * 　┃　　　┻　　　┃
 * 　┃　　　　　　　┃
 * 　┗━┓　　　┏━┛
 * 　　　┃　　　┃                  神兽保佑, 永无BUG!
 * 　　　┃　　　┃
 * 　　　┃　　　┃     Code is far away from bug with the animal protecting
 * 　　　┃　 　　┗━━━┓
 * 　　　┃ 　　　　　　　┣┓
 * 　　　┃ 　　　　　　　┏┛
 * 　　　┗┓┓┏━┳┓┏┛
 * 　　　　┃┫┫　┃┫┫
 * 　　　　┗┻┛　┗┻┛
 * ━━━━━━感觉萌萌哒━━━━━━
 * <p>
 * <p>
 * 思维方式*热情*能力
 */
@Service
@WorkFlowAnnotation(WorkFlow.APPLY_PURCHASE)
public class PurchaseApprovalServiceImpl implements PurchaseApprovalService,WorkFlowHelper  {

    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsRejectServiceImpl.class);

    @Resource
    private UrlConfig urlConfig;

    @Resource
    private WorkFlowBaseUrl workFlowBaseUrl;

    /**
     * 审核回调接口
     *
     * @param vo
     * @return
     */
    @Override
    public String workFlowCallback(WorkFlowCallbackVO vo) {
        //审批驳回
        RejectRecord rejectRecord = new RejectRecord();
        rejectRecord.setRejectRecordId(vo.getFormNo());
        if (Objects.equals(vo.getApplyStatus(), ApplyStatus.APPROVAL_FAILED.getNumber())) {
            return WorkFlowReturn.SUCCESS;
        }else if (Objects.equals(vo.getApplyStatus(), ApplyStatus.REVOKED.getNumber())) {
            //撤销
            return WorkFlowReturn.SUCCESS;
        }else if (Objects.equals(vo.getApplyStatus(), ApplyStatus.APPROVAL_SUCCESS.getNumber())) {
            //审批
            return WorkFlowReturn.SUCCESS;
        }
        return WorkFlowReturn.FALSE;
    }



    @Transactional(rollbackFor = Exception.class)
    public void workFlow(String formNo, String userName,String directSupervisorCode) {
        WorkFlowVO workFlowVO = new WorkFlowVO();
        //在审批中看到的页面
        workFlowVO.setFormUrl(workFlowBaseUrl.applyPurchase + "?code=" + formNo + "&" + workFlowBaseUrl.authority);
        workFlowVO.setHost(workFlowBaseUrl.supplierHost);
        workFlowVO.setUpdateUrl(workFlowBaseUrl.callBackBaseUrl + WorkFlow.APPLY_PURCHASE.getNum());
        workFlowVO.setFormNo(formNo);
        workFlowVO.setTitle(userName + "创建退供申请单审批");
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("auditPersonId",directSupervisorCode);
        workFlowVO.setVariables(jsonObject.toString());
        WorkFlowRespVO workFlowRespVO = callWorkFlowApi(workFlowVO, WorkFlow.APPLY_PURCHASE);
        //判断是否成功
        if (workFlowRespVO.getSuccess()) {
        } else {
            throw new BizException(ResultCode.PURCHASE_ERROR);
        }
    }

    public WorkFlowRespVO callWorkFlowApi(WorkFlowVO vo, WorkFlow workFlow) {
        vo.setKey(workFlow.getKey());
        LOGGER.info("GoodsRejectApprovalServiceImpl-callWorkFlowApi-工作流vo是：[{}],枚举是：[{}]", JSON.toJSONString(vo),JSON.toJSONString(workFlow));
        if(StringUtils.isEmpty(vo.getTitle())){
            vo.setTitle(workFlow.getTitle());
        }
        vo.setTicket(UUID.randomUUID().toString());
        vo.setTimeStamp(System.currentTimeMillis()+"");
        AuthToken currentAuthToken = AuthenticationInterceptor.getCurrentAuthToken();
        vo.setUsername(currentAuthToken.getPersonId());
        vo.setCurrentPositionCode(currentAuthToken.getPositionCode());
        //调用审批的接口
        Map<String, Object> stringObjectMap = MapUtil.objectToMap(vo);
        String s = stringObjectMap.toString() + urlConfig.ENCRYPTION_KEY;
        vo.setSign(MD5Utils.getMD5(s).toUpperCase());
        try {
            HttpClient httpClient = HttpClientHelper.getCurrentClient(HttpClient.get(urlConfig.WORKFLOW_URL));
            httpClient.addParameter("s", JSON.toJSONString(vo));
            String result1 = httpClient.action().result();
            LOGGER.info("调用审批流传入的参数是:[{}]",s);
            LOGGER.info("审批流返回数据:{}",result1);
            if(result1.startsWith("{")){
                return JSON.parseObject(result1,WorkFlowRespVO.class);
            }else {
                WorkFlowRespVO workFlowRespVO = new WorkFlowRespVO();
                LOGGER.info("审批接口数据返回错误，数据是：{}", result1);
                workFlowRespVO.setSuccess(false);
                workFlowRespVO.setMsg("审批接口数据返回错误");
                return workFlowRespVO;
            }
        } catch (Exception e) {
            WorkFlowRespVO workFlowRespVO = new WorkFlowRespVO();
            workFlowRespVO.setSuccess(false);
            workFlowRespVO.setMsg("调用审批接口失败");
            return workFlowRespVO;
        }
    }

}
