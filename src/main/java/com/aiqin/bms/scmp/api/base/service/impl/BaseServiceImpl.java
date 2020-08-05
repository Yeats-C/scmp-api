package com.aiqin.bms.scmp.api.base.service.impl;

import com.aiqin.bms.scmp.api.base.*;
import com.aiqin.bms.scmp.api.base.service.BaseService;
import com.aiqin.bms.scmp.api.common.BizException;
import com.aiqin.bms.scmp.api.config.AuthenticationInterceptor;
import com.aiqin.bms.scmp.api.supplier.dao.EncodingRuleDao;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.EncodingRule;
import com.aiqin.bms.scmp.api.util.AuthToken;
import com.aiqin.bms.scmp.api.util.CodeUtils;
import com.aiqin.bms.scmp.api.workflow.enumerate.WorkFlow;
import com.aiqin.bms.scmp.api.workflow.vo.request.WorkFlowCallbackVO;
import com.aiqin.bms.scmp.api.workflow.vo.request.WorkFlowVO;
import com.aiqin.bms.scmp.api.workflow.vo.response.WorkFlowRespVO;
import com.aiqin.ground.util.exception.GroundRuntimeException;
import com.aiqin.ground.util.id.IdUtil;
import com.aiqin.ground.util.protocol.MessageId;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.aiqin.platform.flows.client.constant.FormUpdateUrlType;
import com.aiqin.platform.flows.client.constant.TpmBpmUtils;
import com.aiqin.platform.flows.client.domain.vo.StartProcessParamVO;
import com.aiqin.platform.flows.client.service.FormApplyCommonService;
import com.aiqin.platform.flows.client.service.FormOperateService;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

@Service
@Slf4j
public class BaseServiceImpl implements BaseService {

    @Value("${mgs.control.system-code}")
    private String systemCode;

    @Autowired
    private UrlConfig urlConfig;

    @Autowired
    private EncodingRuleDao encodingRuleDao;

    @Autowired
    private FormApplyCommonService formApplyCommonService;
    @Resource
    private FormOperateService formOperateService;

    @Resource
    private CodeUtils codeUtils;


    @Override
    public AuthToken getUser() {
        AuthToken currentAuthToken = AuthenticationInterceptor.getCurrentAuthToken();
        if (Objects.isNull(currentAuthToken)) {
            throw new BizException(ResultCode.LOGIN_ERROR);
        }
        return currentAuthToken;
    }

    @Override
    public String getStoreApiUrl(String path) {
        StringBuilder sb = new StringBuilder();
        sb.append(urlConfig.STORE_API_URL).append("/backstage").append(path);
        return sb.toString();
    }

    @Override
    public WorkFlowRespVO callWorkFlowApi(WorkFlowVO vo, WorkFlow workFlow) {
        log.info("BaseServiceImpl-callWorkFlowApi-工作流vo是：[{}],枚举是：[{}]", JSON.toJSONString(vo), JSON.toJSONString(workFlow));
        AuthToken currentAuthToken = AuthenticationInterceptor.getCurrentAuthToken();
        StartProcessParamVO paramVO = new StartProcessParamVO();
        paramVO.setCostType(workFlow.getKey());
        paramVO.setFormType(workFlow.getKey());
        paramVO.setCurrentUser(currentAuthToken.getPersonId());
        paramVO.setFormNo(vo.getFormNo());
        paramVO.setTitle(StringUtils.isNotBlank(vo.getTitle()) ? vo.getTitle() : workFlow.getTitle());
        paramVO.setRemark(null);
        paramVO.setFormUrl(vo.getFormUrl());
        paramVO.setFormUpdateUrl(vo.getUpdateUrl());
        paramVO.setPositionCode(vo.getPositionCode());
        paramVO.setFormUpdateUrlType(FormUpdateUrlType.HTTP);
        paramVO.setReceiptType(systemCode);

        paramVO.setPositionCode(vo.getPositionCode());
        paramVO.setSignTicket(IdUtil.uuid());
        if (StringUtils.isNotBlank(vo.getVariables())) {
            Map map = JSON.parseObject(vo.getVariables(), Map.class);
            paramVO.setVariables(map);
        }
        log.info("调用审批流发起申请,request={}", JSON.toJSON(paramVO));
        HttpResponse response = formApplyCommonService.submitActBaseProcessScmp(paramVO);
        log.info("调用审批流发起申请返回结果,result={}", JSON.toJSON(response));
        WorkFlowRespVO workFlowRespVO = new WorkFlowRespVO();
        if (Objects.equals(response.getCode(), "0")) {
            workFlowRespVO.setSuccess(true);
            Map<String, Integer> data = (Map<String, Integer>) response.getData();
            workFlowRespVO.setStatus(data.get("status"));
        } else {
            workFlowRespVO.setSuccess(false);
            workFlowRespVO.setMsg(response.getMessage());
        }
        return workFlowRespVO;


//        vo.setKey(workFlow.getKey());
//        if(StringUtils.isEmpty(vo.getTitle())){
//            vo.setTitle(workFlow.getTitle());
//        }
//        vo.setTimeStamp(System.currentTimeMillis()+"");
//        vo.setTicket(UUID.randomUUID().toString());
//
//        vo.setUsername(currentAuthToken.getPersonId());
//        vo.setCurrentPositionCode(currentAuthToken.getPositionCode());
//        //调用审批的接口
//        Map<String, Object> stringObjectMap = objectToMap(vo);
//        String s = stringObjectMap.toString() + urlConfig.ENCRYPTION_KEY;
//        vo.setSign(MD5Utils.getMD5(s).toUpperCase());
//        try {
//            HttpClient httpClient = HttpClientHelper.getCurrentClient(HttpClient.get(urlConfig.WORKFLOW_URL));
//            httpClient.addParameter("s", JSON.toJSONString(vo));
//            log.info("调用审批流传入的参数是:[{}]",s);
//            String result1 = httpClient.action().result();
//            log.info("审批流返回数据:{}",result1);
//            if(result1.startsWith("{")){
//                return JSON.parseObject(result1,WorkFlowRespVO.class);
//            }else {
//                log.info("审批接口数据返回错误，数据是：{}", result1);
//                WorkFlowRespVO workFlowRespVO = new WorkFlowRespVO();
//                workFlowRespVO.setSuccess(false);
//                workFlowRespVO.setMsg("审批接口数据返回错误");
//                return workFlowRespVO;
//            }
//        } catch (Exception e) {
//            WorkFlowRespVO workFlowRespVO = new WorkFlowRespVO();
//            workFlowRespVO.setSuccess(false);
//            workFlowRespVO.setMsg("调用审批接口失败");
//            return workFlowRespVO;
//        }
    }

    /**
     * 排序转map
     *
     * @param obj
     * @return java.util.Map<java.lang.String                                                               ,                                                               java.lang.Object>
     * @author zth
     * @date 2019/1/18
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
     * 设置状态
     *
     * @param costApplyO
     * @return com.aiqin.mgs.api.api.domain.request.workflow.WorkFlowCallbackVO
     * @author zth
     * @date 2019/1/22
     */
    protected WorkFlowCallbackVO updateSupStatus(WorkFlowCallbackVO costApplyO) {
        /**保存流程状态*/
        if (Indicator.COST_FORM_STATUS_APPROVED.getCode().equals(costApplyO.getUpdateFormStatus())) {
            //审核通过
            costApplyO.setApplyStatus(ApplyStatus.APPROVAL_SUCCESS.getNumber());
        } else if (TpmBpmUtils.isPass(costApplyO.getUpdateFormStatus(), costApplyO.getOptBtn())) {
            //审核中
            costApplyO.setApplyStatus(ApplyStatus.APPROVAL.getNumber());
        } else if (IndicatorStr.PROCESS_BTN_CANCEL.getCode().equals(costApplyO.getOptBtn())) {
            //撤销
            costApplyO.setApplyStatus(ApplyStatus.REVOKED.getNumber());
        } else {
            //驳回
            costApplyO.setApplyStatus(ApplyStatus.APPROVAL_FAILED.getNumber());
        }
        return costApplyO;
    }

    @Override
    public WorkFlowRespVO cancelWorkFlow(WorkFlowVO vo) {
        log.info("BaseServiceImpl-cancelWorkFlow-工作流vo是：[{}]", JSON.toJSONString(vo));
        // AuthToken currentAuthToken = AuthenticationInterceptor.getCurrentAuthToken();
        HttpResponse httpResponse = formOperateService.commonCancel(vo.getFormNo(), vo.getUsername());
        WorkFlowRespVO workFlowRespVO = new WorkFlowRespVO();
        if (httpResponse.getCode().equals(MessageId.SUCCESS_CODE)) {
            workFlowRespVO.setSuccess(Boolean.TRUE);
        } else {
            workFlowRespVO.setSuccess(Boolean.FALSE);
            workFlowRespVO.setMsg(httpResponse.getMessage());
        }
        return workFlowRespVO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public synchronized String getCode(String prefix, String Code) {
        //EncodingRule numberingType = encodingRuleDao.getNumberingType(Code);
        String redisCode = codeUtils.getRedisCode(Code);
        String code;
        if (StringUtils.isNotBlank(prefix)) {
            code = prefix + redisCode;
        } else {
            code = redisCode;
        }
        //更新编码
        //encodingRuleDao.updateNumberValue(numberingType.getNumberingValue(), numberingType.getId());
        return code;
    }



}
