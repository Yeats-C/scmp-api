package com.aiqin.bms.scmp.api.product.web;

import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.aiqin.bms.scmp.api.base.PageResData;
import com.aiqin.bms.scmp.api.product.domain.ProductDistributor;
import com.aiqin.bms.scmp.api.product.domain.request.ProductSearchRequest;
import com.aiqin.bms.scmp.api.product.domain.request.UnsoldDistributorProductRequest;
import com.aiqin.bms.scmp.api.product.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Createed by sunx on 2018/11/19.<br/>
 */
@RestController
@RequestMapping("search/product")
@Api("商品查询相关")
@Slf4j
public class ProductSearchController {

    @Resource
    private ProductService productService;

    @PostMapping("/unsold")
    @ApiOperation("滞销商品")
    public HttpResponse<PageResData<ProductDistributor>> unsoldProductPage(@RequestBody UnsoldDistributorProductRequest request) {
        log.info("search/product/unsold [{}]", request);
        return HttpResponse.success(productService.unsoldProductPage(request));
    }
}
