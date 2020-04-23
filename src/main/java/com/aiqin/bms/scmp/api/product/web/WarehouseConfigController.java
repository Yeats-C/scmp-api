package com.aiqin.bms.scmp.api.product.web;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.base.PageResData;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.common.BizException;
import com.aiqin.bms.scmp.api.common.TagTypeCode;
import com.aiqin.bms.scmp.api.config.AuthenticationInterceptor;
import com.aiqin.bms.scmp.api.product.domain.pojo.Stock;
import com.aiqin.bms.scmp.api.product.domain.request.*;
import com.aiqin.bms.scmp.api.product.domain.request.inbound.InboundReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.merchant.MerchantLockStockReqVo;
import com.aiqin.bms.scmp.api.product.domain.response.*;
import com.aiqin.bms.scmp.api.product.domain.response.stock.StockBatchRespVO;
import com.aiqin.bms.scmp.api.product.domain.response.stock.StockFlowRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.stock.StockRespVO;
import com.aiqin.bms.scmp.api.product.service.StockService;
import com.aiqin.bms.scmp.api.product.service.WarehouseConfigService;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.ApplyUseTagRecord;
import com.aiqin.bms.scmp.api.supplier.service.ApplyUseTagRecordService;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wuyongqiang
 * @description 库存管理请求控制器
 * @date 2018/11/20 15:04
 */
@RestController
@Api(tags = "库房配置接口")
@RequestMapping("/warehouseConfig")
@Slf4j
public class WarehouseConfigController {



    @Resource
    private WarehouseConfigService warehouseConfigService;

    @PostMapping("/search/page")
    @ApiOperation(value = "库房管理列表")
    public HttpResponse<BasePage<WarehouseConfigResp>> getPage(@RequestBody WarehouseConfigReq warehouseConfigReq) {
        log.info("进来咯");
        return HttpResponse.success(warehouseConfigService.getPage(warehouseConfigReq));
    }

    @GetMapping("/refresh")
    @ApiOperation(value = "刷新")
    public HttpResponse<WarehouseConfigResp> refresh(@RequestParam("stock_code") String stock_code) {
        log.info("进来咯");
        return HttpResponse.success(warehouseConfigService.refresh(stock_code));
    }

    @PostMapping("/save")
    @ApiOperation(value = "库房配置增加")
    public HttpResponse<Boolean> save(@RequestBody WarehouseConfigReq warehouseConfigReq) {
        return HttpResponse.success(warehouseConfigService.save(warehouseConfigReq));
    }

    @GetMapping("/load")
    @ApiOperation(value = "库房配置详情")
    public HttpResponse<WarehouseConfigResp> load(Long id) {
        return HttpResponse.success(warehouseConfigService.load(id));
    }


    @PostMapping("/update")
    @ApiOperation(value = "库房配置详情")
    public HttpResponse<Boolean> update( @RequestBody WarehouseConfigReq warehouseConfigReq) {
        return HttpResponse.success(warehouseConfigService.update(warehouseConfigReq));
    }
}
