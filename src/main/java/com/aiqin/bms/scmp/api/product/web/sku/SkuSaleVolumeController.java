package com.aiqin.bms.scmp.api.product.web.sku;

import com.aiqin.bms.scmp.api.product.domain.request.SkuSaleVolumeVo;
import com.aiqin.bms.scmp.api.product.service.SkuSaleVolumeService;
import com.aiqin.ground.util.protocol.http.HttpResponse;
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
 * @className SkuSaleVoolumeController
 * @date 2019/1/3 14:12
 * @description sku销量接口发布
 * @version 1.0
 */
@RestController
@Api(description="sku销量相关api")
@RequestMapping("/sku/saleVolume")
public class SkuSaleVolumeController {
    @Autowired
    private SkuSaleVolumeService skuSaleVolumeService;

    @PostMapping("/saveBatch")
    @ApiOperation("批量添加sku销量")
    public HttpResponse getSkuList(@RequestBody @Validated List<SkuSaleVolumeVo> skuSaleVolumeVos){
        return skuSaleVolumeService.saveBatch(skuSaleVolumeVos);
    }

}
