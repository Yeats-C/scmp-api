package com.aiqin.bms.scmp.api.form.web;

import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.util.CollectionUtils;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.aiqin.platform.flows.client.domain.SaveMsgRequest;
import com.aiqin.platform.flows.client.service.FormDetailService;
import com.aiqin.platform.flows.client.service.FormMsgService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;
import sun.rmi.runtime.Log;

import javax.annotation.Resource;

@RestController
@RequestMapping("/form/msg")
@Api(tags = "审批流-审批历史消息")
@Slf4j
public class FormMsgController {

    @Resource
    private FormMsgService formMsgService;
    @Resource
    private FormDetailService formDetailService;

    @PostMapping("/detail")
    @ApiOperation("查询历史消息")
    HttpResponse findMsgProcessList(@RequestParam("person_id") String personId, @RequestParam(value = "form_no", required = false) String formNo, @RequestParam(value = "process_instance_id", required = false) String processInstanceId, @RequestParam(value = "is_history", required = false) String isHistory) {
        log.info("查询历史消息,personId={},form_no={},process_instance_id={},isHistory={}", personId, formNo, processInstanceId, isHistory);
        if (StringUtils.isBlank(processInstanceId)) {
            if (StringUtils.isBlank(formNo)) {
                log.error("查询历史消息-没有传表单编号");
                return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
            }
            Object data = formDetailService.getProcessInstanceIdByProcessCode(formNo).getData();
            if (data == null) {
                log.error("查询历史消息-查询不到流程实例id");
                return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
            }
            processInstanceId = String.valueOf(data);
        }
        return formMsgService.findMsgProcessList(processInstanceId, personId, isHistory);
    }

    @PostMapping("/")
    @ApiOperation("保存回复/追加的消息")
    HttpResponse saveMsgProcess(@RequestBody SaveMsgRequest request) {
        return formMsgService.saveMsgProcess(request);
    }

}
