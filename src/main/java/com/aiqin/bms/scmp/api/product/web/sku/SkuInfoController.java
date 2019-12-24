package com.aiqin.bms.scmp.api.product.web.sku;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.common.BizException;
import com.aiqin.bms.scmp.api.constant.Global;
import com.aiqin.bms.scmp.api.product.domain.excel.SkuImportMain;
import com.aiqin.bms.scmp.api.product.domain.excel.SkuImportReq;
import com.aiqin.bms.scmp.api.product.domain.pojo.NewProduct;
import com.aiqin.bms.scmp.api.product.domain.product.apply.ProductApplyInfoRespVO;
import com.aiqin.bms.scmp.api.product.domain.request.changeprice.QuerySkuInfoReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.newproduct.NewProductUpdateReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.sku.*;
import com.aiqin.bms.scmp.api.product.domain.response.changeprice.QuerySkuInfoRespVO;
import com.aiqin.bms.scmp.api.product.domain.response.draft.ProductSkuDraftRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.sku.*;
import com.aiqin.bms.scmp.api.product.service.NewProductService;
import com.aiqin.bms.scmp.api.product.service.SkuInfoService;
import com.aiqin.bms.scmp.api.util.BeanCopyUtils;
import com.aiqin.bms.scmp.api.util.CollectionUtils;
import com.aiqin.ground.util.exception.GroundRuntimeException;
import com.aiqin.ground.util.protocol.MessageId;
import com.aiqin.ground.util.protocol.Project;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
    @Autowired
    private NewProductService productService;


    @PostMapping("/add")
    @ApiOperation("新增sku信息")
    public HttpResponse<Integer> addSkuInfo(@RequestBody AddSkuInfoReqVO addSkuInfoReqVO){
        try {
            // 修改spu
            NewProduct spuInfo = addSkuInfoReqVO.getSpuInfo();
            NewProductUpdateReqVO newProductUpdateReqVO = new NewProductUpdateReqVO();
            BeanCopyUtils.copy(spuInfo, newProductUpdateReqVO);
            productService.updateProduct(newProductUpdateReqVO);
            return HttpResponse.successGenerics(skuInfoService.saveDraftSkuInfo(addSkuInfoReqVO));
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
            return HttpResponse.successGenerics(skuInfoService.updateDraftSkuInfo(addSkuInfoReqVO));
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
        return HttpResponse.successGenerics(skuInfoService.getSkuDraftInfo(skuCode));
    }

    @GetMapping("/detail")
    @ApiOperation("SKU正式数据详情")
    public HttpResponse<ProductSkuDetailResp> getSkuDetail(String skuCode){
        return HttpResponse.successGenerics(skuInfoService.getSkuDetail(skuCode));
    }

    @PostMapping("/list")
    @ApiOperation("sku管理列表分页查询")
    public HttpResponse<BasePage<QueryProductSkuListResp>> getSkuList(@RequestBody QuerySkuListReqVO querySkuListReqVO){
        return HttpResponse.successGenerics(skuInfoService.querySkuList(querySkuListReqVO));
    }

    @PostMapping("/list/supplyUnitCode")
    @ApiOperation("sku管理列表分页查询")
    public HttpResponse<List<QueryProductSkuListResp>> querySkuListBySupplyUnitCode(String supplyUnitCode){
        return HttpResponse.successGenerics(skuInfoService.querySkuListBySupplyUnitCode(supplyUnitCode));
    }

    @GetMapping("/product/draft")
    @ApiOperation("待提交商品列表")
    public HttpResponse<List<ProductDraftListResp>> getProductDraftList(){
        return HttpResponse.successGenerics(skuInfoService.getProductDraftList());
    }

    @PostMapping("/draft/list")
    @ApiOperation("待提交sku列表")
    public HttpResponse<BasePage<ProductSkuDraftRespVo>> getSkuDraftList(@RequestBody QuerySkuDraftListReqVO reqVO){
        return HttpResponse.successGenerics(skuInfoService.getProductSkuDraftList(reqVO));
    }

    @PostMapping("/draft/submit/check")
    @ApiOperation("提交审批验证,是否为同一修改类型或同一采购组")
    public HttpResponse<List<String>> submitCheck(@RequestBody QuerySkuDraftListReqVO reqVO){
        List<ProductSkuDraftRespVo> list = skuInfoService.getProductSkuDraftListNoPage(reqVO);
        if (CollectionUtils.isEmptyCollection(list)) {
            return HttpResponse.failure(ResultCode.NO_HAVE_INFO_ERROR);
        }
        Byte applyType = list.get(0).getApplyType();
        String groupCode = list.get(0).getPurchaseGroupCode();
        for (ProductSkuDraftRespVo productSkuDraftRespVo : list) {
            if (applyType - productSkuDraftRespVo.getApplyType() != 0) {
                return HttpResponse.failure(ResultCode.SKU_DIFFERENT_APPLY_TYPE);
            }
            if (!StringUtils.equals(groupCode, productSkuDraftRespVo.getPurchaseGroupCode())) {
                return HttpResponse.failure(ResultCode.SKU_DIFFERENT_GRPUP_TYPE);
            }
        }
        List<String> skuCodeList = list.stream().map(ProductSkuDraftRespVo::getCode).collect(Collectors.toList());
        return HttpResponse.successGenerics(skuCodeList);
    }

    @DeleteMapping("/draft/list")
    @ApiOperation("删除所有符合条件待提交sku列表")
    public HttpResponse<Integer> deleteDraft(@RequestBody QuerySkuDraftListReqVO reqVO){
        log.info(reqVO.toString());
        List<ProductSkuDraftRespVo> list = skuInfoService.getProductSkuDraftListNoPage(reqVO);
        List<String> skuList = list.stream().map(ProductSkuDraftRespVo::getCode).collect(Collectors.toList());
        try {
            return HttpResponse.successGenerics(skuInfoService.deleteProductSkuDraft(skuList));
        } catch (BizException bz){
            return HttpResponse.failure(bz.getMessageId(),0);
        }catch (Exception e) {
            log.error(Global.ERROR, e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR,0);
        }
    }

    @PostMapping("/apply/add")
    @ApiOperation("新增sku申请")
    public HttpResponse addSkuApply(@RequestBody SaveSkuApplyInfoReqVO saveSkuApplyInfoReqVO){
        try {
            return HttpResponse.successGenerics(skuInfoService.saveSkuApplyInfo(saveSkuApplyInfoReqVO, null, null));
        } catch (Exception e) {
            log.error(Global.ERROR, e);
            return HttpResponse.failure(MessageId.create(Project.PRODUCT_API, 400, e.getMessage()));
        }
    }

    @GetMapping("/apply/detail")
    @ApiOperation("商品申请详情")
    public HttpResponse<ProductApplyInfoRespVO<ProductSkuApplyVo2>> getApplyDetail(String applyCode){
        return HttpResponse.successGenerics(skuInfoService.getSkuApplyDetail(applyCode));
    }

    @GetMapping("/apply/skuDetail")
    @ApiOperation("单个sku申请详情")
    public HttpResponse<ProductSkuDetailResp> getSkuApplyDetail(String skuCode,String applyCode){
        return HttpResponse.successGenerics(skuInfoService.getProductSkuApplyDetail(skuCode,applyCode));
    }

    @GetMapping("/apply/cancel")
    @ApiOperation("sku申请撤销")
    public HttpResponse<Integer> cancelSkuApply(String applyCode){
        return HttpResponse.successGenerics(skuInfoService.cancelSkuApply(applyCode));
    }

    @PostMapping("/all")
    @ApiOperation("获取所有的SKU根据条件")
    public HttpResponse<List<ProductSkuRespVo>> getProductSkuInfos(@RequestBody QuerySkuReqVO reqVO){
        try {
            return HttpResponse.successGenerics(skuInfoService.getProductSkuInfos(reqVO));
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
            return HttpResponse.successGenerics(skuInfoService.getSkuListByQueryVO(reqVO));
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
            return HttpResponse.successGenerics(skuInfoService.importSkuNew(file));
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
            return HttpResponse.successGenerics(skuInfoService.importSkuForSupplyPlatform(file));
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
            return HttpResponse.successGenerics(skuInfoService.importSkuNewSave(reqVO));
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
            return HttpResponse.successGenerics(skuInfoService.importSkuUpdateForSupplyPlatform(reqVO));
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
            return HttpResponse.successGenerics(skuInfoService.importSkuNewUpdate(reqVO));
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
            return HttpResponse.successGenerics(skuInfoService.importSkuUpdate(file));
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
            return HttpResponse.successGenerics(skuInfoService.exportSku(resp));
        } catch (BizException e) {
            return HttpResponse.failure(e.getMessageId());
        }catch (Exception e) {
            log.error(Global.ERROR, e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    @GetMapping("/exportSkuInfo")
    @ApiOperation("导出审批通过的sku信息,多个skuCode通过逗号隔开")
    public HttpResponse<Boolean> exportSkuBySkuCode(HttpServletResponse resp, String skuCode) {
        List<String> list;
        if (StringUtils.isNotBlank(skuCode)) {
            String[] split = skuCode.split(",");
            list = Lists.newArrayList(split);
        } else {
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        try {
            return HttpResponse.successGenerics(skuInfoService.exportFormalSkuBySkuCode(resp, list));
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
            return HttpResponse.successGenerics(skuInfoService.exportAddSku(resp,code));
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
            return HttpResponse.successGenerics(skuInfoService.exportEditSku(resp,code));
        } catch (BizException e) {
            return HttpResponse.failure(e.getMessageId());
        }catch (Exception e) {
            log.error(Global.ERROR, e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    @PostMapping("/apply/reUpdate")
    @ApiOperation(value = "审批追踪重新编辑")
    public HttpResponse<Integer> reUpdateApply(@RequestParam("applyCode") String applyCode){
        try {
            return HttpResponse.successGenerics( skuInfoService.reUpdateApply(applyCode));
        }  catch (BizException e) {
            return HttpResponse.failure(e.getMessageId());
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

}
