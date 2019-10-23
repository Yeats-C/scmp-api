package com.aiqin.bms.scmp.api.product.web.sku;


import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.common.BizException;
import com.aiqin.bms.scmp.api.constant.Global;
import com.aiqin.bms.scmp.api.product.domain.request.sku.supplier.QuerySkuSupplyUnitReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.sku.supplier.UpdateSkuSupplyUnitReqVo;
import com.aiqin.bms.scmp.api.product.domain.response.sku.ProductSkuSupplyUnitCapacityRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.sku.supplier.QueryProductSkuSupplyUnitsRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.sku.supplier.SkuSupplierDetailRepsVo;
import com.aiqin.bms.scmp.api.product.service.ProductSkuSupplyUnitService;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(tags = "sku供应商管理api")
@RequestMapping("/product/sku/supplier")
@Slf4j
public class SkuSupplierController {

    @Autowired
    private ProductSkuSupplyUnitService productSkuSupplyUnitService;

    @PostMapping("/list")
    @ApiOperation("正式列表")
    public HttpResponse<BasePage<QueryProductSkuSupplyUnitsRespVo>> list(QuerySkuSupplyUnitReqVo reqVo){
        try {
            return HttpResponse.success(productSkuSupplyUnitService.getListPage(reqVo));
        } catch (BizException e) {
            return HttpResponse.failure(e.getMessageId());
        } catch (Exception e) {
            log.error(Global.ERROR, e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    @GetMapping("/detai")
    @ApiOperation("正式详情")
    public HttpResponse<SkuSupplierDetailRepsVo> detail(String skuCode) {
        try {
            return HttpResponse.success(productSkuSupplyUnitService.detail(skuCode));
        } catch (BizException e) {
            return HttpResponse.failure(e.getMessageId());
        } catch (Exception e) {
            log.error(Global.ERROR, e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    @GetMapping("/capacity")
    @ApiOperation("正式查看产能")
    public HttpResponse<List<ProductSkuSupplyUnitCapacityRespVo>> capacityInfo(String supplyUnitCode,String productSkuCode){
        try {
            return HttpResponse.success(productSkuSupplyUnitService.getCapacityInfoBySupplyUnitCodeAndProductSkuCode(supplyUnitCode,productSkuCode));
        } catch (BizException e) {
            return HttpResponse.failure(e.getMessageId());
        } catch (Exception e) {
            log.error(Global.ERROR, e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    @PostMapping("/save")
    @ApiOperation("保存")
    public HttpResponse<Integer> update(UpdateSkuSupplyUnitReqVo reqVo){

        try {
            return HttpResponse.success(productSkuSupplyUnitService.update(reqVo));
        } catch (BizException e) {
            return HttpResponse.failure(e.getMessageId());
        } catch (Exception e) {
            log.error(Global.ERROR, e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

}
