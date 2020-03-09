package com.aiqin.bms.scmp.api.product.web.salearea;

import com.aiqin.bms.scmp.api.base.AreaBasic;
import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.common.BizException;
import com.aiqin.bms.scmp.api.product.domain.request.salearea.*;
import com.aiqin.bms.scmp.api.product.domain.response.salearea.*;
import com.aiqin.bms.scmp.api.product.service.ProductSaleAreaService;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-05-09
 * @time: 16:31
 */
@RestController
@Slf4j
@RequestMapping("/product/salearea")
@Api(description = "销售区域api")
public class ProductSaleAreaController {
    @Autowired
    private ProductSaleAreaService productSaleAreaService;

    @PostMapping("/add")
    @ApiOperation("新增销售区域")
    public HttpResponse<Boolean> addSaleArea(@RequestBody ProductSkuSaleAreaMainReqVO request) {
        log.info("ProductSaleAreaController--addSaleAreaDraft--入参: [{}]", request);
        try {
           return HttpResponse.success(productSaleAreaService.addSaleArea(request));
        } catch (BizException e) {
            log.error(e.getMessageId().getMessage());
            return HttpResponse.failure(e.getMessageId());
        } catch (Exception ex){
            ex.printStackTrace();
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    @PostMapping("/list/official")
    @ApiOperation("正式数据列表")
    public HttpResponse<BasePage<QueryProductSaleAreaMainRespVO>> queryListForOfficial(@RequestBody QueryProductSaleAreaMainReqVO request) {
        log.info("ProductSaleAreaController--ProductSaleAreaController--入参: [{}]", request);
        try {
            return HttpResponse.success(productSaleAreaService.queryListForOfficial(request));
        } catch (BizException e) {
            log.error(e.getMessageId().getMessage());
            return HttpResponse.failure(e.getMessageId());
        } catch (Exception ex){
            ex.printStackTrace();
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }
//    @PostMapping("/add/apply")
//    @ApiOperation("新增销售区域申请")
//    public HttpResponse<Boolean> addSaleAreaApply(@RequestBody ApplySaleAreaReqVO reqVO) {
//        log.info("ProductSaleAreaController--addSaleAreaApply--入参: [{}]", reqVO);
//        try {
//            return HttpResponse.success(productSaleAreaService.addSaleAreaApply(reqVO));
//        } catch (BizException e) {
//            log.error(e.getMessageId().getMessage());
//            return HttpResponse.failure(e.getMessageId());
//        } catch (Exception ex){
//            ex.printStackTrace();
//            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
//        }
//    }

    @GetMapping("/official/info")
    @ApiOperation("销售区域详情")
    public HttpResponse<ProductSaleAreaForOfficialMainRespVO> officialView(@RequestParam String code) {
        log.info("ProductSaleAreaController--officialView--入参: [{}]", code);
        try {
            return HttpResponse.success(productSaleAreaService.officialView(code));
        } catch (BizException e) {
            log.error(e.getMessageId().getMessage());
            return HttpResponse.failure(e.getMessageId());
        } catch (Exception ex){
            ex.printStackTrace();
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    @PostMapping("/edit")
    @ApiOperation("编辑销售区域")
    public HttpResponse<Boolean> editView(@RequestBody ProductSkuSaleAreaMainReqVO productSkuSaleAreaMainReqVO) {
        log.info("ProductSaleAreaController--editView--入参: [{}]", productSkuSaleAreaMainReqVO.toString());
        try {
            return HttpResponse.success(productSaleAreaService.editView(productSkuSaleAreaMainReqVO));
        } catch (BizException e) {
            log.error(e.getMessageId().getMessage());
            return HttpResponse.failure(e.getMessageId());
        } catch (Exception ex){
            ex.printStackTrace();
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    @DeleteMapping("/draft/delete")
    @ApiOperation("删除临时表数据")
    public HttpResponse<Boolean> deleteDraft(@RequestParam String code) {
        log.info("ProductSaleAreaController--deleteDraft--入参: [{}]", code);
        try {
            return HttpResponse.success(productSaleAreaService.deleteDraft(code));
        } catch (BizException e) {
            log.error(e.getMessageId().getMessage());
            return HttpResponse.failure(e.getMessageId());
        } catch (Exception ex){
            ex.printStackTrace();
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    @GetMapping("/apply/cancel")
    @ApiOperation("撤销数据")
    public HttpResponse<Boolean> cancel(@RequestParam String code) {
        log.info("ProductSaleAreaController--cancel--入参: [{}]", code);
        try {
            return HttpResponse.success(productSaleAreaService.cancelApply(code));
        } catch (BizException e) {
            log.error(e.getMessageId().getMessage());
            return HttpResponse.failure(e.getMessageId());
        } catch (Exception ex){
            ex.printStackTrace();
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }
    @PostMapping("/official/skuList")
    @ApiOperation("sku商品销售区域列表")
    public HttpResponse<BasePage<QueryProductSaleAreaSkuRespVO>> officialSkuList(@RequestBody QueryProductSaleAreaReqVO reqVO) {
        log.info("ProductSaleAreaController--officialSkuList--入参: [{}]", JSONObject.toJSONString(reqVO));
        try {
            return HttpResponse.success(productSaleAreaService.officialSkuList(reqVO));
        } catch (BizException e) {
            log.error(e.getMessageId().getMessage());
            return HttpResponse.failure(e.getMessageId());
        } catch (Exception ex){
            ex.printStackTrace();
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    @PostMapping ("/official/skuDetail")
    @ApiOperation("sku商品销售详情")
    public HttpResponse<QueryProductSaleAreaSkuRespVO> skuDetail(@RequestBody QueryProductDetailReqVO reqVO) {
        log.info("ProductSaleAreaController--officialSkuList--入参: [{}]", JSONObject.toJSONString(reqVO));
        try {
            return HttpResponse.success(productSaleAreaService.skuDetail(reqVO));
        } catch (BizException e) {
            log.error(e.getMessageId().getMessage());
            return HttpResponse.failure(e.getMessageId());
        } catch (Exception ex){
            ex.printStackTrace();
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    @PostMapping ("/edit/skuDetail")
    @ApiOperation("sku商品销售编辑")
    public HttpResponse<QueryProductSaleAreaSkuRespVO> skuEdit(@RequestBody QueryProductDetailReqVO reqVO) {
        log.info("ProductSaleAreaController--officialSkuList--入参: [{}]", JSONObject.toJSONString(reqVO));
        try {
            return HttpResponse.success(productSaleAreaService.skuEdit(reqVO));
        } catch (BizException e) {
            log.error(e.getMessageId().getMessage());
            return HttpResponse.failure(e.getMessageId());
        } catch (Exception ex){
            ex.printStackTrace();
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

//    @PostMapping("/draft/skuList")
//    @ApiOperation("选sku时的列表")
//    public HttpResponse<BasePage<QueryProductSaleAreaForSkuRespVO>> skuList(@RequestBody QueryProductSaleAreaForSkuReqVO reqVO) {
//        log.info("ProductSaleAreaController--officialSkuList--入参: [{}]", JSONObject.toJSONString(reqVO));
//        try {
//            return HttpResponse.success(productSaleAreaService.skuList(reqVO));
//        } catch (BizException e) {
//            log.error(e.getMessageId().getMessage());
//            return HttpResponse.failure(e.getMessageId());
//        } catch (Exception ex){
//            ex.printStackTrace();
//            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
//        }
//    }
    @GetMapping("/fuzzysearch")
    @ApiOperation("模糊搜索")
    public HttpResponse<List<ProductSaleAreaFuzzySearchRespVO>> fuzzySearch(@RequestParam String name) {
        log.info("ProductSaleAreaController--fuzzySearch--入参: [{}]", name);
        try {
            return HttpResponse.success(productSaleAreaService.fuzzySearch(name));
        } catch (BizException e) {
            log.error(e.getMessageId().getMessage());
            return HttpResponse.failure(e.getMessageId());
        } catch (Exception ex){
            ex.printStackTrace();
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    @PostMapping("/area/list")
    @ApiOperation("省列表查询")
    public HttpResponse<BasePage<AreaBasic>> areaList(@RequestBody QueryAreaReqVO req) {
        log.info("ProductSaleAreaController--areaList--入参: [{}]", JSONObject.toJSONString(req));
        try {
            return HttpResponse.success(productSaleAreaService.areaList(req));
        } catch (BizException e) {
            log.error(e.getMessageId().getMessage());
            return HttpResponse.failure(e.getMessageId());
        } catch (Exception ex){
            ex.printStackTrace();
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    @PostMapping("/store/list")
    @ApiOperation("门店列表查询")
    public HttpResponse<BasePage<StoreInfo>> storeList(@RequestBody QueryStoreReqVO req) {
        log.info("ProductSaleAreaController--storeList--入参: [{}]", JSONObject.toJSONString(req));
        try {
            return HttpResponse.success(productSaleAreaService.storeList(req));
        } catch (BizException e) {
            log.error(e.getMessageId().getMessage());
            return HttpResponse.failure(e.getMessageId());
        } catch (Exception ex){
            ex.printStackTrace();
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    @GetMapping("/area/sale")
    @ApiOperation("查询商品是否在指定的区域可以销售")
    public HttpResponse skuAreaSale(@RequestParam("sku_code") String skuCode,
                                    @RequestParam("province_code") String provinceCode,
                                    @RequestParam("store_code") String storeCode) {
        return productSaleAreaService.skuAreaSale(skuCode, provinceCode, storeCode);
    }
}
