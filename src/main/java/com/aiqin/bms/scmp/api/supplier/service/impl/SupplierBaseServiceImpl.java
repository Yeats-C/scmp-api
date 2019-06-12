package com.aiqin.bms.scmp.api.supplier.service.impl;

import com.aiqin.bms.scmp.api.base.*;
import com.aiqin.bms.scmp.api.common.workflow.WorkFlowCallbackVO;
import com.aiqin.bms.scmp.api.common.workflow.WorkFlowVO;
import com.aiqin.bms.scmp.api.config.AuthenticationInterceptor;
import com.aiqin.bms.scmp.api.supplier.domain.response.workflow.WorkFlowRespVO;
import com.aiqin.bms.scmp.api.supplier.service.SupplierBaseService;
import com.aiqin.bms.scmp.api.util.AuthToken;
import com.aiqin.bms.scmp.api.util.HttpClientHelper;
import com.aiqin.bms.scmp.api.util.MD5Utils;
import com.aiqin.bms.scmp.api.workflow.annotation.WorkFlow;
import com.aiqin.ground.util.exception.GroundRuntimeException;
import com.aiqin.ground.util.http.HttpClient;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

/**
 * Description:
 *
 * @author: zth
 * @date: 2019-01-03
 * @time: 11:10
 */
@Service
@Slf4j
public class SupplierBaseServiceImpl implements SupplierBaseService {

    @Autowired
    private UrlConfig urlConfig;

    @Override
    public String getPurChaseApiUrl(String path){
        StringBuilder sb = new StringBuilder();
       // sb.append(urlConfig.PURCHASE_API_URL).append("/purchase").append(path);
        return sb.toString();
    }

    @Override
    public String getProductApiUrl(String path){
        StringBuilder sb = new StringBuilder();
        //sb.append(urlConfig.PRODUCT_API_URL).append("/product").append(path);
        return sb.toString();
    }
    @Override
    public String getSkuApiUrl(String path){
        StringBuilder sb = new StringBuilder();
        //sb.append(urlConfig.PRODUCT_API_URL).append("/sku").append(path);
        return sb.toString();
    }

    @Override
    public String getStoreApiUrl(String path){
        StringBuilder sb = new StringBuilder();
        sb.append(urlConfig.STORE_API_URL).append("/backstage").append(path);
        return sb.toString();
    }

    @Override
    public WorkFlowRespVO callWorkFlowApi(WorkFlowVO vo, WorkFlow workFlow) {
        log.info("SupplierBaseServiceImpl-callWorkFlowApi-工作流vo是：[{}],枚举是：[{}]",JSON.toJSONString(vo),JSON.toJSONString(workFlow));
        vo.setKey(workFlow.getKey());
      //  vo.setTitle(workFlow.getTitle());
        vo.setTimeStamp(System.currentTimeMillis()+"");
        //TODO 从登陆人拿，目前暂时拿不到，现在先写死
        vo.setTicket(UUID.randomUUID().toString());
        AuthToken currentAuthToken = AuthenticationInterceptor.getCurrentAuthToken();
        vo.setUsername(currentAuthToken.getPersonId());
        vo.setCurrentPositionCode(currentAuthToken.getPositionCode());
//        vo.setUsername(urlConfig.USER_NAME_KEY);
//        vo.setCurrentPositionCode(urlConfig.CURRENT_POSITION_CODE_KEY);
        //调用审批的接口
        Map<String, Object> stringObjectMap = objectToMap(vo);
        String s = stringObjectMap.toString() + urlConfig.ENCRYPTION_KEY;
        vo.setSign(MD5Utils.getMD5(s).toUpperCase());
        try {
            HttpClient httpClient = HttpClientHelper.getCurrentClient(HttpClient.get(urlConfig.WORKFLOW_URL));
            httpClient.addParameter("s", JSON.toJSONString(vo));
            log.info("调用审批流传入的参数是:[{}]",s);
            String result1 = httpClient.action().result();
            if(result1.startsWith("{")){
                return JSON.parseObject(result1,WorkFlowRespVO.class);
            }else {
                log.info("审批接口数据返回错误，数据是：{}", result1);
                WorkFlowRespVO workFlowRespVO = new WorkFlowRespVO();
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

    /**
     * 排序转map
     * @author zth
     * @date 2019/1/18
     * @param obj
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    private static Map<String, Object> objectToMap(Object obj) {
        try {
            Map<String, Object> map = new TreeMap<>(
                    new Comparator<String>() {
                        @Override
                        public int compare(String obj1, String obj2) {
                            // 降序排序
                            return obj2.compareTo(obj1);
                        }
                    });
            Class<?> clazz = obj.getClass();
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                String fieldName = field.getName();
                if (!"sign".equals(fieldName) && !"title".equals(fieldName)) {
                    Object value = field.get(obj);
                    map.put(fieldName, value);
                }
            }
            return map;
        } catch (IllegalAccessException e) {
            e.getMessage();
            throw new GroundRuntimeException("转换失败！");
        }
    }
    /**
     * 判断是否通过
     * @author zth
     * @date 2019/1/22
     * @param updateFormStatus
     * @param optBtn
     * @return boolean
     */
    public static boolean isPass(Integer updateFormStatus, String optBtn) {
        if (updateFormStatus.equals(Indicator.COST_FORM_STATUS_APPROVING.getCode())) {
            if (optBtn.equals(IndicatorStr.PROCESS_BTN_ISSUE.getCode())
                    || optBtn.equals(IndicatorStr.PROCESS_BTN_APPROVAL.getCode())
                    || optBtn.equals(IndicatorStr.PROCESS_BTN_AUDIT.getCode())
                    || optBtn.equals(IndicatorStr.PROCESS_BTN_BATCH_COMPLETE.getCode())) {
                //已阅 同意 审核
                return true;
            }
        }
        return false;
    }
    /**
     * 设置状态
     * @author zth
     * @date 2019/1/22
     * @param costApplyO
     * @return com.aiqin.mgs.supplier.api.domain.request.workflow.WorkFlowCallbackVO
     */
    public WorkFlowCallbackVO updateSupStatus(WorkFlowCallbackVO costApplyO) {
        /**保存流程状态*/
        if (costApplyO.getUpdateFormStatus().equals(Indicator.COST_FORM_STATUS_APPROVED.getCode())) {
            //审核通过
            costApplyO.setApplyStatus(ApplyStatus.APPROVAL_SUCCESS.getNumber());
        } else if (isPass(costApplyO.getUpdateFormStatus(), costApplyO.getOptBtn())) {
            //审核中
            costApplyO.setApplyStatus(ApplyStatus.APPROVAL.getNumber());
        } else if(costApplyO.getOptBtn().equals(IndicatorStr.PROCESS_BTN_CANCEL.getCode())) {
            //撤销
            costApplyO.setApplyStatus(ApplyStatus.REVOKED.getNumber());
        } else {
            //驳回
            costApplyO.setApplyStatus(ApplyStatus.APPROVAL_FAILED.getNumber());
        }
        return costApplyO;
    }
    public WorkFlowRespVO cancelWorkFlow(WorkFlowVO vo){
        log.info("SupplierBaseServiceImpl-cancelWorkFlow-工作流vo是：[{}]",JSON.toJSONString(vo));
//        vo.setTimeStamp(System.currentTimeMillis()+"");
        //TODO 从登陆人拿，目前暂时拿不到，现在先写死
        vo.setTicket(UUID.randomUUID().toString());
        AuthToken currentAuthToken = AuthenticationInterceptor.getCurrentAuthToken();
        vo.setUsername(currentAuthToken.getPersonId());
        vo.setCurrentPositionCode(currentAuthToken.getPositionCode());
//        vo.setUsername(urlConfig.USER_NAME_KEY);
//        vo.setCurrentPositionCode(urlConfig.CURRENT_POSITION_CODE_KEY);
        //调用审批的接口
        Map<String, Object> stringObjectMap = objectToMap(vo);
        String s = stringObjectMap.toString() + urlConfig.ENCRYPTION_KEY;
        vo.setSign(MD5Utils.getMD5(s).toUpperCase());
        //设置返回vo
        WorkFlowRespVO workFlowRespVO = new WorkFlowRespVO();
        try {
            HttpClient httpClient = HttpClientHelper.getCurrentClient(HttpClient.get(urlConfig.WORKFLOW_CANCEL_URL));
            httpClient.addParameter("s", JSON.toJSONString(vo));
            log.info("调用审批流传入的参数是:[{}]",s);
            String result1 = httpClient.action().result();
            if(result1.startsWith("{")){
                return JSON.parseObject(result1,WorkFlowRespVO.class);
            }else {
                log.info("审批接口数据返回错误，数据是：{}", result1);
                workFlowRespVO.setSuccess(false);
                workFlowRespVO.setMsg("审批接口数据返回错误");
                return workFlowRespVO;
            }
        } catch (Exception e) {
            workFlowRespVO.setSuccess(false);
            workFlowRespVO.setMsg("调用审批接口失败");
            return workFlowRespVO;
        }
    }
}
