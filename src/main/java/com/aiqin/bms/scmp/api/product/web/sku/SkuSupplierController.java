package com.aiqin.bms.scmp.api.product.web.sku;


import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.common.BizException;
import com.aiqin.bms.scmp.api.constant.Global;
import com.aiqin.bms.scmp.api.product.domain.request.draft.SaveReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.sku.supplier.ApplySkuSupplyUnitReqVo;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Api(tags = "sku供应商管理api")
@RequestMapping("/product/sku/supplier")
@Slf4j
public class SkuSupplierController {

    @Autowired
    private ProductSkuSupplyUnitService productSkuSupplyUnitService;

    @PostMapping("/list")
    @ApiOperation("正式列表")
    public HttpResponse<BasePage<QueryProductSkuSupplyUnitsRespVo>> list(@RequestBody QuerySkuSupplyUnitReqVo reqVo){
        try {
            return HttpResponse.success(productSkuSupplyUnitService.getListPage(reqVo));
        } catch (BizException e) {
            return HttpResponse.failure(e.getMessageId());
        } catch (Exception e) {
            log.error(Global.ERROR, e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    @GetMapping("/detail")
    @ApiOperation("正式详情")
    public HttpResponse<SkuSupplierDetailRepsVo> detail(@RequestParam("skuCode") String skuCode) {
        try {
            return HttpResponse.success(productSkuSupplyUnitService.detail(skuCode));
        } catch (BizException e) {
            return HttpResponse.failure(e.getMessageId());
        } catch (Exception e) {
            log.error(Global.ERROR, e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    @PostMapping("/update")
    @ApiOperation("保存")
    public HttpResponse<Integer> update(@RequestBody UpdateSkuSupplyUnitReqVo reqVo){
        try {
            return HttpResponse.success(productSkuSupplyUnitService.update(reqVo));
        } catch (BizException e) {
            return HttpResponse.failure(e.getMessageId());
        } catch (Exception e) {
            log.error(Global.ERROR, e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }
    @PostMapping("/draft/list")
    @ApiOperation("待审请列表")
    public HttpResponse<BasePage<QueryProductSkuSupplyUnitsRespVo>> draftList(@RequestBody QuerySkuSupplyUnitReqVo reqVo){
        try {
            return HttpResponse.success(productSkuSupplyUnitService.getDraftListPage(reqVo));
        } catch (Exception e) {
            log.error(Global.ERROR, e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    @DeleteMapping("/draft/delete")
    @ApiOperation("待审请列表删除")
    public HttpResponse<Integer> draftDelete(@RequestParam("id") Long id) {
        try {
            return HttpResponse.success(productSkuSupplyUnitService.deleteDraftById(id));
        } catch (BizException e) {
            return HttpResponse.failure(e.getMessageId());
        } catch (Exception e) {
            log.error(Global.ERROR, e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    @GetMapping("/draft/detail")
    @ApiOperation("待审请列表详情")
    public HttpResponse<SkuSupplierDetailRepsVo> draftDetail(@RequestParam("id") Long id){
        try {
            return HttpResponse.successGenerics(productSkuSupplyUnitService.draftDetail(id));
        } catch (BizException e) {
            return HttpResponse.failure(e.getMessageId());
        } catch (Exception e) {
            log.error(Global.ERROR, e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    @PostMapping("/draft/update")
    @ApiOperation("待审请修改")
    public HttpResponse<Integer> draftUpdate(@RequestBody UpdateSkuSupplyUnitReqVo reqVo){
        try {
            return HttpResponse.successGenerics(productSkuSupplyUnitService.draftUpdate(reqVo));
        } catch (BizException e) {
            return HttpResponse.failure(e.getMessageId());
        } catch (Exception e) {
            log.error(Global.ERROR, e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }
    @PostMapping("/apply/save")
    @ApiOperation("审请提交")
    public HttpResponse<Integer> applySave(@RequestBody ApplySkuSupplyUnitReqVo reqVo){
        try {
            return HttpResponse.successGenerics( productSkuSupplyUnitService.applySave(reqVo));
        } catch (BizException e) {
            return HttpResponse.failure(e.getMessageId());
        } catch (Exception e) {
            log.error(Global.ERROR, e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    @PostMapping("/draft/idList")
    @ApiOperation("根据条件获取临时表id列表")
    public HttpResponse draftCodeList(@RequestBody QuerySkuSupplyUnitReqVo reqVo) {
        try {
            List<Long> ids = productSkuSupplyUnitService.getDraftListNoPage(reqVo).stream().map(QueryProductSkuSupplyUnitsRespVo::getId).collect(Collectors.toList());
            return HttpResponse.successGenerics(ids);
        } catch (Exception e) {
            log.error(Global.ERROR, e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }
}
