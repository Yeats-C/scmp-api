package com.aiqin.bms.scmp.api.product.web.sku;

import com.aiqin.ground.util.protocol.MessageId;
import com.aiqin.ground.util.protocol.Project;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.common.*;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuDraft;
import com.aiqin.bms.scmp.api.product.domain.request.changeprice.QuerySkuInfoReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.sku.AddSkuInfoReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.sku.QuerySkuListReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.sku.QuerySkuReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.sku.SaveSkuApplyInfoReqVO;
import com.aiqin.bms.scmp.api.product.domain.response.changeprice.QuerySkuInfoRespVO;
import com.aiqin.bms.scmp.api.product.domain.response.sku.*;
import com.aiqin.bms.scmp.api.product.service.SkuInfoService;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @功能说明:
 * @author: wangxu
 * @date: 2019/1/28 0028 10:44
 */
@RestController
@Api(description="sku功能相关api")
@RequestMapping("/sku/info")
@Slf4j
public class SkuInfoController {
    @Autowired
    SkuInfoService skuInfoService;

    @PostMapping("/add")
    @ApiOperation("新增sku信息")
    public HttpResponse<Integer> addSkuInfo(@RequestBody AddSkuInfoReqVO addSkuInfoReqVO){
        try {
            return HttpResponse.success(skuInfoService.saveDraftSkuInfo(addSkuInfoReqVO));
        } catch (BizException bz){
            return HttpResponse.failure(bz.getMessageId(),0);
        }catch (Exception e) {
            e.printStackTrace();
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR,0);
        }
    }

    @GetMapping("/draft/detail")
    @ApiOperation("sku草稿详情")
    public HttpResponse<ProductSkuDetailResp> getSkuDraft(String skuCode){
        return HttpResponse.success(skuInfoService.getSkuDraftInfo(skuCode));
    }

    @GetMapping("/detail")
    @ApiOperation("SKU正式数据详情")
    public HttpResponse<ProductSkuDetailResp> getSkuDetail(String skuCode){
        return HttpResponse.success(skuInfoService.getSkuDetail(skuCode));
    }

    @PostMapping("/list")
    @ApiOperation("sku管理列表分页查询")
    public HttpResponse<BasePage<QueryProductSkuListResp>> getSkuList(@RequestBody QuerySkuListReqVO querySkuListReqVO){
        return HttpResponse.success(skuInfoService.querySkuList(querySkuListReqVO));
    }

    @PostMapping("/list/supplyUnitCode")
    @ApiOperation("sku管理列表分页查询")
    public HttpResponse<List<QueryProductSkuListResp>> querySkuListBySupplyUnitCode(String supplyUnitCode){
        return HttpResponse.success(skuInfoService.querySkuListBySupplyUnitCode(supplyUnitCode));
    }

    @GetMapping("/product/draft")
    @ApiOperation("待提交商品列表")
    public HttpResponse<List<ProductDraftListResp>> getProductDraftList(){
        return HttpResponse.success(skuInfoService.getProductDraftList());
    }

    @GetMapping("/draft/list")
    @ApiOperation("待提交sku列表")
    public HttpResponse<List<ProductSkuDraft>> getSkuDraftList(){
        return HttpResponse.success(skuInfoService.getProductSkuDraftList());
    }

    @PostMapping("/apply/add")
    @ApiOperation("新增sku申请")
    public HttpResponse addSkuApply(@RequestBody SaveSkuApplyInfoReqVO saveSkuApplyInfoReqVO){
        try {
            return HttpResponse.success(skuInfoService.saveSkuApplyInfo(saveSkuApplyInfoReqVO));
        } catch (Exception e) {
            e.printStackTrace();
            return HttpResponse.failure(MessageId.create(Project.PRODUCT_API, 400, e.getMessage()));
        }
    }

    @GetMapping("/apply/detail")
    @ApiOperation("商品申请详情")
    public HttpResponse<ApplySkuDetailResp> getApplyDetail(String applyCode){
        return HttpResponse.success(skuInfoService.getSkuApplyDetail(applyCode));
    }

    @GetMapping("/apply/skuDetail")
    @ApiOperation("单个sku申请详情")
    public HttpResponse<ApplyProductSkuDetailResp> getSkuApplyDetail(String skuCode,String applyCode){
        return HttpResponse.success(skuInfoService.getProductSkuApplyDetail(skuCode,applyCode));
    }

    @GetMapping("/apply/cancel")
    @ApiOperation("sku申请撤销")
    public HttpResponse<Integer> cancelSkuApply(String applyCode){
        return HttpResponse.success(skuInfoService.cancelSkuApply(applyCode));
    }

    @PostMapping("/all")
    @ApiOperation("获取所有的SKU根据条件")
    public HttpResponse<List<ProductSkuRespVo>> getProductSkuInfos(@RequestBody QuerySkuReqVO reqVO){
        try {
            return HttpResponse.success(skuInfoService.getProductSkuInfos(reqVO));
        } catch (Exception e) {
            e.printStackTrace();
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR,ResultCode.SYSTEM_ERROR.getMessage());
        }
    }

    @PostMapping("/querySkuList")
    @ApiOperation("根据条件查询sku列表")
    public HttpResponse<BasePage<QuerySkuInfoRespVO>> querySkuList(@RequestBody @Valid QuerySkuInfoReqVO reqVO){
        log.info("SkuInfoController---querySkuList---入参：[{}]", JSON.toJSONString(reqVO));
        try {
            return HttpResponse.success(skuInfoService.getSkuListByQueryVO(reqVO));
        } catch (Exception e) {
            e.printStackTrace();
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR,ResultCode.SYSTEM_ERROR.getMessage());
        }
    }
}
