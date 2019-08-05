package com.aiqin.bms.scmp.api.form.web;

import com.aiqin.bms.scmp.api.form.base.FormProcessKey;
import com.aiqin.bms.scmp.api.form.domain.FormApplyRequest;
import com.aiqin.bms.scmp.api.form.service.FormApplyService;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.aiqin.platform.flows.client.domain.vo.FormCallBackVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/form")
@Api(tags = "审批流-提交表单测试")
public class FormApplyController {

    @Resource
    private FormApplyService formApplyService;

    @PostMapping("/apply")
    @ApiOperation("测试提交")
    HttpResponse apply(@RequestBody FormApplyRequest request) {
        return formApplyService.submitActBaseProcess(request);
    }

    @PostMapping("/callback")
    @ApiOperation("测试回调")
    String callback(@RequestBody FormCallBackVo vo) {
        return formApplyService.callback(vo);
    }


}
