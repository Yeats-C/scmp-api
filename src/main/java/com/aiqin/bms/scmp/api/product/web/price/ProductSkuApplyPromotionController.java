package com.aiqin.bms.scmp.api.product.web.price;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.common.BizException;
import com.aiqin.bms.scmp.api.constant.Global;
import com.aiqin.bms.scmp.api.product.domain.excel.SkuImportMain;
import com.aiqin.bms.scmp.api.product.domain.request.price.PriceApplyPromotionReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.price.PricePromotionProductReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.price.PricePromotionReqVo;
import com.aiqin.bms.scmp.api.product.domain.response.price.PriceApplyPromotionRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.price.PricePromotionProductRespVo;
import com.aiqin.bms.scmp.api.product.service.ProductApplyPromotionService;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@Slf4j
@Api(description = "促销申请管理")
@RequestMapping("/product/apply/promotion")
public class ProductSkuApplyPromotionController {


    @Autowired
    private ProductApplyPromotionService productApplyPromotionService;



    @PostMapping("/loadNamesByPersonId")
    @ApiOperation("获取当前用户的销售组")
    public HttpResponse<List<String>> loadNamesByPersonId() {
        try {
            return HttpResponse.success(productApplyPromotionService.loadNamesByPersonId());
        } catch (BizException e) {
            log.error(e.getMessageId().getMessage());
            return HttpResponse.failure(e.getMessageId());
        } catch (Exception ex) {
            ex.printStackTrace();
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    @PostMapping("/list")
    @ApiOperation("获取申请促销列表")
    public HttpResponse<BasePage<PriceApplyPromotionRespVo>> list(@RequestBody PriceApplyPromotionReqVo priceApplyPromotionReqVo) {
        log.info("ProductSkuApplyPromotionController---save---入参：[{}]", JSON.toJSONString(priceApplyPromotionReqVo));
        try {
            return HttpResponse.success(productApplyPromotionService.list(priceApplyPromotionReqVo));
        } catch (BizException e) {
            log.error(e.getMessageId().getMessage());
            return HttpResponse.failure(e.getMessageId());
        } catch (Exception ex) {
            ex.printStackTrace();
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    @PostMapping("/skuList")
    @ApiOperation("获取申请sku列表")
    public HttpResponse<BasePage<PricePromotionProductRespVo>> skuList(@RequestBody PricePromotionProductReqVo PricePromotionProductReqVo) {
        log.info("ProductSkuApplyPromotionController---save---入参：[{}]", JSON.toJSONString(PricePromotionProductReqVo));
        try {
            return HttpResponse.success(productApplyPromotionService.skuList(PricePromotionProductReqVo));
        } catch (BizException e) {
            log.error(e.getMessageId().getMessage());
            return HttpResponse.failure(e.getMessageId());
        } catch (Exception ex) {
            ex.printStackTrace();
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    @PostMapping("/add")
    @ApiOperation("增加促销")
    public HttpResponse<Boolean> add(@RequestBody PriceApplyPromotionReqVo priceApplyPromotionReqVo) {
        log.info("ProductSkuApplyPromotionController---save---入参：[{}]", JSON.toJSONString(priceApplyPromotionReqVo));
        try {
            return HttpResponse.success(productApplyPromotionService.add(priceApplyPromotionReqVo));
        } catch (BizException e) {
            log.error(e.getMessageId().getMessage());
            return HttpResponse.failure(e.getMessageId());
        } catch (Exception ex) {
            ex.printStackTrace();
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    @PostMapping("/updateOrDelete")
    @ApiOperation("修改促销")
    public HttpResponse<Boolean> updateOrDelete(@RequestBody PriceApplyPromotionReqVo priceApplyPromotionReqVo) {
        log.info("ProductSkuApplyPromotionController---save---入参：[{}]", JSON.toJSONString(priceApplyPromotionReqVo));
        try {
            return HttpResponse.success(productApplyPromotionService.updateOrDelete(priceApplyPromotionReqVo,priceApplyPromotionReqVo.getType()));
        } catch (BizException e) {
            log.error(e.getMessageId().getMessage());
            return HttpResponse.failure(e.getMessageId());
        } catch (Exception ex) {
            ex.printStackTrace();
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    @PostMapping("/load")
    @ApiOperation("获取促销详情")
    public HttpResponse<PriceApplyPromotionRespVo> load(@RequestParam("id") Long id) {
        log.info("ProductSkuApplyPromotionController---save---入参：[{}]", id);
        try {
            return HttpResponse.success(productApplyPromotionService.load(id));
        } catch (BizException e) {
            log.error(e.getMessageId().getMessage());
            return HttpResponse.failure(e.getMessageId());
        } catch (Exception ex) {
            ex.printStackTrace();
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }




    @PostMapping("/importSkuNew")
    @ApiOperation("新增导入sku商品")
    public HttpResponse<List<PricePromotionProductRespVo>> importSkuNew(MultipartFile file) {
        log.info("SkuInfoController---importSkuNew---入参：[{}]", JSON.toJSONString(file.getOriginalFilename()));
        try {
            return HttpResponse.successGenerics(productApplyPromotionService.importSkuNew(file));
        } catch (BizException e) {
            return HttpResponse.failure(e.getMessageId());
        } catch (Exception e) {
            log.error(Global.ERROR, e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

//    @PostMapping("/loadGeneratePromotion")
//    @ApiOperation("查看生成促销单" )
//    public HttpResponse<LoadGeneratePromotionRespVo> loadGeneratePromotion(@RequestBody List<PriceApplyPromotionReqVo> priceApplyPromotionReqVoList) {
//        log.info("ProductSkuApplyPromotionController---save---入参：[{}]", JSON.toJSONString(priceApplyPromotionReqVoList));
//        try {
//            return HttpResponse.success(productApplyPromotionService.loadGeneratePromotion(priceApplyPromotionReqVoList));
//        } catch (BizException e) {
//            log.error(e.getMessageId().getMessage());
//            return HttpResponse.failure(e.getMessageId());
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
//        }
//    }

    @PostMapping("/generatePromotion")
    @ApiOperation("生成促销单")
    public HttpResponse<Boolean> generatePromotion(@RequestBody PricePromotionReqVo pricePromotionReqVo) {
//        log.info("ProductSkuApplyPromotionController---save---入参：[{}]", JSON.toJSONString(pricePromotionReqVo));
        try {
            return HttpResponse.success(productApplyPromotionService.generatePromotion(pricePromotionReqVo));
        } catch (BizException e) {
            log.error(e.getMessageId().getMessage());
            return HttpResponse.failure(e.getMessageId());
        } catch (Exception ex) {
            ex.printStackTrace();
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }
}