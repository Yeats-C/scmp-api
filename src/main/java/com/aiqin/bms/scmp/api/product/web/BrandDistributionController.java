package com.aiqin.bms.scmp.api.product.web;

import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.aiqin.bms.scmp.api.base.PageResData;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.product.domain.ProductBrandDistribution;
import com.aiqin.bms.scmp.api.product.domain.request.brand.ModifyBrandDistributionStatusRequest;
import com.aiqin.bms.scmp.api.product.domain.request.brand.ProductBrandDistributionPageRequest;
import com.aiqin.bms.scmp.api.product.domain.request.brand.QueryBrandDistributionRequest;
import com.aiqin.bms.scmp.api.product.service.BrandDistributionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Createed by sunx on 2018/12/5.<br/>
 */
@RestController
@RequestMapping("/distribution/brand")
@Slf4j
@Api(tags = "分销机构品牌管理模块")
public class BrandDistributionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BrandDistributionController.class);

    @Resource
    private BrandDistributionService brandDistributionService;

    @PostMapping("/page")
    @ApiOperation(value = "a.查询门店品牌分页")
    public HttpResponse<PageResData<ProductBrandDistribution>> brandDistributionPage(@RequestBody ProductBrandDistributionPageRequest request) {
        LOGGER.info("/distribution/brand/page [{}]", request);
        return HttpResponse.success(brandDistributionService.brandDistributionPage(request));
    }

    @GetMapping("/detail")
    @ApiOperation(value = "b.根据分销机构id+品牌id获取明细信息")
    public HttpResponse<ProductBrandDistribution> brandDistributionDetail(@RequestParam("brand_id") String brandId,
                                                                          @RequestParam("distributor_id") String distributorId) {
        LOGGER.info("/distribution/brand/detail [{}],[{}]", brandId, distributorId);
        return HttpResponse.success(brandDistributionService.brandDistributionDetail(brandId, distributorId));
    }

    @PutMapping("/info")
    @ApiOperation(value = "c.品牌编辑", notes = "必传brand_id，distributor_id")
    public HttpResponse updateBrandDistribution(@RequestBody ProductBrandDistribution productBrandDistribution) {
        LOGGER.info("/distribution/brand/info [{}]", productBrandDistribution);
        try {
            brandDistributionService.updateBrandDistribution(productBrandDistribution);
            return HttpResponse.success();
        } catch (Exception ex) {
            LOGGER.error("ex [{}],[{}]", ex, productBrandDistribution);
        }
        return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
    }

    @PutMapping("/info/status")
    @ApiOperation(value = "d.品牌状态编辑-删除-优选-展示")
    public HttpResponse modifyInfoStatus(@RequestBody ModifyBrandDistributionStatusRequest request) {
        LOGGER.info("/distribution/brand/info/status [{}]", request);
        try {
            brandDistributionService.modifyInfoStatus(request);
            return HttpResponse.success();
        } catch (Exception ex) {
            LOGGER.error("ex [{}],[{}]", ex, request);
        }
        return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
    }

    @PostMapping("/list")
    @ApiOperation(value = "e.查询门店品牌列表")
    public HttpResponse<List<ProductBrandDistribution>> queryBrandDistributions(@RequestBody QueryBrandDistributionRequest request) {
        LOGGER.info("/distribution/brand/list [{}]", request);
        return HttpResponse.success(brandDistributionService.queryBrandDistributions(request));
    }
}
