package com.aiqin.bms.scmp.api.product.web.price;

import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.common.*;
import com.aiqin.bms.scmp.api.product.domain.request.price.QueryProductSkuPriceInfoReqVO;
import com.aiqin.bms.scmp.api.product.domain.response.price.ProductSkuPriceInfoRespVO;
import com.aiqin.bms.scmp.api.product.domain.response.price.QueryProductSkuPriceInfoRespVO;
import com.aiqin.bms.scmp.api.product.service.ProductSkuPriceInfoService;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-05-30
 * @time: 10:22
 */
@RestController
@RequestMapping("/product/price")
@Slf4j
@Api(description = "价格信息api")
public class ProductSkuPriceInfoController {
    @Autowired
    private ProductSkuPriceInfoService productSkuPriceInfoService;

    @PostMapping("/list")
    @ApiOperation("列表")
    public HttpResponse<BasePage<QueryProductSkuPriceInfoRespVO>> list(@RequestBody QueryProductSkuPriceInfoReqVO reqVO) {
        log.info("ProductSkuPriceInfoController---list---入参：[{}]", JSONObject.toJSONString(reqVO));
        try {
            return HttpResponse.success(productSkuPriceInfoService.list(reqVO));
        } catch (BizException e) {
            log.error(e.getMessageId().getMessage());
            return HttpResponse.failure(e.getMessageId());
        } catch (Exception ex) {
            ex.printStackTrace();
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    @GetMapping("/view")
    @ApiOperation("查看")
    public HttpResponse<ProductSkuPriceInfoRespVO> view(@RequestParam String code) {
        log.info("ProductSkuPriceInfoController---view---入参：[{}]", code);
        try {
            return HttpResponse.success(productSkuPriceInfoService.view(code));
        } catch (BizException e) {
            log.error(e.getMessageId().getMessage());
            return HttpResponse.failure(e.getMessageId());
        } catch (Exception ex) {
            ex.printStackTrace();
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }
}
