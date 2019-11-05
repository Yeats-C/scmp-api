package com.aiqin.bms.scmp.api.product.web.price;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.common.BizException;
import com.aiqin.bms.scmp.api.constant.Global;
import com.aiqin.bms.scmp.api.product.domain.request.changeprice.PriceMeasurementReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.changeprice.ProductSkuChangePriceReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.changeprice.QueryProductSkuChangePriceReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.changeprice.QuerySkuInfoReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.price.PriceApplyPromotionReqVo;
import com.aiqin.bms.scmp.api.product.domain.response.changeprice.*;
import com.aiqin.bms.scmp.api.product.domain.response.price.PriceApplyPromotionRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.price.PromotionRespVo;
import com.aiqin.bms.scmp.api.product.service.ProductApplyPromotionService;
import com.aiqin.bms.scmp.api.product.service.ProductSkuChangePriceService;
import com.aiqin.bms.scmp.api.util.DateUtils;
import com.aiqin.bms.scmp.api.util.IdSequenceUtils;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@Slf4j
@Api(description = "促销申请管理")
@RequestMapping("/product/apply/promotion")
public class ProductSkuPromotionController {
    @Autowired
    private ProductApplyPromotionService productApplyPromotionService;

    @PostMapping("/list")
    @ApiOperation("获取申请促销列表")
    public HttpResponse<BasePage<PriceApplyPromotionReqVo>> list(@RequestBody PriceApplyPromotionReqVo priceApplyPromotionReqVo) {
        log.info("ProductSkuPromotionController---save---入参：[{}]", JSON.toJSONString(priceApplyPromotionReqVo));
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

    @PostMapping("/add")
    @ApiOperation("增加促销")
    public HttpResponse<Boolean> add(@RequestBody PriceApplyPromotionReqVo priceApplyPromotionReqVo) {
        log.info("ProductSkuPromotionController---save---入参：[{}]", JSON.toJSONString(priceApplyPromotionReqVo));
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
    public HttpResponse<Boolean> updateOrDelete(@RequestParam("priceApplyPromotionReqVo") PriceApplyPromotionReqVo priceApplyPromotionReqVo,
                                                @RequestParam("type")Integer type) {
        log.info("ProductSkuPromotionController---save---入参：[{}]", JSON.toJSONString(priceApplyPromotionReqVo));
        try {
            return HttpResponse.success(productApplyPromotionService.updateOrDelete(priceApplyPromotionReqVo,type));
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
    public HttpResponse<PriceApplyPromotionRespVo> load(@Param("id") Long id) {
        log.info("ProductSkuPromotionController---save---入参：[{}]", id);
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


    @PostMapping("/load")
    @ApiOperation("生成促销单")
    public HttpResponse<PromotionRespVo> generatePromotion(@Param("id") Long id) {
        log.info("ProductSkuPromotionController---save---入参：[{}]", id);
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
}