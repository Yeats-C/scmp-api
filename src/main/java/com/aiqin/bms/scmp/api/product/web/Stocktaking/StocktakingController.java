package com.aiqin.bms.scmp.api.product.web.Stocktaking;

import com.aiqin.bms.scmp.api.product.domain.Stocktaking.StocktakingInfo;
import com.aiqin.bms.scmp.api.product.domain.request.Stocktaking.SelectStocktakingRequest;
import com.aiqin.bms.scmp.api.product.domain.request.Stocktaking.SelectStocktakingWholeRequest;
import com.aiqin.bms.scmp.api.product.domain.request.Stocktaking.StocktakingWhole;
import com.aiqin.bms.scmp.api.product.domain.request.dictionary.ProductDistributorQuVoPage;
import com.aiqin.bms.scmp.api.product.domain.response.inbound.ProductDistributorReVoPage;
import com.aiqin.bms.scmp.api.product.service.Stocktaking.StocktakingService;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * Created by hzy on 2019/03/18.
 */
@RestController
@Api(tags = "盘点接口")
@Slf4j
@RequestMapping("/stocktaking")
public class StocktakingController {

    @Resource
    private StocktakingService stocktakingService;

    
    @PostMapping("/add")
    @ApiOperation(value = "添加盘点数据")
    public HttpResponse addStocktaking(@RequestBody StocktakingWhole param) {
    	
    	log.info("添加盘点数据参数:{}", param);
        return stocktakingService.addStocktakingWhole(param);
    }
    
    
    @PostMapping("/inventor")
    @ApiOperation(value = "查询库存")
    public HttpResponse<ProductDistributorReVoPage> selectInfo(@RequestBody ProductDistributorQuVoPage param) {
    	
    	log.info("查询库存参数:{}", param);
        return stocktakingService.searchInventories(param);	
    }
    
    
    @PostMapping("/list")
    @ApiOperation(value = "查询盘点记录")
    public HttpResponse<StocktakingInfo> selectStocktaking(@RequestBody SelectStocktakingRequest param) {
    	
    	log.info("查询盘点记录参数:[{}]",param);
        return stocktakingService.selectStocktaking(param);
    }
    
    @PostMapping("/detail")
    @ApiOperation(value = "查询盘点详情")
    public HttpResponse<StocktakingWhole> selectStocktakingWhole(@RequestBody SelectStocktakingWholeRequest param) {
    	
    	log.info("查询盘点详情参数:{}", param);
        return stocktakingService.selectStocktakingWhole(param);
    }

    @GetMapping("/update")
    @ApiOperation(value = "更新商品信息删除标识")
    public HttpResponse updateDelFlag(@Valid @RequestParam(name="stocktaking_product_id",required=true)String stocktakingProductId) {
    	
    	log.info("更新商品信息删除标识参数StocktakingProductId:[{}]", stocktakingProductId);
        return stocktakingService.updateDelFlag(stocktakingProductId);
    }
}