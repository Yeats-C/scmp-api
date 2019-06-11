package com.aiqin.bms.scmp.api.supplier.web;

import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.supplier.domain.request.OperationLogVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.LogData;
import com.aiqin.bms.scmp.api.supplier.service.OperationLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("log")
@Api(description = "日志操作")
public class OperationLogController {
@Autowired
private OperationLogService operationLogService;
    @GetMapping("/list")
    @ApiOperation("查询日志")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页数 从1开始", required = false, paramType = "query"),
            @ApiImplicitParam(name = "count", value = "每页个数", required = false, paramType = "query")
    })
    public HttpResponse<BasePage<LogData> > myArticle(Integer page, Integer count){
        return HttpResponse.success(operationLogService.list(page, count));
    }
    @PostMapping("/objectType")
    @ApiOperation("查询日志")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "project", value = "57(商品字典)，62(供应商字典)", required = true, paramType = "query")
    })
    public HttpResponse<BasePage<LogData>> myArticle(@RequestBody OperationLogVo operationLogVo, Integer project){
        return HttpResponse.success(operationLogService.getLogType(operationLogVo,project));
    }
}
