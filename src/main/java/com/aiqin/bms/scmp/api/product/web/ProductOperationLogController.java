package com.aiqin.bms.scmp.api.product.web;

import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.aiqin.bms.scmp.api.product.domain.request.OperationLogVo;
import com.aiqin.bms.scmp.api.product.service.ProductOperationLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product/operation")
@Api(description = "供应商字典")
public class ProductOperationLogController {
    @Autowired
    private ProductOperationLogService productOperationLogService;

    @PostMapping("/objectType")
    @ApiOperation("查询日志")
    public HttpResponse myArticle(@RequestBody OperationLogVo operationLogVo){
        return HttpResponse.success(productOperationLogService.getLogType(operationLogVo));
    }
}
