package com.aiqin.bms.scmp.api.product.web.price;

import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.common.*;
import com.aiqin.bms.scmp.api.product.domain.request.changeprice.ProductSkuChangePriceReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.changeprice.QueryProductSkuChangePriceReqVO;
import com.aiqin.bms.scmp.api.product.domain.response.changeprice.ProductSkuChangePriceRespVO;
import com.aiqin.bms.scmp.api.product.domain.response.changeprice.QueryProductSkuChangePriceRespVO;
import com.aiqin.bms.scmp.api.product.service.ProductSkuChangePriceService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@Api(description = "变价api")
@RequestMapping("/product/changePrice")
public class ProductSkuChangePriceController {
    @Autowired
    private ProductSkuChangePriceService productSkuChangePriceService;

    @PostMapping("/save")
    @ApiOperation("保存")
    public HttpResponse<Boolean> save(@RequestBody ProductSkuChangePriceReqVO reqVO) {
        log.info("ProductSkuChangePriceController---save---入参：[{}]", JSON.toJSONString(reqVO));
        try {
            return HttpResponse.success(productSkuChangePriceService.save(reqVO));
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
    public HttpResponse<ProductSkuChangePriceRespVO> view(@RequestParam String code) {
        log.info("ProductSkuChangePriceController---view---入参：[{}]", code);
        try {
            return HttpResponse.success(productSkuChangePriceService.view(code));
        } catch (BizException e) {
            log.error(e.getMessageId().getMessage());
            return HttpResponse.failure(e.getMessageId());
        } catch (Exception ex) {
            ex.printStackTrace();
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    @GetMapping("/editView")
    @ApiOperation("编辑时获取数据")
    public HttpResponse<ProductSkuChangePriceRespVO> editView(@RequestParam String code) {
        log.info("ProductSkuChangePriceController---editView---入参：[{}]", code);
        try {
            return HttpResponse.success(productSkuChangePriceService.editView(code));
        } catch (BizException e) {
            log.error(e.getMessageId().getMessage());
            return HttpResponse.failure(e.getMessageId());
        } catch (Exception ex) {
            ex.printStackTrace();
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }
    @PostMapping("/update")
    @ApiOperation("更新")
    public HttpResponse<ProductSkuChangePriceRespVO> update(@RequestBody ProductSkuChangePriceReqVO reqVO) {
        log.info("ProductSkuChangePriceController---update---入参：[{}]", JSONObject.toJSONString(reqVO));
        try {
            return HttpResponse.success(productSkuChangePriceService.update(reqVO));
        } catch (BizException e) {
            log.error(e.getMessageId().getMessage());
            return HttpResponse.failure(e.getMessageId());
        } catch (Exception ex) {
            ex.printStackTrace();
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    @PostMapping("/list")
    @ApiOperation("列表")
    public HttpResponse<BasePage<QueryProductSkuChangePriceRespVO>> list(@RequestBody QueryProductSkuChangePriceReqVO reqVO) {
        log.info("ProductSkuChangePriceController---list---入参：[{}]", JSONObject.toJSONString(reqVO));
        try {
            return HttpResponse.success(productSkuChangePriceService.list(reqVO));
        } catch (BizException e) {
            log.error(e.getMessageId().getMessage());
            return HttpResponse.failure(e.getMessageId());
        } catch (Exception ex) {
            ex.printStackTrace();
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    @GetMapping("/cancel")
    @ApiOperation("撤销")
    public HttpResponse<Boolean> cancel(@RequestParam String code) {
        log.info("ProductSkuChangePriceController---cancel---入参：[{}]", code);
        try {
            return HttpResponse.success(productSkuChangePriceService.cancelData(code));
        } catch (BizException e) {
            log.error(e.getMessageId().getMessage());
            return HttpResponse.failure(e.getMessageId());
        } catch (Exception ex) {
            ex.printStackTrace();
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }
}