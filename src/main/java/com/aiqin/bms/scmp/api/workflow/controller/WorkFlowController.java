package com.aiqin.bms.scmp.api.workflow.controller;

import com.aiqin.bms.scmp.api.common.WorkFlowReturn;
import com.aiqin.bms.scmp.api.workflow.enumerate.WorkFlow;
import com.aiqin.bms.scmp.api.workflow.service.WorkFlowService;
import com.aiqin.bms.scmp.api.workflow.vo.request.WorkFlowCallbackVO;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-06-12
 * @time: 17:10
 */
@RestController
@RequestMapping("/workFlow")
@Slf4j
@Api(description = "审批流api")
public class WorkFlowController {
    @Autowired
    private WorkFlowService workFlowService;

    @PostMapping("/workFlowCallBack/{type}")
    @ApiOperation("审批回调")
    public String workFlowCallBack(@RequestBody WorkFlowCallbackVO vo,
                                   @PathVariable("type") Integer type) {
        log.info("WorkFlowController-workFlowCallBack-参数是：[{}],类型是：[{}]", JSONObject.toJSONString(vo), type);
        try {
            if (Objects.isNull(WorkFlow.getAll().get(type))) {
                return WorkFlowReturn.FALSE;
            }
            return workFlowService.WorkFlowCallBack(WorkFlow.getAll().get(type), vo);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return WorkFlowReturn.FALSE;
        }
    }
}
