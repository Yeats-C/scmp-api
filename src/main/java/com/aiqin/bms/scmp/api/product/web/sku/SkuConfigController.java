package com.aiqin.bms.scmp.api.product.web.sku;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.common.BizException;
import com.aiqin.bms.scmp.api.product.domain.request.sku.config.QuerySkuConfigReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.sku.config.SaveSkuConfigReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.sku.config.UpdateSkuConfigReqVo;
import com.aiqin.bms.scmp.api.product.domain.response.sku.config.SkuConfigsRepsVo;
import com.aiqin.bms.scmp.api.product.service.ProductSkuConfigService;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @功能说明:
 * @author: wangxu
 * @date: 2019/1/18 0018 15:44
 */
@RestController
@Api(description = "sku配置相关api")
@RequestMapping("/product/sku/config")
@Slf4j
public class SkuConfigController {

    @Autowired
    private ProductSkuConfigService productSkuConfigService;

    @PostMapping("/save")
    @ApiOperation("保存SKU配置信息")
    public HttpResponse<Integer> save(@RequestBody List<SaveSkuConfigReqVo> configReqVos) {
        try {
            return HttpResponse.success(productSkuConfigService.insertDraftList(configReqVos));
        } catch (BizException e) {
            return HttpResponse.failure(e.getMessageId());
        } catch (Exception e) {
            e.printStackTrace();
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    @PostMapping("/update")
    @ApiOperation("修改SKU配置信息")
    public HttpResponse<Integer> update(@RequestBody List<UpdateSkuConfigReqVo> configReqVos) {
        try {
            return HttpResponse.success(productSkuConfigService.updateDraftList(configReqVos));
        } catch (BizException e) {
            return HttpResponse.failure(e.getMessageId());
        } catch (Exception e) {
            e.printStackTrace();
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    @PostMapping("/list")
    @ApiOperation("SKU配置信息列表")
    public HttpResponse<BasePage<SkuConfigsRepsVo>> list(@RequestBody QuerySkuConfigReqVo reqVo) {
        try {
            return HttpResponse.success(productSkuConfigService.findList(reqVo));
        } catch (BizException e) {
            return HttpResponse.failure(e.getMessageId());
        } catch (Exception e) {
            e.printStackTrace();
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    @GetMapping("/view")
    @ApiOperation(("sku配置信息详情"))
    public HttpResponse view (@RequestParam("skuCode") String skuCode) {
        try {
            return HttpResponse.success(productSkuConfigService.detail(skuCode));
        } catch (BizException e) {
            return HttpResponse.failure(e.getMessageId());
        } catch (Exception e) {
            e.printStackTrace();
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }


}
