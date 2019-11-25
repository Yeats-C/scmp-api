package com.aiqin.bms.scmp.api.product.web.price;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.common.BizException;
import com.aiqin.bms.scmp.api.product.domain.request.price.PricePromotionReqVo;
import com.aiqin.bms.scmp.api.product.domain.response.price.PricePromotionRespVo;
import com.aiqin.bms.scmp.api.product.service.ProductPromotionService;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@Api(description = "促销管理")
@RequestMapping("/product/promotion")
public class ProductSkuPromotionController {
    @Autowired
    private ProductPromotionService productPromotionService;

    @PostMapping("/list")
    @ApiOperation("获取促销列表")
    public HttpResponse<BasePage<PricePromotionRespVo>> list(@RequestBody PricePromotionReqVo priceApplyPromotionReqVo) {
        log.info("ProductSkuPromotionController---save---入参：[{}]", JSON.toJSONString(priceApplyPromotionReqVo));
        try {
            return HttpResponse.success(productPromotionService.list(priceApplyPromotionReqVo));
        } catch (BizException e) {
            log.error(e.getMessageId().getMessage());
            return HttpResponse.failure(e.getMessageId());
        } catch (Exception ex) {
            ex.printStackTrace();
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    @PostMapping("/detail")
    @ApiOperation("促销单详情")
    public HttpResponse<PricePromotionRespVo> detail(@RequestParam("id") Long id) {
        log.info("ProductSkuPromotionController---save---入参：[{}]", JSON.toJSONString(id));
        try {
            return HttpResponse.success(productPromotionService.detail(id));
        } catch (BizException e) {
            log.error(e.getMessageId().getMessage());
            return HttpResponse.failure(e.getMessageId());
        } catch (Exception ex) {
            ex.printStackTrace();
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    @PostMapping("/delete")
    @ApiOperation("取消促销单")
    public HttpResponse<Boolean> delete(@RequestParam("id") Long id) {
        log.info("ProductSkuPromotionController---save---入参：[{}]", JSON.toJSONString(id));
        try {
            return HttpResponse.success(productPromotionService.delete(id));
        } catch (BizException e) {
            log.error(e.getMessageId().getMessage());
            return HttpResponse.failure(e.getMessageId());
        } catch (Exception ex) {
            ex.printStackTrace();
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

}