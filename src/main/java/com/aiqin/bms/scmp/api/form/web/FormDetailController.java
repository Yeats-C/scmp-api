package com.aiqin.bms.scmp.api.form.web;

import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.aiqin.platform.flows.client.service.FormDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/form/detail")
@Api(tags = "审批流-表单详情")
@Slf4j
public class FormDetailController {

    @Resource
    private FormDetailService formDetailService;

    @GetMapping("/key/{form_no}")
    @ApiOperation("根据表单编号获取流程定义key")
    HttpResponse getProcessDefinitionKeyByProcessCode(@PathVariable(value = "form_no") String formNo) {
        return formDetailService.getProcessDefinitionKeyByProcessCode(formNo);
    }

    @GetMapping("/process/id/{form_no}")
    @ApiOperation("根据表单编号获取流程实例id")
    HttpResponse getProcessInstanceIdByProcessCode(@PathVariable(value = "form_no") String formNo) {
        return formDetailService.getProcessInstanceIdByProcessCode(formNo);
    }

    @PostMapping("/log")
    @ApiOperation("查询流程日志")
    HttpResponse goTaskOpLogList(@RequestParam(value = "process_instance_id", required = false) String processInstanceId,
                                 @RequestParam(value = "form_no", required = false) String formNo,
                                 @RequestParam(value = "is_intervene", required = false) String isIntervene,
                                 @RequestParam("person_id") String personId) {
        log.info("查询流程日志,personId={},form_no={},process_instance_id={},isIntervene={}", personId, formNo, processInstanceId, isIntervene);
        if (StringUtils.isBlank(processInstanceId)) {
            if (StringUtils.isBlank(formNo)) {
                log.error("查询流程日志-没有传表单编号");
                return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
            }
            Object data = formDetailService.getProcessInstanceIdByProcessCode(formNo).getData();
            if (data == null) {
                log.error("查询流程日志-查询不到流程实例id");
                return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
            }
            processInstanceId = String.valueOf(data);
        }
        return formDetailService.goTaskOpLogList(processInstanceId, isIntervene, personId);
    }

    @PostMapping("/pic")
    @ApiOperation("获取流程图片信息")
    HttpResponse getProcessTracking(@RequestParam(value = "process_instance_id", required = false) String processInstanceId,
                                    @RequestParam(value = "form_no", required = false) String formNo,
                                    @RequestParam(value = "is_intervene", required = false) String isIntervene,
                                    @RequestParam("person_id") String personId) {
        log.info("获取流程图片信息,personId={},form_no={},process_instance_id={},isIntervene={}", personId, formNo, processInstanceId, isIntervene);
        if (StringUtils.isBlank(processInstanceId)) {
            if (StringUtils.isBlank(formNo)) {
                log.error("获取流程图片信息-没有传表单编号");
                return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
            }
            Object data = formDetailService.getProcessInstanceIdByProcessCode(formNo).getData();
            if (data == null) {
                log.error("获取流程图片信息-查询不到流程实例id");
                return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
            }
            processInstanceId = String.valueOf(data);
        }
        return formDetailService.getProcessTracking(processInstanceId, isIntervene, personId);
    }


}
