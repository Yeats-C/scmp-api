package com.aiqin.bms.scmp.api.form.web;

import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.aiqin.platform.flows.client.domain.FormListRequest;
import com.aiqin.platform.flows.client.service.FormListService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/form/list")
@Api(tags = "审批流-审批列表")
public class FormListController {

    @Resource
    private FormListService formListService;
    
//    private final String receiptType = "scmp-system";
    
    @Value("${mgs.control.system-code}")
    private String systemCode;

    @PostMapping("/send")
    @ApiOperation("查询我的已发")
    HttpResponse findActBaseList(@RequestBody FormListRequest formListRequest) {
        formListRequest.setReceiptType(2);
        return formListService.findActBaseList(formListRequest);
    }

    @PostMapping("/wait")
    @ApiOperation("查询我的待办")
    HttpResponse findMyTaskList(@RequestBody FormListRequest formListRequest) {
        formListRequest.setReceiptType(2);
        return formListService.findMyTaskList(formListRequest);
    }

    @PostMapping("/done")
    @ApiOperation("查询我的已办")
    HttpResponse findMyDoTaskList(@RequestBody FormListRequest formListRequest) {
        formListRequest.setReceiptType(2);
        return formListService.findMyDoTaskList(formListRequest);
    }

    @PostMapping("/relate")
    @ApiOperation("查询抄送我的")
    HttpResponse findMyTaskListTaskDefinitionKeyWithzhsp(@RequestBody FormListRequest formListRequest) {
        formListRequest.setReceiptType(2);
        return formListService.findMyTaskListTaskDefinitionKeyWithzhsp(formListRequest);
    }

    @GetMapping("/count")
    @ApiOperation("查询数量")
    HttpResponse findTaskListCount(@RequestParam("person_id") String personId, @RequestParam(value = "process_key", required = false) String processKey) {
        return formListService.findTaskListCount(personId, "2", processKey);
    }

    @GetMapping("/process")
    @ApiOperation("审批类型列表")
    HttpResponse getProcessListByType() {
        return formListService.getProcessListByType(systemCode);
    }

}
