package com.aiqin.bms.scmp.api.form.web;

import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.aiqin.platform.flows.client.domain.*;
import com.aiqin.platform.flows.client.service.FormOperateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/form/operate")
@Api(tags = "审批流-功能操作")
public class FormOperateController {

    @Resource
    private FormOperateService formOperateService;

    @PostMapping("/complete")
    @ApiOperation("同意")
    HttpResponse complete(@RequestBody FormCompleteRequest request) {
        return formOperateService.complete(request);
    }

    @PostMapping("/read")
    @ApiOperation("已阅")
    HttpResponse read(@RequestBody FormCompleteRequest request) {
        return formOperateService.read(request);
    }

    @PostMapping("/save/in")
    @ApiOperation("知会")
    HttpResponse saveInform(@RequestBody FormSaveInRequest request) {
        return formOperateService.saveInform(request);
    }

    @PostMapping("/reject")
    @ApiOperation("驳回")
    HttpResponse saveRejectToApply(@RequestBody FormRejectRequest request) {
        return formOperateService.saveRejectToApply(request);
    }

    @PostMapping("/send/in")
    @ApiOperation("转发")
    HttpResponse saveProcessSendInform(@RequestBody FormSendInRequest request) {
        return formOperateService.saveProcessSendInform(request);
    }

    @PostMapping("/cancel")
    @ApiOperation("撤销")
    HttpResponse saveToCancel(@RequestBody FormCancelRequest request) {
        return formOperateService.saveToCancel(request);
    }

    @PostMapping("/back")
    @ApiOperation("回退")
    HttpResponse saveTurnBack(@RequestBody FormBackRequest request) {
        return formOperateService.saveTurnBack(request);
    }

    @GetMapping("/back/process")
    @ApiOperation("获得回退的审批节点")
    HttpResponse saveToCancel(@RequestParam("process_instance_id") String processInstanceId, @RequestParam("task_id") String taskId) {
        return formOperateService.getBackHisProcessOptLog(processInstanceId, taskId);
    }

    @PostMapping("/node")
    @ApiOperation("保存加签")
    HttpResponse saveAddNode(@RequestBody FormCacheAddNodeRequest request) {
        return formOperateService.saveAddNode(request);
    }

    @GetMapping("/node/cache")
    @ApiOperation("获取缓存加签")
    HttpResponse getCacheAddNode(@RequestParam("task_id") String taskId) {
        return formOperateService.getCacheAddNode(taskId);
    }

    @PostMapping("/node/cache")
    @ApiOperation("添加缓存加签")
    HttpResponse cacheAddNode(@RequestBody FormCacheAddNodeRequest request) {
        return formOperateService.cacheAddNode(request);
    }

}
