package com.aiqin.bms.scmp.api.product.web.sku;

import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.aiqin.bms.scmp.api.product.domain.request.SkuPurchaseVolumeVo;
import com.aiqin.bms.scmp.api.product.service.SkuPurchaseVolumeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author knight.xie
 * @className SkuPurchaseVolumeController
 * @date 2019/1/3 14:12
 * @description sku进货量接口发布
 * @version 1.0
 */
@RestController
@Api(description="sku进货量相关api")
@RequestMapping("/sku/purchaseVolume")
public class SkuPurchaseVolumeController {
    @Autowired
    private SkuPurchaseVolumeService skuPurchaseVolumeService;

    @PostMapping("/saveBatch")
    @ApiOperation("批量添加sku进货量")
    public HttpResponse getSkuList(@RequestBody @Validated List<SkuPurchaseVolumeVo> skuPurchaseVolumeVos){
        return skuPurchaseVolumeService.saveBatch(skuPurchaseVolumeVos);
    }

}
