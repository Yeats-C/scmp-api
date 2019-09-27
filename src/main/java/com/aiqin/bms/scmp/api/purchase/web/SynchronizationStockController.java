package com.aiqin.bms.scmp.api.purchase.web;

import com.aiqin.bms.scmp.api.base.PagesRequest;
import com.aiqin.bms.scmp.api.purchase.jobs.SynchronizationStockService;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author: zhao shuai
 * @create: 2019-09-27
 **/

@Api(tags = "同步库存的数据")
@RequestMapping("/synchronization")
@RestController
public class SynchronizationStockController {

    @Resource
    private SynchronizationStockService synchronizationStockService;

    @GetMapping("/stock")
    @ApiOperation("同步库存数据的定时任务")
    public HttpResponse synchronizationStock(@RequestParam(value = "page_size", required = false) Integer pageSize,
                                             @RequestParam(value = "page_no", required = false) Integer pageNo,
                                             @RequestParam(value = "is_page", required = false) Integer isPage) {
        PagesRequest request = new PagesRequest();
        request.setPageNo(pageNo);
        request.setPageSize(pageSize);
        return synchronizationStockService.synchronizationStock(request,isPage);
    }
}
