package com.aiqin.bms.scmp.api.product.web.sku;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.common.BizException;
import com.aiqin.bms.scmp.api.product.domain.request.sku.config.QuerySkuConfigReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.sku.config.SaveSkuConfigReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.sku.config.UpdateSkuConfigSupplierReqVo;
import com.aiqin.bms.scmp.api.product.domain.response.sku.config.SkuConfigDetailRepsVo;
import com.aiqin.bms.scmp.api.product.domain.response.sku.config.SkuConfigsRepsVo;
import com.aiqin.bms.scmp.api.product.service.ProductSkuConfigService;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    @PostMapping("/importSave")
    @ApiOperation("保存SKU配置导入信息")
    public HttpResponse<Integer> saveImport(@RequestBody List<SaveSkuConfigReqVo> configReqVos) {
        try {
            return HttpResponse.success(productSkuConfigService.importSaveDraft(configReqVos));
        } catch (BizException e) {
            return HttpResponse.failure(e.getMessageId());
        } catch (Exception e) {
            e.printStackTrace();
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    @PostMapping("/update")
    @ApiOperation("修改SKU配置信息")
    public HttpResponse<Integer> update(@RequestBody UpdateSkuConfigSupplierReqVo reqVo) {
        try {
            return HttpResponse.success(productSkuConfigService.updateDraftList(reqVo));
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
    public HttpResponse<SkuConfigDetailRepsVo> view (@RequestParam("skuCode") String skuCode) {
        try {
            return HttpResponse.success(productSkuConfigService.detail(skuCode));
        } catch (Exception e) {
            e.printStackTrace();
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    @PostMapping("/import")
    @ApiOperation(("sku配置信息导入"))
    public HttpResponse<List<SaveSkuConfigReqVo>> importData (MultipartFile file) {
        try {
            return HttpResponse.success(productSkuConfigService.importData(file));
        } catch (Exception e) {
            e.printStackTrace();
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

}
