package com.aiqin.bms.scmp.api.product.web.sku;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.common.BizException;
import com.aiqin.bms.scmp.api.constant.Global;
import com.aiqin.bms.scmp.api.product.domain.excel.SkuImportMain;
import com.aiqin.bms.scmp.api.product.domain.excel.SkuImportReq;
import com.aiqin.bms.scmp.api.product.domain.product.apply.ProductApplyInfoRespVO;
import com.aiqin.bms.scmp.api.product.domain.request.changeprice.QuerySkuInfoReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.sku.*;
import com.aiqin.bms.scmp.api.product.domain.response.changeprice.QuerySkuInfoRespVO;
import com.aiqin.bms.scmp.api.product.domain.response.draft.ProductSkuDraftRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.sku.*;
import com.aiqin.bms.scmp.api.product.service.SkuInfoService;
import com.aiqin.ground.util.protocol.MessageId;
import com.aiqin.ground.util.protocol.Project;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
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
            log.error(Global.ERROR, e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR,0);
        }
    }

    @PostMapping("/update")
    @ApiOperation("修改临时表信息")
    public HttpResponse<Integer> updateSkuInfo(@RequestBody AddSkuInfoReqVO addSkuInfoReqVO){
        try {
            return HttpResponse.success(skuInfoService.updateDraftSkuInfo(addSkuInfoReqVO));
        } catch (BizException bz){
            return HttpResponse.failure(bz.getMessageId(),0);
        }catch (Exception e) {
            log.error(Global.ERROR, e);
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

    @PostMapping("/draft/list")
    @ApiOperation("待提交sku列表")
    public HttpResponse<BasePage<ProductSkuDraftRespVo>> getSkuDraftList(@RequestBody QuerySkuDraftListReqVO reqVO){
        return HttpResponse.success(skuInfoService.getProductSkuDraftList(reqVO));
    }

    @PostMapping("/apply/add")
    @ApiOperation("新增sku申请")
    public HttpResponse addSkuApply(@RequestBody SaveSkuApplyInfoReqVO saveSkuApplyInfoReqVO){
        try {
            return HttpResponse.success(skuInfoService.saveSkuApplyInfo(saveSkuApplyInfoReqVO, null, null));
        } catch (Exception e) {
            log.error(Global.ERROR, e);
            return HttpResponse.failure(MessageId.create(Project.PRODUCT_API, 400, e.getMessage()));
        }
    }

    @GetMapping("/apply/detail")
    @ApiOperation("商品申请详情")
    public HttpResponse<ProductApplyInfoRespVO<ProductSkuApplyVo>> getApplyDetail(String applyCode){
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
            log.error(Global.ERROR, e);
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
            log.error(Global.ERROR, e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR,ResultCode.SYSTEM_ERROR.getMessage());
        }
    }

    @PostMapping("/importSkuNew")
    @ApiOperation("新增导入sku")
    public HttpResponse<SkuImportMain> importSkuNew(MultipartFile file){
        log.info("SkuInfoController---importSkuNew---入参：[{}]", JSON.toJSONString(file.getOriginalFilename()));
        try {
            return HttpResponse.success(skuInfoService.importSkuNew(file));
        } catch (BizException e) {
            return HttpResponse.failure(e.getMessageId());
        }catch (Exception e) {
            log.error(Global.ERROR, e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }
    @PostMapping("/importSkuForSupplyPlatform")
    @ApiOperation("新增导入sku")
    public HttpResponse<SkuImportMain> importSkuForSupplyPlatform(MultipartFile file){
        log.info("SkuInfoController---importSkuForSupplyPlatform---入参：[{}]", JSON.toJSONString(file.getOriginalFilename()));
        try {
            return HttpResponse.success(skuInfoService.importSkuForSupplyPlatform(file));
        } catch (BizException e) {
            return HttpResponse.failure(e.getMessageId());
        }catch (Exception e) {
            log.error(Global.ERROR, e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    @PostMapping("/importSkuNewSave")
    @ApiOperation("新增导入sku保存")
    public HttpResponse<Boolean> importSkuNewSave(@RequestBody SkuImportReq reqVO){
        log.info("SkuInfoController---importSkuNewSave---入参：[{}]", JSON.toJSONString(reqVO));
        try {
            return HttpResponse.success(skuInfoService.importSkuNewSave(reqVO));
        } catch (BizException e) {
            return HttpResponse.failure(e.getMessageId());
        }catch (Exception e) {
            log.error(Global.ERROR, e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    @PostMapping("/importSkuUpdateForSupplyPlatform")
    @ApiOperation("申请确认导入")
    public HttpResponse<Boolean> importSkuUpdateForSupplyPlatform(@RequestBody SkuImportReq reqVO){
        log.info("SkuInfoController---importSkuNewSave---入参：[{}]", JSON.toJSONString(reqVO));
        try {
            return HttpResponse.success(skuInfoService.importSkuUpdateForSupplyPlatform(reqVO));
        } catch (BizException e) {
            return HttpResponse.failure(e.getMessageId());
        }catch (Exception e) {
            log.error(Global.ERROR, e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }


    @PostMapping("/importSkuNewUpdate")
    @ApiOperation("修改导入sku保存")
    public HttpResponse<Boolean> importSkuNewUpdate(@RequestBody SkuImportReq reqVO){
        log.info("SkuInfoController---importSkuNewUpdate---入参：[{}]", JSON.toJSONString(reqVO));
        try {
            return HttpResponse.success(skuInfoService.importSkuNewUpdate(reqVO));
        } catch (BizException e) {
            return HttpResponse.failure(e.getMessageId());
        }catch (Exception e) {
            log.error(Global.ERROR, e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    @PostMapping("/importSkuUpdate")
    @ApiOperation("修改导入sku")
    public HttpResponse<SkuImportMain> importSkuUpdate(MultipartFile file){
        log.info("SkuInfoController---importSkuNew---入参：[{}]", JSON.toJSONString(file.getOriginalFilename()));
        try {
            return HttpResponse.success(skuInfoService.importSkuUpdate(file));
        } catch (BizException e) {
            return HttpResponse.failure(e.getMessageId());
        }catch (Exception e) {
            log.error(Global.ERROR, e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    @GetMapping("/exportSku")
    public HttpResponse<Boolean> exportSku(HttpServletResponse resp){
        log.info("SkuInfoController---exportSku---入参：[{}]");
        try {
            return HttpResponse.success(skuInfoService.exportSku(resp));
        } catch (BizException e) {
            return HttpResponse.failure(e.getMessageId());
        }catch (Exception e) {
            log.error(Global.ERROR, e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }
    @GetMapping("/exportAddSku")
    public HttpResponse<Boolean> exportAddSku(HttpServletResponse resp,String code){
        log.info("SkuInfoController---exportSku---入参：[{}]",code);
        try {
            return HttpResponse.success(skuInfoService.exportAddSku(resp,code));
        } catch (BizException e) {
            return HttpResponse.failure(e.getMessageId());
        }catch (Exception e) {
            log.error(Global.ERROR, e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    @GetMapping("/exportEditSku")
    public HttpResponse<Boolean> exportEditSku(HttpServletResponse resp,String code){
        log.info("SkuInfoController---exportSku---入参：[{}]",code);
        try {
            return HttpResponse.success(skuInfoService.exportEditSku(resp,code));
        } catch (BizException e) {
            return HttpResponse.failure(e.getMessageId());
        }catch (Exception e) {
            log.error(Global.ERROR, e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

}
